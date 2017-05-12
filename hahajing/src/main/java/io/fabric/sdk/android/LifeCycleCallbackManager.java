package io.fabric.sdk.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.util.HashSet;
import java.util.Set;

class LifeCycleCallbackManager {
    private final Application application;
    private InnerActivityLifeCycleCallbacks innerCallbacks;

    public static abstract class Callbacks {
        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityStopped(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
        }
    }

    private static class InnerActivityLifeCycleCallbacks {
        private final Application application;
        private final Set<ActivityLifecycleCallbacks> registeredCallbacks = new HashSet();

        InnerActivityLifeCycleCallbacks(Application application) {
            this.application = application;
        }

        @TargetApi(14)
        private void clearCallbacks() {
            for (ActivityLifecycleCallbacks callback : this.registeredCallbacks) {
                this.application.unregisterActivityLifecycleCallbacks(callback);
            }
        }

        @TargetApi(14)
        private boolean registerLifecycleCallbacks(final Callbacks callbacks) {
            if (this.application == null) {
                return false;
            }
            ActivityLifecycleCallbacks callbackWrapper = new ActivityLifecycleCallbacks() {
                public void onActivityCreated(Activity activity, Bundle bundle) {
                    callbacks.onActivityCreated(activity, bundle);
                }

                public void onActivityStarted(Activity activity) {
                    callbacks.onActivityStarted(activity);
                }

                public void onActivityResumed(Activity activity) {
                    callbacks.onActivityResumed(activity);
                }

                public void onActivityPaused(Activity activity) {
                    callbacks.onActivityPaused(activity);
                }

                public void onActivityStopped(Activity activity) {
                    callbacks.onActivityStopped(activity);
                }

                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                    callbacks.onActivitySaveInstanceState(activity, bundle);
                }

                public void onActivityDestroyed(Activity activity) {
                    callbacks.onActivityDestroyed(activity);
                }
            };
            this.application.registerActivityLifecycleCallbacks(callbackWrapper);
            this.registeredCallbacks.add(callbackWrapper);
            return true;
        }
    }

    LifeCycleCallbackManager(Context context) {
        this.application = (Application) context.getApplicationContext();
        if (VERSION.SDK_INT >= 14) {
            this.innerCallbacks = new InnerActivityLifeCycleCallbacks(this.application);
        }
    }

    public boolean registerCallbacks(Callbacks callbacks) {
        return this.innerCallbacks != null && this.innerCallbacks.registerLifecycleCallbacks(callbacks);
    }

    public void resetCallbacks() {
        if (this.innerCallbacks != null) {
            this.innerCallbacks.clearCallbacks();
        }
    }
}
