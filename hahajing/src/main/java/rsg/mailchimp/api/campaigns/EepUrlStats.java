package rsg.mailchimp.api.campaigns;

import java.util.Date;
import java.util.List;
import java.util.Map;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class EepUrlStats implements RPCStructConverter {
    public Date firstRetweet;
    public Date firstTweet;
    public Date lastRetweet;
    public Date lastTweet;
    public Integer retweets;
    public String service;
    public List<Status> statuses;
    public Integer tweets;

    public static class Status implements RPCStructConverter {
        public Date datetime;
        public Boolean isRetweet;
        public String screenName;
        public String status;
        public String statusId;

        public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
            Utils.populateObjectFromRPCStruct(this, struct, true, new String[0]);
        }
    }

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        this.service = key;
        Utils.populateObjectFromRPCStruct(this, struct, true, "statuses");
        Object obj = struct.get("statuses");
        if (obj instanceof Object[]) {
            this.statuses = Utils.extractObjectsFromList(Status.class, (Object[]) obj);
        }
    }
}
