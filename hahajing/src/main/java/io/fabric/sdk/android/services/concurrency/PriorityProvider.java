package io.fabric.sdk.android.services.concurrency;

public interface PriorityProvider<T> {
    Priority getPriority();
}
