package rsg.mailchimp.api.data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import rsg.mailchimp.api.MailChimpApiException;
import rsg.mailchimp.api.RPCStructConverter;
import rsg.mailchimp.api.Utils;

public class AccountDetails implements RPCStructConverter {
    public String affiliateLink;
    public AccountContact contact;
    public Integer emailsLeft;
    public Date firstPayment;
    public Boolean isApproved;
    public Boolean isTrial;
    public Date lastLogin;
    public Date lastPayment;
    public Date memberSince;
    public List<ModuleInfo> modules;
    public List<AccountPayments> payments;
    public Boolean pendingMonthly;
    public PlanType planType;
    public Integer timesLoggedIn;
    public String timezone;
    public String userId;
    public String username;

    public enum PlanType {
        monthly,
        payasyougo,
        free
    }

    public void populateFromRPCStruct(String key, Map struct) throws MailChimpApiException {
        Utils.populateObjectFromRPCStruct(this, struct, true, "orders", "rewards", "modules", "contact", "planType");
        this.planType = PlanType.valueOf((String) struct.get("plan_type"));
        this.contact = new AccountContact();
        this.contact.populateFromRPCStruct(null, (Map) struct.get("contact"));
        Object ordersObj = struct.get("orders");
        if (ordersObj instanceof Object[]) {
            this.payments = Utils.extractObjectsFromList(AccountPayments.class, (Object[]) ordersObj);
        }
    }
}
