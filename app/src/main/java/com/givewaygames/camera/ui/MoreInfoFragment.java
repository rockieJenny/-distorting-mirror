package com.givewaygames.camera.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.GridView;
import com.givewaygames.ads.HouseAds;
import com.givewaygames.camera.activities.CameraFilterActivity;
import com.givewaygames.camera.utils.AnalyticsHelper;
import com.givewaygames.camera.utils.AppRater;
import com.givewaygames.camera.utils.Toast;
import com.givewaygames.goofyglass.R;
import com.givewaygames.gwgl.utils.Log;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MoreInfoFragment extends BaseFragment implements OnClickListener {
    View emailButton;
    View facebookButton;
    View googlePlusButton;
    View moreAppsButton;
    View rateButton;

    public static MoreInfoFragment newInstance() {
        return new MoreInfoFragment();
    }

    public boolean closeOnOutsideClick() {
        return true;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        initialize((CameraFilterActivity) activity, R.layout.more_info_dialog);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        this.rateButton = rootView.findViewById(R.id.rate_btn);
        this.rateButton.setOnClickListener(this);
        this.emailButton = rootView.findViewById(R.id.email_btn);
        this.emailButton.setOnClickListener(this);
        this.googlePlusButton = rootView.findViewById(R.id.google_plus_btn);
        this.googlePlusButton.setOnClickListener(this);
        this.facebookButton = rootView.findViewById(R.id.facebook_btn);
        this.facebookButton.setOnClickListener(this);
        GridView group = (GridView) rootView.findViewById(R.id.ad_group);
        HouseAds ads = HouseAds.getInstance(getActivity(), null, getResources().getBoolean(R.bool.amazon));
        group.setAdapter(new HouseAdAdapter(getActivity(), ads, ads.getFullHouseAds()));
        return rootView;
    }

    public void onClick(View v) {
        String buttonHit = "";
        String extraInfo = "";
        if (v == this.rateButton) {
            AppRater.goToRatingPage(getActivity());
            buttonHit = "Rate";
        } else if (v == this.emailButton) {
            sendEmail(getActivity(), null, null, null);
            buttonHit = "Email";
        } else if (v == this.googlePlusButton) {
            followUsOnGooglePlus();
            buttonHit = "Google Plus";
        } else if (v == this.facebookButton) {
            likeUsOnFacebook();
            buttonHit = "Facebook";
        } else if (Log.isE) {
            Log.e("MoreInfoFragment", "Nobody to handle it");
            return;
        } else {
            return;
        }
        AnalyticsHelper.getInstance().sendEvent("more_info", buttonHit, extraInfo, 0);
    }

    public static void sendEmail(Activity activity, String titleTag, String body, Throwable exception) {
        Intent i = new Intent("android.intent.action.SEND");
        i.setType("text/plain");
        i.putExtra("android.intent.extra.EMAIL", new String[]{"givewaygames@gmail.com"});
        String title = activity.getResources().getString(R.string.app_name);
        if (titleTag != null) {
            title = title + " - " + titleTag;
        }
        i.putExtra("android.intent.extra.SUBJECT", title);
        if (body == null && exception != null) {
            body = "";
        }
        if (exception != null) {
            StringWriter sw = new StringWriter();
            exception.printStackTrace(new PrintWriter(sw));
            body = (body + "\n\n") + sw.toString();
        }
        if (body != null) {
            i.putExtra("android.intent.extra.TEXT", body);
        }
        try {
            activity.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(R.string.no_email_clients, 0);
        }
    }

    public void followUsOnGooglePlus() {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("https://plus.google.com/105038533175648913476/posts"));
        if (isCallable(intent)) {
            getActivity().startActivity(intent);
        } else {
            Toast.makeText(R.string.no_internet_permission, 0);
        }
    }

    public void likeUsOnFacebook() {
        Intent intent;
        try {
            getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
            intent = new Intent("android.intent.action.VIEW", Uri.parse("fb://profile/393486050699652"));
        } catch (Exception e) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/GivewayGames"));
        }
        if (isCallable(intent)) {
            getActivity().startActivity(intent);
        } else {
            Toast.makeText(R.string.no_internet_permission, 0);
        }
    }

    protected void onClickPositive(View v) {
    }

    protected void onClickNegative(View v) {
    }

    private boolean isCallable(Intent intent) {
        return getActivity().getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }
}
