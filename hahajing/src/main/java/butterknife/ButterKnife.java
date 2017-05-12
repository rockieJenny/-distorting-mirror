package butterknife;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.util.Property;
import android.view.View;
import butterknife.internal.ButterKnifeProcessor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ButterKnife {
    static final Map<Class<?>, Method> INJECTORS = new LinkedHashMap();
    static final Method NO_OP = null;
    static final Map<Class<?>, Method> RESETTERS = new LinkedHashMap();
    private static final String TAG = "ButterKnife";
    private static boolean debug = false;

    public interface Action<T extends View> {
        void apply(T t, int i);
    }

    public enum Finder {
        VIEW {
            public View findOptionalView(Object source, int id) {
                return ((View) source).findViewById(id);
            }

            protected Context getContext(Object source) {
                return ((View) source).getContext();
            }
        },
        ACTIVITY {
            public View findOptionalView(Object source, int id) {
                return ((Activity) source).findViewById(id);
            }

            protected Context getContext(Object source) {
                return (Activity) source;
            }
        },
        DIALOG {
            public View findOptionalView(Object source, int id) {
                return ((Dialog) source).findViewById(id);
            }

            protected Context getContext(Object source) {
                return ((Dialog) source).getContext();
            }
        };

        public abstract View findOptionalView(Object obj, int i);

        protected abstract Context getContext(Object obj);

        public static <T extends View> T[] arrayOf(T... views) {
            return views;
        }

        public static <T extends View> List<T> listOf(T... views) {
            return new ImmutableViewList(views);
        }

        public View findRequiredView(Object source, int id, String who) {
            View view = findOptionalView(source, id);
            if (view != null) {
                return view;
            }
            throw new IllegalStateException("Required view '" + getContext(source).getResources().getResourceEntryName(id) + "' with ID " + id + " for " + who + " was not found. If this view is optional add '@Optional' annotation.");
        }
    }

    public interface Setter<T extends View, V> {
        void set(T t, V v, int i);
    }

    private ButterKnife() {
        throw new AssertionError("No instances.");
    }

    public static void setDebug(boolean debug) {
        debug = debug;
    }

    public static void inject(Activity target) {
        inject(target, target, Finder.ACTIVITY);
    }

    public static void inject(View target) {
        inject(target, target, Finder.VIEW);
    }

    public static void inject(Dialog target) {
        inject(target, target, Finder.DIALOG);
    }

    public static void inject(Object target, Activity source) {
        inject(target, source, Finder.ACTIVITY);
    }

    public static void inject(Object target, View source) {
        inject(target, source, Finder.VIEW);
    }

    public static void inject(Object target, Dialog source) {
        inject(target, source, Finder.DIALOG);
    }

    public static void reset(Object target) {
        Class<?> targetClass = target.getClass();
        try {
            if (debug) {
                Log.d(TAG, "Looking up view injector for " + targetClass.getName());
            }
            Method reset = findResettersForClass(targetClass);
            if (reset != null) {
                reset.invoke(null, new Object[]{target});
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable e2) {
            Throwable t = e2;
            if (t instanceof InvocationTargetException) {
                t = t.getCause();
            }
            throw new RuntimeException("Unable to reset views for " + target, t);
        }
    }

    static void inject(Object target, Object source, Finder finder) {
        Class<?> targetClass = target.getClass();
        try {
            if (debug) {
                Log.d(TAG, "Looking up view injector for " + targetClass.getName());
            }
            Method inject = findInjectorForClass(targetClass);
            if (inject != null) {
                inject.invoke(null, new Object[]{finder, target, source});
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Throwable e2) {
            Throwable t = e2;
            if (t instanceof InvocationTargetException) {
                t = t.getCause();
            }
            throw new RuntimeException("Unable to inject views for " + target, t);
        }
    }

    private static Method findInjectorForClass(Class<?> cls) throws NoSuchMethodException {
        Method inject = (Method) INJECTORS.get(cls);
        if (inject != null) {
            if (debug) {
                Log.d(TAG, "HIT: Cached in injector map.");
            }
            return inject;
        }
        String clsName = cls.getName();
        if (clsName.startsWith(ButterKnifeProcessor.ANDROID_PREFIX) || clsName.startsWith(ButterKnifeProcessor.JAVA_PREFIX)) {
            if (debug) {
                Log.d(TAG, "MISS: Reached framework class. Abandoning search.");
            }
            return NO_OP;
        }
        try {
            inject = Class.forName(clsName + ButterKnifeProcessor.SUFFIX).getMethod("inject", new Class[]{Finder.class, cls, Object.class});
            if (debug) {
                Log.d(TAG, "HIT: Class loaded injection class.");
            }
        } catch (ClassNotFoundException e) {
            if (debug) {
                Log.d(TAG, "Not found. Trying superclass " + cls.getSuperclass().getName());
            }
            inject = findInjectorForClass(cls.getSuperclass());
        }
        INJECTORS.put(cls, inject);
        return inject;
    }

    private static Method findResettersForClass(Class<?> cls) throws NoSuchMethodException {
        Method inject = (Method) RESETTERS.get(cls);
        if (inject != null) {
            if (debug) {
                Log.d(TAG, "HIT: Cached in injector map.");
            }
            return inject;
        }
        String clsName = cls.getName();
        if (clsName.startsWith(ButterKnifeProcessor.ANDROID_PREFIX) || clsName.startsWith(ButterKnifeProcessor.JAVA_PREFIX)) {
            if (debug) {
                Log.d(TAG, "MISS: Reached framework class. Abandoning search.");
            }
            return NO_OP;
        }
        try {
            inject = Class.forName(clsName + ButterKnifeProcessor.SUFFIX).getMethod("reset", new Class[]{cls});
            if (debug) {
                Log.d(TAG, "HIT: Class loaded injection class.");
            }
        } catch (ClassNotFoundException e) {
            if (debug) {
                Log.d(TAG, "Not found. Trying superclass " + cls.getSuperclass().getName());
            }
            inject = findResettersForClass(cls.getSuperclass());
        }
        RESETTERS.put(cls, inject);
        return inject;
    }

    public static <T extends View> void apply(List<T> list, Action<? super T> action) {
        int count = list.size();
        for (int i = 0; i < count; i++) {
            action.apply((View) list.get(i), i);
        }
    }

    public static <T extends View, V> void apply(List<T> list, Setter<? super T, V> setter, V value) {
        int count = list.size();
        for (int i = 0; i < count; i++) {
            setter.set((View) list.get(i), value, i);
        }
    }

    @TargetApi(14)
    public static <T extends View, V> void apply(List<T> list, Property<? super T, V> setter, V value) {
        int count = list.size();
        for (int i = 0; i < count; i++) {
            setter.set(list.get(i), value);
        }
    }

    public static <T extends View> T findById(View view, int id) {
        return view.findViewById(id);
    }

    public static <T extends View> T findById(Activity activity, int id) {
        return activity.findViewById(id);
    }

    public static <T extends View> T findById(Dialog dialog, int id) {
        return dialog.findViewById(id);
    }
}
