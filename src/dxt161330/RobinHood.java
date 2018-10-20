package dxt161330;

public class RobinHood<T> extends HashingAlgorithm<T>{

	Object table[];
	// array to represent whether a particular index is free(0), an element exits(1), deleted(2) 
	private int free[];
	private int capacity, size;
	private double loadFactor;
	
	RobinHood() {
		this(32);
	}
	
	RobinHood(int capacity){
		this.capacity = capacity;
		table = new Object[this.capacity];
		free = new int[this.capacity];
		size = 0;
	}

	@Override
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
	
	public int displacement(T x, int loc) {
		int i0 = indexFor(hash(x.hashCode()), capacity);
		if(loc >= i0) {
			return (loc - i0);
		} else {
			return (table.length + loc - i0);
		}
	}

	// Checks whether the table contains x or not
	@Override
	public boolean contains(T x) {

		int location = find(x);
		if(!(table[location] == null) && table[location].equals(x)) {
			return true;
		}
		return false;
	}

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

	@Override
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
	
	public int size() {
		return size;
	}
}
