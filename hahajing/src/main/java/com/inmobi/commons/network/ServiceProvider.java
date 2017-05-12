package com.inmobi.commons.network;

import com.inmobi.commons.network.abstraction.INetworkListener;
import com.inmobi.commons.network.abstraction.INetworkServiceProvider;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceProvider implements INetworkServiceProvider {
    private static ServiceProvider a;
    private ExecutorService b = Executors.newFixedThreadPool(15);

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        if (a == null) {
            synchronized (ServiceProvider.class) {
                if (a == null) {
                    a = new ServiceProvider();
                }
            }
        }
        return a;
    }

    public void executeTask(Request request, INetworkListener iNetworkListener) {
        this.b.execute(new NetworkRequestTask(request, iNetworkListener));
    }
}
