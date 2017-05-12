package rsg.mailchimp.api;

import android.content.Context;
import com.givewaygames.goofyglass.R;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.xmlrpc.android.XMLRPCClient;
import org.xmlrpc.android.XMLRPCException;

public class MailChimpApi {
    private static final MessageFormat API_URL = new MessageFormat("https://{0}.api.mailchimp.com/1.2/");
    private static final String DEFAULT_DATA_CENTER = "us1";
    protected String apiKey;
    private String endpointUrl;

    public MailChimpApi(Context ctx) {
        this(ctx.getResources().getText(R.anim.fade_in));
    }

    public MailChimpApi(CharSequence apiKey) {
        this.apiKey = apiKey.toString();
        this.endpointUrl = API_URL.format(new Object[]{parseDataCenter(this.apiKey)});
    }

    protected MailChimpApiException buildMailChimpException(XMLRPCException e) {
        return new MailChimpApiException("API Call failed, due to '" + e.getMessage() + "'", e);
    }

    protected <T extends RPCStructConverter> List<T> callListMethod(Class<T> clazz, String methodName, Object... params) throws MailChimpApiException {
        Map callResult = callMethod(methodName, params);
        if (callResult instanceof Object[]) {
            return Utils.extractObjectsFromList(clazz, (Object[]) callResult);
        }
        if (!(callResult instanceof Map)) {
            return new ArrayList(0);
        }
        Set<Entry<String, Object>> entries = callResult.entrySet();
        List<T> retVal = new ArrayList(entries.size());
        for (Entry<String, Object> entry : entries) {
            try {
                RPCStructConverter newInst = (RPCStructConverter) clazz.newInstance();
                newInst.populateFromRPCStruct((String) entry.getKey(), (Map) entry.getValue());
                retVal.add(newInst);
            } catch (IllegalAccessException e) {
                throw new MailChimpApiException("Couldn't create instance of class (" + clazz.getName() + ") make sure it is publically accessible");
            } catch (InstantiationException e2) {
                throw new MailChimpApiException("Couldn't create instance of class (" + clazz.getName() + ") make sure it has a zero-args constructor");
            }
        }
        return retVal;
    }

    protected Object callMethod(String methodName, Object... params) throws MailChimpApiException {
        int i = 1;
        if (params != null) {
            i = params.length + 1;
        }
        try {
            Object[] parameters = new Object[i];
            if (params != null && params.length > 0) {
                System.arraycopy(params, 0, parameters, 1, params.length);
            }
            parameters[0] = this.apiKey;
            return getClient().callEx(methodName, parameters);
        } catch (XMLRPCException e) {
            throw buildMailChimpException(e);
        }
    }

    protected XMLRPCClient getClient() {
        return new XMLRPCClient(this.endpointUrl);
    }

    private static final String parseDataCenter(String apiKey) {
        String dataCenter = DEFAULT_DATA_CENTER;
        int index = apiKey.lastIndexOf(45);
        if (index > 0) {
            return apiKey.substring(index + 1);
        }
        return dataCenter;
    }
}
