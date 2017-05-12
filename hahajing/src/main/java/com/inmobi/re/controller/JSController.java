package com.inmobi.re.controller;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.inmobi.re.container.IMWebView;
import com.inmobi.re.controller.util.NavigationStringEnum;
import com.inmobi.re.controller.util.TransitionStringEnum;
import java.lang.reflect.Field;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlrpc.android.IXMLRPCSerializer;

public abstract class JSController {
    public static final String EXIT = "exit";
    public static final String FULL_SCREEN = "fullscreen";
    public static final String STYLE_NORMAL = "normal";
    protected ExpandProperties expProps = new ExpandProperties();
    protected IMWebView imWebView;
    protected Context mContext;
    protected ExpandProperties temporaryexpProps = new ExpandProperties();

    public static class ReflectedParcelable implements Parcelable {
        public int describeContents() {
            return 0;
        }

        protected ReflectedParcelable(Parcel parcel) {
            Field[] declaredFields = getClass().getDeclaredFields();
            int i = 0;
            while (i < declaredFields.length) {
                try {
                    Field field = declaredFields[i];
                    Class type = field.getType();
                    if (type.isEnum()) {
                        String cls = type.toString();
                        if (cls.equals("class com.mraid.NavigationStringEnum")) {
                            field.set(this, NavigationStringEnum.fromString(parcel.readString()));
                        } else if (cls.equals("class com.mraid.TransitionStringEnum")) {
                            field.set(this, TransitionStringEnum.fromString(parcel.readString()));
                        }
                    } else if (!(field.get(this) instanceof Creator)) {
                        field.set(this, parcel.readValue(null));
                    }
                    i++;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return;
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
        }

        public void writeToParcel(Parcel parcel, int i) {
            Field[] declaredFields = getClass().getDeclaredFields();
            int i2 = 0;
            while (i2 < declaredFields.length) {
                try {
                    Field field = declaredFields[i2];
                    Class type = field.getType();
                    if (type.isEnum()) {
                        String cls = type.toString();
                        if (cls.equals("class com.mraid.NavigationStringEnum")) {
                            parcel.writeString(((NavigationStringEnum) field.get(this)).getText());
                        } else if (cls.equals("class com.mraid.TransitionStringEnum")) {
                            parcel.writeString(((TransitionStringEnum) field.get(this)).getText());
                        }
                    } else {
                        Object obj = field.get(this);
                        if (!(obj instanceof Creator)) {
                            parcel.writeValue(obj);
                        }
                    }
                    i2++;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                    return;
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
        }
    }

    public static class Dimensions extends ReflectedParcelable {
        public static final Creator<Dimensions> CREATOR = new Creator<Dimensions>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return a(i);
            }

            public Dimensions a(Parcel parcel) {
                return new Dimensions(parcel);
            }

            public Dimensions[] a(int i) {
                return new Dimensions[i];
            }
        };
        public int height;
        public int width;
        public int x;
        public int y;

        public Dimensions() {
            this.x = -1;
            this.y = -1;
            this.width = -1;
            this.height = -1;
        }

        protected Dimensions(Parcel parcel) {
            super(parcel);
        }

        public String toString() {
            return "x: " + this.x + ", y: " + this.y + ", width: " + this.width + ", height: " + this.height;
        }
    }

    public static class ExpandProperties extends ReflectedParcelable {
        public static final Creator<ExpandProperties> CREATOR = new Creator<ExpandProperties>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return a(i);
            }

            public ExpandProperties a(Parcel parcel) {
                return new ExpandProperties(parcel);
            }

            public ExpandProperties[] a(int i) {
                return new ExpandProperties[i];
            }
        };
        public int actualHeightRequested;
        public int actualWidthRequested;
        public int bottomStuff;
        public int currentX;
        public int currentY;
        public int height;
        public boolean isModal;
        public boolean lockOrientation;
        public String orientation;
        public int portraitHeightRequested;
        public int portraitWidthRequested;
        public String rotationAtExpand;
        public int topStuff;
        public boolean useCustomClose;
        public int width;
        public int x;
        public int y;
        public boolean zeroWidthHeight;

        public ExpandProperties() {
            reinitializeExpandProperties();
        }

        public void reinitializeExpandProperties() {
            this.width = 0;
            this.height = 0;
            this.x = -1;
            this.y = -1;
            this.useCustomClose = false;
            this.isModal = true;
            this.lockOrientation = false;
            this.orientation = "";
            this.actualWidthRequested = 0;
            this.actualHeightRequested = 0;
            this.topStuff = 0;
            this.bottomStuff = 0;
            this.portraitWidthRequested = 0;
            this.portraitHeightRequested = 0;
            this.zeroWidthHeight = false;
            this.rotationAtExpand = "";
            this.currentX = 0;
            this.currentY = 0;
        }

        protected ExpandProperties(Parcel parcel) {
            super(parcel);
        }
    }

    public static class OrientationProperties extends ReflectedParcelable {
        public static final Creator<OrientationProperties> CREATOR = new Creator<OrientationProperties>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return a(i);
            }

            public OrientationProperties a(Parcel parcel) {
                return new OrientationProperties(parcel);
            }

            public OrientationProperties[] a(int i) {
                return new OrientationProperties[i];
            }
        };
        public boolean allowOrientationChange;
        public String forceOrientation;

        public OrientationProperties() {
            initializeOrientationProperties();
        }

        public void initializeOrientationProperties() {
            this.allowOrientationChange = true;
            this.forceOrientation = "";
        }

        protected OrientationProperties(Parcel parcel) {
            super(parcel);
        }
    }

    public static class PlayerProperties extends ReflectedParcelable {
        public static final Creator<PlayerProperties> CREATOR = new Creator<PlayerProperties>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return a(i);
            }

            public PlayerProperties a(Parcel parcel) {
                return new PlayerProperties(parcel);
            }

            public PlayerProperties[] a(int i) {
                return new PlayerProperties[i];
            }
        };
        public boolean audioMuted;
        public boolean autoPlay;
        public boolean doLoop;
        public String id;
        public boolean showControl;
        public String startStyle;
        public String stopStyle;

        public PlayerProperties() {
            this.showControl = true;
            this.autoPlay = true;
            this.audioMuted = false;
            this.doLoop = false;
            String str = JSController.STYLE_NORMAL;
            this.stopStyle = str;
            this.startStyle = str;
            this.id = "";
        }

        public PlayerProperties(Parcel parcel) {
            super(parcel);
        }

        public void setStopStyle(String str) {
            this.stopStyle = str;
        }

        public void setProperties(boolean z, boolean z2, boolean z3, boolean z4, String str, String str2, String str3) {
            this.autoPlay = z2;
            this.showControl = z3;
            this.doLoop = z4;
            this.audioMuted = z;
            this.startStyle = str;
            this.stopStyle = str2;
            this.id = str3;
        }

        public boolean isAutoPlay() {
            return this.autoPlay;
        }

        public boolean showControl() {
            return this.showControl;
        }

        public boolean doLoop() {
            return this.doLoop;
        }

        public boolean doMute() {
            return this.audioMuted;
        }

        public boolean exitOnComplete() {
            return this.stopStyle.equalsIgnoreCase(JSController.EXIT);
        }

        public boolean isFullScreen() {
            return this.startStyle.equalsIgnoreCase(JSController.FULL_SCREEN);
        }

        public void setFullScreen() {
            this.startStyle = JSController.FULL_SCREEN;
        }
    }

    public static class Properties extends ReflectedParcelable {
        public static final Creator<Properties> CREATOR = new Creator<Properties>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return a(i);
            }

            public Properties a(Parcel parcel) {
                return new Properties(parcel);
            }

            public Properties[] a(int i) {
                return new Properties[i];
            }
        };
        public int backgroundColor;
        public float backgroundOpacity;
        public boolean useBackground;

        protected Properties(Parcel parcel) {
            super(parcel);
        }

        public Properties() {
            this.useBackground = false;
            this.backgroundColor = 0;
            this.backgroundOpacity = 0.0f;
        }
    }

    public static class ResizeProperties extends ReflectedParcelable {
        public static final Creator<ResizeProperties> CREATOR = new Creator<ResizeProperties>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return a(i);
            }

            public ResizeProperties a(Parcel parcel) {
                return new ResizeProperties(parcel);
            }

            public ResizeProperties[] a(int i) {
                return new ResizeProperties[i];
            }
        };
        public boolean allowOffscreen;
        public String customClosePosition;
        public int height;
        public int offsetX;
        public int offsetY;
        public int width;

        public ResizeProperties() {
            initializeResizeProperties();
        }

        public void initializeResizeProperties() {
            this.offsetY = 0;
            this.offsetX = 0;
            this.height = 0;
            this.width = 0;
            this.allowOffscreen = false;
            this.customClosePosition = "top-right";
        }

        protected ResizeProperties(Parcel parcel) {
            super(parcel);
        }
    }

    public abstract void stopAllListeners();

    public JSController(IMWebView iMWebView, Context context) {
        this.imWebView = iMWebView;
        this.mContext = context;
    }

    protected static Object getFromJSON(JSONObject jSONObject, Class<?> cls) throws IllegalAccessException, InstantiationException, NumberFormatException, NullPointerException {
        Field[] declaredFields = cls.getDeclaredFields();
        Object newInstance = cls.newInstance();
        int i = 0;
        while (i < declaredFields.length) {
            Field field = declaredFields[i];
            String replace = field.getName().replace('_', '-');
            String obj = field.getType().toString();
            try {
                if (obj.equals(IXMLRPCSerializer.TYPE_INT)) {
                    int i2;
                    obj = jSONObject.getString(replace).toLowerCase(Locale.ENGLISH);
                    try {
                        if (obj.startsWith("#")) {
                            i2 = -1;
                            try {
                                if (obj.startsWith("#0x")) {
                                    i2 = Integer.decode(obj.substring(1)).intValue();
                                } else {
                                    i2 = Integer.parseInt(obj.substring(1), 16);
                                }
                            } catch (NumberFormatException e) {
                            }
                        } else {
                            i2 = Integer.parseInt(obj);
                        }
                    } catch (NumberFormatException e2) {
                        i2 = 0;
                    }
                    field.set(newInstance, Integer.valueOf(i2));
                    i++;
                } else {
                    if (obj.equals("class java.lang.String")) {
                        field.set(newInstance, jSONObject.getString(replace));
                    } else if (obj.equals(IXMLRPCSerializer.TYPE_BOOLEAN)) {
                        field.set(newInstance, Boolean.valueOf(jSONObject.getBoolean(replace)));
                    } else if (obj.equals("float")) {
                        field.set(newInstance, Float.valueOf(Float.parseFloat(jSONObject.getString(replace))));
                    } else if (obj.equals("class com.mraid.NavigationStringEnum")) {
                        field.set(newInstance, NavigationStringEnum.fromString(jSONObject.getString(replace)));
                    } else if (obj.equals("class com.mraid.TransitionStringEnum")) {
                        field.set(newInstance, TransitionStringEnum.fromString(jSONObject.getString(replace)));
                    }
                    i++;
                }
            } catch (JSONException e3) {
            }
        }
        return newInstance;
    }
}
