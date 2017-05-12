package com.givewaygames.camera.ui;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import com.givewaygames.camera.activities.CameraFilterActivity;
import com.givewaygames.camera.fragments.BaseDialogFragment;

public class FragmentAlertDialog extends BaseDialogFragment implements OnClickListener {
    public static final String ARG_CANCEL_ID = "cancel_id";
    public static final String ARG_CANCEL_TEXT = "cancel";
    public static final String ARG_IS_CANCELABLE = "arg_is_cancelable";
    public static final String ARG_MESSAGE = "message";
    public static final String ARG_MESSAGE_STR = "message_str";
    public static final String ARG_OK_ID = "ok_id";
    public static final String ARG_OK_TEXT = "ok";
    public static final String ARG_TITLE = "title";
    public static final String ARG_TITLE_STR = "title_str";

    public static class Builder {
        Bundle bundle = new Bundle();
        boolean isCancelable = true;
        int message;
        String messageStr = null;
        int negativeId;
        int negativeText;
        int positiveId;
        int positiveText;
        int title;
        String titleStr = null;

        public Builder setTitle(int id) {
            this.title = id;
            return this;
        }

        public Builder setMessage(int id) {
            this.message = id;
            return this;
        }

        public Builder setTitle(String text) {
            this.titleStr = text;
            return this;
        }

        public Builder setMessage(String text) {
            this.messageStr = text;
            return this;
        }

        public Builder setNegativeButton(int negative, int negativeId) {
            this.negativeText = negative;
            this.negativeId = negativeId;
            return this;
        }

        public Builder setPositiveButton(int positive, int positiveId) {
            this.positiveText = positive;
            this.positiveId = positiveId;
            return this;
        }

        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        public void setBundle(Bundle bundle) {
            this.bundle = bundle;
        }

        public DialogFragment show(CameraFilterActivity activity, String tag) {
            if (activity.isFinishing()) {
                return null;
            }
            DialogFragment frag = new FragmentAlertDialog();
            Bundle args = new Bundle();
            args.putInt("title", this.title);
            args.putInt("message", this.message);
            args.putString(FragmentAlertDialog.ARG_TITLE_STR, this.titleStr);
            args.putString(FragmentAlertDialog.ARG_MESSAGE_STR, this.messageStr);
            args.putInt(FragmentAlertDialog.ARG_OK_TEXT, this.positiveText);
            args.putInt(FragmentAlertDialog.ARG_CANCEL_TEXT, this.negativeText);
            args.putInt(FragmentAlertDialog.ARG_OK_ID, this.positiveId);
            args.putInt(FragmentAlertDialog.ARG_CANCEL_ID, this.negativeId);
            args.putBoolean(FragmentAlertDialog.ARG_IS_CANCELABLE, this.isCancelable);
            frag.setArguments(args);
            frag.show(activity.getSupportFragmentManager(), tag);
            return frag;
        }
    }

    public boolean closeOnOutsideClick() {
        return getArguments().getBoolean(ARG_IS_CANCELABLE, true);
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String titleStr = getArguments().getString(ARG_TITLE_STR);
        String messageStr = getArguments().getString(ARG_MESSAGE_STR);
        if (titleStr == null) {
            titleStr = getString(getArguments().getInt("title"));
        }
        if (messageStr == null) {
            messageStr = getString(getArguments().getInt("message"));
        }
        int cancelText = getArguments().getInt(ARG_CANCEL_TEXT);
        int okText = getArguments().getInt(ARG_OK_TEXT);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity()).setTitle(titleStr).setMessage(messageStr).setCancelable(false);
        if (cancelText != 0) {
            builder.setNegativeButton(cancelText, this);
        }
        if (okText != 0) {
            builder.setPositiveButton(okText, this);
        }
        return builder.create();
    }

    protected void onClickPositive() {
        Activity activity = getActivity();
        if (activity instanceof CameraFilterActivity) {
            ((CameraFilterActivity) activity).onAlertDialogPositiveButton(getArguments().getInt(ARG_OK_ID), getArguments());
        }
    }

    protected void onClickNegative() {
        Activity activity = getActivity();
        if (activity instanceof CameraFilterActivity) {
            ((CameraFilterActivity) activity).onAlertDialogNegativeButton(getArguments().getInt(ARG_CANCEL_ID), getArguments());
        }
    }

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case -2:
                onClickNegative();
                return;
            case -1:
                onClickPositive();
                return;
            default:
                return;
        }
    }
}
