package rsg.mailchimp.api.lists;

import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import rsg.mailchimp.api.Constants.EmailType;
import rsg.mailchimp.api.Constants.SubscribeStatus;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class MemberInfo implements RPCStructConverter {
    public String campaignId;
    public String email;
    public EmailType emailType;
    public String id;
    public String ipOpt;
    public String ipSignup;
    public MailChimpListStatus[] lists;
    public Integer memberRating;
    public MergeFieldListUtil mergeFields;
    public SubscribeStatus status;
    public Date timestamp;

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        Utils.populateObjectFromRPCStruct(this, struct, true, "emailType", "status", "lists", "merges");
        Object rpcObj = struct.get("email_type");
        if (rpcObj != null) {
            this.emailType = EmailType.valueOf((String) rpcObj);
        }
        rpcObj = struct.get("status");
        if (rpcObj != null) {
            this.status = SubscribeStatus.valueOf((String) rpcObj);
        }
        rpcObj = struct.get("merges");
        this.mergeFields = new MergeFieldListUtil();
        if (rpcObj instanceof Map) {
            this.mergeFields.putAll((Map) struct.get("merges"));
        }
        rpcObj = struct.get("lists");
        if (rpcObj instanceof Map) {
            Set<Entry<String, Object>> entries = ((Map) rpcObj).entrySet();
            this.lists = new MailChimpListStatus[entries.size()];
            int i = 0;
            for (Entry<String, Object> entry : entries) {
                this.lists[i] = new MailChimpListStatus();
                this.lists[i].listId = (String) entry.getKey();
                this.lists[i].status = SubscribeStatus.valueOf(entry.getValue().toString());
                i++;
            }
        }
    }
}
