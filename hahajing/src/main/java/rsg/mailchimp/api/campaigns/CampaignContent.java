package rsg.mailchimp.api.campaigns;

import com.millennialmedia.android.MMRequest;
import java.util.Hashtable;
import org.xmlrpc.android.XMLRPCSerializable;

public class CampaignContent implements XMLRPCSerializable {
    public String archiveBytes;
    public String archiveType = MMRequest.KEY_ZIP_CODE;
    public String html;
    public String templateHtmlFooter;
    public String templateHtmlHeader;
    public String templateHtmlMain;
    public String templateHtmlSidecolumn;
    public String text;
    public String url;

    public Object getSerializable() {
        Hashtable<String, Object> map = new Hashtable();
        if (this.url != null) {
            map.put("url", this.url);
        } else if (this.archiveBytes != null) {
            map.put("achive_bytes", this.archiveBytes);
            map.put("archive_type", this.archiveType);
        } else if (this.html == null && this.text == null) {
            if (this.templateHtmlHeader != null) {
                map.put("html_HEADER", this.templateHtmlHeader);
            }
            if (this.templateHtmlMain != null) {
                map.put("html_MAIN", this.templateHtmlMain);
            }
            if (this.templateHtmlSidecolumn != null) {
                map.put("html_SIDECOLUMN", this.templateHtmlSidecolumn);
            }
            if (this.templateHtmlFooter != null) {
                map.put("html_FOOTER", this.templateHtmlFooter);
            }
        } else {
            map.put("html", this.html);
            map.put("text", this.text);
        }
        return map;
    }
}
