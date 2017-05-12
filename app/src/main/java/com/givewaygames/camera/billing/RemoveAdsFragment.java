package com.givewaygames.camera.billing;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.givewaygames.ads.MailingListSignupDialogFragment;
import com.givewaygames.ads.SubscribeManager;
import com.givewaygames.camera.activities.CameraFilterActivity;
import com.givewaygames.camera.fragments.BaseDialogFragment;
import com.givewaygames.camera.utils.AnalyticsHelper;
import com.givewaygames.goofyglass.R;

public class RemoveAdsFragment extends BaseDialogFragment implements OnClickListener {
    public boolean closeOnOutsideClick() {
        return true;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String price = ((CameraFilterActivity) getActivity()).getBillingWrapper().price;
        return new Builder(getActivity()).setNegativeButton(17039360, null).setPositiveButton(getString(R.string.remove_and_price, price), this).setTitle(R.string.remove_ads).setMessage(getString(R.string.remove_ads_details, price)).create();
    }

    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case -3:
                new MailingListSignupDialogFragment().show(getActivity().getSupportFragmentManager(), SubscribeManager.FRAG_MAILING_LIST_SIGNUP);
                return;
            case -1:
                CameraFilterActivity activity = (CameraFilterActivity) getActivity();
                if (activity != null) {
                    activity.getBillingWrapper().unlockAll(activity);
                    AnalyticsHelper.getInstance().sendEvent("buy_event", "donate", "yes", 0);
                    return;
                }
                return;
            default:
                return;
        }
    }
}
