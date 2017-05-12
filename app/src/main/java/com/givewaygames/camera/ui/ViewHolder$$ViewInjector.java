package com.givewaygames.camera.ui;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife.Finder;
import com.givewaygames.goofyglass.R;
import it.sephiroth.android.library.widget.HListView;

public class ViewHolder$$ViewInjector {
    public static void inject(Finder finder, ViewHolder target, Object source) {
        target.hiddenRow = finder.findRequiredView(source, R.id.hidden_bottom_row, "field 'hiddenRow'");
        target.previewRelativeLayout = (RelativeLayout) finder.findRequiredView(source, R.id.preview_relative_layout, "field 'previewRelativeLayout'");
        target.photoTaken = (ImageView) finder.findRequiredView(source, R.id.photo_thumbnail, "field 'photoTaken'");
        target.shutterGroup = finder.findRequiredView(source, R.id.shutter_group, "field 'shutterGroup'");
        target.randomButton = (ImageView) finder.findRequiredView(source, R.id.random_button, "field 'randomButton'");
        target.takePhotoButton = (ImageView) finder.findRequiredView(source, R.id.take_photo_image, "field 'takePhotoButton'");
        target.tilesButton = (ImageView) finder.findRequiredView(source, R.id.tiles_button, "field 'tilesButton'");
        target.importImageButton = (ImageView) finder.findRequiredView(source, R.id.import_image_btn, "field 'importImageButton'");
        target.famousImageButton = (ImageView) finder.findRequiredView(source, R.id.famous_image_btn, "field 'famousImageButton'");
        target.cameraImageButton = (ImageView) finder.findRequiredView(source, R.id.camera_image_btn, "field 'cameraImageButton'");
        target.moreInfoButton = (ImageView) finder.findRequiredView(source, R.id.more_info_btn, "field 'moreInfoButton'");
        target.switchCameraButton = (ImageView) finder.findRequiredView(source, R.id.switch_camera, "field 'switchCameraButton'");
        target.switchModeButton = (ImageView) finder.findRequiredView(source, R.id.switch_mode, "field 'switchModeButton'");
        target.unlockButton = (ImageView) finder.findRequiredView(source, R.id.unlock_button, "field 'unlockButton'");
        target.loadingFrame = (FrameLayout) finder.findRequiredView(source, R.id.loading_bar, "field 'loadingFrame'");
        target.loadingText = (TextView) finder.findRequiredView(source, R.id.loading_text, "field 'loadingText'");
        target.debugLoadProgress = (TextView) finder.findOptionalView(source, R.id.debug_load_progress);
        target.debugPhotoProgress = (TextView) finder.findOptionalView(source, R.id.debug_photo_progress);
        target.debugExtra = (TextView) finder.findOptionalView(source, R.id.debug_extra);
        target.modeMenu = (ViewGroup) finder.findOptionalView(source, R.id.mode_dropdown);
        target.fadeActionGroup = (ViewGroup) finder.findRequiredView(source, R.id.tertiary_menu, "field 'fadeActionGroup'");
        target.internalAd = (FrameLayout) finder.findRequiredView(source, R.id.internal_ad_root, "field 'internalAd'");
        target.externalAd = (ViewGroup) finder.findRequiredView(source, R.id.external_ad, "field 'externalAd'");
        target.verticalFilterGroup = (ListView) finder.findOptionalView(source, R.id.filter_picker_vertical);
        target.horizontalFilterGroup = (HListView) finder.findOptionalView(source, R.id.filter_picker_horizontal);
    }

    public static void reset(ViewHolder target) {
        target.hiddenRow = null;
        target.previewRelativeLayout = null;
        target.photoTaken = null;
        target.shutterGroup = null;
        target.randomButton = null;
        target.takePhotoButton = null;
        target.tilesButton = null;
        target.importImageButton = null;
        target.famousImageButton = null;
        target.cameraImageButton = null;
        target.moreInfoButton = null;
        target.switchCameraButton = null;
        target.switchModeButton = null;
        target.unlockButton = null;
        target.loadingFrame = null;
        target.loadingText = null;
        target.debugLoadProgress = null;
        target.debugPhotoProgress = null;
        target.debugExtra = null;
        target.modeMenu = null;
        target.fadeActionGroup = null;
        target.internalAd = null;
        target.externalAd = null;
        target.verticalFilterGroup = null;
        target.horizontalFilterGroup = null;
    }
}
