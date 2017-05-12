package com.givewaygames.camera.fragments;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import com.givewaygames.camera.activities.CameraFilterActivity;

public abstract class BaseDialogFragment extends DialogFragment {
    public abstract boolean closeOnOutsideClick();

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof CameraFilterActivity) {
            ((CameraFilterActivity) activity).dialogFragments.add(this);
            return;
        }
        throw new RuntimeException("BaseDialogFragment must have a CameraFilterActivity parent");
    }

    public void onDetach() {
        super.onDetach();
        ((CameraFilterActivity) getActivity()).closeDialogFragment(this);
    }
}
