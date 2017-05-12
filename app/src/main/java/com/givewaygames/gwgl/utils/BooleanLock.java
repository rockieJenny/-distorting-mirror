package com.givewaygames.gwgl.utils;

public class BooleanLock {
    private boolean value;

    public BooleanLock(boolean initialValue) {
        this.value = initialValue;
    }

    public BooleanLock() {
        this(false);
    }

    public synchronized void setValue(boolean newValue) {
        if (newValue != this.value) {
            this.value = newValue;
            notifyAll();
        }
    }

    public synchronized boolean waitToSetTrue(long msTimeout) {
        boolean success;
        success = waitUntilFalse(msTimeout);
        if (success) {
            setValue(true);
        }
        return success;
    }

    public synchronized boolean waitToSetFalse(long msTimeout) throws InterruptedException {
        boolean success;
        success = waitUntilTrue(msTimeout);
        if (success) {
            setValue(false);
        }
        return success;
    }

    public synchronized boolean isTrue() {
        return this.value;
    }

    public synchronized boolean isFalse() {
        return !this.value;
    }

    public synchronized boolean waitUntilTrue(long msTimeout) {
        return waitUntilStateIs(true, msTimeout);
    }

    public synchronized boolean waitUntilFalse(long msTimeout) {
        return waitUntilStateIs(false, msTimeout);
    }

    public synchronized boolean waitUntilStateIs(boolean state, long msTimeout) {
        boolean z = true;
        synchronized (this) {
            if (msTimeout == 0) {
                while (this.value != state) {
                    try {
                        wait();
                    } catch (Exception e) {
                    }
                }
            } else {
                long endTime = System.currentTimeMillis() + msTimeout;
                long msRemaining = msTimeout;
                while (this.value != state && msRemaining > 0) {
                    wait(msRemaining);
                    msRemaining = endTime - System.currentTimeMillis();
                }
                if (this.value != state) {
                    z = false;
                }
            }
        }
        return z;
    }
}
