package Interview.InterViewSolved;

import java.util.concurrent.atomic.AtomicLong;

public class Alibaba_rpcxianliu_200313 {
    public static void main(String[] args) {

    }
    private final static AtomicLong ZERO = new AtomicLong(0);
    private AtomicLong counter = ZERO;
    private static long timestamp = System.currentTimeMillis();
    private long permitsPerSecond;

    public Alibaba_rpcxianliu_200313(long permitsPerSecond) {
        this.permitsPerSecond = permitsPerSecond;
    }

    public boolean tryAcquire() {
        long now = System.currentTimeMillis();
        if (now - timestamp < 1000) {
            if (counter.get() < permitsPerSecond) {
                counter.incrementAndGet();
                return true;
            } else {
                return false;
            }
        } else {
            counter = ZERO;
            timestamp = now;
            return false;
        }
    }

}
