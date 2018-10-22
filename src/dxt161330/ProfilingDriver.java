package dxt161330;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;

/**
 * This class is used to compare the performance of the Java's set class vs Custom open addressing schemes implemented
 */
public class ProfilingDriver {
    public static void main(String[] args) throws IOException {
        HashSet<Integer> javaSet = new HashSet<>();
        DoubleHashing<Integer> doubleHashedSet = new DoubleHashing<>();
        RobinHood<Integer> robinHoodSet = new RobinHood<>();
        int []randomArr = getRandomArray(2000000);
        int uniqueElements;

        System.out.println("ANALYSIS OF DIFFERENT HASHING ALGORITHMS");
        Timer benchMark = new Timer();
        benchMark.start();
        uniqueElements = distinctElements(randomArr,javaSet);
        removeAll(randomArr,javaSet);
        benchMark.end();
        System.out.println("Java uniqueElements = " + uniqueElements);
        System.out.println(benchMark);

        Timer doubleHashTime = new Timer();
        doubleHashTime.start();
        uniqueElements = distinctElements(randomArr,doubleHashedSet);
        removeAll(randomArr,javaSet);
        doubleHashTime.end();
        System.out.println("DoubleHashing uniqueElements = " + uniqueElements);
        System.out.println(doubleHashTime);

        Timer robinHoodTime = new Timer();
        robinHoodTime.start();
        uniqueElements = distinctElements(randomArr,robinHoodSet);
        removeAll(randomArr,javaSet);
        robinHoodTime.end();
        System.out.println("RobingHood uniqueElements = " + uniqueElements);
        System.out.println(robinHoodTime);

    }
    private static int distinctElements(int[] arr, Object rawSet){
        HashingAlgorithm<Integer> customHashAlgo = null;
        HashSet<Integer> javaHash = null;
        if(rawSet instanceof HashingAlgorithm){
            customHashAlgo = (HashingAlgorithm<Integer>) rawSet;
        }else {
            javaHash = (HashSet<Integer>) rawSet;
        }
        for(int element: arr) {
            if(javaHash!=null) {
                if(!javaHash.contains(element)) //using contains just to realistic usage even though duplicates are removed
                    javaHash.add(element);
            }
            else {
                if(!customHashAlgo.contains(element))
                    customHashAlgo.add(element);
            }
        }
        if(javaHash!=null)
            return javaHash.size();
        return customHashAlgo.size();
    }
    private static void removeAll(int[] arr, Object rawSet) {
        HashingAlgorithm<Integer> customHashAlgo = null;
        HashSet<Integer> javaHash = null;
        if(rawSet instanceof HashingAlgorithm){
            customHashAlgo = (HashingAlgorithm<Integer>) rawSet;
        }else {
            javaHash = (HashSet<Integer>) rawSet;
        }
        for(int element: arr) {
            if(javaHash!=null) {
                if(javaHash.contains(element))
                    javaHash.remove(element);
            }
            else {
                if(customHashAlgo.contains(element))
                    customHashAlgo.remove(element);
            }
        }
    }
    private static int[] getRandomArray(int length){
        Random r = new Random();
        int []arr = new int[length];
        for (int i=0;i<length;++i){
            arr[i] = r.nextInt();
        }
        return arr;
    }
}
