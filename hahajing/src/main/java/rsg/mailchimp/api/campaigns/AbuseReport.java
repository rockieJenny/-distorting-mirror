package rsg.mailchimp.api.campaigns;

import java.util.Date;
import rsg.mailchimp.api.data.GenericStructConverter;

public class AbuseReport extends GenericStructConverter {
    public String campaignId;
    public Date date;
    public String email;
    public String type;
}
