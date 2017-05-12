package com.google.android.gms.analytics;

import android.text.TextUtils;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.HitTypes;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.y.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HitBuilders {

    protected static class HitBuilder<T extends HitBuilder> {
        private Map<String, String> BA = new HashMap();
        ProductAction BB;
        Map<String, List<Product>> BC = new HashMap();
        List<Promotion> BD = new ArrayList();
        List<Product> BE = new ArrayList();

        protected HitBuilder() {
        }

        public T addImpression(Product product, String impressionList) {
            if (product == null) {
                ae.W("product should be non-null");
            } else {
                if (impressionList == null) {
                    impressionList = "";
                }
                if (!this.BC.containsKey(impressionList)) {
                    this.BC.put(impressionList, new ArrayList());
                }
                ((List) this.BC.get(impressionList)).add(product);
            }
            return this;
        }

        public T addProduct(Product product) {
            if (product == null) {
                ae.W("product should be non-null");
            } else {
                this.BE.add(product);
            }
            return this;
        }

        public T addPromotion(Promotion promotion) {
            if (promotion == null) {
                ae.W("promotion should be non-null");
            } else {
                this.BD.add(promotion);
            }
            return this;
        }

        public Map<String, String> build() {
            Map<String, String> hashMap = new HashMap(this.BA);
            if (this.BB != null) {
                hashMap.putAll(this.BB.build());
            }
            int i = 1;
            for (Promotion aq : this.BD) {
                hashMap.putAll(aq.aq(s.B(i)));
                i++;
            }
            i = 1;
            for (Product aq2 : this.BE) {
                hashMap.putAll(aq2.aq(s.A(i)));
                i++;
            }
            int i2 = 1;
            for (Entry entry : this.BC.entrySet()) {
                List<Product> list = (List) entry.getValue();
                String D = s.D(i2);
                int i3 = 1;
                for (Product aq3 : list) {
                    hashMap.putAll(aq3.aq(D + s.C(i3)));
                    i3++;
                }
                if (!TextUtils.isEmpty((CharSequence) entry.getKey())) {
                    hashMap.put(D + "nm", entry.getKey());
                }
                i2++;
            }
            return hashMap;
        }

        protected String get(String paramName) {
            return (String) this.BA.get(paramName);
        }

        public final T set(String paramName, String paramValue) {
            y.eK().a(a.MAP_BUILDER_SET);
            if (paramName != null) {
                this.BA.put(paramName, paramValue);
            } else {
                ae.W(" HitBuilder.set() called with a null paramName.");
            }
            return this;
        }

        public final T setAll(Map<String, String> params) {
            y.eK().a(a.MAP_BUILDER_SET_ALL);
            if (params != null) {
                this.BA.putAll(new HashMap(params));
            }
            return this;
        }

        public T setCampaignParamsFromUrl(String utmParams) {
            y.eK().a(a.MAP_BUILDER_SET_CAMPAIGN_PARAMS);
            Object ao = an.ao(utmParams);
            if (!TextUtils.isEmpty(ao)) {
                Map an = an.an(ao);
                set(Fields.CAMPAIGN_CONTENT, (String) an.get("utm_content"));
                set(Fields.CAMPAIGN_MEDIUM, (String) an.get("utm_medium"));
                set(Fields.CAMPAIGN_NAME, (String) an.get("utm_campaign"));
                set(Fields.CAMPAIGN_SOURCE, (String) an.get("utm_source"));
                set(Fields.CAMPAIGN_KEYWORD, (String) an.get("utm_term"));
                set(Fields.CAMPAIGN_ID, (String) an.get("utm_id"));
                set("&gclid", (String) an.get("gclid"));
                set("&dclid", (String) an.get("dclid"));
                set("&gmob_t", (String) an.get("gmob_t"));
            }
            return this;
        }

        public T setCustomDimension(int index, String dimension) {
            set(s.y(index), dimension);
            return this;
        }

        public T setCustomMetric(int index, float metric) {
            set(s.z(index), Float.toString(metric));
            return this;
        }

        protected T setHitType(String hitType) {
            set(Fields.HIT_TYPE, hitType);
            return this;
        }

        public T setNewSession() {
            set(Fields.SESSION_CONTROL, "start");
            return this;
        }

        public T setNonInteraction(boolean nonInteraction) {
            set(Fields.NON_INTERACTION, an.E(nonInteraction));
            return this;
        }

        public T setProductAction(ProductAction action) {
            this.BB = action;
            return this;
        }

        public T setPromotionAction(String action) {
            this.BA.put("&promoa", action);
            return this;
        }
    }

    @Deprecated
    public static class AppViewBuilder extends HitBuilder<AppViewBuilder> {
        public AppViewBuilder() {
            y.eK().a(a.CONSTRUCT_APP_VIEW);
            set(Fields.HIT_TYPE, "screenview");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }
    }

    public static class EventBuilder extends HitBuilder<EventBuilder> {
        public EventBuilder() {
            y.eK().a(a.CONSTRUCT_EVENT);
            set(Fields.HIT_TYPE, "event");
        }

        public EventBuilder(String category, String action) {
            this();
            setCategory(category);
            setAction(action);
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public EventBuilder setAction(String action) {
            set(Fields.EVENT_ACTION, action);
            return this;
        }

        public EventBuilder setCategory(String category) {
            set(Fields.EVENT_CATEGORY, category);
            return this;
        }

        public EventBuilder setLabel(String label) {
            set(Fields.EVENT_LABEL, label);
            return this;
        }

        public EventBuilder setValue(long value) {
            set(Fields.EVENT_VALUE, Long.toString(value));
            return this;
        }
    }

    public static class ExceptionBuilder extends HitBuilder<ExceptionBuilder> {
        public ExceptionBuilder() {
            y.eK().a(a.CONSTRUCT_EXCEPTION);
            set(Fields.HIT_TYPE, HitTypes.EXCEPTION);
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public ExceptionBuilder setDescription(String description) {
            set(Fields.EX_DESCRIPTION, description);
            return this;
        }

        public ExceptionBuilder setFatal(boolean fatal) {
            set(Fields.EX_FATAL, an.E(fatal));
            return this;
        }
    }

    @Deprecated
    public static class ItemBuilder extends HitBuilder<ItemBuilder> {
        public ItemBuilder() {
            y.eK().a(a.CONSTRUCT_ITEM);
            set(Fields.HIT_TYPE, HitTypes.ITEM);
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public ItemBuilder setCategory(String category) {
            set(Fields.ITEM_CATEGORY, category);
            return this;
        }

        public ItemBuilder setCurrencyCode(String currencyCode) {
            set(Fields.CURRENCY_CODE, currencyCode);
            return this;
        }

        public ItemBuilder setName(String name) {
            set(Fields.ITEM_NAME, name);
            return this;
        }

        public ItemBuilder setPrice(double price) {
            set(Fields.ITEM_PRICE, Double.toString(price));
            return this;
        }

        public ItemBuilder setQuantity(long quantity) {
            set(Fields.ITEM_QUANTITY, Long.toString(quantity));
            return this;
        }

        public ItemBuilder setSku(String sku) {
            set(Fields.ITEM_SKU, sku);
            return this;
        }

        public ItemBuilder setTransactionId(String transactionid) {
            set(Fields.TRANSACTION_ID, transactionid);
            return this;
        }
    }

    public static class ScreenViewBuilder extends HitBuilder<ScreenViewBuilder> {
        public ScreenViewBuilder() {
            y.eK().a(a.CONSTRUCT_APP_VIEW);
            set(Fields.HIT_TYPE, "screenview");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }
    }

    public static class SocialBuilder extends HitBuilder<SocialBuilder> {
        public SocialBuilder() {
            y.eK().a(a.CONSTRUCT_SOCIAL);
            set(Fields.HIT_TYPE, "social");
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public SocialBuilder setAction(String action) {
            set(Fields.SOCIAL_ACTION, action);
            return this;
        }

        public SocialBuilder setNetwork(String network) {
            set(Fields.SOCIAL_NETWORK, network);
            return this;
        }

        public SocialBuilder setTarget(String target) {
            set(Fields.SOCIAL_TARGET, target);
            return this;
        }
    }

    public static class TimingBuilder extends HitBuilder<TimingBuilder> {
        public TimingBuilder() {
            y.eK().a(a.CONSTRUCT_TIMING);
            set(Fields.HIT_TYPE, HitTypes.TIMING);
        }

        public TimingBuilder(String category, String variable, long value) {
            this();
            setVariable(variable);
            setValue(value);
            setCategory(category);
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public TimingBuilder setCategory(String category) {
            set(Fields.TIMING_CATEGORY, category);
            return this;
        }

        public TimingBuilder setLabel(String label) {
            set(Fields.TIMING_LABEL, label);
            return this;
        }

        public TimingBuilder setValue(long value) {
            set(Fields.TIMING_VALUE, Long.toString(value));
            return this;
        }

        public TimingBuilder setVariable(String variable) {
            set(Fields.TIMING_VAR, variable);
            return this;
        }
    }

    @Deprecated
    public static class TransactionBuilder extends HitBuilder<TransactionBuilder> {
        public TransactionBuilder() {
            y.eK().a(a.CONSTRUCT_TRANSACTION);
            set(Fields.HIT_TYPE, HitTypes.TRANSACTION);
        }

        public /* bridge */ /* synthetic */ Map build() {
            return super.build();
        }

        public TransactionBuilder setAffiliation(String affiliation) {
            set(Fields.TRANSACTION_AFFILIATION, affiliation);
            return this;
        }

        public TransactionBuilder setCurrencyCode(String currencyCode) {
            set(Fields.CURRENCY_CODE, currencyCode);
            return this;
        }

        public TransactionBuilder setRevenue(double revenue) {
            set(Fields.TRANSACTION_REVENUE, Double.toString(revenue));
            return this;
        }

        public TransactionBuilder setShipping(double shipping) {
            set(Fields.TRANSACTION_SHIPPING, Double.toString(shipping));
            return this;
        }

        public TransactionBuilder setTax(double tax) {
            set(Fields.TRANSACTION_TAX, Double.toString(tax));
            return this;
        }

        public TransactionBuilder setTransactionId(String transactionid) {
            set(Fields.TRANSACTION_ID, transactionid);
            return this;
        }
    }
}
