package rsg.mailchimp.api.campaigns;

import android.content.Context;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import rsg.mailchimp.api.Constants;
import rsg.mailchimp.api.Constants.CampaignType;
import rsg.mailchimp.api.MailChimpApi;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.Utils;
import rsg.mailchimp.api.data.FolderInfo;

public class CampaignMethods extends MailChimpApi {
    public CampaignMethods(Context ctx) {
        super(ctx);
    }

    public CampaignMethods(CharSequence apiKey) {
        super(apiKey);
    }

    public List<CampaignDetails> listCampaigns(CampaignListFilters filters) throws MailChimpApiException {
        Object filtersParam = filters.getSearchOptions().size() > 0 ? filters.getSearchOptions() : "";
        return callListMethod(CampaignDetails.class, "campaigns", filtersParam, Integer.valueOf(filters.getStart()), Integer.valueOf(filters.getLimit()));
    }

    public CampaignContent campaignContent(String campaignId) throws MailChimpApiException {
        return campaignContent(campaignId, true);
    }

    public CampaignContent campaignContent(String campaignId, boolean archiveVersion) throws MailChimpApiException {
        Map result = (Map) callMethod("campaignContent", campaignId, Boolean.valueOf(archiveVersion));
        CampaignContent content = new CampaignContent();
        content.html = (String) result.get("html");
        content.text = (String) result.get("text");
        return content;
    }

    public String campaignCreate(CampaignType type, CampaignOptions options, CampaignContent content) throws MailChimpApiException {
        return campaignCreate(type, options, content, null, null);
    }

    public String campaignCreate(CampaignType type, CampaignOptions options, CampaignContent content, Map<String, String> segmentOptions, Map<String, String> typeOptions) throws MailChimpApiException {
        String str = "campaignCreate";
        Object[] objArr = new Object[5];
        objArr[0] = type.toString();
        objArr[1] = options;
        objArr[2] = content;
        if (segmentOptions == null) {
            segmentOptions = "";
        }
        objArr[3] = segmentOptions;
        if (typeOptions == null) {
            typeOptions = "";
        }
        objArr[4] = typeOptions;
        return (String) callMethod(str, objArr);
    }

    public boolean campaignDelete(String campaignId) throws MailChimpApiException {
        return ((Boolean) callMethod("campaignDelete", campaignId)).booleanValue();
    }

    public List<FolderInfo> campaignFolders() throws MailChimpApiException {
        return callListMethod(FolderInfo.class, "campaignFolders", new Object[0]);
    }

    public boolean campaignPause(String campaignId) throws MailChimpApiException {
        return ((Boolean) callMethod("campaignPause", campaignId)).booleanValue();
    }

    public boolean campaignResume(String campaignId) throws MailChimpApiException {
        return ((Boolean) callMethod("campaignResume", campaignId)).booleanValue();
    }

    public boolean campaignSendNow(String campaignId) throws MailChimpApiException {
        return ((Boolean) callMethod("campaignSendNow", campaignId)).booleanValue();
    }

    public Integer createFolder(String name) throws MailChimpApiException {
        return (Integer) callMethod("createFolder", name);
    }

    public boolean campaignSendTest(String campaignId, String[] emails) throws MailChimpApiException {
        return campiagnSendTest(campaignId, emails, null);
    }

    public boolean campiagnSendTest(String campaignId, String[] emails, String sendType) throws MailChimpApiException {
        String str = "campaignSendTest";
        Object[] objArr = new Object[3];
        objArr[0] = campaignId;
        objArr[1] = emails;
        if (sendType == null) {
            sendType = "";
        }
        objArr[2] = sendType;
        return ((Boolean) callMethod(str, objArr)).booleanValue();
    }

    public SharedReportDetails campiagnShareReport(String campaignId, boolean secured, String password) throws MailChimpApiException {
        return campaignShareReport(campaignId, null, secured, password);
    }

    public SharedReportDetails campaignShareReport(String campaignId, String emailAddress, boolean secured, String password) throws MailChimpApiException {
        String str = null;
        Hashtable<String, Object> opts = new Hashtable();
        opts.put("secure", Boolean.valueOf(secured));
        if (emailAddress != null) {
            opts.put("to_email", emailAddress);
        }
        if (password != null) {
            opts.put("password", password);
        }
        Map results = (Map) callMethod("campaignShareReport", campaignId, opts);
        SharedReportDetails details = new SharedReportDetails();
        details.title = results.get("title") == null ? null : results.get("title").toString();
        details.url = results.get("url") == null ? null : results.get("url").toString();
        details.secureUrl = results.get("secure_url") == null ? null : results.get("secure_url").toString();
        if (results.get("password") != null) {
            str = results.get("password").toString();
        }
        details.password = str;
        return details;
    }

    public List<TemplateDetails> campiagnTemplates() throws MailChimpApiException {
        return callListMethod(TemplateDetails.class, "campaignTemplates", new Object[0]);
    }

    public boolean campiagnSchedule(String campaignId, Date time) throws MailChimpApiException {
        return campaignSchedule(campaignId, time, null);
    }

    public boolean campaignSchedule(String campaignId, Date time, Date splitTimeB) throws MailChimpApiException {
        String str = "campaignSchedule";
        Object[] objArr = new Object[3];
        objArr[0] = campaignId;
        objArr[1] = Constants.TIME_FMT.format(time);
        objArr[2] = splitTimeB == null ? "" : Constants.TIME_FMT.format(splitTimeB);
        return ((Boolean) callMethod(str, objArr)).booleanValue();
    }

    public boolean campaignUnschedule(String campaignId) throws MailChimpApiException {
        return ((Boolean) callMethod("campaignUnschedule", campaignId)).booleanValue();
    }

    public boolean campaignUpdateTitle(String campaignId, String newTitle) throws MailChimpApiException {
        return campaignUpdate(campaignId, "title", newTitle);
    }

    public boolean campaignUpdateSubject(String campaignId, String newSubject) throws MailChimpApiException {
        return campaignUpdate(campaignId, "subject", newSubject);
    }

    public boolean campaignUpdateFromEmail(String campaignId, String newFromEmail) throws MailChimpApiException {
        return campaignUpdate(campaignId, "from_email", newFromEmail);
    }

    public boolean campaignUpdateFromName(String campaignId, String newFromName) throws MailChimpApiException {
        return campaignUpdate(campaignId, "from_name", newFromName);
    }

    public boolean campaignUpdateToName(String campaignId, String newName) throws MailChimpApiException {
        return campaignUpdate(campaignId, "to_name", newName);
    }

    public boolean campaignUpdateAutoTweet(String campaignId, boolean turnOn) throws MailChimpApiException {
        return campaignUpdate(campaignId, "auto_tweet", new Boolean(turnOn));
    }

    public boolean campaignUpdateListToSendTo(String campaignId, String newListId) throws MailChimpApiException {
        return campaignUpdate(campaignId, "list_id", newListId);
    }

    public boolean campaignUpdate(String campaignId, String name, Object value) throws MailChimpApiException {
        return ((Boolean) callMethod("campaignUpdate", campaignId, name, value)).booleanValue();
    }

    public List<AbuseReport> campaignAbuseReports(String campaignId, Integer start, Integer limit, Date since) throws MailChimpApiException {
        Object limitVal;
        int startVal = start == null ? 0 : start.intValue();
        if (limit == null) {
            limitVal = "";
        } else {
            Integer limitVal2 = limit;
        }
        String sinceVal = since == null ? "" : Constants.TIME_FMT.format(since);
        List<AbuseReport> list = callListMethod(AbuseReport.class, "campaignAbuseReports", campaignId, sinceVal, Integer.valueOf(startVal), limitVal);
        for (AbuseReport report : list) {
            report.campaignId = campaignId;
        }
        return list;
    }

    public List<CampaignAdvice> campaignAdvice(String campaignId) throws MailChimpApiException {
        return callListMethod(CampaignAdvice.class, "campaignAdvice", campaignId);
    }

    public CampaignAnalytics campaignAnalytics(String campaignId) throws MailChimpApiException {
        Map map = (Map) callMethod("campaignAnalytics", campaignId);
        if (map == null) {
            return null;
        }
        CampaignAnalytics analytics = new CampaignAnalytics();
        analytics.populateFromRPCStruct(null, map);
        return analytics;
    }

    public List<BounceMessage> campaignBounceMessages(String campaignId) throws MailChimpApiException {
        return campaignBounceMessages(campaignId, null, null, null);
    }

    public List<BounceMessage> campaignBounceMessages(String campaignId, Integer start, Integer limit, Date since) throws MailChimpApiException {
        int i = 0;
        Class cls = BounceMessage.class;
        String str = "campaignBounceMessages";
        Object[] objArr = new Object[4];
        objArr[0] = campaignId;
        if (start != null) {
            i = start.intValue();
        }
        objArr[1] = Integer.valueOf(i);
        objArr[2] = Integer.valueOf(limit == null ? 25 : limit.intValue());
        objArr[3] = since == null ? "" : Constants.TIME_FMT.format(since);
        return callListMethod(cls, str, objArr);
    }

    public List<URLClickStats> campaignClickStats(String campaignId) throws MailChimpApiException {
        return callListMethod(URLClickStats.class, "campaignClickStats", campaignId);
    }

    public List<ECommOrders> campaignECommOrders(String campaignId) throws MailChimpApiException {
        return campaignECommOrders(campaignId, null, null, null);
    }

    public List<ECommOrders> campaignECommOrders(String campaignId, Integer start, Integer limit, Date since) throws MailChimpApiException {
        int i = 0;
        Class cls = ECommOrders.class;
        String str = "campaignEcommOrders";
        Object[] objArr = new Object[4];
        objArr[0] = campaignId;
        if (start != null) {
            i = start.intValue();
        }
        objArr[1] = Integer.valueOf(i);
        objArr[2] = Integer.valueOf(limit == null ? 100 : limit.intValue());
        objArr[3] = since == null ? "" : Constants.TIME_FMT.format(since);
        return callListMethod(cls, str, objArr);
    }

    public List<EepUrlStats> campaignEepUrlStats(String campaignId) throws MailChimpApiException {
        return callListMethod(EepUrlStats.class, "campaignEepUrlStats", campaignId);
    }

    public List<EmailDomainPerformance> campaignEmailDomainPerformance(String campaignId) throws MailChimpApiException {
        return callListMethod(EmailDomainPerformance.class, "campaignEmailDomainPerformance", campaignId);
    }

    public List<GeoOpens> campaignGeoOpens(String campaignId) throws MailChimpApiException {
        return callListMethod(GeoOpens.class, "campaignGeoOpens", campaignId);
    }

    public List<GeoOpens> campaignGeoOpens(String campaignId, String countryCode) throws MailChimpApiException {
        return callListMethod(GeoOpens.class, "campaignGeoOpensForCountry", campaignId, countryCode);
    }

    public List<String> campaignHardBounces(String campaignId) throws MailChimpApiException {
        return campaignHardBounces(campaignId, null, null);
    }

    public List<String> campaignHardBounces(String campaignId, Integer start, Integer limit) throws MailChimpApiException {
        String str = "campaignHardBounces";
        Object[] objArr = new Object[3];
        objArr[0] = campaignId;
        objArr[1] = Integer.valueOf(start == null ? 0 : start.intValue());
        objArr[2] = Integer.valueOf(limit == null ? 1000 : limit.intValue());
        Object obj = callMethod(str, objArr);
        if (obj instanceof Object[]) {
            return Utils.convertObjectArrayToString((Object[]) obj);
        }
        return new ArrayList(0);
    }

    public List<String> campaignSoftBounces(String campaignId) throws MailChimpApiException {
        return campaignSoftBounces(campaignId, null, null);
    }

    private List<String> campaignSoftBounces(String campaignId, Integer start, Integer limit) throws MailChimpApiException {
        String str = "campaignSoftBounces";
        Object[] objArr = new Object[3];
        objArr[0] = campaignId;
        objArr[1] = Integer.valueOf(start == null ? 0 : start.intValue());
        objArr[2] = Integer.valueOf(limit == null ? 1000 : limit.intValue());
        Object obj = callMethod(str, objArr);
        if (obj instanceof Object[]) {
            return Utils.convertObjectArrayToString((Object[]) obj);
        }
        return new ArrayList(0);
    }

    public List<String> campaignUnsubscribes(String campaignId) throws MailChimpApiException {
        return campaignUnsubscribes(campaignId, null, null);
    }

    private List<String> campaignUnsubscribes(String campaignId, Integer start, Integer limit) throws MailChimpApiException {
        String str = "campaignUnsubscribes";
        Object[] objArr = new Object[3];
        objArr[0] = campaignId;
        objArr[1] = Integer.valueOf(start == null ? 0 : start.intValue());
        objArr[2] = Integer.valueOf(limit == null ? 1000 : limit.intValue());
        Object obj = callMethod(str, objArr);
        if (obj instanceof Object[]) {
            return Utils.convertObjectArrayToString((Object[]) obj);
        }
        return new ArrayList(0);
    }

    public CampaignStats campaignStats(String campaignId) throws MailChimpApiException {
        Object obj = callMethod("campaignStats", campaignId);
        if (!(obj instanceof Map)) {
            return null;
        }
        CampaignStats stats = new CampaignStats();
        stats.populateFromRPCStruct(null, (Map) obj);
        return stats;
    }

    public List<EmailClickCounts> campaignClickDetailAIM(String campaignId, String url) throws MailChimpApiException {
        return campaignClickDetailAIM(campaignId, url, null, null);
    }

    public List<EmailClickCounts> campaignClickDetailAIM(String campaignId, String url, Integer start, Integer limit) throws MailChimpApiException {
        int i = 0;
        Class cls = EmailClickCounts.class;
        String str = "campaignClickDetailAIM";
        Object[] objArr = new Object[4];
        objArr[0] = campaignId;
        objArr[1] = url;
        if (start != null) {
            i = start.intValue();
        }
        objArr[2] = Integer.valueOf(i);
        objArr[3] = Integer.valueOf(limit == null ? 1000 : limit.intValue());
        return callListMethod(cls, str, objArr);
    }

    public List<EmailAIMStats> campaignEmailStatsAIMAll(String campaignId) throws MailChimpApiException {
        return campaignEmailStatsAIMAll(campaignId, null);
    }

    public List<EmailAIMStats> campaignEmailStatsAIMAll(String campaignId, String emailAddress) throws MailChimpApiException {
        if (emailAddress != null) {
            return callListMethod(EmailAIMStats.class, "campaignEmailStatsAIM", campaignId, emailAddress);
        }
        return callListMethod(EmailAIMStats.class, "campaignEmailStatsAIMAll", campaignId);
    }

    public List<String> campaignNotOpenedAIM(String campaignId) throws MailChimpApiException {
        return campaignNotOpenedAIM(campaignId, null, null);
    }

    public List<String> campaignNotOpenedAIM(String campaignId, Integer start, Integer limit) throws MailChimpApiException {
        String str = "campaignNotOpenedAIM";
        Object[] objArr = new Object[3];
        objArr[0] = campaignId;
        objArr[1] = Integer.valueOf(start == null ? 0 : start.intValue());
        objArr[2] = Integer.valueOf(limit == null ? 1000 : limit.intValue());
        Object obj = callMethod(str, objArr);
        if (obj instanceof Object[]) {
            return Utils.convertObjectArrayToString((Object[]) obj);
        }
        return new ArrayList(0);
    }

    public List<EmailOpenCounts> campaignOpenedAIM(String campaignId) throws MailChimpApiException {
        return campaignOpenedAIM(campaignId, null, null);
    }

    public List<EmailOpenCounts> campaignOpenedAIM(String campaignId, Integer start, Integer limit) throws MailChimpApiException {
        int i = 0;
        Class cls = EmailOpenCounts.class;
        String str = "campaignOpenedAIM";
        Object[] objArr = new Object[3];
        objArr[0] = campaignId;
        if (start != null) {
            i = start.intValue();
        }
        objArr[1] = Integer.valueOf(i);
        objArr[2] = Integer.valueOf(limit == null ? 1000 : limit.intValue());
        return callListMethod(cls, str, objArr);
    }
}
