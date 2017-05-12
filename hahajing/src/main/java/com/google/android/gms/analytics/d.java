package com.google.android.gms.analytics;

import com.google.android.gms.internal.ha;
import java.util.Collection;
import java.util.Map;

interface d {
    void a(Map<String, String> map, long j, String str, Collection<ha> collection);

    r dV();

    void dispatch();

    void l(long j);

    void setDryRun(boolean z);
}
