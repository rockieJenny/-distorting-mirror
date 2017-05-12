package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.Task.OnCompletionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PriorityTask implements Dependency, PriorityProvider, Task, Comparable {
    private final List<Dependency> dependencies = new ArrayList();
    private final AtomicBoolean hasRun = new AtomicBoolean(false);
    private final List<OnCompletionListener> onCompletionListeners = new ArrayList();
    private final AtomicReference<Throwable> throwable = new AtomicReference(null);

    public synchronized Collection<Dependency> getDependencies() {
        return this.dependencies;
    }

    public synchronized void addDependency(Dependency dependency) {
        this.dependencies.add(dependency);
    }

    public synchronized void notifyFinished() {
        this.hasRun.set(true);
        for (OnCompletionListener listener : this.onCompletionListeners) {
            listener.onComplete(this);
        }
    }

    public boolean isFinished() {
        return this.hasRun.get();
    }

    public synchronized void addCompletionListener(OnCompletionListener completionListener) {
        this.onCompletionListeners.add(completionListener);
    }

    public Priority getPriority() {
        return Priority.NORMAL;
    }

    public void setError(Throwable throwable) {
        this.throwable.set(throwable);
    }

    public Throwable getError() {
        return (Throwable) this.throwable.get();
    }

    public final int compareTo(Object other) {
        return Priority.compareTo(this, other);
    }

    public synchronized boolean canProcess() {
        boolean z;
        if (getDependencies() != null) {
            for (Dependency dependency : getDependencies()) {
                if ((dependency instanceof Task) && !((Task) dependency).isFinished()) {
                    z = false;
                    break;
                }
            }
        }
        z = true;
        return z;
    }
}
