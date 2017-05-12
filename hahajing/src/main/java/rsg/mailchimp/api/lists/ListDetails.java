package rsg.mailchimp.api.lists;

import java.util.Date;
import rsg.mailchimp.api.data.GenericStructConverter;

public class ListDetails extends GenericStructConverter {
    public Integer cleanedCount;
    public Integer cleanedCountSinceSend;
    public Date dateCreated;
    public String defaultFromEmail;
    public String defaultFromName;
    public String defaultLanguage;
    public String defaultSubject;
    public Boolean emailTypeOption;
    public String id;
    public Double listRating;
    public Integer memberCount;
    public Integer memberCountSinceSend;
    public String name;
    public Integer unsubscribeCount;
    public Integer unsubscribeCountSinceSend;
    public Integer webId;
}
