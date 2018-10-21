package dxt161330;

/**
 * RobinHood Hashing Algorithm: An Open Addressing scheme in which a new element being inserted with a larger probe
 * count can displace an existing element in the table with a smaller probe count.
 * Ver 1.0: 10/15/2018
 * @author Dinesh, Kautil
 * @param <T>
 */
public class RobinHood<T> extends HashingAlgorithm<T>{


	/**
	 * ratio of size to capacity after which resize should be done to ensure optimal working <b>this</b> algorithm
	 */
	private static final double RESIZE_THRESHOLD = 0.5;
	//
	private static final int MAX_DISPLACEMENT = 4;
	private static final int INVALID_INDEX = -1;


	/**
	 *  Default constructor which initializes the table with default capacity as 32
 	 */
	RobinHood() {
		this(32);
	}

	/**
	 *  Parameterized constructor which takes initial capacity as argument
	 * @param capacity
	 */
	RobinHood(int capacity){
		this.capacity = capacity;
		table = new Object[this.capacity];
		free = new int[this.capacity];
		size = 0;
	}

	/**
	 * adds an element to the table returns true
	 * if the element is added successfully otherwise returns false
	 * @param x
	 * @return
	 */
	public boolean add(T x) {
		if(x==null){
			throw new IllegalArgumentException("Key cannot be null");
		}
		if(contains(x)) {
			return false;
		}

		int location = indexFor(hash(x.hashCode()),capacity);
		double loadFactor = ((double) size+1)/capacity;
		if(loadFactor > RESIZE_THRESHOLD) {
			resize();
		}

		int displacement = 0,swaps = 0;
		T startKey = x;
		while(true) {
			if(free[location]==FREE || free[location]==DELETED) {
				table[location] = x;
				size++;
				free[location] = OCCUPIED;
				return true;
			} else if(displacement<(MAX_DISPLACEMENT)&&displacement((T) table[location], location) >= displacement(x, location)) {
				//element at location is more away from actual index than current element, continue search
				displacement++;
				location = (location + 1) % capacity;
			} else {
				//element at location is not as far as current element,
				// replace element in table and make replaced element search for a new place
				T temp = x;
				x = (T) table[location];
				table[location] = temp;
				location = (location + 1) % capacity;
				displacement = displacement(x, location);
				if(x.equals(startKey)){
					//we have reached to the start point
					break;
				}
			}
		}
		//all the locations tried
		return false;
	}

	/**
	 * Calculate displacement of x from its ideal location of h(x).
	 * @param x
	 * @param loc
	 * @return
	 */
	public int displacement(T x, int loc) {
		int i0 = indexFor(hash(x.hashCode()), capacity);
		if(loc >= i0) {
			return (loc - i0);
		} else {
			return (table.length + loc - i0);
		}
	}

	/**
	 * Checks whether the table contains x or not
	 * @param key
	 * @return
	 */
	public boolean contains(T key) {
		int location = find(key);
		return location!=INVALID_INDEX && !(table[location] == null) && table[location].equals(key);
	}

	/**
	 * search for x and return index of x. If x is not found, return index where x can be added.
	 * @param x
	 * @return
	 */
	private int find(T x) {
		int index = indexFor(hash(x.hashCode()), capacity);
		int displacement = 0;
		while(displacement<MAX_DISPLACEMENT) {
			//System.out.println(ik);
			if(free[index]!=DELETED && table[index]!=null && table[index].equals(x)){
				return index;
			}else if(free[index] == FREE) {
				//this index has never been touched hence search can stop here
				return INVALID_INDEX;
			}
			index = (index + 1) % capacity;
			++displacement;
		}
		return INVALID_INDEX;
	}

	/**
	 *	Removes an element. Returns the element removed if successful otherwise returns null
 	 */
	public T remove(T x) {
		int location = find(x);
		if(location!=INVALID_INDEX && !(table[location] == null) && table[location].equals(x)) {
			T removedElement = (T) table[location];
			free[location] = DELETED;
			table[location] = null;
			size--;
			return removedElement;
		}
		return null;
	}

	/**
	 *	returns the number of elements in the table
 	 */
	public int size() {
		return size;
	}
}
