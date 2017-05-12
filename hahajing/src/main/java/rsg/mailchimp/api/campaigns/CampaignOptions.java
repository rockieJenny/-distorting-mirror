package rsg.mailchimp.api.campaigns;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Map;
import org.xmlrpc.android.XMLRPCSerializable;

public class CampaignOptions implements XMLRPCSerializable {
    public Map<String, String> analytics;
    public Boolean authenticate;
    public Boolean autoFooter;
    public Boolean autoTweet;
    public Integer folderId;
    public String fromEmail;
    public String fromName;
    public Boolean generateText;
    public Boolean inlineCss;
    public String listId;
    public String subject;
    public Integer templateId;
    public String title;
    public String toEmail;
    public TrackingOptions[] tracking;

    public enum TrackingOptions {
        opens,
        html_clicks,
        text_clicks
    }

    public Object getSerializable() {
        Hashtable<String, Object> map = new Hashtable();
        if (this.listId == null) {
            throw new IllegalArgumentException("listId required for campaign options");
        } else if (this.subject == null) {
            throw new IllegalArgumentException("subject required for campaign options");
        } else if (this.fromEmail == null) {
            throw new IllegalArgumentException("fromEmail required for campaign options");
        } else if (this.fromName == null) {
            throw new IllegalArgumentException("fromName required for campaign options");
        } else if (this.toEmail == null) {
            throw new IllegalArgumentException("toEmail required for campaign options");
        } else {
            try {
                for (Field f : getClass().getFields()) {
                    if (f.get(this) != null) {
                        map.put(convertVarNameToApiName(f.getName()), f.get(this));
                    }
                }
                return map;
            } catch (Exception e) {
                throw new RuntimeException("Error occurred serializing the CampaignOptions: " + e.getMessage());
            }
        }
    }

    private String convertVarNameToApiName(String name) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (Character.isUpperCase(c)) {
                buf.append('_');
                buf.append(Character.toLowerCase(c));
            } else {
                buf.append(c);
            }
        }
        return buf.toString();
    }
}
