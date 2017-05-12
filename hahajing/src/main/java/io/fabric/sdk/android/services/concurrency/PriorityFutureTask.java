package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.Task.OnCompletionListener;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class PriorityFutureTask<V> extends FutureTask<V> implements Dependency, PriorityProvider, Task, Comparable {
    private Dependency dep;
    private PriorityProvider priority;
    private Task task;

    public PriorityFutureTask(Callable<V> callable) {
        super(callable);
        checkAndInit(callable);
    }

    public PriorityFutureTask(Runnable runnable, V result) {
        super(runnable, result);
        checkAndInit(runnable);
    }

    public <T extends Dependency & PriorityProvider & Task> PriorityFutureTask(Callable<V> callable, T t) {
        super(callable);
        init(t);
    }

    public <T extends Dependency & PriorityProvider & Task> PriorityFutureTask(Runnable runnable, V result, T t) {
        super(runnable, result);
        init(t);
    }

    private void checkAndInit(Object object) {
        if ((object instanceof Task) && (object instanceof Dependency) && (object instanceof PriorityProvider)) {
            this.task = (Task) object;
            this.dep = (Dependency) object;
            this.priority = (PriorityProvider) object;
            return;
        }
        init(new PriorityTask());
    }

    private <T extends Dependency & PriorityProvider & Task> void init(T t) {
        this.task = (Task) t;
        this.dep = (Dependency) t;
        this.priority = (PriorityProvider) t;
    }

    public int compareTo(Object another) {
        return Priority.compareTo(this, another);
    }

    public void addDependency(Dependency dependency) {
        this.dep.addDependency(dependency);
    }

    public Collection<Dependency> getDependencies() {
        return this.dep.getDependencies();
    }

    public Priority getPriority() {
        return this.priority.getPriority();
    }

    public void notifyFinished() {
        this.task.notifyFinished();
    }

    public boolean isFinished() {
        return this.task.isFinished();
    }

    public void addCompletionListener(OnCompletionListener completionListener) {
        this.task.addCompletionListener(completionListener);
    }

    public void setError(Throwable throwable) {
        this.task.setError(throwable);
    }

    public Throwable getError() {
        return this.task.getError();
    }

    public boolean canProcess() {
        return this.task.canProcess();
    }
}
