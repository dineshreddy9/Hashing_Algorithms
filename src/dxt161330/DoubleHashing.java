package dxt161330;

import java.util.Arrays;
import java.util.Scanner;

public class DoubleHashing<T> implements HashingAlgorithm<T> {
	
	Object table[];
	// array to represent whether a particular index is free(0), an element exits(1), deleted(2) 
	private int free[];
	private int capacity, size;
	
	DoubleHashing(int capacity){
		this.capacity = capacity;
		table = new Object[this.capacity];
		free = new int[this.capacity];
		size = 0;
	}
	
	public boolean add(T x) {

		int location = find(x);
		System.out.println(location);
		if(location == -1 || contains(x)) {
			return false;
		}
		
		else {
			table[location] = x;
			free[location] = 1;
			size++;
			return true;
		}
	}

	// temporary code for testing
	public int hashCode(T x) {
		return x.hashCode() % 19;
	}
	
	public int secondHashFunction(T x) {
		return x.hashCode();
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

		int k = 0, ik = hashCode(x);
		while(true) {
			//ik = hashCode(x);
			if(free[ik] == 0 || table[ik].equals(x)) {
				return ik;
			} else if(free[ik] == 2) {
				break;
			} else if(free[ik] == 1) {
				k++;
				ik = (hashCode(x) + k * secondHashFunction(x)) % capacity;
			} else if(size == capacity) {
				return -1;
			}
		}

		int deletedSpot = ik;
		
		while(true) {
			k++;
			ik = (hashCode(x) + k * secondHashFunction(x)) % capacity;
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
			T removedElement = (T) table[location];
			free[location] = 2;
			table[location] = null;
			size--;
			return removedElement;
		}
		return null;
	}

	static<T> int distinctElements(T[ ] arr) { 
		return 0;
	}
}
