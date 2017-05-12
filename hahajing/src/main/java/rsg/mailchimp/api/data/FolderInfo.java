package rsg.mailchimp.api.data;

import java.util.Map;
import org.xmlrpc.android.IXMLRPCSerializer;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;

public class FolderInfo implements RPCStructConverter {
    public Integer folderId;
    public String name;

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        this.folderId = Integer.valueOf(((Number) struct.get("folder_id")).intValue());
        this.name = (String) struct.get(IXMLRPCSerializer.TAG_NAME);
    }
}
