package rsg.mailchimp.api.campaigns;

import java.util.Map;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class EmailDomainPerformance implements RPCStructConverter {
    public Integer bounces;
    public Integer bouncesPct;
    public Integer clicks;
    public Integer clicksPct;
    public Integer delivered;
    public String domain;
    public Integer emails;
    public Integer emailsPct;
    public Integer opens;
    public Integer opensPct;
    public Integer totalSent;
    public Integer unsubs;
    public Integer unsubsPct;

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        Utils.populateObjectFromRPCStruct(this, struct, true, new String[0]);
    }
}
