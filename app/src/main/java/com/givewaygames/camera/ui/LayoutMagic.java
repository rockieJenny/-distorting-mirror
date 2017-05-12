package com.givewaygames.camera.ui;

import android.content.res.Resources;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.givewaygames.camera.state.AppState;
import com.givewaygames.goofyglass.R;

public class LayoutMagic {
    public void refreshPhotoThumbnail(AppState state, ViewHolder viewHolder) {
        if (state.getUiState().isLandscapeLeft()) {
            View thumbnail = viewHolder.photoTaken;
            removeParams(thumbnail);
            addParams(thumbnail, 12, -1);
            addParams(thumbnail, 0, R.id.tertiary_menu);
        }
    }

    public void refreshAdLayout(AppState state, ViewHolder viewHolder) {
        if (state.getUiState().isLandscapeLeft()) {
            View ad = viewHolder.internalAd;
            removeParams(ad);
            addParams(ad, 10, -1);
            addParams(ad, 0, R.id.tertiary_menu);
        }
    }

    public void refreshActionBarUI(AppState state, ViewHolder viewHolder) {
        if (state.getUiState().isLandscapeLeft()) {
            View actionBar = viewHolder.fadeActionGroup;
            View moreInfo = viewHolder.moreInfoButton;
            View unlock = viewHolder.unlockButton;
            View switchCamera = viewHolder.switchCameraButton;
            View switchMode = viewHolder.switchModeButton;
            View filterGroup = viewHolder.getFilterGroup();
            View modeMenu = viewHolder.modeMenu;
            for (View v : new View[]{actionBar, moreInfo, unlock, switchCamera, switchMode, modeMenu, filterGroup}) {
                removeParams(v);
            }
            View first = modeMenu.getChildAt(0);
            View mid = modeMenu.getChildAt(1);
            View last = modeMenu.getChildAt(2);
            modeMenu.removeAllViews();
            modeMenu.addView(last);
            modeMenu.addView(mid);
            modeMenu.addView(first);
            addParams(actionBar, 11, -1);
            addParams(moreInfo, 12, -1);
            addParams(unlock, 2, R.id.more_info_btn);
            addParams(switchMode, 10, -1);
            addParams(switchCamera, 3, R.id.switch_mode);
            addParams(modeMenu, 10, -1);
            addParams(modeMenu, 0, R.id.tertiary_menu);
            addParams(filterGroup, 1, R.id.shutter_group);
        }
    }

    public void refreshPreviewLayout(Resources resources, AppState appState, ViewHolder viewHolder) {
        int i;
        View preview = viewHolder.previewRelativeLayout;
        View hiddenRow = viewHolder.hiddenRow;
        boolean isPortrait = appState.getUiState().isPortrait();
        boolean isLandscapeRight = appState.getUiState().isLandscapeRight();
        LayoutParams params = (LayoutParams) preview.getLayoutParams();
        LayoutParams rowParams = (LayoutParams) hiddenRow.getLayoutParams();
        removeParams(preview);
        removeParams(hiddenRow);
        int bh = (int) (resources.getDimension(R.dimen.bottom_height) + 0.5f);
        if (isPortrait) {
            i = -1;
        } else {
            i = bh;
        }
        rowParams.width = i;
        if (!isPortrait) {
            bh = -1;
        }
        rowParams.height = bh;
        int align = isPortrait ? 12 : isLandscapeRight ? 11 : 9;
        rowParams.addRule(align, -1);
        int toThe = isPortrait ? 2 : isLandscapeRight ? 0 : 1;
        params.addRule(toThe, R.id.hidden_bottom_row);
    }

    public void refreshShutterGroup(AppState appState, ViewHolder viewHolder) {
        if (appState.getUiState().isLandscapeLeft()) {
            View random = viewHolder.randomButton;
            View shutterGroup = viewHolder.shutterGroup;
            View menu = viewHolder.tilesButton;
            View takePhoto = viewHolder.takePhotoButton;
            removeParams(menu);
            removeParams(takePhoto);
            addParams(shutterGroup, 9, -1);
            addParams(shutterGroup, 11, 0);
            addParams(random, 9, -1);
            addParams(random, 11, 0);
            addParams(takePhoto, 12, -1);
            addParams(takePhoto, 14, -1);
            addParams(menu, 10, -1);
            addParams(menu, 14, -1);
        }
    }

    private void removeParams(View v) {
        int[] rules = ((LayoutParams) v.getLayoutParams()).getRules();
        for (int i = 0; i < rules.length; i++) {
            rules[i] = 0;
        }
    }

    private void addParams(View v, int verb, int anchor) {
        ((LayoutParams) v.getLayoutParams()).addRule(verb, anchor);
    }
}
