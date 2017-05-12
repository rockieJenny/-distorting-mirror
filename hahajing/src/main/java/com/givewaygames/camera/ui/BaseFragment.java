package com.givewaygames.camera.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import com.givewaygames.camera.activities.CameraFilterActivity;
import com.givewaygames.goofyglass.R;

public abstract class BaseFragment extends DialogFragment {
    int layoutId;

    public class ClickStealer implements OnClickListener {
        public void onClick(View v) {
            if (BaseFragment.this.closeOnOutsideClick() && BaseFragment.this.getActivity() != null && !BaseFragment.this.getActivity().isFinishing()) {
                BaseFragment.this.getActivity().getSupportFragmentManager().popBackStack();
            }
        }
    }

    public abstract boolean closeOnOutsideClick();

    protected abstract void onClickNegative(View view);

    protected abstract void onClickPositive(View view);

    protected void initialize(CameraFilterActivity activity, int layoutId) {
        activity.dialogFragments.add(this);
        this.layoutId = layoutId;
    }

    public void onDetach() {
        super.onDetach();
        ((CameraFilterActivity) getActivity()).closeDialogFragment(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View trueRoot = inflater.inflate(this.layoutId, container, false);
        FrameLayout root = (FrameLayout) trueRoot.findViewById(R.id.rotate_root);
        Button cancel = (Button) root.findViewById(R.id.cancel_button);
        if (cancel != null) {
            cancel.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (BaseFragment.this.getActivity() != null && !BaseFragment.this.getActivity().isFinishing()) {
                        BaseFragment.this.getActivity().getSupportFragmentManager().popBackStack();
                        BaseFragment.this.onClickNegative(v);
                    }
                }
            });
        }
        Button ok = (Button) root.findViewById(R.id.ok_button);
        if (ok != null) {
            ok.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (BaseFragment.this.getActivity() != null && !BaseFragment.this.getActivity().isFinishing()) {
                        BaseFragment.this.getActivity().getSupportFragmentManager().popBackStack();
                        BaseFragment.this.onClickPositive(v);
                    }
                }
            });
        }
        trueRoot.setOnClickListener(new ClickStealer());
        return trueRoot;
    }
}
