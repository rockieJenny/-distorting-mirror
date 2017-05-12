package io.fabric.sdk.android.services.concurrency;

public interface Task {

    public interface OnCompletionListener<E> {
        void onComplete(E e);
    }

    void addCompletionListener(OnCompletionListener onCompletionListener);

    boolean canProcess();

    Throwable getError();

    boolean isFinished();

    void notifyFinished();

    void setError(Throwable th);
}
