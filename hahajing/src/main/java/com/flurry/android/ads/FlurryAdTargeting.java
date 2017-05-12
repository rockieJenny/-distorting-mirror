package com.flurry.android.ads;

import com.flurry.sdk.e;
import java.util.Map;

public final class FlurryAdTargeting extends e {
    public void setLocation(float f, float f2) {
        super.setLocation(f, f2);
    }

    public void clearLocation() {
        super.clearLocation();
    }

    public void setUserCookies(Map<String, String> map) {
        super.setUserCookies(map);
    }

    public void clearUserCookies() {
        super.clearUserCookies();
    }

    public void setKeywords(Map<String, String> map) {
        super.setKeywords(map);
    }

    public void clearKeywords() {
        super.clearKeywords();
    }

    public void setAge(int i) {
        super.setAge(i);
    }

    public void clearAge() {
        super.clearAge();
    }

    public void setGender(FlurryGender flurryGender) {
        super.setGender(flurryGender == null ? FlurryGender.UNKNOWN.ordinal() : flurryGender.ordinal());
    }

    public void clearGender() {
        super.clearGender();
    }

    public void setEnableTestAds(boolean z) {
        super.setEnableTestAds(z);
    }
}
