package rsg.mailchimp.api.campaigns;

import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.util.Date;
import java.util.Hashtable;
import rsg.mailchimp.api.Constants;
import rsg.mailchimp.api.Constants.CampaignStatus;
import rsg.mailchimp.api.Constants.CampaignType;

public class CampaignListFilters {
    private static final int LIMIT_MAX = 1000;
    private int limit = 25;
    private Hashtable<String, Object> searchOptions = new Hashtable();
    private int start = 0;

    Hashtable<String, Object> getSearchOptions() {
        return this.searchOptions;
    }

    int getStart() {
        return this.start;
    }

    int getLimit() {
        return this.limit;
    }

    public CampaignListFilters setStart(int startVal) {
        this.start = startVal;
        return this;
    }

    public CampaignListFilters setLimit(int limit) {
        if (limit > 1000) {
            throw new IllegalArgumentException("limit (" + limit + ") exceeds maximum value (" + 1000 + ")");
        } else if (limit < 1) {
            throw new IllegalArgumentException("limit called with a value <= 0");
        } else {
            this.limit = limit;
            return this;
        }
    }

    public CampaignListFilters setCampaignId(String campaignId) {
        this.searchOptions.put("campaign_id", campaignId);
        return this;
    }

    public CampaignListFilters setListId(String listId) {
        this.searchOptions.put("list_id", listId);
        return this;
    }

    public CampaignListFilters setFolderId(String folderId) {
        this.searchOptions.put("folder_id", folderId);
        return this;
    }

    public CampaignListFilters setStatus(CampaignStatus status) {
        this.searchOptions.put("status", status.toString());
        return this;
    }

    public CampaignListFilters setType(CampaignType type) {
        this.searchOptions.put(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, type.toString());
        return this;
    }

    public CampaignListFilters setFromEmail(String email) {
        this.searchOptions.put("from_email", email);
        return this;
    }

    public CampaignListFilters setTitle(String title) {
        this.searchOptions.put("title", title);
        return this;
    }

    public CampaignListFilters setSubject(String subject) {
        this.searchOptions.put("subject", subject);
        return this;
    }

    public CampaignListFilters setSendtimeStart(Date time) {
        this.searchOptions.put("sendtime_start", Constants.TIME_FMT.format(time));
        return this;
    }

    public CampaignListFilters setSendtimeEnd(Date time) {
        this.searchOptions.put("sendtime_end", Constants.TIME_FMT.format(time));
        return this;
    }

    public CampaignListFilters setExact(boolean isExactSearch) {
        this.searchOptions.put("exact", new Boolean(isExactSearch));
        return this;
    }
}
