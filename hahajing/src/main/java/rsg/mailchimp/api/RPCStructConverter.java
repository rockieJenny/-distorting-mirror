package rsg.mailchimp.api;

import java.util.Map;

public interface RPCStructConverter {
    void populateFromRPCStruct(String str, Map map) throws MailChimpApiException;
}
