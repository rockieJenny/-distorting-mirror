package com.google.android.gms.analytics.ecommerce;

import com.google.analytics.tracking.android.Fields;
import com.google.android.gms.internal.jx;
import java.util.HashMap;
import java.util.Map;

public class ProductAction {
    public static final String ACTION_ADD = "add";
    public static final String ACTION_CHECKOUT = "checkout";
    public static final String ACTION_CHECKOUT_OPTION = "checkout_option";
    @Deprecated
    public static final String ACTION_CHECKOUT_OPTIONS = "checkout_options";
    public static final String ACTION_CLICK = "click";
    public static final String ACTION_DETAIL = "detail";
    public static final String ACTION_PURCHASE = "purchase";
    public static final String ACTION_REFUND = "refund";
    public static final String ACTION_REMOVE = "remove";
    Map<String, String> CD = new HashMap();

    public ProductAction(String action) {
        put("&pa", action);
    }

    public Map<String, String> build() {
        return new HashMap(this.CD);
    }

    void put(String name, String value) {
        jx.b((Object) name, (Object) "Name should be non-null");
        this.CD.put(name, value);
    }

    public ProductAction setCheckoutOptions(String value) {
        put("&col", value);
        return this;
    }

    public ProductAction setCheckoutStep(int value) {
        put("&cos", Integer.toString(value));
        return this;
    }

    public ProductAction setProductActionList(String value) {
        put("&pal", value);
        return this;
    }

    public ProductAction setProductListSource(String value) {
        put("&pls", value);
        return this;
    }

    public ProductAction setTransactionAffiliation(String value) {
        put(Fields.TRANSACTION_AFFILIATION, value);
        return this;
    }

    public ProductAction setTransactionCouponCode(String value) {
        put("&tcc", value);
        return this;
    }

    public ProductAction setTransactionId(String value) {
        put(Fields.TRANSACTION_ID, value);
        return this;
    }

    public ProductAction setTransactionRevenue(double value) {
        put(Fields.TRANSACTION_REVENUE, Double.toString(value));
        return this;
    }

    public ProductAction setTransactionShipping(double value) {
        put(Fields.TRANSACTION_SHIPPING, Double.toString(value));
        return this;
    }

    public ProductAction setTransactionTax(double value) {
        put(Fields.TRANSACTION_TAX, Double.toString(value));
        return this;
    }
}
