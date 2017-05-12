package com.inmobi.commons.thinICE.icedatacollector;

import android.telephony.NeighboringCellInfo;
import com.inmobi.commons.thinICE.cellular.CellOperatorInfo;
import com.inmobi.commons.thinICE.cellular.CellTowerInfo;
import com.inmobi.commons.thinICE.location.LocationInfo;
import com.inmobi.commons.thinICE.wifi.WifiInfo;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public final class Sample {
    public CellOperatorInfo cellOperator;
    public CellTowerInfo connectedCellTowerInfo;
    public WifiInfo connectedWifiAp;
    public HashMap<String, LocationInfo> lastKnownLocations;
    public long utc;
    public List<NeighboringCellInfo> visibleCellTowerInfo;
    public List<WifiInfo> visibleWifiAp;
    public int zoneOffset;

    Sample() {
        Calendar instance = Calendar.getInstance();
        this.utc = instance.getTimeInMillis();
        this.zoneOffset = instance.get(16) + instance.get(15);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getClass().getSimpleName()).append("[\n");
        stringBuilder.append("-- utc: ").append(this.utc).append("\n");
        stringBuilder.append("-- zoneOffset: ").append(this.zoneOffset).append("\n");
        stringBuilder.append("-- cell operator: ").append(this.cellOperator).append("\n");
        stringBuilder.append("-- connected wifi access point: ").append(this.connectedWifiAp).append("\n");
        stringBuilder.append("-- last known locations:");
        if (this.lastKnownLocations == null) {
            stringBuilder.append(" null\n");
        } else {
            stringBuilder.append("\n");
            for (LocationInfo append : this.lastKnownLocations.values()) {
                stringBuilder.append("   + ").append(append).append("\n");
            }
        }
        stringBuilder.append("-- visible wifi aps:");
        if (this.visibleWifiAp == null) {
            stringBuilder.append(" null\n");
        } else {
            stringBuilder.append("\n");
            for (WifiInfo append2 : this.visibleWifiAp) {
                stringBuilder.append("   + ").append(append2).append("\n");
            }
        }
        stringBuilder.append("-- connected serving cell tower: ").append(this.connectedCellTowerInfo).append("\n");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
