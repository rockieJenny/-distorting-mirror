package com.google.android.gms.appindexing;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.internal.jx;
import com.google.android.gms.plus.PlusShare;
import com.inmobi.commons.analytics.db.AnalyticsEvent;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import org.xmlrpc.android.IXMLRPCSerializer;

public class Thing {
    final Bundle DI;

    public static class Builder {
        final Bundle DJ = new Bundle();

        public Thing build() {
            return new Thing(this.DJ);
        }

        public Builder put(String key, Thing value) {
            jx.i(key);
            if (value != null) {
                this.DJ.putParcelable(key, value.DI);
            }
            return this;
        }

        public Builder put(String key, String value) {
            jx.i(key);
            if (value != null) {
                this.DJ.putString(key, value);
            }
            return this;
        }

        public Builder setDescription(String description) {
            put(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, description);
            return this;
        }

        public Builder setId(String id) {
            if (id != null) {
                put(AnalyticsEvent.EVENT_ID, id);
            }
            return this;
        }

        public Builder setName(String name) {
            jx.i(name);
            put(IXMLRPCSerializer.TAG_NAME, name);
            return this;
        }

        public Builder setType(String type) {
            put(AnalyticsSQLiteHelper.EVENT_LIST_TYPE, type);
            return this;
        }

        public Builder setUrl(Uri url) {
            jx.i(url);
            put("url", url.toString());
            return this;
        }
    }

    Thing(Bundle propertyBundle) {
        this.DI = propertyBundle;
    }

    public Bundle fI() {
        return this.DI;
    }
}
