package rsg.mailchimp.api.lists;

import java.util.List;
import java.util.Map;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.MailChimpErrorMessage;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class BatchResults implements RPCStructConverter {
    public Integer errorCount;
    public List<MailChimpErrorMessage> errors;
    public Integer successCount;

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        Utils.populateObjectFromRPCStruct(this, struct, true, "errors");
        Object errorsObj = struct.get("errors");
        if (errorsObj != null && (errorsObj instanceof Object[])) {
            this.errors = Utils.extractObjectsFromList(MailChimpErrorMessage.class, (Object[]) errorsObj);
        }
    }
}
