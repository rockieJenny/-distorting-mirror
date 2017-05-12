package com.inmobi.commons.thinICE.cellular;

import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import com.inmobi.commons.thinICE.icedatacollector.BuildSettings;
import com.inmobi.commons.thinICE.icedatacollector.IceDataCollector;
import java.util.ArrayList;
import java.util.List;

public final class CellUtil {
    private static final String[] a = new String[]{"android.permission.ACCESS_COARSE_LOCATION"};
    private static final String[] b = new String[]{"android.permission.ACCESS_FINE_LOCATION"};
    private static final String[] c = new String[]{"android.permission.ACCESS_COARSE_LOCATION"};

    static class a {
        static int a(Context context) {
            try {
                ArrayList arrayList = (ArrayList) TelephonyManager.class.getMethod("getAllCellInfo", (Class[]) null).invoke((TelephonyManager) context.getSystemService("phone"), (Object[]) null);
                if (arrayList != null) {
                    Object obj = arrayList.get(0);
                    obj = obj.getClass().getMethod("getCellSignalStrength", (Class[]) null).invoke(obj, (Object[]) null);
                    return ((Integer) obj.getClass().getMethod("getDbm", (Class[]) null).invoke(obj, (Object[]) null)).intValue();
                }
            } catch (Throwable e) {
                if (BuildSettings.DEBUG) {
                    Log.e(IceDataCollector.TAG, "Error getting cell tower signal strength", e);
                }
            }
            return 0;
        }
    }

    private static int[] a(String str) {
        int[] iArr = new int[]{-1, -1};
        if (!(str == null || str.equals(""))) {
            try {
                int parseInt = Integer.parseInt(str.substring(0, 3));
                int parseInt2 = Integer.parseInt(str.substring(3));
                iArr[0] = parseInt;
                iArr[1] = parseInt2;
            } catch (IndexOutOfBoundsException e) {
            } catch (NumberFormatException e2) {
            }
        }
        return iArr;
    }

    public static CellOperatorInfo getCellNetworkInfo(Context context) {
        CellOperatorInfo cellOperatorInfo = new CellOperatorInfo();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        int[] a = a(telephonyManager.getNetworkOperator());
        cellOperatorInfo.currentMcc = a[0];
        cellOperatorInfo.currentMnc = a[1];
        int[] a2 = a(telephonyManager.getSimOperator());
        cellOperatorInfo.simMcc = a2[0];
        cellOperatorInfo.simMnc = a2[1];
        return cellOperatorInfo;
    }

    public static boolean hasGetCurrentServingCellPermission(Context context) {
        boolean z = true;
        for (String checkCallingOrSelfPermission : a) {
            if (context.checkCallingOrSelfPermission(checkCallingOrSelfPermission) != 0) {
                z = false;
            }
        }
        boolean z2 = true;
        for (String checkCallingOrSelfPermission2 : b) {
            if (context.checkCallingOrSelfPermission(checkCallingOrSelfPermission2) != 0) {
                z2 = false;
            }
        }
        if (z || r2) {
            return true;
        }
        return false;
    }

    public static boolean hasGetVisibleCellTowerPermission(Context context) {
        for (String checkCallingOrSelfPermission : c) {
            if (context.checkCallingOrSelfPermission(checkCallingOrSelfPermission) != 0) {
                return false;
            }
        }
        return true;
    }

    public static CellTowerInfo getCurrentCellTower(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        int[] a = a(telephonyManager.getNetworkOperator());
        CellLocation cellLocation = telephonyManager.getCellLocation();
        if (cellLocation == null || a[0] == -1) {
            return null;
        }
        CellTowerInfo cellTowerInfo = new CellTowerInfo();
        String valueOf = String.valueOf(a[0]);
        String valueOf2 = String.valueOf(a[1]);
        int networkId;
        int systemId;
        if (cellLocation instanceof CdmaCellLocation) {
            CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cellLocation;
            networkId = cdmaCellLocation.getNetworkId();
            int baseStationId = cdmaCellLocation.getBaseStationId();
            systemId = cdmaCellLocation.getSystemId();
            cellTowerInfo.signalStrength = a.a(context);
            if (!(networkId == -1 || baseStationId == -1 || systemId == -1)) {
                String toHexString = Integer.toHexString(networkId);
                String toHexString2 = Integer.toHexString(baseStationId);
                cellTowerInfo.id = valueOf + "-" + valueOf2 + "-" + toHexString + "-" + toHexString2 + "-" + Integer.toHexString(systemId);
            }
        } else {
            GsmCellLocation gsmCellLocation = (GsmCellLocation) cellLocation;
            networkId = gsmCellLocation.getCid();
            systemId = gsmCellLocation.getLac();
            cellTowerInfo.signalStrength = a.a(context);
            if (!(networkId == -1 || systemId == -1)) {
                String toHexString3 = Integer.toHexString(systemId);
                cellTowerInfo.id = valueOf + "-" + valueOf2 + "-" + toHexString3 + "-" + Integer.toHexString(networkId);
            }
        }
        return cellTowerInfo;
    }

    public static List<NeighboringCellInfo> getVisibleCellTower(Context context) {
        List<NeighboringCellInfo> neighboringCellInfo = ((TelephonyManager) context.getSystemService("phone")).getNeighboringCellInfo();
        if (neighboringCellInfo == null) {
            return null;
        }
        if (neighboringCellInfo.size() == 0) {
            return null;
        }
        return neighboringCellInfo;
    }

    public static List<Integer> getVisibleCellTowerIds(Context context) {
        List<NeighboringCellInfo> visibleCellTower = getVisibleCellTower(context);
        if (visibleCellTower == null || visibleCellTower.size() == 0) {
            return null;
        }
        List<Integer> arrayList = new ArrayList();
        for (NeighboringCellInfo cid : visibleCellTower) {
            arrayList.add(Integer.valueOf(cid.getCid()));
        }
        return arrayList;
    }
}
