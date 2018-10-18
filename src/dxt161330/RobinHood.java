package dxt161330;

public class RobinHood<T> implements HashingAlgorithm<T>{

	Object table[];
	// array to represent whether a particular index is free(0), an element exits(1), deleted(2) 
	private int free[];
	private int capacity, size;

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

		int location = hashCode(x);
		int displacement = 0;

		while(size != capacity) {
			if(free[location]==0 || free[location]==2) {
				table[location] = x;
				size++;
				free[location] = 1;
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

	public int displacement(T x, int loc) {
		int i0 = hashCode(x);
		if(loc >= i0) {
			return (loc - i0);
		} else {
			return (table.length + loc - i0);
		}
	}

	// temporary code for testing
	public int hashCode(T x) {
		return x.hashCode() % 19;
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

		int ik = 0;
		while(true) {
			ik = hashCode(x);
			if(free[ik] == 0 || table[ik].equals(x)) {
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
		if(table[location].equals(x)) {
			T removedElement = (T) table[location];
			free[location] = 2;
			table[location] = null;
			return removedElement;
		}
		return null;
	}

	static<T> int distinctElements(T[ ] arr) { 
		return 0;
	}
}
