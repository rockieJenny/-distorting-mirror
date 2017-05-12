package com.inmobi.commons.metric;

import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import com.inmobi.commons.internal.FileOperations;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.metric.Logger.MetricsCallback;
import com.inmobi.commons.uid.UID;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONObject;

public class Storage {
    MetricConfigParams a;
    private String b;
    private String c;
    private String d;
    private int e;
    private PreProcessor f;
    private Queuer g;
    private long h;
    private long i;
    private AtomicBoolean j;
    private AtomicBoolean k;
    private MetricsCallback l;

    public interface PreProcessor {
        JSONObject process(JSONObject jSONObject);
    }

    public void setCallback(MetricsCallback metricsCallback) {
        this.l = metricsCallback;
    }

    public Storage(int i, String str, Queuer queuer, MetricConfigParams metricConfigParams) {
        this.b = "inmobi.cache.data";
        this.c = "inmobi.cache.data.events.number";
        this.d = "inmobi.cache.data.events.timestamp";
        this.e = 0;
        this.f = null;
        this.g = null;
        this.a = null;
        this.h = -1;
        this.i = -1;
        this.j = new AtomicBoolean(false);
        this.k = new AtomicBoolean(false);
        this.l = null;
        this.a = metricConfigParams;
        this.g = queuer;
        this.e = i;
        this.b += "." + str;
        this.c += "." + str;
        this.d += "." + str;
    }

    public Storage(int i, String str, Queuer queuer, MetricConfigParams metricConfigParams, PreProcessor preProcessor) {
        this(i, str, queuer, metricConfigParams);
        this.f = preProcessor;
    }

    public long getEvents() {
        return this.h;
    }

    public long getTimestamp() {
        return this.i;
    }

    public void setPreprocessor(PreProcessor preProcessor) {
        this.f = preProcessor;
    }

    public String readLocalCache() {
        try {
            return FileOperations.readFileAsString(InternalSDKUtil.getContext(), this.b);
        } catch (Exception e) {
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Unable to read metric info.");
            return "null,";
        }
    }

    public void readNumberOfEventsAndTimeStampFromPersistent() {
        if (this.h == -1) {
            try {
                this.h = Long.parseLong(FileOperations.readFileAsString(InternalSDKUtil.getContext(), this.c));
                this.i = Long.parseLong(FileOperations.readFileAsString(InternalSDKUtil.getContext(), this.d));
            } catch (Exception e) {
                this.h = 0;
            }
            if (this.i == -1) {
                this.i = System.currentTimeMillis() / 1000;
                a();
            }
        }
    }

    private void a() {
        try {
            FileOperations.writeStringToFile(InternalSDKUtil.getContext(), this.c, "" + this.h, false);
            FileOperations.writeStringToFile(InternalSDKUtil.getContext(), this.d, "" + this.i, false);
        } catch (Exception e) {
            this.h = 0;
        }
    }

    public void saveLocalCache() {
        if (this.j.compareAndSet(false, true)) {
            if (this.l != null) {
                this.l.movedMetricDataToFileMemory(this.b);
            }
            try {
                FileOperations.writeStringToFile(InternalSDKUtil.getContext(), this.b, this.g.get(), true);
                this.h += this.g.a();
                this.g.reset();
                a();
            } catch (Throwable e) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Someting went wrong while saving metrics log to persistent storage", e);
            }
            this.j.set(false);
            try {
                synchronized (this.j) {
                    this.j.notify();
                }
            } catch (Throwable e2) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Error saving local cache", e2);
            }
        }
    }

    public void resetFile() {
        try {
            FileOperations.writeStringToFile(InternalSDKUtil.getContext(), this.b, "", false);
            this.h = 0;
            this.i = System.currentTimeMillis() / 1000;
            a();
        } catch (Throwable e) {
            Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Someting went wrong while saving metrics log to persistent storage", e);
        }
    }

    public void sendFile() {
        if (this.k.compareAndSet(false, true)) {
            while (!this.j.compareAndSet(false, true)) {
                try {
                    synchronized (this.j) {
                        this.j.wait();
                    }
                } catch (Exception e) {
                    Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Unable to get lock for reading file before sending metric data");
                    return;
                }
            }
            if (this.l != null) {
                this.l.reportingStartedWithRequest(this.a.getUrl());
            }
            try {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Sending " + this.h + " events to server..");
                String readLocalCache = readLocalCache();
                JSONObject jSONObject = new JSONObject(InternalSDKUtil.getEncodedMap(UID.getInstance().getMapForEncryption(null)));
                Calendar instance = Calendar.getInstance();
                jSONObject.put("tz", instance.get(15));
                jSONObject.put("ts", instance.getTimeInMillis());
                jSONObject.put("product", this.e);
                jSONObject.put("payload", new JSONArray("[" + readLocalCache.substring(0, readLocalCache.length() - 1) + "]"));
                if (this.f != null) {
                    jSONObject = this.f.process(jSONObject);
                }
                String jSONObject2 = jSONObject.toString();
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "sending " + jSONObject2 + " to url :" + this.a.getUrl());
                MetricEndPoint.sendData(this.a.getUrl(), jSONObject2);
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "data sent successfully to url :" + this.a.getUrl());
                resetFile();
                if (this.l != null) {
                    this.l.reportingSuccess();
                }
            } catch (Exception e2) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Unable to send data");
                if (this.l != null) {
                    this.l.reportingFailure();
                }
            }
            this.j.set(false);
            try {
                synchronized (this.j) {
                    this.j.notify();
                }
            } catch (Exception e3) {
                Log.internal(AdTrackerConstants.IAT_LOGGING_TAG, "Unable to lock file.");
            }
            this.k.set(false);
        }
    }

    public void saveToLatest() {
        saveLocalCache();
    }
}
