package io.fabric.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import io.fabric.sdk.android.LifeCycleCallbackManager.Callbacks;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.PriorityThreadPoolExecutor;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class Fabric {
    static final boolean DEFAULT_DEBUGGABLE = false;
    static final Logger DEFAULT_LOGGER = new DefaultLogger();
    static final String ROOT_DIR = ".Fabric";
    public static final String TAG = "Fabric";
    static volatile Fabric singleton;
    private WeakReference<Activity> activity;
    private final Context context;
    final boolean debuggable;
    private final ExecutorService executorService;
    private final IdManager idManager;
    private final InitializationCallback<Fabric> initializationCallback;
    private AtomicBoolean initialized = new AtomicBoolean(false);
    private final InitializationCallback<?> kitInitializationCallback;
    private final Map<Class<? extends Kit>, Kit> kits;
    private LifeCycleCallbackManager lifeCycleCallbackManager;
    final Logger logger;
    private final Handler mainHandler;
    private Onboarding onboarding;

    public static class Builder {
        private String appIdentifier;
        private String appInstallIdentifier;
        private final Context context;
        private boolean debuggable;
        private ExecutorService executorService;
        private Handler handler;
        private InitializationCallback<Fabric> initializationCallback;
        private Kit[] kits;
        private Logger logger;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        public Builder kits(Kit... kits) {
            if (kits == null) {
                throw new IllegalArgumentException("Kits must not be null.");
            } else if (kits.length == 0) {
                throw new IllegalArgumentException("Kits must not be empty.");
            } else if (this.kits != null) {
                throw new IllegalStateException("Kits already set.");
            } else {
                this.kits = kits;
                return this;
            }
        }

        public Builder executorService(ExecutorService executorService) {
            if (executorService == null) {
                throw new IllegalArgumentException("ExecutorService must not be null.");
            } else if (this.executorService != null) {
                throw new IllegalStateException("ExecutorService already set.");
            } else {
                this.executorService = executorService;
                return this;
            }
        }

        public Builder handler(Handler handler) {
            if (handler == null) {
                throw new IllegalArgumentException("Handler must not be null.");
            } else if (this.handler != null) {
                throw new IllegalStateException("Handler already set.");
            } else {
                this.handler = handler;
                return this;
            }
        }

        public Builder logger(Logger logger) {
            if (logger == null) {
                throw new IllegalArgumentException("Logger must not be null.");
            } else if (this.logger != null) {
                throw new IllegalStateException("Logger already set.");
            } else {
                this.logger = logger;
                return this;
            }
        }

        public Builder appIdentifier(String appIdentifier) {
            if (appIdentifier == null) {
                throw new IllegalArgumentException("appIdentifier must not be null.");
            } else if (this.appIdentifier != null) {
                throw new IllegalStateException("appIdentifier already set.");
            } else {
                this.appIdentifier = appIdentifier;
                return this;
            }
        }

        public Builder appInstallIdentifier(String appInstallIdentifier) {
            if (appInstallIdentifier == null) {
                throw new IllegalArgumentException("appInstallIdentifier must not be null.");
            } else if (this.appInstallIdentifier != null) {
                throw new IllegalStateException("appInstallIdentifier already set.");
            } else {
                this.appInstallIdentifier = appInstallIdentifier;
                return this;
            }
        }

        public Builder debuggable(boolean enabled) {
            this.debuggable = enabled;
            return this;
        }

        public Builder initializationCallback(InitializationCallback<Fabric> initializationCallback) {
            if (initializationCallback == null) {
                throw new IllegalArgumentException("initializationCallback must not be null.");
            } else if (this.initializationCallback != null) {
                throw new IllegalStateException("initializationCallback already set.");
            } else {
                this.initializationCallback = initializationCallback;
                return this;
            }
        }

        public Fabric build() {
            if (this.kits == null) {
                throw new IllegalStateException("Kits must not be null.");
            }
            if (this.executorService == null) {
                this.executorService = PriorityThreadPoolExecutor.create();
            }
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper());
            }
            if (this.logger == null) {
                if (this.debuggable) {
                    this.logger = new DefaultLogger(3);
                } else {
                    this.logger = new DefaultLogger();
                }
            }
            if (this.appIdentifier == null) {
                this.appIdentifier = this.context.getPackageName();
            }
            if (this.initializationCallback == null) {
                this.initializationCallback = InitializationCallback.EMPTY;
            }
            Map<Class<? extends Kit>, Kit> kitMap = Fabric.getKitMap(Arrays.asList(this.kits));
            return new Fabric(this.context, kitMap, this.executorService, this.handler, this.logger, this.debuggable, this.initializationCallback, new IdManager(this.context, this.appIdentifier, this.appInstallIdentifier, kitMap.values()));
        }
    }

    static Fabric singleton() {
        if (singleton != null) {
            return singleton;
        }
        throw new IllegalStateException("Must Initialize Fabric before using singleton()");
    }

    Fabric(Context context, Map<Class<? extends Kit>, Kit> kits, ExecutorService executorService, Handler mainHandler, Logger logger, boolean debuggable, InitializationCallback callback, IdManager idManager) {
        this.context = context;
        this.kits = kits;
        this.executorService = executorService;
        this.mainHandler = mainHandler;
        this.logger = logger;
        this.debuggable = debuggable;
        this.initializationCallback = callback;
        this.kitInitializationCallback = createKitInitializationCallback(kits.size());
        this.idManager = idManager;
    }

    public static Fabric with(Context context, Kit... kits) {
        if (singleton == null) {
            synchronized (Fabric.class) {
                if (singleton == null) {
                    setFabric(new Builder(context).kits(kits).build());
                }
            }
        }
        return singleton;
    }

    public static Fabric with(Fabric fabric) {
        if (singleton == null) {
            synchronized (Fabric.class) {
                if (singleton == null) {
                    setFabric(fabric);
                }
            }
        }
        return singleton;
    }

    private static void setFabric(Fabric fabric) {
        singleton = fabric;
        fabric.init();
    }

    public Fabric setCurrentActivity(Activity activity) {
        this.activity = new WeakReference(activity);
        return this;
    }

    public Activity getCurrentActivity() {
        if (this.activity != null) {
            return (Activity) this.activity.get();
        }
        return null;
    }

    private void init() {
        setCurrentActivity(extractActivity(this.context));
        this.lifeCycleCallbackManager = new LifeCycleCallbackManager(this.context);
        this.lifeCycleCallbackManager.registerCallbacks(new Callbacks() {
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Fabric.this.setCurrentActivity(activity);
            }

            public void onActivityStarted(Activity activity) {
                Fabric.this.setCurrentActivity(activity);
            }

            public void onActivityResumed(Activity activity) {
                Fabric.this.setCurrentActivity(activity);
            }
        });
        initializeKits(this.context);
    }

    public String getVersion() {
        return "1.0.1.21";
    }

    public String getIdentifier() {
        return "io.fabric.sdk.android:fabric";
    }

    void initializeKits(Context context) {
        StringBuilder initInfo;
        this.onboarding = new Onboarding(getKits());
        this.onboarding.injectParameters(context, this, InitializationCallback.EMPTY, this.idManager);
        this.onboarding.initialize();
        List<Kit> kits = new ArrayList(this.kits.values());
        Collections.sort(kits);
        if (getLogger().isLoggable("Fabric", 3)) {
            initInfo = new StringBuilder("Initializing ").append(getIdentifier()).append(" [Version: ").append(getVersion()).append("], with the following kits:\n");
        } else {
            initInfo = null;
        }
        for (Kit kit : kits) {
            kit.injectParameters(context, this, this.kitInitializationCallback, this.idManager);
        }
        for (Kit kit2 : kits) {
            kit2.addDependency(this.onboarding);
            addAnnotatedDependencies(this.kits, kit2);
            kit2.initialize();
            if (initInfo != null) {
                initInfo.append(kit2.getIdentifier()).append(" [Version: ").append(kit2.getVersion()).append("]\n");
            }
        }
        if (initInfo != null) {
            getLogger().d("Fabric", initInfo.toString());
        }
    }

    void addAnnotatedDependencies(Map<Class<? extends Kit>, Kit> kits, Kit kit) {
        DependsOn dependsOn = (DependsOn) kit.getClass().getAnnotation(DependsOn.class);
        if (dependsOn != null) {
            for (Class<? extends Kit> cls : dependsOn.value()) {
                kit.addDependency((Kit) kits.get(cls));
            }
        }
    }

    private Activity extractActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        return null;
    }

    public LifeCycleCallbackManager getLifeCycleCallbackManager() {
        return this.lifeCycleCallbackManager;
    }

    public ExecutorService getExecutorService() {
        return this.executorService;
    }

    public Handler getMainHandler() {
        return this.mainHandler;
    }

    public Collection<Kit> getKits() {
        return this.kits.values();
    }

    public static <T extends Kit> T getKit(Class<T> cls) {
        return (Kit) singleton().kits.get(cls);
    }

    public static Logger getLogger() {
        if (singleton == null) {
            return DEFAULT_LOGGER;
        }
        return singleton.logger;
    }

    public static boolean isDebuggable() {
        if (singleton == null) {
            return false;
        }
        return singleton.debuggable;
    }

    public static boolean isInitialized() {
        return singleton != null && singleton.initialized.get();
    }

    public String getAppIdentifier() {
        return this.idManager.getAppIdentifier();
    }

    public String getAppInstallIdentifier() {
        return this.idManager.getAppInstallIdentifier();
    }

    private static Map<Class<? extends Kit>, Kit> getKitMap(Collection<? extends Kit> kits) {
        HashMap<Class<? extends Kit>, Kit> map = new HashMap(kits.size());
        addToKitMap(map, kits);
        return map;
    }

    private static void addToKitMap(Map<Class<? extends Kit>, Kit> map, Collection<? extends Kit> kits) {
        for (Kit kit : kits) {
            map.put(kit.getClass(), kit);
            if (kit instanceof KitGroup) {
                addToKitMap(map, ((KitGroup) kit).getKits());
            }
        }
    }

    InitializationCallback<?> createKitInitializationCallback(final int size) {
        return new InitializationCallback() {
            final CountDownLatch kitInitializedLatch = new CountDownLatch(size);

            public void success(Object o) {
                this.kitInitializedLatch.countDown();
                if (this.kitInitializedLatch.getCount() == 0) {
                    Fabric.this.initialized.set(true);
                    Fabric.this.initializationCallback.success(Fabric.this);
                }
            }

            public void failure(Exception exception) {
                Fabric.this.initializationCallback.failure(exception);
            }
        };
    }
}
