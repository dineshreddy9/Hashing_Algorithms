/*
 * @author Dinesh, Kautil
 * RobinHood Hashing Algorithm: An Open Addressing scheme in which a new element being inserted with a larger probe
 *  count can displace an existing element in the table with a smaller probe count.
 * Ver 1.0: 10/15/2018 
 */
package dxt161330;

public class RobinHood<T> extends HashingAlgorithm<T>{
	
	// to store the elements
	Object table[];
	// array to represent whether a particular index is free(0), an element exits(1), deleted(2) 
	private int free[];
	// capacity is the length of the table. size is the number of elements in the table
	private int capacity, size;
	// loadFactor = size/capacity
	private double loadFactor;
	
	// Default constructor which initializes the table with default capacity as 32 
	RobinHood() {
		this(32);
	}
	
	// Parameterized constructor which takes initial capacity as argument
	RobinHood(int capacity){
		this.capacity = capacity;
		table = new Object[this.capacity];
		free = new int[this.capacity];
		size = 0;
	}

	// adds an element to the table
	// returns true if the element is added successfully otherwise returns false
	public boolean add(T x) {

		if(contains(x)) {
			return false;
		}

		int location = indexFor(hash(x.hashCode()),capacity);
		int displacement = 0;

		while(size != capacity) {
			if(free[location]==0 || free[location]==2) {
				table[location] = x;
				size++;
				free[location] = 1;
				loadFactor = (double) size/capacity;
				// considering threshold = 0.5
				if(loadFactor > 0.5) {
					rehash();
				}
				return true;
			} else if(displacement((T) table[location], location) >= displacement(x, location)) {
				displacement++;
				location = (location + 1) % capacity;
			} else {
				T temp = x;
				x = (T) table[location];
				table[location] = temp;
				location = (location + 1) % capacity;
				displacement = displacement(x, location);
			}
		}
		return false;
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
	
	// Calculate displacement of x from its ideal location of h(x).
	public int displacement(T x, int loc) {
		int i0 = indexFor(hash(x.hashCode()), capacity);
		if(loc >= i0) {
			return (loc - i0);
		} else {
			return (table.length + loc - i0);
		}
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

		int ik = indexFor(hash(x.hashCode()), capacity);
		while(true) {
			//System.out.println(ik);
			if(free[ik] == 0 || (table[ik]!=null && table[ik].equals(x))) {
				return ik;
			} else if(free[ik] == 1) {
				break;
			} else if(free[ik] == 2) {
				ik = (ik + 1) % capacity;;
			}
		}

		int deletedSpot = ik;
		
		while(true) {
			
			ik = (ik + 1) % capacity;
			if(!(table[ik] == null) && table[ik].equals(x)) {
				return ik;
			}
			if(free[ik] == 0) {
				return deletedSpot;
			}
		}

	}
	
	// Removes an element. Returns the element removed if successful otherwise returns null
	public T remove(T x) {
		int location = find(x);
		if(!(table[location] == null) && table[location].equals(x)) {
			T removedElement = (T) table[location];
			free[location] = 2;
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
