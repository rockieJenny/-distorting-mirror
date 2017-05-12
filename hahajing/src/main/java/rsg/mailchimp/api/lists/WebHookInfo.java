package rsg.mailchimp.api.lists;

import java.util.Map;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;

public class WebHookInfo implements RPCStructConverter {
    public Map<String, Boolean> actions;
    public Map<String, Boolean> sources;
    public String url;

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        this.url = (String) struct.get("url");
        this.actions = (Map) struct.get("actions");
        this.sources = (Map) struct.get("sources");
    }
}
