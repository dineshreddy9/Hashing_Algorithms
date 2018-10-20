/*
 * @author Dinesh, Kautil
 * Double Hashing Algorithm: An Open Addressing scheme in which a second hash function ( h2 )
 *  is used to determine step length: ik = (h(x) + k * h2(x)) % n.
 * Ver 1.0: 10/17/2018 
 */
package dxt161330;

public class DoubleHashing<T> extends HashingAlgorithm<T> {

	// to store the elements
	Object table[];
	// array to represent whether a particular index is free(0), an element exits(1), deleted(2)
	private int free[];
	// capacity is the length of the table. size is the number of elements in the table
	private int capacity, size;
	// loadFactor = size/capacity
	private double loadFactor;

	// Default constructor which initializes the table with default capacity as 32 
	DoubleHashing(){
		this(32);
	}

	// Parameterized constructor which takes initial capacity as argument
	DoubleHashing(int capacity){
		this.capacity = capacity;
		table = new Object[this.capacity];
		free = new int[this.capacity];
		size = 0;
	}

	// adds an element to the table
	// returns true if the element is added successfully otherwise returns false
	public boolean add(T x) {

		int location = find(x);
		if(location == -1 || contains(x)) {
			return false;
		}

		else {
			table[location] = x;
			free[location] = OCCUPIED;
			size++;
			loadFactor = (double) size/capacity;
			// considering threshold = 0.5
			if(loadFactor > 0.5) {
				rehash();
			}
			return true;
		}
	}

	// called when the loadfactor exceeds a threshold value
	// capacity of the table is doubled and the elements are rehashed
	public void rehash() {
		Object[] table2 = table;
		capacity = 2*capacity;
		table = new Object[capacity];
		free = new int[capacity];
		size=0;
		for(int i = 0; i < table2.length; i++) {
			if(table2[i]!=null) add((T) table2[i]);
		}
	}

	public int secondHashFunction(T x) {
		return Math.abs(indexFor(hash(x.hashCode()),2*capacity));
	}

	// Checks whether the table contains x or not
	public boolean contains(T x) {

		int location = find(x);
		if(!(table[location] == null) && table[location].equals(x)) {
			return true;
		}
		return false;
	}

	// search for x and return index of x. If x is not found, return index where x can be added.
	public int find(T x) {
		int k = 0, ik = indexFor(hash(x.hashCode()),capacity);
		while(true) {
			//ik = hashCode(x);
			if(free[ik] == FREE || (table[ik]!=null && table[ik].equals(x))) {
				return ik;
			} else if(free[ik] == DELETED) {
				break;
			} else if(free[ik] == OCCUPIED) {
				k++;
				ik = (indexFor(hash(x.hashCode()),capacity) + k * secondHashFunction(x)) % capacity;
			} else if(size == capacity) {
				return -1;
			}
		}

		int deletedSpot = ik;

		while(true) {
			k++;
			ik = (hash(x.hashCode()) + k * secondHashFunction(x)) % capacity;
			if(!(table[ik] == null) && table[ik].equals(x)) {
				return ik;
			}
			if(free[ik] == FREE) {
				return deletedSpot;
			}
		}

	}

	// Removes an element. Returns the element removed if successful otherwise returns null
	public T remove(T x) {
		int location = find(x);
		if(!(table[location] == null) && table[location].equals(x)) {
			T removedElement = (T) table[location];
			free[location] = DELETED;
			table[location] = null;
			size--;
			return removedElement;
		}
		return null;
	}

	// returns the number of elements in the table
	public int size() {
		return size;
	}
}
