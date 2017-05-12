package rsg.mailchimp.api.campaigns;

import java.util.Map;
import org.xmlrpc.android.IXMLRPCSerializer;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class CampaignAnalytics implements RPCStructConverter {
    public Integer bounces;
    public Integer ecommConversions;
    public Integer goalConversions;
    public Double goalValue;
    public GoalConversions[] goals;
    public Integer newVisits;
    public Integer pages;
    public Double revenue;
    public Double timeOnSite;
    public Integer transactions;
    public Integer visits;

    public class GoalConversions {
        public Integer conversions;
        public String name;
    }

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        Utils.populateObjectFromRPCStruct(this, struct, true, new String[0]);
        Object goalsObj = struct.get("goals");
        if (goalsObj != null && (goalsObj instanceof Object[])) {
            Object[] goals = (Object[]) goalsObj;
            this.goals = new GoalConversions[goals.length];
            int i = 0;
            for (Map goalStruct : goals) {
                this.goals[i] = new GoalConversions();
                this.goals[i].name = (String) goalStruct.get(IXMLRPCSerializer.TAG_NAME);
                this.goals[i].conversions = Integer.valueOf(((Number) goalStruct.get("conversions")).intValue());
                i++;
            }
        }
    }
}
