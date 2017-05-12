package com.givewaygames.camera.activities;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.internal.DebouncingOnClickListener;
import com.givewaygames.goofyglass.R;

public class CameraFilterActivity$$ViewInjector {
    public static void inject(Finder finder, final CameraFilterActivity target, Object source) {
        finder.findRequiredView(source, R.id.random_button, "method 'onRandomClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onRandomClicked();
            }
        });
        finder.findRequiredView(source, R.id.take_photo_image, "method 'onTakePhotoClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onTakePhotoClicked();
            }
        });
        finder.findRequiredView(source, R.id.tiles_button, "method 'onMenuClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onMenuClicked();
            }
        });
        finder.findRequiredView(source, R.id.import_image_btn, "method 'onImportClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onImportClicked();
            }
        });
        finder.findRequiredView(source, R.id.camera_image_btn, "method 'onCameraClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onCameraClicked();
            }
        });
        finder.findRequiredView(source, R.id.famous_image_btn, "method 'onFamousClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onFamousClicked();
            }
        });
        finder.findRequiredView(source, R.id.photo_thumbnail, "method 'onViewTakenPhotoClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onViewTakenPhotoClicked();
            }
        });
        finder.findRequiredView(source, R.id.more_info_btn, "method 'onMoreInfoClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onMoreInfoClicked();
            }
        });
        finder.findRequiredView(source, R.id.unlock_button, "method 'onViewTakenPhoto'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onViewTakenPhoto();
            }
        });
        finder.findRequiredView(source, R.id.switch_camera, "method 'onSwitchCameraClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onSwitchCameraClicked();
            }
        });
        finder.findRequiredView(source, R.id.switch_mode, "method 'onSwitchModeClicked'").setOnClickListener(new DebouncingOnClickListener() {
            public void doClick(View p0) {
                target.onSwitchModeClicked();
            }
        });
    }

    public static void reset(CameraFilterActivity target) {
    }
}
