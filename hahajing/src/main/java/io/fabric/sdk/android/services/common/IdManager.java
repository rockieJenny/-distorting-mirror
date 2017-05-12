package io.fabric.sdk.android.services.common;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.google.android.gms.games.quest.Quests;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import java.security.GeneralSecurityException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import org.json.JSONObject;

public class IdManager {
    public static final String APPLICATION_INSTALL_ID_FIELD = "APPLICATION_INSTALLATION_UUID";
    private static final String BAD_ANDROID_ID = "9774d56d682e549c";
    public static final String BETA_DEVICE_TOKEN_FIELD = "font_token";
    private static final String BLUETOOTH_ERROR_MESSAGE = "Utils#getBluetoothMacAddress failed, returning null. Requires prior call to BluetoothAdatpter.getDefaultAdapter() on thread that has called Looper.prepare()";
    public static final String COLLECT_DEVICE_IDENTIFIERS = "com.crashlytics.CollectDeviceIdentifiers";
    public static final String COLLECT_USER_IDENTIFIERS = "com.crashlytics.CollectUserIdentifiers";
    public static final String DEFAULT_VERSION_NAME = "0.0";
    private static final String FORWARD_SLASH_REGEX = Pattern.quote("/");
    private static final Pattern ID_PATTERN = Pattern.compile("[^\\p{Alnum}]");
    public static final String MODEL_FIELD = "model";
    public static final String OS_VERSION_FIELD = "os_version";
    private static final String PREFKEY_INSTALLATION_UUID = "crashlytics.installation.id";
    private static final String SDK_ASSETS_ROOT = ".TwitterSdk";
    private final Context appContext;
    private final String appIdentifier;
    private final String appInstallIdentifier;
    private final boolean collectHardwareIds;
    private final boolean collectUserIds;
    private final ReentrantLock installationIdLock = new ReentrantLock();
    private final InstallerPackageNameProvider installerPackageNameProvider;
    private final Collection<Kit> kits;

    public enum DeviceIdentifierType {
        WIFI_MAC_ADDRESS(1),
        BLUETOOTH_MAC_ADDRESS(2),
        FONT_TOKEN(53),
        ANDROID_ID(100),
        ANDROID_DEVICE_ID(101),
        ANDROID_SERIAL(102),
        ANDROID_ADVERTISING_ID(Quests.SELECT_RECENTLY_FAILED);
        
        public final int protobufIndex;

        private DeviceIdentifierType(int pbufIndex) {
            this.protobufIndex = pbufIndex;
        }
    }

    public IdManager(Context appContext, String appIdentifier, String appInstallIdentifier, Collection<Kit> kits) {
        if (appContext == null) {
            throw new IllegalArgumentException("appContext must not be null");
        } else if (appIdentifier == null) {
            throw new IllegalArgumentException("appIdentifier must not be null");
        } else if (kits == null) {
            throw new IllegalArgumentException("kits must not be null");
        } else {
            this.appContext = appContext;
            this.appIdentifier = appIdentifier;
            this.appInstallIdentifier = appInstallIdentifier;
            this.kits = kits;
            this.installerPackageNameProvider = new InstallerPackageNameProvider();
            this.collectHardwareIds = CommonUtils.getBooleanResourceValue(appContext, COLLECT_DEVICE_IDENTIFIERS, true);
            if (!this.collectHardwareIds) {
                Fabric.getLogger().d("Fabric", "Device ID collection disabled for " + appContext.getPackageName());
            }
            this.collectUserIds = CommonUtils.getBooleanResourceValue(appContext, COLLECT_USER_IDENTIFIERS, true);
            if (!this.collectUserIds) {
                Fabric.getLogger().d("Fabric", "User information collection disabled for " + appContext.getPackageName());
            }
        }
    }

    public String createIdHeaderValue(String apiKey, String packageName) {
        try {
            Cipher cipher = CommonUtils.createCipher(1, CommonUtils.sha1(apiKey + packageName.replaceAll("\\.", new StringBuilder(new String(new char[]{'s', 'l', 'c'})).reverse().toString())));
            JSONObject ids = new JSONObject();
            addAppInstallIdTo(ids);
            addDeviceIdentifiersTo(ids);
            addOsVersionTo(ids);
            addModelName(ids);
            String toReturn = "";
            if (ids.length() <= 0) {
                return toReturn;
            }
            try {
                return CommonUtils.hexify(cipher.doFinal(ids.toString().getBytes()));
            } catch (GeneralSecurityException e) {
                Fabric.getLogger().e("Fabric", "Could not encrypt IDs", e);
                return toReturn;
            }
        } catch (GeneralSecurityException e2) {
            Fabric.getLogger().e("Fabric", "Could not create cipher to encrypt headers.", e2);
            return "";
        }
    }

    private void addAppInstallIdTo(JSONObject ids) {
        try {
            ids.put(APPLICATION_INSTALL_ID_FIELD.toLowerCase(Locale.US), getAppInstallIdentifier());
        } catch (Exception e) {
            Fabric.getLogger().e("Fabric", "Could not write application id to JSON", e);
        }
    }

    private void addDeviceIdentifiersTo(JSONObject ids) {
        for (Entry<DeviceIdentifierType, String> id : getDeviceIdentifiers().entrySet()) {
            try {
                ids.put(((DeviceIdentifierType) id.getKey()).name().toLowerCase(Locale.US), id.getValue());
            } catch (Exception e) {
                Fabric.getLogger().e("Fabric", "Could not write value to JSON: " + ((DeviceIdentifierType) id.getKey()).name(), e);
            }
        }
    }

    private void addOsVersionTo(JSONObject ids) {
        try {
            ids.put(OS_VERSION_FIELD, getOsVersionString());
        } catch (Exception e) {
            Fabric.getLogger().e("Fabric", "Could not write OS version to JSON", e);
        }
    }

    private void addModelName(JSONObject ids) {
        try {
            ids.put(MODEL_FIELD, getModelName());
        } catch (Exception e) {
            Fabric.getLogger().e("Fabric", "Could not write model to JSON", e);
        }
    }

    public boolean canCollectUserIds() {
        return this.collectUserIds;
    }

    private boolean hasPermission(String permission) {
        return this.appContext.checkCallingPermission(permission) == 0;
    }

    private String formatId(String id) {
        return id == null ? null : ID_PATTERN.matcher(id).replaceAll("").toLowerCase(Locale.US);
    }

    public String getAppInstallIdentifier() {
        String appInstallId = this.appInstallIdentifier;
        if (appInstallId != null) {
            return appInstallId;
        }
        SharedPreferences prefs = CommonUtils.getSharedPrefs(this.appContext);
        appInstallId = prefs.getString(PREFKEY_INSTALLATION_UUID, null);
        if (appInstallId == null) {
            return createInstallationUUID(prefs);
        }
        return appInstallId;
    }

    public String getAppIdentifier() {
        return this.appIdentifier;
    }

    public String getOsVersionString() {
        return String.format(Locale.US, "%s/%s", new Object[]{removeForwardSlashesIn(VERSION.RELEASE), removeForwardSlashesIn(VERSION.INCREMENTAL)});
    }

    public String getModelName() {
        return String.format(Locale.US, "%s/%s", new Object[]{removeForwardSlashesIn(Build.MANUFACTURER), removeForwardSlashesIn(Build.MODEL)});
    }

    private String removeForwardSlashesIn(String s) {
        return s.replaceAll(FORWARD_SLASH_REGEX, "");
    }

    public String getDeviceUUID() {
        String toReturn = "";
        if (!this.collectHardwareIds) {
            return toReturn;
        }
        toReturn = getAndroidId();
        if (toReturn != null) {
            return toReturn;
        }
        SharedPreferences prefs = CommonUtils.getSharedPrefs(this.appContext);
        toReturn = prefs.getString(PREFKEY_INSTALLATION_UUID, null);
        if (toReturn == null) {
            return createInstallationUUID(prefs);
        }
        return toReturn;
    }

    private String createInstallationUUID(SharedPreferences prefs) {
        this.installationIdLock.lock();
        try {
            String uuid = prefs.getString(PREFKEY_INSTALLATION_UUID, null);
            if (uuid == null) {
                uuid = formatId(UUID.randomUUID().toString());
                prefs.edit().putString(PREFKEY_INSTALLATION_UUID, uuid).commit();
            }
            this.installationIdLock.unlock();
            return uuid;
        } catch (Throwable th) {
            this.installationIdLock.unlock();
        }
    }

    public Map<DeviceIdentifierType, String> getDeviceIdentifiers() {
        Map<DeviceIdentifierType, String> ids = new HashMap();
        for (Kit kit : this.kits) {
            if (kit instanceof DeviceIdentifierProvider) {
                for (Entry<DeviceIdentifierType, String> entry : ((DeviceIdentifierProvider) kit).getDeviceIdentifiers().entrySet()) {
                    putNonNullIdInto(ids, (DeviceIdentifierType) entry.getKey(), (String) entry.getValue());
                }
            }
        }
        putNonNullIdInto(ids, DeviceIdentifierType.ANDROID_ID, getAndroidId());
        putNonNullIdInto(ids, DeviceIdentifierType.ANDROID_DEVICE_ID, getTelephonyId());
        putNonNullIdInto(ids, DeviceIdentifierType.ANDROID_SERIAL, getSerialNumber());
        putNonNullIdInto(ids, DeviceIdentifierType.WIFI_MAC_ADDRESS, getWifiMacAddress());
        putNonNullIdInto(ids, DeviceIdentifierType.BLUETOOTH_MAC_ADDRESS, getBluetoothMacAddress());
        putNonNullIdInto(ids, DeviceIdentifierType.ANDROID_ADVERTISING_ID, getAdvertisingId());
        return Collections.unmodifiableMap(ids);
    }

    public String getInstallerPackageName() {
        return this.installerPackageNameProvider.getInstallerPackageName(this.appContext);
    }

    public String getAdvertisingId() {
        if (!this.collectHardwareIds) {
            return null;
        }
        AdvertisingInfo advertisingInfo = new AdvertisingInfoProvider(this.appContext).getAdvertisingInfo();
        if (advertisingInfo != null) {
            return advertisingInfo.advertisingId;
        }
        return null;
    }

    private void putNonNullIdInto(Map<DeviceIdentifierType, String> idMap, DeviceIdentifierType idKey, String idValue) {
        if (idValue != null) {
            idMap.put(idKey, idValue);
        }
    }

    public String getAndroidId() {
        if (!this.collectHardwareIds) {
            return null;
        }
        String androidId = Secure.getString(this.appContext.getContentResolver(), "android_id");
        if (BAD_ANDROID_ID.equals(androidId)) {
            return null;
        }
        return formatId(androidId);
    }

    private String[] getTwitterSdkAssetsList() {
        return new String[0];
    }

    public String getTelephonyId() {
        if (this.collectHardwareIds && hasPermission("android.permission.READ_PHONE_STATE")) {
            TelephonyManager tm = (TelephonyManager) this.appContext.getSystemService("phone");
            if (tm != null) {
                return formatId(tm.getDeviceId());
            }
        }
        return null;
    }

    public String getWifiMacAddress() {
        if (this.collectHardwareIds && hasPermission("android.permission.ACCESS_WIFI_STATE")) {
            WifiManager wifiMgr = (WifiManager) this.appContext.getSystemService("wifi");
            if (wifiMgr != null) {
                WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                if (wifiInfo != null) {
                    return formatId(wifiInfo.getMacAddress());
                }
            }
        }
        return null;
    }

    public String getBluetoothMacAddress() {
        if (this.collectHardwareIds && hasPermission("android.permission.BLUETOOTH")) {
            try {
                BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
                if (bt != null) {
                    formatId(bt.getAddress());
                }
            } catch (Exception e) {
                Fabric.getLogger().e("Fabric", BLUETOOTH_ERROR_MESSAGE, e);
            }
        }
        return null;
    }

    public String getSerialNumber() {
        if (this.collectHardwareIds && VERSION.SDK_INT >= 9) {
            try {
                return formatId((String) Build.class.getField("SERIAL").get(null));
            } catch (Exception e) {
                Fabric.getLogger().e("Fabric", "Could not retrieve android.os.Build.SERIAL value", e);
            }
        }
        return null;
    }
}
