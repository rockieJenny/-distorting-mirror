package rsg.mailchimp.api.campaigns;

import java.util.Date;
import java.util.Map;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class BounceMessage implements RPCStructConverter {
    public Date date;
    public String email;
    public String message;

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        Utils.populateObjectFromRPCStruct(this, struct, true, new String[0]);
    }
}
