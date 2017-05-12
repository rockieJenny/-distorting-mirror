package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.services.concurrency.Task.OnCompletionListener;
import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public abstract class PriorityAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> implements Dependency, PriorityProvider, Task, Comparable {
    private final Dependency dep;
    private final PriorityProvider priority;
    private final Task task;

    private static class ProxyExecutor<Result> implements Executor {
        private final Executor executor;
        private final PriorityAsyncTask task;

        public ProxyExecutor(Executor ex, PriorityAsyncTask task) {
            this.executor = ex;
            this.task = task;
        }

        public void execute(Runnable command) {
            this.executor.execute(new PriorityFutureTask(command, null, this.task));
        }
    }

    public PriorityAsyncTask() {
        PriorityTask pTask = new PriorityTask();
        this.task = pTask;
        this.dep = pTask;
        this.priority = pTask;
    }

    public final void executeOnExecutor(ExecutorService exec, Params... params) {
        super.executeOnExecutor(new ProxyExecutor(exec, this), params);
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
