
package dxt161330;

/**
 * Double Hashing Algorithm: An Open Addressing scheme in which a second hash function ( h2 )
 *  is used to determine step length: ik = (h(x) + k * h2(x)) % n.
 * Ver 1.0: 10/17/2018
 * @author Dinesh, Kautil
 * @param <T>
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
	 */
	public boolean add(T x) {
		double loadFactor = ((double) size+1)/capacity;
		if(loadFactor > RESIZE_THRESHOLD) {
			resize();
		}
		if(contains(x)) {
			return false;
		}

		int k = 0;
		int index = indexFor(hash(x.hashCode()),capacity);
		int startIndex = index;
		int secondHashVal = indexForHash2(x);
		while(true) {
			if(free[index]==FREE || free[index]==DELETED) {
				table[index] = x;
				size++;
				free[index] = OCCUPIED;
				return true;
			}
			if(table[index].equals(x)){
				//we have reached to the starting point
				break;
			}
			k++;
			index = (startIndex + k * secondHashVal) % capacity;
		}
		return false;
	}

	/**
	 * @param x
	 * @return
	 */
	private int hash2(T x) {
		return hash(x.hashCode()*x.hashCode());
	}

	/**
	 * Checks whether the table contains x or not
	 * @param x
	 * @return
	 */
	public boolean contains(T x) {
		int location = find(x);
		if(location!=INVALID_INDEX && !(table[location] == null) && table[location].equals(x)) {
			return true;
		}
		return false;
	}

	/**
	 * search for x and return index of x. If x is not found, return index where x can be added.
	 * @param x
	 * @return
	 */
	protected int find(T x) {
		int k = 0;
		int index = indexFor(hash(x.hashCode()),capacity);
		int startIndex = index,secondHashVal = indexForHash2(x);
		while(free[index] != FREE) { //when index has never been touched search can stop.
			if(free[index]!=DELETED && table[index]!=null && table[index].equals(x)){
				return index;
			}
			k++;
			index = (startIndex + k * secondHashVal) % capacity;
		}
		return INVALID_INDEX;
	}

	private int indexForHash2(T x){
		return 1 + hash2(x)%9; //+1 to avoid it being 0
	}
}
