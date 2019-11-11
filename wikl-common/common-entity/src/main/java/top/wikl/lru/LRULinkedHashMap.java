package top.wikl.lru;


import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import com.googlecode.concurrentlinkedhashmap.Weighers;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * LRU
 *
 * @param
 * @author XYL
 * @date 2019/10/30 17:34
 * @return
 * @since V1.0
 */
public class LRULinkedHashMap<K, V> {
    public static final int DEFAULT_CONCURENCY_LEVEL = 64;

    private final ConcurrentLinkedHashMap<K, V> map;
    private final AtomicLong requests = new AtomicLong(0);
    private final AtomicLong hits = new AtomicLong(0);
    private final AtomicLong lastRequests = new AtomicLong(0);
    private final AtomicLong lastHits = new AtomicLong(0);
    private volatile boolean capacitySetManually;

    public LRULinkedHashMap(int capacity) {
        this(capacity, DEFAULT_CONCURENCY_LEVEL);
    }

    public LRULinkedHashMap(int capacity, int concurrency) {
        map = new ConcurrentLinkedHashMap.Builder<K, V>().weigher(Weighers.<V>singleton())
                .initialCapacity(capacity).maximumWeightedCapacity(capacity)
                .concurrencyLevel(concurrency).build();
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public V get(K key) {
        V v = map.get(key);
        requests.incrementAndGet();
        if (v != null) {
            hits.incrementAndGet();
        }
        return v;
    }

    public V getInternal(K key) {
        return map.get(key);
    }

    public void remove(K key) {
        map.remove(key);
    }

    public long getCapacity() {
        return map.capacity();
    }

    public boolean isCapacitySetManually() {
        return capacitySetManually;
    }

    public void updateCapacity(int capacity) {
        map.setCapacity(capacity);
    }

    public void setCapacity(int capacity) {
        updateCapacity(capacity);
        capacitySetManually = true;
    }

    public int getSize() {
        return map.size();
    }

    public long getHits() {
        return hits.get();
    }

    public long getRequests() {
        return requests.get();
    }

    public double getRecentHitRate() {
        long r = requests.get();
        long h = hits.get();
        try {
            return ((double) (h - lastHits.get())) / (r - lastRequests.get());
        } finally {
            lastRequests.set(r);
            lastHits.set(h);
        }
    }

    public void clear() {
        map.clear();
        requests.set(0);
        hits.set(0);
    }

    public Set<K> getKeySet() {
        return map.keySet();
    }


    public static void main(String[] args) {
        LRULinkedHashMap<Integer, Integer> cache = new LRULinkedHashMap<>(5);
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            int k = r.nextInt(10);
            System.out.println("input " + k);
            cache.put(k, k);
            System.out.println(cache.getKeySet().toString());
        }


        LRULinkedHashMap lruCache = new LRULinkedHashMap(5);

        lruCache.put("001", "用户1信息");
        lruCache.put("002", "用户1信息");
        lruCache.put("003", "用户1信息");
        lruCache.put("004", "用户1信息");
        lruCache.put("005", "用户1信息");
        lruCache.get("002");
        lruCache.put("004", "用户2信息更新");
        lruCache.put("006", "用户6信息");

        System.out.println(lruCache.get("001"));

        System.out.println(lruCache.get("004"));

        System.out.println(lruCache.get("006"));
    }
}