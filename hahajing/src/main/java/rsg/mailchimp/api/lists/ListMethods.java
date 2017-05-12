package rsg.mailchimp.api.lists;

import android.content.Context;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import rsg.mailchimp.api.Constants;
import rsg.mailchimp.api.Constants.EmailType;
import rsg.mailchimp.api.Constants.SubscribeStatus;
import rsg.mailchimp.api.MailChimpApi;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.Utils;
import rsg.mailchimp.api.campaigns.AbuseReport;

public class ListMethods extends MailChimpApi {
    public ListMethods(CharSequence apiKey) {
        super(apiKey);
    }

    public ListMethods(Context ctx) {
        super(ctx);
    }

    public boolean listSubscribe(String listId, String emailAddress) throws MailChimpApiException {
        return listSubscribe(listId, emailAddress, null, null, null, null, null, null);
    }

    public boolean listSubscribe(String listId, String emailAddress, MergeFieldListUtil mergeFields) throws MailChimpApiException {
        return listSubscribe(listId, emailAddress, mergeFields, null, null, null, null, null);
    }

    public boolean listSubscribe(String listId, String emailAddress, MergeFieldListUtil mergeFields, EmailType emailType) throws MailChimpApiException {
        return listSubscribe(listId, emailAddress, mergeFields, emailType, null, null, null, null);
    }

    public boolean listSubscribe(String listId, String emailAddress, MergeFieldListUtil mergeFields, EmailType emailType, Boolean doubleOptIn, Boolean updateExisting, Boolean replaceInterests, Boolean sendWelcome) throws MailChimpApiException {
        String str = "listSubscribe";
        Object[] objArr = new Object[8];
        objArr[0] = listId;
        objArr[1] = emailAddress;
        if (mergeFields == null) {
            mergeFields = new String[]{""};
        }
        objArr[2] = mergeFields;
        objArr[3] = emailType == null ? "" : emailType.toString();
        if (doubleOptIn == null) {
            doubleOptIn = "";
        }
        objArr[4] = doubleOptIn;
        if (updateExisting == null) {
            updateExisting = "";
        }
        objArr[5] = updateExisting;
        if (replaceInterests == null) {
            replaceInterests = "";
        }
        objArr[6] = replaceInterests;
        if (sendWelcome == null) {
            sendWelcome = "";
        }
        objArr[7] = sendWelcome;
        return ((Boolean) callMethod(str, objArr)).booleanValue();
    }

    public boolean listUnsubscribe(String listId, String emailAddress) throws MailChimpApiException {
        return listUnsubscribe(listId, emailAddress, null, null, null);
    }

    public boolean listUnsubscribe(String listId, String emailAddress, Boolean deleteMember, Boolean sendGoodbye, Boolean sendNotify) throws MailChimpApiException {
        String str = "listUnsubscribe";
        Object[] objArr = new Object[5];
        objArr[0] = listId;
        objArr[1] = emailAddress;
        if (deleteMember == null) {
            deleteMember = "";
        }
        objArr[2] = deleteMember;
        if (sendGoodbye == null) {
            sendGoodbye = "";
        }
        objArr[3] = sendGoodbye;
        if (sendNotify == null) {
            sendNotify = "";
        }
        objArr[4] = sendNotify;
        return ((Boolean) callMethod(str, objArr)).booleanValue();
    }

    public List<String> listsForEmail(String email) throws MailChimpApiException {
        Object obj = callMethod("listsForEmail", email);
        if (obj instanceof Object[]) {
            return Utils.convertObjectArrayToString((Object[]) obj);
        }
        return new ArrayList(0);
    }

    public MemberInfo listMemberInfo(String listId, String emailAddress) throws MailChimpApiException {
        Object obj = callMethod("listMemberInfo", listId, emailAddress);
        if (obj == null || !(obj instanceof Map)) {
            return null;
        }
        MemberInfo info = new MemberInfo();
        info.populateFromRPCStruct(null, (Map) obj);
        return info;
    }

    public List<MemberInfo> listMembers(String listId) throws MailChimpApiException {
        return listMembers(listId, null);
    }

    public List<MemberInfo> listMembers(String listId, SubscribeStatus status) throws MailChimpApiException {
        return listMembers(listId, status, null, null, null);
    }

    public List<MemberInfo> listMembers(String listId, SubscribeStatus status, Integer start, Integer limit, Date since) throws MailChimpApiException {
        ArrayList<Object> params = new ArrayList();
        params.add(listId);
        params.add(status == null ? SubscribeStatus.subscribed.toString() : status.toString());
        if (since != null) {
            params.add(Constants.TIME_FMT.format(since));
        }
        if (start != null) {
            params.add(start);
        }
        if (limit != null) {
            params.add(limit);
        }
        return callListMethod(MemberInfo.class, "listMembers", params.toArray());
    }

    public List<AbuseReport> listAbuseReports(String listId) throws MailChimpApiException {
        return listAbuseReports(listId, null, null, null);
    }

    public List<AbuseReport> listAbuseReports(String listId, Integer start, Integer limit, Date since) throws MailChimpApiException {
        Object limitVal;
        int startVal = start == null ? 0 : start.intValue();
        if (limit == null) {
            limitVal = "";
        } else {
            Integer limitVal2 = limit;
        }
        String sinceVal = since == null ? "" : Constants.TIME_FMT.format(since);
        return callListMethod(AbuseReport.class, "listAbuseReports", listId, Integer.valueOf(startVal), limitVal, sinceVal);
    }

    public BatchResults listBatchSubscribe(String listId, List<MergeFieldListUtil> toSubscribe) throws MailChimpApiException {
        return listBatchSubscribe(listId, toSubscribe, null, null, null);
    }

    public BatchResults listBatchSubscribe(String listId, List<MergeFieldListUtil> toSubscribe, Boolean doubleOptIn, Boolean updateExisting, Boolean replaceInterests) throws MailChimpApiException {
        String str = "listBatchSubscribe";
        Object[] objArr = new Object[5];
        objArr[0] = listId;
        objArr[1] = toSubscribe;
        if (doubleOptIn == null) {
            doubleOptIn = "";
        }
        objArr[2] = doubleOptIn;
        if (updateExisting == null) {
            updateExisting = "";
        }
        objArr[3] = updateExisting;
        if (replaceInterests == null) {
            replaceInterests = "";
        }
        objArr[4] = replaceInterests;
        Object obj = callMethod(str, objArr);
        if (obj == null || !(obj instanceof Map)) {
            return null;
        }
        BatchResults results = new BatchResults();
        results.populateFromRPCStruct(null, (Map) obj);
        return results;
    }

    public BatchResults listBatchUnsubscribe(String listId, List<String> toUnsubscribe) throws MailChimpApiException {
        return listBatchUnsubscribe(listId, toUnsubscribe, null, null, null);
    }

    public BatchResults listBatchUnsubscribe(String listId, List<String> toUnsubscribe, Boolean deleteMember, Boolean sendGoodbye, Boolean sendNotify) throws MailChimpApiException {
        String str = "listBatchUnsubscribe";
        Object[] objArr = new Object[5];
        objArr[0] = listId;
        objArr[1] = toUnsubscribe;
        if (deleteMember == null) {
            deleteMember = "";
        }
        objArr[2] = deleteMember;
        if (sendGoodbye == null) {
            sendGoodbye = "";
        }
        objArr[3] = sendGoodbye;
        if (sendNotify == null) {
            sendNotify = "";
        }
        objArr[4] = sendNotify;
        Object obj = callMethod(str, objArr);
        if (obj == null || !(obj instanceof Map)) {
            return null;
        }
        BatchResults results = new BatchResults();
        results.populateFromRPCStruct(null, (Map) obj);
        return results;
    }

    public List<GrowthHistory> listGrowthHistory(String listId) throws MailChimpApiException {
        return callListMethod(GrowthHistory.class, "listGrowthHistory", listId);
    }

    public boolean listInterestGroupAdd(String listId, String groupName) throws MailChimpApiException {
        return ((Boolean) callMethod("listInterestGroupAdd", listId, groupName)).booleanValue();
    }

    public boolean listInterestGroupDel(String listId, String groupName) throws MailChimpApiException {
        return ((Boolean) callMethod("listInterestGroupDel", listId, groupName)).booleanValue();
    }

    public boolean listInterestGroupUpdate(String listId, String oldGroupName, String newGroupName) throws MailChimpApiException {
        return ((Boolean) callMethod("listInterestGroupUpdate", listId, oldGroupName, newGroupName)).booleanValue();
    }

    public InterestGroupInfo listInterestGroups(String listId) throws MailChimpApiException {
        Object obj = callMethod("listInterestGroups", listId);
        if (obj == null || !(obj instanceof Map)) {
            return null;
        }
        InterestGroupInfo info = new InterestGroupInfo();
        info.populateFromRPCStruct(null, (Map) obj);
        return info;
    }

    public boolean listMergeVarAdd(String listId, String tag, String description, MergeFieldOptions options) throws MailChimpApiException {
        String str = "listMergeVarAdd";
        Object[] objArr = new Object[4];
        objArr[0] = listId;
        objArr[1] = tag;
        objArr[2] = description;
        if (options == null) {
            options = new String[]{""};
        }
        objArr[3] = options;
        return ((Boolean) callMethod(str, objArr)).booleanValue();
    }

    public List<MergeFieldInfo> listMergeVars(String listId) throws MailChimpApiException {
        return callListMethod(MergeFieldInfo.class, "listMergeVars", listId);
    }

    public boolean listMergeVarDel(String listId, String tag) throws MailChimpApiException {
        return ((Boolean) callMethod("listMergeVarDel", listId, tag)).booleanValue();
    }

    public boolean listMergeVarUpdate(String listId, String tag, MergeFieldOptions options) throws MailChimpApiException {
        String str = "listMergeVarUpdate";
        Object[] objArr = new Object[3];
        objArr[0] = listId;
        objArr[1] = tag;
        if (options == null) {
            options = new String[]{""};
        }
        objArr[2] = options;
        return ((Boolean) callMethod(str, objArr)).booleanValue();
    }

    public boolean listWebhookAdd(String listId, String url, Integer actionsFlag, Integer sourcesFlag) throws MailChimpApiException {
        ArrayList<Object> params = new ArrayList(4);
        params.add(listId);
        params.add(url);
        if (actionsFlag != null) {
            params.add(Utils.getWebHookActions(actionsFlag.intValue()));
        }
        if (sourcesFlag != null) {
            params.add(Utils.getWebHookSources(sourcesFlag.intValue()));
        }
        return ((Boolean) callMethod("listWebhookAdd", params.toArray())).booleanValue();
    }

    public boolean listWebhookDel(String listId, String url) throws MailChimpApiException {
        return ((Boolean) callMethod("listWebhookDel", listId, url)).booleanValue();
    }

    public List<WebHookInfo> listWebhooks(String listId) throws MailChimpApiException {
        return callListMethod(WebHookInfo.class, "listWebhooks", listId);
    }

    public List<ListDetails> lists() throws MailChimpApiException {
        return callListMethod(ListDetails.class, "lists", new Object[0]);
    }
}
