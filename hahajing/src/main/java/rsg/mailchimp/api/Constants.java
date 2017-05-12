package rsg.mailchimp.api;

import java.text.SimpleDateFormat;

public class Constants {
    public static final SimpleDateFormat TIME_FMT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final int WEBHOOK_ACTION_CLEANED = 16;
    public static final int WEBHOOK_ACTION_PROFILE = 8;
    public static final int WEBHOOK_ACTION_SUBSCRIBE = 2;
    public static final int WEBHOOK_ACTION_UNSUBSCRIBE = 4;
    public static final int WEBHOOK_ACTION_UPEMAIL = 32;
    public static final int WEBHOOK_SOURCE_ADMIN = 4;
    public static final int WEBHOOK_SOURCE_API = 8;
    public static final int WEBHOOK_SOURCE_USER = 2;

    public enum CampaignStatus {
        sent,
        save,
        paused,
        schedule,
        sending
    }

    public enum CampaignType {
        regular,
        plaintext,
        absplit,
        rss,
        trans,
        auto
    }

    public enum EmailType {
        html,
        text,
        mobile
    }

    public enum InterestGroupType {
        checkbox,
        radio,
        select
    }

    public enum MergeFieldType {
        email,
        text,
        number,
        radio,
        dropdown,
        date,
        address,
        phone,
        url,
        imageurl
    }

    public enum SubscribeStatus {
        subscribed,
        unsubscribed,
        cleaned
    }
}
