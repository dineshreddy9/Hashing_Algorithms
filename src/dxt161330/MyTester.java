package dxt161330;

import java.util.HashSet;
import java.util.Random;

public class MyTester {

	public static void main(String[] args) {

		RobinHood<Integer> rhSet = new RobinHood<>();
		DoubleHashing<Integer> dh = new DoubleHashing<>();
		HashSet<Integer> hs = new HashSet<>();

		int size = 10000000;
		int rhSize, dhSize, hsSize;
		Integer[] arr = new Integer[size];

		Random r = new Random(Integer.MAX_VALUE);
		for (int i = 0; i < arr.length; i++) {
			arr[i] = r.nextInt();
		}
		
		// adding and finding distinct elements
		long millis = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i++) {
			rhSet.add(arr[i]);
		}
		long rhAddTime = System.currentTimeMillis() - millis;
		rhSize = rhSet.size();
		long rhTime = System.currentTimeMillis() - millis;
		
		millis = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i++) {
			dh.add(arr[i]);
		}
		long dhAddTime = System.currentTimeMillis() - millis;
		dhSize = dh.size();
		long dhTime = System.currentTimeMillis() - millis;
		
		millis = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i++) {
			hs.add(arr[i]);
		}
		long hsAddTime = System.currentTimeMillis() - millis;
		hsSize = dh.size();
		long hsTime = System.currentTimeMillis() - millis;
		
		// contains
		millis = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i++) {
			rhSet.contains(arr[i]);
		}
		long rhContainsTime = System.currentTimeMillis() - millis;
		
		millis = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i++) {
			dh.contains(arr[i]);
		}
		long dhContainsTime = System.currentTimeMillis() - millis;
		
		millis = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i++) {
			hs.contains(arr[i]);
		}
		long hsContainsTime = System.currentTimeMillis() - millis;
		
		// remove
		millis = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i++) {
			rhSet.remove(arr[i]);
		}
		long rhRemoveTime = System.currentTimeMillis() - millis;
		
		millis = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i++) {
			dh.remove(arr[i]);
		}
		long dhRemoveTime = System.currentTimeMillis() - millis;
		
		millis = System.currentTimeMillis();
		for (int i = 0; i < arr.length; i++) {
			hs.remove(arr[i]);
		}
		long hsRemoveTime = System.currentTimeMillis() - millis;

		System.out.println("Add: " + " (RobinHood: " + (float)rhAddTime/1000 + "s): " 
		+ " (DoubleHashing: " + (float)dhAddTime/1000 + "s): "
		+ "(HashSet: " + (float)hsAddTime/1000 + "s): " );
		
		System.out.println("DistinctElements: " + " (RobinHood: " + (float)rhTime/1000 + "s): " + rhSize
				+ " (DoubleHashing: " + (float)dhTime/1000 + "s): " + dhSize
				+ "(Hashset: " + (float)hsTime/1000 + "s): " + hsSize);
		
		System.out.println("Contains: " + " (RobinHood: " + (float)rhContainsTime/1000 + "s): " 
				+ " (DoubleHashing: " + (float)dhContainsTime/1000 + "s): "
				+ "(HashSet: " + (float)hsContainsTime/1000 + "s): " );
		
		System.out.println("Remove: " + " (RobinHood: " + (float)rhRemoveTime/1000 + "s): " 
				+ " (DoubleHashing: " + (float)dhRemoveTime/1000 + "s): "
				+ "(HashSet: " + (float)hsRemoveTime/1000 + "s): " );
		
	}
}
