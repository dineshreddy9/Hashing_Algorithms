package dxt161330;

import java.util.Arrays;
import java.util.Scanner;

public class RobinHood<T> {

	Object table[];
	// array to represent whether a particular index is free(0), an element exits(1), deleted(2) 
	int free[];
	int size, numofElements ;

	RobinHood(int size){
		this.size = size;
		table = new Object[this.size];
		free = new int[this.size];
		numofElements = 0;
	}

	public boolean add(T x) {

		if(contains(x)) {
			return false;
		}

		int location = hashCode(x);
		int displacement = 0;

		while(numofElements != size) {
			if(free[location]==0 || free[location]==2) {
				table[location] = x;
				numofElements++;
				free[location] = 1;
				return true;
			} else if(displacement((T) table[location], location) >= displacement(x, location)) {
				displacement++;
				location = (location + 1) % size;
			} else {
				T temp = x;
				x = (T) table[location];
				table[location] = temp;
				location = (location + 1) % size;
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
		return (int) x % 19;
	}

	// Checks whether the table contains x or not
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
				ik = (ik + 1) % size;;
			}
		}

		int deletedSpot = ik;
		
		while(true) {
			
			ik = (ik + 1) % size;
			if(!(table[ik] == null) && table[ik].equals(x)) {
				return ik;
			}
			if(free[ik] == 0) {
				return deletedSpot;
			}
		}

	}

	public T remove(T x) {
		int location = find(x);
		if(table[location].equals(x)) {
			free[location] = 2;
			return (T) table[location];
		}
		return null;
	}

	static<T> int distinctElements(T[ ] arr) { 
		return 0;
	}
}
