package com.millennialmedia.android;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.json.JSONArray;
import org.json.JSONObject;

class VideoLogEvent implements Parcelable, Externalizable {
    public static final Creator<VideoLogEvent> CREATOR = new Creator<VideoLogEvent>() {
        public VideoLogEvent createFromParcel(Parcel in) {
            return new VideoLogEvent(in);
        }

        public VideoLogEvent[] newArray(int size) {
            return new VideoLogEvent[size];
        }
    };
    private static final String TAG = VideoLogEvent.class.getName();
    static final long serialVersionUID = 795553873017368584L;
    String[] activities;
    long position;

    VideoLogEvent(JSONObject logObject) {
        deserializeFromObj(logObject);
    }

    VideoLogEvent(Parcel in) {
        try {
            this.position = in.readLong();
            this.activities = new String[in.readInt()];
            in.readStringArray(this.activities);
        } catch (Exception e) {
            MMLog.e(TAG, "VideoLogEvent parcel creation exception: ", e);
        }
    }

    private void deserializeFromObj(JSONObject logObject) {
        if (logObject != null) {
            this.position = (long) (logObject.optInt("time") * 1000);
            JSONArray jsonArray = logObject.optJSONArray("urls");
            if (jsonArray != null) {
                this.activities = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    this.activities[i] = jsonArray.optString(i);
                }
                return;
            }
            this.activities = new String[0];
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.position);
        dest.writeInt(this.activities.length);
        dest.writeStringArray(this.activities);
    }

    public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
        this.position = input.readLong();
        int count = input.readInt();
        this.activities = new String[count];
        for (int i = 0; i < count; i++) {
            this.activities[i] = (String) input.readObject();
        }
    }

    public void writeExternal(ObjectOutput output) throws IOException {
        output.writeLong(this.position);
        output.writeInt(this.activities.length);
        for (String temp : this.activities) {
            output.writeObject(temp);
        }
    }
}
