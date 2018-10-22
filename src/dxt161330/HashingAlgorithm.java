package dxt161330;

public abstract class HashingAlgorithm <T> {

    protected static final int FREE = 0, OCCUPIED = 1, DELETED = 2;
    protected static final int INVALID_INDEX = -1;
    // to store the elements
    Object table[];
    // array to represent whether a particular index is free(0), an element exits(1), deleted(2)
    protected int free[];
    // capacity is the length of the table. size is the number of elements in the table
    protected int capacity, size;

    /**
     *
     * @param capacity
     */
    protected HashingAlgorithm(int capacity) {
        this.table = new Object[capacity];
        this.free = new int[capacity];
        this.capacity = capacity;
        this.size = 0;
    }

    public abstract boolean add(T x);
    public abstract  boolean contains(T x);
    protected abstract int find(T x);
    // Code extracted from Java’s HashMap:
    protected final int hash(int h) {
        // This function ensures that hashCodes that differ only by
        // constant multiples at each bit position have a bounded
        // number of collisions (approximately 8 at default load factor).
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
    protected int indexFor(int h, int length) {
        // length = table.length is a power of 2 // Key x is stored at table[ hash( x.hashCode( ) ) & ( table.length − 1 ) ].
        return h & (length-1);
    }

    /**
     * called when the loadfactor exceeds a threshold value
     * capacity of the table is doubled and the elements are rehashed
     */
    public void resize() {
        Object[] table2 = table;
        capacity = 2*capacity;
        table = new Object[capacity];
        free = new int[capacity];
        size=0;
        for(int i = 0; i < table2.length; i++) {
            if(table2[i]!=null) add(cast(table2[i]));
        }
    }

    @SuppressWarnings("unchecked")
    protected T cast(Object t){
        return (T) t;
    }

    /**
     *	returns the number of elements in the table
     */
    public int size() {
        return size;
    }

    /**
     * Removes an element. Returns the element removed if successful otherwise returns null
     * @param x
     * @return
     */
    public T remove(T x) {
        int location = find(x);
        if(location!=INVALID_INDEX && !(table[location] == null) && table[location].equals(x)) {
            T removedElement = cast(table[location]);
            free[location] = DELETED;
            table[location] = null;
            size--;
            return removedElement;
        }
        return null;
    }
}
