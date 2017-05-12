package com.android.camera.ui;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import com.android.camera.Util;
import com.givewaygames.camera.activities.CameraFilterActivity;
import com.givewaygames.camera.state.AppState;
import com.givewaygames.camera.utils.Toast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import demo.com.hahajing.R;

public class SharePopup extends Fragment implements OnClickListener, OnItemClickListener{
    private static final String ADAPTER_COLUMN_ICON = "icon";
    private static final String ARG_URI = "uri";
    private boolean disableAnimations = false;
    private ArrayList<ComponentName> mComponent = new ArrayList();
    private final ViewBinder mViewBinder = new ViewBinder() {
        public boolean setViewValue(View view, Object data, String text) {
            if (!(view instanceof ImageView)) {
                return false;
            }
            ((ImageView) view).setImageDrawable((Drawable) data);
            return true;
        }
    };
    private String mimeType;
    private Drawable providedThumbnail = null;
    private ViewGroup root;
    private String scannedPath;
    private ViewGroup shareGroup;
    private ViewGroup shareList;
    private ImageView thumbnail;

    public static SharePopup newInstance(Uri uri) {
        SharePopup sharePopup = new SharePopup();
        Bundle args = new Bundle();
        args.putParcelable(ARG_URI, uri);
        sharePopup.setArguments(args);
        return sharePopup;
    }

    public void disableAnimations() {
        this.disableAnimations = true;
    }

    public void setThumbnailOnCreate(Drawable providedThumbnail) {
        this.providedThumbnail = providedThumbnail;
    }

    public Drawable getThumbnail() {
        return this.thumbnail != null ? this.thumbnail.getDrawable() : null;
    }

    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (!this.disableAnimations) {
            return super.onCreateAnimation(transit, enter, nextAnim);
        }
        Animation animation = new Animation() {
        };
        animation.setDuration(0);
        return animation;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Uri uri = getUri();
        this.mimeType = uri != null ? activity.getContentResolver().getType(uri) : null;
        if (this.mimeType == null) {
            this.mimeType = "image/";
        }
        ((CameraFilterActivity) activity).dialogFragments.add(this);
    }

    public void onDetach() {
        super.onDetach();
        ((CameraFilterActivity) getActivity()).closeDialogFragment(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.root = (ViewGroup) inflater.inflate(R.layout.share_popup, container, false);
        View shareEither = this.root.findViewById(R.id.share_list_vertical);
        if (shareEither == null) {
            shareEither = this.root.findViewById(R.id.share_list_horizontal);
        }
        this.shareGroup = (ViewGroup) this.root.findViewById(R.id.share_group);
        this.shareList = (ViewGroup) shareEither;
        this.thumbnail = (ImageView) this.root.findViewById(R.id.thumbnail);
        this.thumbnail.setOnClickListener(this);
        this.root.findViewById(R.id.goto_gallery_button).setOnClickListener(this);
        createShareMenu(getActivity());
        MediaScannerConnection.scanFile(getActivity(), new String[]{getUri().toString()}, null, new MediaScannerConnectionClient() {
            public void onMediaScannerConnected() {
            }

            public void onScanCompleted(String path, Uri uri) {
                SharePopup.this.scannedPath = path;
            }
        });
        if (this.providedThumbnail != null) {
            this.thumbnail.setImageDrawable(this.providedThumbnail);
            this.providedThumbnail = null;
        } else {
            Picasso.with(getActivity()).load(getUri()).fit().centerInside().into(this.thumbnail);
        }
        refreshShareLayout();
        return this.root;
    }

    public void onClick(View v) {
        if (getActivity() != null) {
            int id = v.getId();
            if (id == R.id.goto_gallery_button || id == R.id.thumbnail) {
                onClickGallery(v.getContext());
            }
        }
    }

    public void createShareMenu(Activity activity) {
        PackageManager packageManager = activity.getPackageManager();
        List<ResolveInfo> infos = packageManager.queryIntentActivities(new Intent("android.intent.action.SEND").setType(this.mimeType), 0);
        ArrayList<HashMap<String, Object>> items = new ArrayList();
        for (ResolveInfo info : infos) {
            ComponentName component = new ComponentName(info.activityInfo.packageName, info.activityInfo.name);
            HashMap<String, Object> map = new HashMap();
            map.put("icon", info.loadIcon(packageManager));
            items.add(map);
            this.mComponent.add(component);
        }
        ListAdapter listItemAdapter = new SimpleAdapter(activity, items, R.layout.share_icon, new String[]{"icon"}, new int[]{R.id.icon});
        listItemAdapter.setViewBinder(this.mViewBinder);
        if (this.shareList instanceof HListView) {
            ((HListView) this.shareList).setAdapter(listItemAdapter);
            ((HListView) this.shareList).setOnItemClickListener(this);
            return;
        }
        ((ListView) this.shareList).setAdapter(listItemAdapter);
        ((ListView) this.shareList).setOnItemClickListener(this);
    }

    public Uri getUri() {
        return (Uri) getArguments().getParcelable(ARG_URI);
    }

    public void setUri(Uri uri) {
        getArguments().putParcelable(ARG_URI, uri);
    }

    public void onItemClick(android.widget.AdapterView<?> adapterView, View view, int index, long id) {
        onItemClickGeneric(view, index);
    }

    private void onClickGallery(Context context) {
        Activity activity = getActivity();
        if (activity != null) {
            if (getUri().getScheme().equals("file") && this.scannedPath == null) {
                invalidSearchType(context);
            } else {
                Util.viewUri(getUri(), activity);
            }
        }
    }

    private void onItemClickGeneric(View view, int index) {
        Activity activity = getActivity();
        if (activity != null) {
            Uri uri = getUri();
            if (uri.getScheme().equals("file") && this.scannedPath == null) {
                invalidSearchType(view.getContext());
                return;
            }
            Intent intent = new Intent("android.intent.action.SEND");
            intent.setType(this.mimeType);
            intent.putExtra("android.intent.extra.STREAM", uri);
            intent.setComponent((ComponentName) this.mComponent.get(index));
            activity.startActivity(intent);
        }
    }

    private void invalidSearchType(Context c) {
        Toast.makeText(R.string.waiting_for_full_quality, 1);
    }

    private void refreshShareLayout() {
        if (AppState.getInstance().getUiState().isLandscapeLeft()) {
            LayoutParams params = (LayoutParams) this.thumbnail.getLayoutParams();
            params.addRule(0, 0);
            params.addRule(1, R.id.share_group);
            params = (LayoutParams) this.shareGroup.getLayoutParams();
            params.addRule(11, 0);
            params.addRule(9, -1);
            FrameLayout.LayoutParams p = (FrameLayout.LayoutParams) this.root.getLayoutParams();
            p.leftMargin = p.rightMargin;
            p.rightMargin = 0;
        }
    }
}
