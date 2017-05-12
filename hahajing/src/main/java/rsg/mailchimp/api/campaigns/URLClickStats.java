package rsg.mailchimp.api.campaigns;

import java.util.Map;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class URLClickStats implements RPCStructConverter {
    public Integer clicks;
    public Integer unique;
    public String url;

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        this.url = key;
        Utils.populateObjectFromRPCStruct(this, struct, true, new String[0]);
    }
}
