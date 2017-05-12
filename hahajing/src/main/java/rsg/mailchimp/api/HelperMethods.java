package rsg.mailchimp.api;

import android.content.Context;
import java.util.List;
import java.util.Map;
import rsg.mailchimp.api.data.AccountDetails;
import rsg.mailchimp.api.data.ChimpChatter;

public class HelperMethods extends MailChimpApi {
    public HelperMethods(CharSequence apiKey) {
        super(apiKey);
    }

    public HelperMethods(Context ctx) {
        super(ctx);
    }

    public String ping() throws MailChimpApiException {
        return (String) callMethod("ping", new Object[0]);
    }

    public List<ChimpChatter> chimpChatter() throws MailChimpApiException {
        return callListMethod(ChimpChatter.class, "chimpChatter", new Object[0]);
    }

    public AccountDetails getAccountDetails() throws MailChimpApiException {
        Object obj = callMethod("getAccountDetails", new Object[0]);
        if (obj == null || !(obj instanceof Map)) {
            return null;
        }
        AccountDetails details = new AccountDetails();
        details.populateFromRPCStruct(null, (Map) obj);
        return details;
    }

    public String inlineCss(String html, Boolean stripCss) throws MailChimpApiException {
        boolean z = false;
        String str = "inlineCss";
        Object[] objArr = new Object[2];
        objArr[0] = html;
        if (stripCss != null) {
            z = stripCss.booleanValue();
        }
        objArr[1] = Boolean.valueOf(z);
        return (String) callMethod(str, objArr);
    }
}
