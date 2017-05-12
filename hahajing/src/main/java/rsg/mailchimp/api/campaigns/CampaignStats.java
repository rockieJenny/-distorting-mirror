package rsg.mailchimp.api.campaigns;

import java.util.Date;
import java.util.Map;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class CampaignStats implements RPCStructConverter {
    public Integer abuseReports;
    public Integer clicks;
    public Integer emailsSent;
    public Integer forwards;
    public Integer forwardsOpens;
    public Integer hardBounces;
    public Date lastClick;
    public Date lastOpen;
    public Integer opens;
    public Integer softBounces;
    public Integer syntaxErrors;
    public Integer uniqueClicks;
    public Integer uniqueOpens;
    public Integer unsubscribes;
    public Integer usersWhoClicked;

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        Utils.populateObjectFromRPCStruct(this, struct, true, new String[0]);
    }
}
