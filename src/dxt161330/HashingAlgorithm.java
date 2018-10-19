package dxt161330;

public abstract class HashingAlgorithm <T> {
    public abstract boolean add(T x);
    public abstract  boolean contains(T x);
    public abstract T remove(T x);
    // Code extracted from Java’s HashMap:
    protected final int hash(int h) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor). h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
    protected int indexFor(int h, int length) {
        // length = table.length is a power of 2 // Key x is stored at table[ hash( x.hashCode( ) ) & ( table.length − 1 ) ].
        return h & (length-1);
    }
}
