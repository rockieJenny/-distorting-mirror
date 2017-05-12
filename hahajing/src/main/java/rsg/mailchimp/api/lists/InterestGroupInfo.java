package rsg.mailchimp.api.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.xmlrpc.android.IXMLRPCSerializer;
import rsg.mailchimp.api.Constants.InterestGroupType;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;

public class InterestGroupInfo implements RPCStructConverter {
    public List<String> groups;
    public String name;
    public InterestGroupType type;

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        this.name = (String) struct.get(IXMLRPCSerializer.TAG_NAME);
        Object obj = struct.get("form_field");
        if (obj != null) {
            this.type = InterestGroupType.valueOf((String) obj);
        }
        obj = struct.get("groups");
        if (obj != null && (obj instanceof Object[])) {
            Object[] values = (Object[]) obj;
            this.groups = new ArrayList(values.length);
            for (Object o : values) {
                this.groups.add((String) o);
            }
        }
    }
}
