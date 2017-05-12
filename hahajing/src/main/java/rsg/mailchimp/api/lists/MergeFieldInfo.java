package rsg.mailchimp.api.lists;

import com.squareup.otto.Bus;
import java.util.Map;
import rsg.mailchimp.api.Constants.MergeFieldType;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class MergeFieldInfo implements RPCStructConverter {
    public String defaultValue;
    public Boolean isPublic;
    public String name;
    public String order;
    public boolean required;
    public Boolean show;
    public String size;
    public String tag;
    public MergeFieldType type;

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        Utils.populateObjectFromRPCStruct(this, struct, true, "req", "fieldType", "public", Bus.DEFAULT_IDENTIFIER);
        this.required = ((Boolean) struct.get("req")).booleanValue();
        this.defaultValue = (String) struct.get(Bus.DEFAULT_IDENTIFIER);
        this.isPublic = (Boolean) struct.get("public");
        this.type = MergeFieldType.valueOf((String) struct.get("field_type"));
    }
}
