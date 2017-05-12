package rsg.mailchimp.api.data;

import java.util.Date;

public class AccountPayments extends GenericStructConverter {
    public Double amount;
    public Double creditsUser;
    public Date date;
    public Integer orderId;
    public String type;
}
