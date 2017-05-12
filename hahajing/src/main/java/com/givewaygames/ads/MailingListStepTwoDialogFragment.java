package com.givewaygames.ads;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class MailingListStepTwoDialogFragment extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new Builder(getActivity()).setTitle(R.string.subscribe).setMessage(R.string.subscribe_part_deux).setPositiveButton(17039370, null).create();
    }
}
