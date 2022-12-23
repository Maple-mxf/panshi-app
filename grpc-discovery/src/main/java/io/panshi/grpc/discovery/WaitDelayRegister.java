package io.panshi.grpc.discovery;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public final class WaitDelayRegister implements DelayRegister {

    private final Duration waitTime;

    public WaitDelayRegister(Duration waitTime) {
        this.waitTime = waitTime;
    }

    @Override
    public boolean allowRegis() {
        try {
            TimeUnit.SECONDS.sleep(waitTime.getSeconds());
        } catch (InterruptedException ignore) {
            Thread.currentThread().interrupt();
        }
        return true;
    }
}
