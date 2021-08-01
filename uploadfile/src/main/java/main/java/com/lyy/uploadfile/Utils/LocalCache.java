package main.java.com.lyy.uploadfile.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class LocalCache {

    Logger log = LoggerFactory.getLogger(LocalCache.class);

    protected final static ScheduledExecutorService expirePool = Executors.newScheduledThreadPool(16);
    protected static final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();

    public static Object put(String key, Object value) {
        return cache.put(key, value);
    }

    /**
     *
     * @param key 键
     * @param value 值
     * @param delay 毫秒
     * @return
     */
    public static Object set(String key, Object value, long delay){
        if (cache.contains(key)) {
            return value;
        }
        Object val = cache.put(key, value);
        expirePool.schedule(new ExpiredThread(key), delay, TimeUnit.MILLISECONDS);
        return val;
    }

    public static Object get(String key){
        return cache.get(key);
    }

    static class ExpiredThread implements Runnable{
        private String key;

        public ExpiredThread(String key) {
            super();
            this.key = key;
        }

        @Override
        public void run() {
            cache.remove(key);
        }
    }
}
