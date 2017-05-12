package rsg.mailchimp.api.campaigns;

import com.inmobi.commons.analytics.db.AnalyticsEvent;
import java.util.Map;
import org.xmlrpc.android.IXMLRPCSerializer;
import rsg.mailchimp.api.RPCStructConverter;

public class TemplateDetails implements RPCStructConverter {
    public Integer id;
    public String layout;
    public String name;
    public String previewImageUrl;
    public Map sections;

    public void populateFromRPCStruct(String key, Map struct) {
        this.id = Integer.valueOf(((Number) struct.get(AnalyticsEvent.EVENT_ID)).intValue());
        this.name = (String) struct.get(IXMLRPCSerializer.TAG_NAME);
        this.layout = (String) struct.get("layout");
        this.previewImageUrl = (String) struct.get("preview_image");
        if (struct.get("sections") instanceof Map) {
            this.sections = (Map) struct.get("sections");
        }
    }
}
