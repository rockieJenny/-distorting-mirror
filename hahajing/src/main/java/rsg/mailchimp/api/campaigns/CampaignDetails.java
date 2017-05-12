package rsg.mailchimp.api.campaigns;

import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.util.Date;
import java.util.Map;
import rsg.mailchimp.api.Constants.CampaignStatus;
import rsg.mailchimp.api.Constants.CampaignType;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class CampaignDetails implements RPCStructConverter {
    public String analytics;
    public String analyticsTag;
    public String archiveUrl;
    public Date createTime;
    public Integer emailsSent;
    public Integer folderId;
    public String fromEmail;
    public String fromName;
    public String id;
    public Boolean inlineCss;
    public String listId;
    public String segmentText;
    public Date sendTime;
    public CampaignStatus status;
    public String subject;
    public String title;
    public String toEmail;
    public Boolean trackClicksHtml;
    public Boolean trackClicksText;
    public Boolean trackOpens;
    public CampaignType type;
    public Integer webId;

    public void populateFromRPCStruct(String key, Map vals) throws MailChimpApiException {
        Utils.populateObjectFromRPCStruct(this, vals, true, "status", AnalyticsSQLiteHelper.EVENT_LIST_TYPE);
        String statusVal = (String) vals.get("status");
        if (statusVal != null) {
            this.status = CampaignStatus.valueOf(statusVal);
        }
        String typeVal = (String) vals.get(AnalyticsSQLiteHelper.EVENT_LIST_TYPE);
        if (typeVal != null) {
            this.type = CampaignType.valueOf(typeVal);
        }
    }
}
