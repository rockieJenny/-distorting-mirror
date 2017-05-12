package com.inmobi.commons.internal;

import android.content.Context;
import android.telephony.NeighboringCellInfo;
import com.inmobi.commons.analytics.bootstrapper.AnalyticsInitializer;
import com.inmobi.commons.analytics.bootstrapper.ThinICEConfig;
import com.inmobi.commons.analytics.db.AnalyticsEvent;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import com.inmobi.commons.data.DemogInfo;
import com.inmobi.commons.data.LocationInfo;
import com.inmobi.commons.internal.ActivityRecognitionSampler.ActivitySample;
import com.inmobi.commons.thinICE.cellular.CellOperatorInfo;
import com.inmobi.commons.thinICE.cellular.CellTowerInfo;
import com.inmobi.commons.thinICE.cellular.CellUtil;
import com.inmobi.commons.thinICE.icedatacollector.Sample;
import com.inmobi.commons.thinICE.icedatacollector.ThinICEConfigSettings;
import com.inmobi.commons.thinICE.wifi.WifiInfo;
import com.inmobi.commons.uid.UID;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.util.Calendar;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONPayloadCreator implements PayloadCreator {
    public String toPayloadString(List<Sample> list, List<ActivitySample> list2, Context context) {
        JSONObject a;
        JSONObject jSONObject = new JSONObject(InternalSDKUtil.getEncodedMap(UID.getInstance().getMapForEncryption(AnalyticsInitializer.getConfigParams().getDeviceIdMaskMap())));
        JSONArray jSONArray = new JSONArray();
        ThinICEConfig thinIceConfig = AnalyticsInitializer.getConfigParams().getThinIceConfig();
        for (Sample sample : list) {
            if (sample != null) {
                a = a(sample, thinIceConfig);
                if (a != null) {
                    jSONArray.put(a);
                }
            }
        }
        for (ActivitySample activitySample : list2) {
            if (activitySample != null) {
                a = a(activitySample);
                if (a != null) {
                    jSONArray.put(a);
                }
            }
        }
        try {
            int i;
            if (jSONArray.length() > 0) {
                jSONObject.put("payload", jSONArray);
            }
            CellOperatorInfo cellNetworkInfo = CellUtil.getCellNetworkInfo(context);
            if (thinIceConfig.isOperatorEnabled()) {
                if (!(ThinICEConfigSettings.bitTest(thinIceConfig.getCellOpsFlag(), 1) || (cellNetworkInfo.simMcc == -1 && cellNetworkInfo.simMnc == -1))) {
                    jSONObject.put("s-ho", cellNetworkInfo.simMcc + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + cellNetworkInfo.simMnc);
                }
                if (!(ThinICEConfigSettings.bitTest(thinIceConfig.getCellOpsFlag(), 2) || (cellNetworkInfo.currentMcc == -1 && cellNetworkInfo.currentMnc == -1))) {
                    jSONObject.put("s-co", cellNetworkInfo.currentMcc + EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR + cellNetworkInfo.currentMnc);
                }
            }
            Calendar instance = Calendar.getInstance();
            System.currentTimeMillis();
            jSONObject.put("tz", instance.get(15) + instance.get(16));
            jSONObject.put("ts", instance.getTimeInMillis());
            jSONObject.put(AdTrackerConstants.SDKVER, "pr-SAND-" + InternalSDKUtil.getInMobiInternalVersion(InternalSDKUtil.INMOBI_SDK_RELEASE_VERSION) + "-" + InternalSDKUtil.INMOBI_SDK_RELEASE_DATE);
            String currentLocationStr = LocationInfo.currentLocationStr();
            if (!(currentLocationStr == null || "".equals(currentLocationStr))) {
                jSONObject.put("u-latlong-accu", currentLocationStr);
                if (LocationInfo.isValidGeoInfo()) {
                    long geoTS = LocationInfo.getGeoTS();
                    if (geoTS != 0) {
                        jSONObject.put("u-ll-ts", geoTS);
                    }
                }
            }
            if (!(InternalSDKUtil.getLtvpSessionId() == null || InternalSDKUtil.getLtvpSessionId().equals(""))) {
                jSONObject.put("u-s-id", InternalSDKUtil.getLtvpSessionId());
            }
            if (DemogInfo.isLocationInquiryAllowed()) {
                i = 1;
            } else {
                i = 0;
            }
            jSONObject.put("loc-allowed", i);
            jSONObject.put("sdk-collected", LocationInfo.isSDKSetLocation());
            return jSONObject.toString();
        } catch (JSONException e) {
            Log.internal(InternalSDKUtil.LOGGING_TAG, "Unable to create payload for sending ThinICE params");
            return null;
        }
    }

    private JSONObject a(ActivitySample activitySample) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("t", 3);
            jSONObject.put("ts", activitySample.getTimestamp());
            jSONObject.put("a", activitySample.getActivity());
            return jSONObject;
        } catch (Exception e) {
            return null;
        }
    }

    private JSONObject a(Sample sample, ThinICEConfig thinICEConfig) {
        try {
            JSONArray jSONArray;
            JSONObject a;
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("t", 1);
            if (thinICEConfig.isConnectedWifiEnabled()) {
                jSONObject.put("c-ap", a(sample.connectedWifiAp));
            }
            if (thinICEConfig.isCellEnabled()) {
                jSONObject.put("c-sc", a(sample.connectedCellTowerInfo));
            }
            if (thinICEConfig.isVisibleCellTowerEnabled()) {
                jSONArray = new JSONArray();
                if (sample.visibleCellTowerInfo != null) {
                    for (NeighboringCellInfo a2 : sample.visibleCellTowerInfo) {
                        a = a(a2);
                        if (a != null) {
                            jSONArray.put(a);
                        }
                    }
                    if (jSONArray.length() > 0) {
                        jSONObject.put("v-sc", jSONArray);
                    }
                }
            }
            if (thinICEConfig.isVisibleWifiEnabled()) {
                jSONArray = new JSONArray();
                if (sample.visibleWifiAp != null) {
                    for (WifiInfo a3 : sample.visibleWifiAp) {
                        a = a(a3);
                        if (a != null) {
                            jSONArray.put(a);
                        }
                    }
                }
                if (jSONArray.length() > 0) {
                    jSONObject.put("v-ap", jSONArray);
                }
            }
            if (jSONObject.length() <= 1) {
                return null;
            }
            jSONObject.put("ts", sample.utc);
            return jSONObject;
        } catch (JSONException e) {
            return null;
        }
    }

    private JSONObject a(WifiInfo wifiInfo) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("bssid", wifiInfo.bssid);
            jSONObject.put("essid", wifiInfo.ssid);
            return jSONObject;
        } catch (Exception e) {
            return null;
        }
    }

    private JSONObject a(NeighboringCellInfo neighboringCellInfo) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AnalyticsEvent.EVENT_ID, neighboringCellInfo.getCid());
            if (neighboringCellInfo.getRssi() == 0) {
                return jSONObject;
            }
            jSONObject.put(AnalyticsEvent.TYPE_START_SESSION, neighboringCellInfo.getRssi());
            return jSONObject;
        } catch (JSONException e) {
            return null;
        }
    }

    private JSONObject a(CellTowerInfo cellTowerInfo) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(AnalyticsEvent.EVENT_ID, cellTowerInfo.id);
            if (cellTowerInfo.signalStrength == 0) {
                return jSONObject;
            }
            jSONObject.put(AnalyticsEvent.TYPE_START_SESSION, cellTowerInfo.signalStrength);
            return jSONObject;
        } catch (Exception e) {
            return null;
        }
    }
}
