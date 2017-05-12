package io.fabric.sdk.android;

import android.content.Context;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.AsyncTask.Status;
import io.fabric.sdk.android.services.concurrency.Dependency;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;
import io.fabric.sdk.android.services.concurrency.PriorityProvider;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import java.io.File;
import java.util.Collection;

public abstract class Kit<Result> implements Comparable<Kit> {
    Context context;
    Fabric fabric;
    IdManager idManager;
    InitializationCallback<Result> initializationCallback;
    InitializationTask<Result> initializationTask = new InitializationTask(this);

    protected enum InitializationStatus {
        PENDING,
        RUNNING,
        FINISHED;

        static InitializationStatus get(Status status) {
            switch (status) {
                case RUNNING:
                    return RUNNING;
                case FINISHED:
                    return FINISHED;
                default:
                    return PENDING;
            }
        }
    }

    static class InitializationTask<Result> extends PriorityAsyncTask<Void, Void, Result> implements PriorityProvider {
        private final Kit<Result> kit;

        protected void onPreExecute() {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0010 in list [B:17:0x0028]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
*/
            /*
            r6 = this;
            r5 = 1;
            super.onPreExecute();
            r1 = 0;
            r2 = r6.kit;	 Catch:{ UnmetDependencyException -> 0x0011, Exception -> 0x001a, all -> 0x0013 }
            r1 = r2.onPreExecute();	 Catch:{ UnmetDependencyException -> 0x0011, Exception -> 0x001a, all -> 0x0013 }
            if (r1 != 0) goto L_0x0010;
        L_0x000d:
            r6.cancel(r5);
        L_0x0010:
            return;
        L_0x0011:
            r0 = move-exception;
            throw r0;	 Catch:{ UnmetDependencyException -> 0x0011, Exception -> 0x001a, all -> 0x0013 }
        L_0x0013:
            r2 = move-exception;
            if (r1 != 0) goto L_0x0019;
        L_0x0016:
            r6.cancel(r5);
        L_0x0019:
            throw r2;
        L_0x001a:
            r0 = move-exception;
            r2 = io.fabric.sdk.android.Fabric.getLogger();	 Catch:{ UnmetDependencyException -> 0x0011, Exception -> 0x001a, all -> 0x0013 }
            r3 = "Fabric";	 Catch:{ UnmetDependencyException -> 0x0011, Exception -> 0x001a, all -> 0x0013 }
            r4 = "Failure onPreExecute()";	 Catch:{ UnmetDependencyException -> 0x0011, Exception -> 0x001a, all -> 0x0013 }
            r2.e(r3, r4, r0);	 Catch:{ UnmetDependencyException -> 0x0011, Exception -> 0x001a, all -> 0x0013 }
            if (r1 != 0) goto L_0x0010;
        L_0x0028:
            r6.cancel(r5);
            goto L_0x0010;
            */
            throw new UnsupportedOperationException("Method not decompiled: io.fabric.sdk.android.Kit.InitializationTask.onPreExecute():void");
        }

        public InitializationTask(Kit<Result> kit) {
            this.kit = kit;
        }

        protected Result doInBackground(Void... voids) {
            if (isCancelled()) {
                return null;
            }
            return this.kit.doInBackground();
        }

        protected void onPostExecute(Result result) {
            this.kit.onPostExecute(result);
            this.kit.initializationCallback.success(result);
        }

        protected void onCancelled(Result result) {
            this.kit.onCancelled(result);
            this.kit.initializationCallback.failure(new InitializationException(this.kit.getIdentifier() + " Initialization was cancelled"));
        }

        public Priority getPriority() {
            return Priority.HIGH;
        }
    }

    protected abstract Result doInBackground();

    public abstract String getIdentifier();

    public abstract String getVersion();

    void injectParameters(Context context, Fabric fabric, InitializationCallback<Result> callback, IdManager idManager) {
        this.fabric = fabric;
        this.context = new FabricContext(context, getIdentifier(), getPath());
        this.initializationCallback = callback;
        this.idManager = idManager;
    }

    final void initialize() {
        this.initializationTask.executeOnExecutor(this.fabric.getExecutorService(), (Void) null);
    }

    protected boolean onPreExecute() {
        return true;
    }

    protected void onPostExecute(Result result) {
    }

    protected void onCancelled(Result result) {
    }

    protected IdManager getIdManager() {
        return this.idManager;
    }

    public Context getContext() {
        return this.context;
    }

    public Fabric getFabric() {
        return this.fabric;
    }

    public String getPath() {
        return ".Fabric" + File.separator + getIdentifier();
    }

    protected InitializationStatus getInitializationStatus() {
        return InitializationStatus.get(this.initializationTask.getStatus());
    }

    protected void addDependency(Kit kit) {
        if (kit == null) {
            throw new UnmetDependencyException("Referenced Kit was null, does the kit exist?");
        }
        this.initializationTask.addDependency(kit.initializationTask);
    }

    protected Collection<Dependency> getDependencies() {
        return this.initializationTask.getDependencies();
    }

    protected void cancel(boolean mayInterrupt) {
        this.initializationTask.cancel(mayInterrupt);
    }

    protected boolean isCancelled() {
        return this.initializationTask.isCancelled();
    }

    public int compareTo(Kit another) {
        if (containsAnnotatedDependency(another)) {
            return 1;
        }
        if (another.containsAnnotatedDependency(this)) {
            return -1;
        }
        if (hasAnnotatedDependency() && !another.hasAnnotatedDependency()) {
            return 1;
        }
        if (hasAnnotatedDependency() || !another.hasAnnotatedDependency()) {
            return 0;
        }
        return -1;
    }

    boolean containsAnnotatedDependency(Kit target) {
        DependsOn dependsOn = (DependsOn) getClass().getAnnotation(DependsOn.class);
        if (dependsOn != null) {
            for (Class<? extends Kit> kit : dependsOn.value()) {
                if (kit.equals(target.getClass())) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean hasAnnotatedDependency() {
        return ((DependsOn) getClass().getAnnotation(DependsOn.class)) != null;
    }
}
