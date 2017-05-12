package com.inmobi.commons.cache;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.inmobi.commons.internal.CommonsException;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.uid.UID;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public final class CacheController {
    private static Map<String, ProductCacheConfig> a = new HashMap();
    private static ProductCacheConfig b = null;
    private static boolean c = true;
    private static Map<String, Validator> d = new HashMap();
    private static Map<String, Map<String, String>> e = new HashMap();

    public interface Committer {
        void onCommit();
    }

    public interface Validator {
        boolean validate(Map<String, Object> map);
    }

    private CacheController() {
    }

    public static ProductConfig getConfig(String str, Context context, Map<String, String> map, Validator validator) throws CommonsException {
        if (validator != null) {
            synchronized (d) {
                d.put(str, validator);
            }
        }
        if (map != null) {
            synchronized (e) {
                e.put(str, map);
            }
        }
        if (c) {
            if (InternalSDKUtil.getContext() == null) {
                if (context == null) {
                    throw new CommonsException(1);
                }
                InternalSDKUtil.setContext(context);
            }
            c = false;
            e();
            d();
        }
        b.getData((Validator) null);
        ProductCacheConfig productCacheConfig = (ProductCacheConfig) a.get(str);
        if (productCacheConfig == null) {
            throw new CommonsException(2);
        }
        productCacheConfig.getData(map, validator);
        return productCacheConfig;
    }

    private static void d() {
        InternalSDKUtil.getContext().registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                CacheController.a();
            }
        }, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    static void a() {
        synchronized (d) {
            for (String str : d.keySet()) {
                try {
                    if (InternalSDKUtil.checkNetworkAvailibility(InternalSDKUtil.getContext())) {
                        Map map;
                        synchronized (e) {
                            map = (Map) e.get(str);
                        }
                        getConfig(str, null, map, (Validator) d.get(str));
                    }
                } catch (CommonsException e) {
                    Log.internal(InternalSDKUtil.LOGGING_TAG, "Unable to reinitialize product " + str);
                }
            }
        }
        if (b != null) {
            b.getData((Validator) null);
        }
    }

    private static void e() {
        Log.internal(InternalSDKUtil.LOGGING_TAG, "Bootstrapping cache.");
        LocalCache.initRoot();
        Iterator keys = LocalCache.getRoot().keys();
        while (keys.hasNext()) {
            final String str = (String) keys.next();
            try {
                Object obj = LocalCache.getRoot().get(str);
                if (obj instanceof JSONObject) {
                    ProductCacheConfig productCacheConfig = new ProductCacheConfig((JSONObject) obj, new Committer() {
                        public void onCommit() {
                            try {
                                ProductCacheConfig productCacheConfig = (ProductCacheConfig) CacheController.a.get(str);
                                if (productCacheConfig != null) {
                                    LocalCache.addToCache(str, productCacheConfig.toJSON());
                                }
                            } catch (Throwable e) {
                                Log.internal(InternalSDKUtil.LOGGING_TAG, "Unable to add json to persistent memory", e);
                            }
                        }
                    });
                    ProductCacheConfig productCacheConfig2 = (ProductCacheConfig) a.get(str);
                    if (productCacheConfig2 != null) {
                        productCacheConfig.setValidator(productCacheConfig2.getValidator());
                        productCacheConfig.setMap(productCacheConfig2.getMap());
                    }
                    a.put(str, productCacheConfig);
                }
            } catch (Throwable e) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Unable to dump config from persistent memory to products in memory", e);
            }
        }
        if (b == null) {
            b = new ProductCacheConfig(LocalCache.getRoot(), new Committer() {
                public void onCommit() {
                    CacheController.f();
                }
            });
            b.getData(UID.getInstance().getMapForEncryption(null), new Validator() {
                public boolean validate(Map<String, Object> map) {
                    return CacheController.b(map);
                }
            });
        } else {
            b.loadFromJSON(LocalCache.getRoot());
        }
        InternalSDKUtil.initialize(InternalSDKUtil.getContext());
    }

    private static void f() {
        try {
            JSONObject jSONObject = new JSONObject(b.getRawData());
            JSONObject populateToNewJSON = InternalSDKUtil.populateToNewJSON(jSONObject.getJSONObject("AND"), jSONObject.getJSONObject("common"), true);
            try {
                populateToNewJSON.put("timestamp", (int) (System.currentTimeMillis() / 1000));
            } catch (JSONException e) {
                Log.internal(InternalSDKUtil.LOGGING_TAG, "Unable to add timestamp to JSON");
            }
            Iterator keys = populateToNewJSON.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                try {
                    Object obj = LocalCache.getRoot().get(str);
                    try {
                        if (obj instanceof JSONObject) {
                            ProductCacheConfig productCacheConfig = (ProductCacheConfig) a.get(str);
                            if (productCacheConfig != null) {
                                ((JSONObject) obj).put("timestamp", 0);
                                ((JSONObject) obj).put("data", productCacheConfig.getRawData());
                            }
                        }
                    } catch (Exception e2) {
                        Log.internal(InternalSDKUtil.LOGGING_TAG, "Error while merging data -> " + e2.getMessage());
                    }
                } catch (JSONException e3) {
                }
            }
            LocalCache.saveRoot(populateToNewJSON);
            e();
            a();
        } catch (JSONException e4) {
        }
    }

    private static boolean b(Map<String, Object> map) {
        try {
            Map populateToNewMap = InternalSDKUtil.populateToNewMap((Map) map.get("AND"), (Map) map.get("common"), true);
            c(populateToNewMap);
            for (String str : populateToNewMap.keySet()) {
                Object obj = populateToNewMap.get(str);
                if (obj instanceof Map) {
                    c((Map) obj);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void c(Map<String, Object> map) {
        InternalSDKUtil.getIntFromMap(map, "expiry", 1, 2147483647L);
        InternalSDKUtil.getIntFromMap(map, "maxRetry", 0, 2147483647L);
        InternalSDKUtil.getIntFromMap(map, "retryInterval", 1, 2147483647L);
        InternalSDKUtil.getStringFromMap(map, "url");
        InternalSDKUtil.getStringFromMap(map, "protocol");
    }
}
