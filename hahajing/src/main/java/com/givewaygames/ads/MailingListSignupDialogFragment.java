package com.givewaygames.ads;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class MailingListSignupDialogFragment extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final FragmentActivity activity = getActivity();
        final View root = activity.getLayoutInflater().inflate(R.layout.subscribe_layout, null, false);
        final AlertDialog alertDialog = new Builder(getActivity()).setTitle(R.string.hate_ads_).setMessage(R.string.subscribe_question_1).setView(root).setPositiveButton(R.string.subscribe, null).setNegativeButton(17039360, null).create();
        alertDialog.setOnShowListener(new OnShowListener() {
            public void onShow(DialogInterface dialog) {
                alertDialog.getButton(-1).setOnClickListener(new OnClickListener() {
                    public void onClick(View view) {
                        EditText emailTextEdit = (EditText) root.findViewById(R.id.email);
                        final String email = emailTextEdit.getText().toString();
                        final String first = ((EditText) root.findViewById(R.id.first_name)).getText().toString();
                        final String last = ((EditText) root.findViewById(R.id.last_name)).getText().toString();
                        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            root.findViewById(R.id.progress_spinner).setVisibility(0);
                            new Thread() {
                                public void run() {
                                    try {
                                        SubscribeManager.getActiveManager().subscribe(email, first, last).join();
                                    } catch (InterruptedException e) {
                                    }
                                    MailingListSignupDialogFragment.this.dismissAllowingStateLoss();
                                    new MailingListStepTwoDialogFragment().show(activity.getSupportFragmentManager(), SubscribeManager.FRAG_MAILING_LIST_SIGNUP);
                                }
                            }.start();
                            return;
                        }
                        emailTextEdit.setError(MailingListSignupDialogFragment.this.getString(R.string.invalid_email_address));
                    }
                });
            }
        });
        return alertDialog;
    }
}
