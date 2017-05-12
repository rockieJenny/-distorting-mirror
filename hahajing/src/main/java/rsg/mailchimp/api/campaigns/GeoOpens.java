package rsg.mailchimp.api.campaigns;

import java.util.Map;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class GeoOpens implements RPCStructConverter {
    public String code;
    public String name;
    public Integer opens;
    public Boolean regionDetail;

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        Utils.populateObjectFromRPCStruct(this, struct, true, new String[0]);
    }
}
