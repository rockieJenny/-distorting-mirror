package com.givewaygames.ads;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.analytics.tracking.android.Tracker;
import com.google.tagmanager.Container;
import com.google.tagmanager.ContainerOpener;
import com.google.tagmanager.ContainerOpener.Notifier;
import com.google.tagmanager.ContainerOpener.OpenType;
import com.google.tagmanager.TagManager;
import com.squareup.picasso.Picasso;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlrpc.android.IXMLRPCSerializer;

public class HouseAds implements OnClickListener, Notifier {
    private static final String CONTAINER_ID = "GTM-TSBTWX";
    private static final boolean DEBUG = false;
    public static final String PREF_HAS_RATED_APP = "has_rated_this_app";
    public static final String TAG = "HouseAds";
    private static HouseAds instance;
    List<HouseAd> fullHouseAds = new ArrayList();
    private Tracker gaTracker;
    List<HouseAd> houseAds = new ArrayList();
    private boolean isAmazon;
    int lastInternal = -1;
    private String mailingList;
    private String moreFromString;
    private final String myPackage;
    private final Random random = new Random();
    final WeakReference<Context> weakContext;

    public static class HouseAd {
        public int imageId;
        public String imageUrl;
        public boolean isSelf;
        public String name;
        public double percent;
        public String title;
        public int triggerId = 0;
        public String url;
    }

    public static synchronized HouseAds getInstance(Context context, Tracker tracker, boolean isAmazon) {
        HouseAds houseAds;
        synchronized (HouseAds.class) {
            if (instance == null) {
                instance = new HouseAds(context, tracker, isAmazon);
            }
            houseAds = instance;
        }
        return houseAds;
    }

    protected HouseAds(Context context, Tracker tracker, boolean isAmazon) {
        this.weakContext = new WeakReference(context);
        this.myPackage = context.getPackageName();
        this.isAmazon = isAmazon;
        this.gaTracker = tracker;
        this.moreFromString = context.getResources().getString(R.string.more_from_giveway);
        this.mailingList = context.getResources().getString(R.string.mailing_list);
        ContainerOpener.openContainer(TagManager.getInstance(context), CONTAINER_ID, OpenType.PREFER_NON_DEFAULT, Long.valueOf(2000), this);
    }

    public List<HouseAd> getHouseAds() {
        return this.houseAds;
    }

    public List<HouseAd> getFullHouseAds() {
        return this.fullHouseAds;
    }

    private int getRandomIdx(List<HouseAd> ads, boolean doWeights) {
        return doWeights ? getRandomIdxByWeight(ads) : getRandomIdxSimple(ads);
    }

    private int getRandomIdxSimple(List<HouseAd> ads) {
        return this.random.nextInt(ads.size());
    }

    private int getRandomIdxByWeight(List<HouseAd> ads) {
        double value = this.random.nextDouble();
        double walk = ((HouseAd) ads.get(0)).percent;
        int idx = 0;
        while (value > walk && idx + 1 < ads.size()) {
            idx++;
            walk += ((HouseAd) ads.get(idx)).percent;
        }
        if (idx >= ads.size()) {
            return ads.size() - 1;
        }
        return idx;
    }

    public boolean hasRatedThisApp(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_HAS_RATED_APP, false);
    }

    public void setHasRatedThisApp(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PREF_HAS_RATED_APP, true).apply();
    }

    private boolean isValidIdx(int idx) {
        return idx != this.lastInternal;
    }

    public HouseAd pickRandomHouseAd(List<HouseAd> ads, boolean useWeights) {
        if (ads.size() == 0) {
            return null;
        }
        int idx = getRandomIdx(ads, useWeights);
        for (int i = 0; i < 5 && !isValidIdx(idx); i++) {
            idx = getRandomIdx(ads, useWeights);
        }
        this.lastInternal = idx;
        return (HouseAd) ads.get(idx);
    }

    public void loadRandomHouseAdFromUninstalled(View v, boolean showSelfAsStars) {
        loadHouseAd(v, pickRandomHouseAd(this.houseAds, true), showSelfAsStars);
    }

    public void loadRandomHouseAdFromAllAvailable(View v, boolean showSelfAsStars) {
        loadHouseAd(v, pickRandomHouseAd(this.fullHouseAds, false), showSelfAsStars);
    }

    public void loadHouseAd(View v, HouseAd houseAd, boolean showSelfAsStars) {
        if (houseAd == null) {
            v.setVisibility(8);
            return;
        }
        TextView title = (TextView) v.findViewById(R.id.ad_title);
        if (title != null) {
            title.setText(houseAd.title);
        }
        try {
            ImageView imageView = (ImageView) v.findViewById(R.id.house_ad_image);
            TextView message;
            if (houseAd.imageId == 0 || !showSelfAsStars) {
                Picasso.with(v.getContext()).load(Uri.parse(houseAd.imageUrl)).resize(256, 256).into(imageView);
                message = (TextView) v.findViewById(R.id.ad_message);
                if (message != null) {
                    message.setText(houseAd.name);
                }
                v.setTag(houseAd);
                v.setOnClickListener(this);
                v.setVisibility(0);
            }
            Picasso.with(v.getContext()).load(houseAd.imageId).resize(256, 256).into(imageView);
            message = (TextView) v.findViewById(R.id.ad_message);
            if (message != null) {
                message.setText(houseAd.name);
            }
            v.setTag(houseAd);
            v.setOnClickListener(this);
            v.setVisibility(0);
        } catch (RuntimeException e) {
            Log.e(TAG, "Picasso error", e);
        }
    }

    public boolean isSafeToShowRated(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int times = prefs.getInt("ha_number_of_times_shown", 0);
        prefs.edit().putInt("ha_number_of_times_shown", times + 1).commit();
        if (times >= 3) {
            return true;
        }
        return false;
    }

    public void onClick(View v) {
        HouseAd houseAd = (HouseAd) v.getTag();
        if (this.gaTracker != null) {
            GAUtils.sendEvent(this.gaTracker, "ads", "house_ad_clicked", houseAd.name, 0);
        }
        if (houseAd.triggerId != 0) {
            onHandleSpecialTrigger();
            return;
        }
        boolean exists;
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(houseAd.url));
        if (v.getContext().getPackageManager().queryIntentActivities(intent, 65536).size() > 0) {
            exists = true;
        } else {
            exists = false;
        }
        if (exists) {
            if (houseAd.isSelf) {
                setHasRatedThisApp(v.getContext());
            }
            v.getContext().startActivity(intent);
            return;
        }
        Toast.makeText(v.getContext(), R.string.activity_not_found, 0).show();
    }

    private void onHandleSpecialTrigger() {
        Context context = (Context) this.weakContext.get();
        if (context instanceof FragmentActivity) {
            new MailingListSignupDialogFragment().show(((FragmentActivity) context).getSupportFragmentManager(), SubscribeManager.FRAG_MAILING_LIST_SIGNUP);
        }
    }

    private String packageToUrl(String pkg) {
        return (((this.isAmazon ? "http://www.amazon.com/gp/mas/dl/android?p=" : "market://details?id=") + pkg) + "&referrer=utm_source%3D" + getNameFromPackage(this.myPackage)) + "%26utm_medium%3Dsquare";
    }

    private boolean isPackageInstalledFullOrFree(String pkg, Context context) {
        if (isPackageInstalled(pkg, context)) {
            return true;
        }
        String modPkg = pkg;
        if (modPkg.endsWith("_ads")) {
            modPkg = modPkg.substring(0, modPkg.lastIndexOf("_ads"));
        }
        if (modPkg.endsWith("_adfree")) {
            modPkg = modPkg.substring(0, modPkg.lastIndexOf("_adfree"));
        }
        if (modPkg.endsWith(".full")) {
            modPkg = modPkg.substring(0, modPkg.lastIndexOf(".full"));
        }
        if (modPkg.endsWith(".free")) {
            modPkg = modPkg.substring(0, modPkg.lastIndexOf(".free"));
        }
        if ((!pkg.equals(modPkg) && isPackageInstalled(modPkg, context)) || isPackageInstalled(modPkg + "_ads", context) || isPackageInstalled(modPkg + "_adfree", context) || isPackageInstalled(modPkg + ".full", context) || isPackageInstalled(modPkg + ".free", context)) {
            return true;
        }
        return false;
    }

    private boolean isPackageInstalled(String pkg, Context context) {
        try {
            context.getPackageManager().getPackageInfo(pkg, 1);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public void containerAvailable(Container container) {
        Context context = (Context) this.weakContext.get();
        if (context != null) {
            double total = 0.0d;
            try {
                JSONArray ads = new JSONArray(container.getString("house_ads"));
                for (int i = 0; i < ads.length(); i++) {
                    JSONObject ad = ads.getJSONObject(i);
                    String name = ad.getString(IXMLRPCSerializer.TAG_NAME);
                    String image = ad.getString("image");
                    String url = ad.optString("url");
                    String title = this.moreFromString;
                    String pkg = null;
                    int imageId = 0;
                    boolean isMine = false;
                    if (url == null || url.length() == 0) {
                        pkg = ad.optString("package");
                        url = packageToUrl(pkg);
                        if (pkg == null || pkg.contains(this.myPackage) || this.myPackage.contains(pkg)) {
                            title = context.getString(R.string.rate);
                            imageId = R.drawable.big_rating;
                            isMine = true;
                        }
                    }
                    Double w = Double.valueOf(ad.optDouble("ad_weight"));
                    double weight = Double.isNaN(w.doubleValue()) ? 1.0d : w.doubleValue();
                    HouseAd houseAd = new HouseAd();
                    houseAd.title = title;
                    houseAd.name = name;
                    houseAd.url = url;
                    houseAd.imageUrl = image;
                    houseAd.percent = weight;
                    houseAd.imageId = imageId;
                    houseAd.isSelf = isMine;
                    if (!Thread.currentThread().isInterrupted()) {
                        Picasso.with(context).load(Uri.parse(houseAd.imageUrl)).skipMemoryCache().fetch();
                    }
                    if (pkg == null || !isPackageInstalledFullOrFree(pkg, context)) {
                        this.houseAds.add(houseAd);
                        this.fullHouseAds.add(houseAd);
                        total += weight;
                    } else if (!isMine) {
                        this.fullHouseAds.add(houseAd);
                    } else if (!isMine) {
                        continue;
                    } else if (!hasRatedThisApp(context)) {
                        this.houseAds.add(houseAd);
                        total += weight;
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, "House ads, json parse error", e);
            }
            for (HouseAd houseAd2 : this.houseAds) {
                houseAd2.percent /= total;
            }
        }
    }

    private String getNameFromPackage(String pkg) {
        int i = pkg.lastIndexOf(46);
        if (i > 0) {
            return pkg.substring(i + 1);
        }
        return pkg;
    }
}
