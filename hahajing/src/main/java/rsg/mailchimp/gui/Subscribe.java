package rsg.mailchimp.gui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.givewaygames.goofyglass.R;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.lists.ListMethods;
import rsg.mailchimp.api.lists.MergeFieldListUtil;

public class Subscribe extends Activity implements OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ad_internal_group_inner_single);
        ((Button) findViewById(R.raw.bloom_frag)).setOnClickListener(this);
        ((Button) findViewById(R.raw.bloom_frag_opt)).setOnClickListener(this);
    }

    public void onClick(View clicked) {
        Log.d(getClass().getName(), "Clicked: " + clicked.toString());
        switch (clicked.getId()) {
            case R.raw.bloom_frag:
                final ProgressDialog dialog = ProgressDialog.show(this, getResources().getText(R.anim.frag_in), getResources().getText(R.anim.frag_out), true, false);
                new Thread(new Runnable() {
                    public void run() {
                        EditText text = (EditText) Subscribe.this.findViewById(R.raw.basic_vert);
                        if (text.getText() != null && text.getText().toString().trim().length() > 0) {
                            Subscribe.this.addToList(text.getText().toString(), dialog);
                        }
                    }
                }).start();
                return;
            case R.raw.bloom_frag_opt:
                ((EditText) findViewById(R.raw.basic_vert)).setText("");
                return;
            default:
                Log.e("MailChimp", "Unable to handle onClick for view " + clicked.toString());
                return;
        }
    }

    private void addToList(String emailAddy, ProgressDialog dialog) {
        MergeFieldListUtil mergeFields = new MergeFieldListUtil();
        mergeFields.addEmail(emailAddy);
        try {
            mergeFields.addDateField("BIRFDAY", new SimpleDateFormat("MM/dd/yyyy").parse("07/30/2007"));
        } catch (ParseException e1) {
            Log.e("MailChimp", "Couldn't parse date, boo: " + e1.getMessage());
        }
        mergeFields.addField("FNAME", "Ona");
        mergeFields.addField("LNAME", "StoutMuntz");
        try {
            new ListMethods(getResources().getText(R.anim.fade_in)).listSubscribe(getText(R.anim.fade_out).toString(), emailAddy, mergeFields);
        } catch (MailChimpApiException e) {
            Log.e("MailChimp", "Exception subscribing person: " + e.getMessage());
        }
        dialog.cancel();
    }
}
