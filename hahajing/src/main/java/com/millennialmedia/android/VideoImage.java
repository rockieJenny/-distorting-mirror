package com.millennialmedia.android;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.widget.ImageButton;
import android.widget.RelativeLayout.LayoutParams;
import com.google.android.gms.cast.TextTrackStyle;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.json.JSONArray;
import org.json.JSONObject;

class VideoImage implements Parcelable, Externalizable {
    public static final Creator<VideoImage> CREATOR = new Creator<VideoImage>() {
        public VideoImage createFromParcel(Parcel in) {
            return new VideoImage(in);
        }

        public VideoImage[] newArray(int size) {
            return new VideoImage[size];
        }
    };
    private static final String TAG = VideoImage.class.getName();
    static final long serialVersionUID = 808334584720834205L;
    int anchor;
    int anchor2;
    long appearanceDelay;
    ImageButton button;
    long contentLength;
    String[] eventLoggingUrls;
    long fadeDuration = 1000;
    float fromAlpha = TextTrackStyle.DEFAULT_FONT_SCALE;
    String imageUrl;
    long inactivityTimeout;
    boolean isLeaveBehind;
    LayoutParams layoutParams;
    String linkUrl;
    String overlayOrientation;
    int paddingBottom = 0;
    int paddingLeft = 0;
    int paddingRight = 0;
    int paddingTop = 0;
    int position;
    int position2;
    float toAlpha = TextTrackStyle.DEFAULT_FONT_SCALE;

    String getImageName() {
        if (this.imageUrl != null) {
            Uri temp = Uri.parse(this.imageUrl);
            if (!(temp == null || temp.getLastPathSegment() == null)) {
                return temp.getLastPathSegment().replaceFirst("\\.[^\\.]*$", ".dat");
            }
        }
        return null;
    }

    VideoImage(JSONObject imageObject) {
        deserializeFromObj(imageObject);
    }

    VideoImage(Parcel in) {
        boolean z = true;
        try {
            this.imageUrl = in.readString();
            this.contentLength = in.readLong();
            this.eventLoggingUrls = new String[in.readInt()];
            in.readStringArray(this.eventLoggingUrls);
            this.linkUrl = in.readString();
            this.overlayOrientation = in.readString();
            this.paddingTop = in.readInt();
            this.paddingBottom = in.readInt();
            this.paddingLeft = in.readInt();
            this.paddingRight = in.readInt();
            this.position = in.readInt();
            this.anchor = in.readInt();
            this.position2 = in.readInt();
            this.anchor2 = in.readInt();
            this.appearanceDelay = in.readLong();
            this.inactivityTimeout = in.readLong();
            this.fromAlpha = in.readFloat();
            this.toAlpha = in.readFloat();
            this.fadeDuration = in.readLong();
            if (in.readInt() != 1) {
                z = false;
            }
            this.isLeaveBehind = z;
        } catch (Exception e) {
            MMLog.e(TAG, "VideoImage parcel creation exception: ", e);
        }
    }

    private void deserializeFromObj(JSONObject imageObject) {
        if (imageObject != null) {
            this.imageUrl = imageObject.optString("image", null);
            this.contentLength = imageObject.optLong("contentLength");
            JSONArray jsonArray = imageObject.optJSONArray("activity");
            if (jsonArray != null) {
                this.eventLoggingUrls = new String[jsonArray.length()];
                for (int i = 0; i < jsonArray.length(); i++) {
                    this.eventLoggingUrls[i] = jsonArray.optString(i);
                }
            } else {
                this.eventLoggingUrls = new String[0];
            }
            this.linkUrl = imageObject.optString("url", null);
            this.overlayOrientation = imageObject.optString("overlayOrientation", null);
            this.position = imageObject.optInt("android-layout");
            this.anchor = imageObject.optInt("android-layoutAnchor");
            this.position2 = imageObject.optInt("android-layout2");
            this.anchor2 = imageObject.optInt("android-layoutAnchor2");
            this.paddingTop = imageObject.optInt("android-paddingTop");
            this.paddingLeft = imageObject.optInt("android-paddingLeft");
            this.paddingRight = imageObject.optInt("android-paddingRight");
            this.paddingBottom = imageObject.optInt("android-paddingBottom");
            this.appearanceDelay = (long) (imageObject.optDouble("appearanceDelay", 0.0d) * 1000.0d);
            this.inactivityTimeout = (long) (imageObject.optDouble("inactivityTimeout", 0.0d) * 1000.0d);
            JSONObject opacityObject = imageObject.optJSONObject("opacity");
            if (opacityObject != null) {
                this.fromAlpha = (float) opacityObject.optDouble("start", 1.0d);
                this.toAlpha = (float) opacityObject.optDouble("end", 1.0d);
                this.fadeDuration = (long) (opacityObject.optDouble("fadeDuration", 1.0d) * 1000.0d);
            }
            this.isLeaveBehind = imageObject.optBoolean("is_leavebehind");
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imageUrl);
        dest.writeLong(this.contentLength);
        dest.writeInt(this.eventLoggingUrls.length);
        dest.writeStringArray(this.eventLoggingUrls);
        dest.writeString(this.linkUrl);
        dest.writeString(this.overlayOrientation);
        dest.writeInt(this.paddingTop);
        dest.writeInt(this.paddingBottom);
        dest.writeInt(this.paddingLeft);
        dest.writeInt(this.paddingRight);
        dest.writeInt(this.position);
        dest.writeInt(this.anchor);
        dest.writeInt(this.position2);
        dest.writeInt(this.anchor2);
        dest.writeLong(this.appearanceDelay);
        dest.writeLong(this.inactivityTimeout);
        dest.writeFloat(this.fromAlpha);
        dest.writeFloat(this.toAlpha);
        dest.writeLong(this.fadeDuration);
        dest.writeInt(this.isLeaveBehind ? 1 : 0);
    }

    public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
        this.imageUrl = (String) input.readObject();
        this.contentLength = input.readLong();
        int count = input.readInt();
        this.eventLoggingUrls = new String[count];
        for (int i = 0; i < count; i++) {
            this.eventLoggingUrls[i] = (String) input.readObject();
        }
        this.linkUrl = (String) input.readObject();
        this.overlayOrientation = (String) input.readObject();
        this.paddingTop = input.readInt();
        this.paddingBottom = input.readInt();
        this.paddingLeft = input.readInt();
        this.paddingRight = input.readInt();
        this.position = input.readInt();
        this.anchor = input.readInt();
        this.position2 = input.readInt();
        this.anchor2 = input.readInt();
        this.appearanceDelay = input.readLong();
        this.inactivityTimeout = input.readLong();
        this.fromAlpha = input.readFloat();
        this.toAlpha = input.readFloat();
        this.fadeDuration = input.readLong();
        this.isLeaveBehind = input.readBoolean();
    }

    public void writeExternal(ObjectOutput output) throws IOException {
        output.writeObject(this.imageUrl);
        output.writeLong(this.contentLength);
        output.writeInt(this.eventLoggingUrls.length);
        for (String temp : this.eventLoggingUrls) {
            output.writeObject(temp);
        }
        output.writeObject(this.linkUrl);
        output.writeObject(this.overlayOrientation);
        output.writeInt(this.paddingTop);
        output.writeInt(this.paddingBottom);
        output.writeInt(this.paddingLeft);
        output.writeInt(this.paddingRight);
        output.writeInt(this.position);
        output.writeInt(this.anchor);
        output.writeInt(this.position2);
        output.writeInt(this.anchor2);
        output.writeLong(this.appearanceDelay);
        output.writeLong(this.inactivityTimeout);
        output.writeFloat(this.fromAlpha);
        output.writeFloat(this.toAlpha);
        output.writeLong(this.fadeDuration);
        output.writeBoolean(this.isLeaveBehind);
    }
}
