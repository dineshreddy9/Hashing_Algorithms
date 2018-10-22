
package dxt161330;

/**
 * Double Hashing Algorithm: An Open Addressing scheme in which a second hash function ( h2 )
 *  is used to determine step length: ik = (h(x) + k * h2(x)) % n.
 * Ver 1.0: 10/17/2018
 * @author Dinesh, Kautil
 * @param <T> type of stored element
 */
public class DoubleHashing<T> extends HashingAlgorithm<T> {
	/**
	 * ratio of size to capacity after which resize should be done to ensure optimal working for <b>this</b> algorithm
	 */
	private static final double RESIZE_THRESHOLD = 0.5;


	/**
	 * Default constructor which initializes the table with default capacity as 32
 	 */
	DoubleHashing(){
		super(32);
	}

	/**
	 * adds an element to the table
	 * returns true if the element is added successfully otherwise returns false
	 * @param x element to be added
	 * @return true is added false else
	 */
	public boolean add(T x) {
		if(x==null){
			throw new IllegalArgumentException("Key cannot be null");
		}
		double loadFactor = ((double) size+1)/capacity;
		if(loadFactor > RESIZE_THRESHOLD) {
			resize();
		}
		int findLocation = find(x);
		if(!(table[findLocation] == null) && table[findLocation].equals(x)) {
			//value already exists in the table
			return false;
		}
		table[findLocation] = x;
		free[findLocation] = OCCUPIED;
		size++;
		return true;
	}

	/**
	 * The second hash function
	 * @param x hashcode of element x
	 */
	private int hash2(T x) {
		return Math.abs(x.hashCode())%9;
	}

	/**
	 * Checks whether the table contains x or not
	 * @param x element to be checked for
	 * @return true if present false else
	 */
	public boolean contains(T x) {
		if(x==null){
			throw new IllegalArgumentException("Key cannot be null");
		}
		int location = find(x);
		if(!(table[location] == null) && table[location].equals(x)) {
			return true;
		}
		return false;
	}

	/**
	 * search for x and return index of x. If x is not found, return index where x can be added.
	 * @param x element
	 */
	protected int find(T x) {
		int k = 0;
		int index = indexFor(hash(x.hashCode()),capacity);
		int startIndex = index,secondHashVal = indexForHash2(x);
		int lastDelete = INVALID_INDEX;
		while(free[index] != FREE) { //when index has never been touched search can stop.
			if(free[index]!=DELETED && table[index]!=null && table[index].equals(x)){
				return index;
			}else if(free[index]==DELETED&&lastDelete==INVALID_INDEX){ //set to first deleted index only
				lastDelete = index;
			}
			k++;
			index = (startIndex + k * secondHashVal) % capacity;
			if(index==startIndex){
				//reached the start again.
				break;
			}
		}
		return lastDelete==INVALID_INDEX?index:lastDelete;
	}

	/**
	 * mapping of hashFunction2 to an index
	 * @param x element
	 */
	private int indexForHash2(T x){
		return 1 + hash2(x); //+1 to avoid it being 0
	}
}
