package io.fabric.sdk.android.services.concurrency;

import io.fabric.sdk.android.Kit;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface DependsOn {
    Class<? extends Kit>[] value();
}
