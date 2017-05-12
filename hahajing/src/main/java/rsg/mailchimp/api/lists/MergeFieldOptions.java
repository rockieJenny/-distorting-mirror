package rsg.mailchimp.api.lists;

import java.util.Date;
import java.util.Hashtable;
import rsg.mailchimp.api.Constants;
import rsg.mailchimp.api.Constants.MergeFieldType;

public class MergeFieldOptions extends Hashtable<String, Object> {
    private static final long serialVersionUID = -4664327902592046297L;

    public void addFieldType(MergeFieldType type) {
        put("field_type", type.toString());
    }

    public void setRequired(boolean required) {
        put("req", Boolean.valueOf(required));
    }

    public void setPublic(boolean isPublic) {
        put("public", Boolean.valueOf(isPublic));
    }

    public void setShow(boolean show) {
        put("show", Boolean.valueOf(show));
    }

    public void setDefaultValue(Date val) {
        setDefaultValue(Constants.TIME_FMT.format(val));
    }

    public void setDefaultValue(String val) {
        put("default_value", val);
    }

    public void setChoices(String[] choices) {
        put("choices", choices);
    }
}
