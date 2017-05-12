package rsg.mailchimp.api.data;

import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.util.Map;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class ChimpChatter implements RPCStructConverter {
    public String campaignId;
    public String listId;
    public String message;
    public Type type;
    public String updateTime;

    public enum Type {
        scheduled,
        sent,
        inspection,
        subscribes,
        unsubscribes,
        low_credits,
        absplit,
        best_opens,
        best_clicks,
        abuse
    }

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        Utils.populateObjectFromRPCStruct(this, struct, true, AnalyticsSQLiteHelper.EVENT_LIST_TYPE);
        this.type = Type.valueOf((String) struct.get(AnalyticsSQLiteHelper.EVENT_LIST_TYPE));
    }
}
