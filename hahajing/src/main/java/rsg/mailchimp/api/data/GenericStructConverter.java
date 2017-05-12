package rsg.mailchimp.api.data;

import java.util.Map;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public abstract class GenericStructConverter implements RPCStructConverter {
    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        Utils.populateObjectFromRPCStruct(this, struct, true, new String[0]);
    }
}
