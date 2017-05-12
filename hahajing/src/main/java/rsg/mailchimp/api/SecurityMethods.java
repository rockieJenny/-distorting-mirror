package rsg.mailchimp.api;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.xmlrpc.android.XMLRPCException;
import rsg.mailchimp.api.data.ApiKeyInfo;

public class SecurityMethods extends MailChimpApi {
    public SecurityMethods(CharSequence apiKey) {
        super(apiKey);
    }

    public SecurityMethods(Context ctx) {
        super(ctx);
    }

    public String apikeyAdd(String username, String password) throws MailChimpApiException {
        return (String) callMethod("apikeyAdd", username, password, this.apiKey);
    }

    public boolean apiKeyExpire(String username, String password, String key) throws MailChimpApiException {
        String str = "apikeyExpire";
        Object[] objArr = new Object[3];
        objArr[0] = username;
        objArr[1] = password;
        if (key == null) {
            key = this.apiKey;
        }
        objArr[2] = key;
        return ((Boolean) callMethod(str, objArr)).booleanValue();
    }

    public List<ApiKeyInfo> getApiKeys(String username, String password, Boolean expiredOnly) throws MailChimpApiException {
        int i = 0;
        String str = "apikeys";
        Object[] objArr = new Object[4];
        objArr[0] = username;
        objArr[1] = password;
        objArr[2] = this.apiKey;
        objArr[3] = Boolean.valueOf(expiredOnly == null ? false : expiredOnly.booleanValue());
        Object callResult = callMethod(str, objArr);
        if (!(callResult instanceof Object[])) {
            return new ArrayList(0);
        }
        Object[] results = (Object[]) callResult;
        List<ApiKeyInfo> list = new ArrayList(results.length);
        int length = results.length;
        while (i < length) {
            Map struct = results[i];
            ApiKeyInfo info = new ApiKeyInfo();
            info.populateFromRPCStruct(null, struct);
            list.add(info);
            i++;
        }
        return list;
    }

    protected Object callMethod(String methodName, Object... params) throws MailChimpApiException {
        try {
            return getClient().callEx(methodName, params);
        } catch (XMLRPCException e) {
            throw buildMailChimpException(e);
        }
    }
}
