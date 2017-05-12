package rsg.mailchimp.api.campaigns;

import java.util.Date;
import rsg.mailchimp.api.data.GenericStructConverter;

public class EmailAIMStats extends GenericStructConverter {
    public String action;
    public Date timestamp;
    public String url;
}
