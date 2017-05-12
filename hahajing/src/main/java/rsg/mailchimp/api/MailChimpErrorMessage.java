package rsg.mailchimp.api;

import rsg.mailchimp.api.data.GenericStructConverter;

public class MailChimpErrorMessage extends GenericStructConverter {
    public Integer code;
    public String message;
}
