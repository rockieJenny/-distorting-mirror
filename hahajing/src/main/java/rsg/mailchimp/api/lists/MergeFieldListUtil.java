package rsg.mailchimp.api.lists;

import com.millennialmedia.android.MMRequest;
import java.util.Date;
import java.util.Hashtable;
import org.xmlrpc.android.XMLRPCSerializable;
import rsg.mailchimp.api.Constants;

public class MergeFieldListUtil extends Hashtable<Object, Object> implements XMLRPCSerializable {
    private static final long serialVersionUID = 2340843397084407707L;

    public void addInterest(String interest) {
        String interests = (String) get("INTERESTS");
        if (interests == null) {
            interests = "";
        } else {
            interests = new StringBuilder(String.valueOf(interests)).append(",").toString();
            remove("INTERESTS");
        }
        put("INTERESTS", new StringBuilder(String.valueOf(interests)).append(interest.replaceAll(",", "\\,")).toString());
    }

    public String[] getInterests() {
        String obj = get("INTERESTS");
        if (obj == null) {
            return new String[0];
        }
        String[] interests = obj.split("(?<!\\\\),");
        String[] retVal = new String[interests.length];
        for (int i = 0; i < retVal.length; i++) {
            retVal[i] = interests[i].trim().replaceAll("\\\\,", ",");
        }
        return retVal;
    }

    public void addOptInIp(String ip) {
        put("OPTINIP", ip);
    }

    public void addAddressField(String key, String address1, String address2, String city, String state, String zip, String country) {
        Hashtable<String, String> addressVals = new Hashtable();
        if (address1 != null) {
            addressVals.put("addr1", address1);
        }
        if (address2 != null) {
            addressVals.put("addr21", address1);
        }
        if (city != null) {
            addressVals.put("city", address1);
        }
        if (state != null) {
            addressVals.put("state", address1);
        }
        if (zip != null) {
            addressVals.put(MMRequest.KEY_ZIP_CODE, address1);
        }
        if (country != null) {
            addressVals.put("country", address1);
        }
        put(key, addressVals);
    }

    public void addDateField(String key, Date date) {
        put(key, Constants.TIME_FMT.format(date));
    }

    public void addNumberField(String key, Number number) {
        put(key, number);
    }

    public void addField(String key, Object value) {
        put(key, value);
    }

    public Object getSerializable() {
        return this;
    }

    public void addEmail(String string) {
        put("EMAIL", string);
    }
}
