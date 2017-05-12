package io.fabric.sdk.android.services.concurrency;

import java.util.Collection;

public interface Dependency {
    void addDependency(Dependency dependency);

    Collection<Dependency> getDependencies();
}
