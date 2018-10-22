package dxt161330;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

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
        benchMark.end();
        System.out.println("Java uniqueElements = " + uniqueElements);
        System.out.println(benchMark);

        Timer doubleHashTime = new Timer();
        doubleHashTime.start();
        uniqueElements = distinctElements(randomArr,doubleHashedSet);
        doubleHashTime.end();
        System.out.println("DoubleHashing uniqueElements = " + uniqueElements);
        System.out.println(doubleHashTime);

        Timer robinHoodTime = new Timer();
        robinHoodTime.start();
        uniqueElements = distinctElements(randomArr,robinHoodSet);
        robinHoodTime.end();
        System.out.println("RobingHood uniqueElements = " + uniqueElements);
        System.out.println(robinHoodTime);

    }
    private static int distinctElements(int[] arr, Object rawSet){
        Timer javaTimer = new Timer();
        HashingAlgorithm<Integer> customHashAlgo = null;
        HashSet<Integer> javaHash = null;
        if(rawSet instanceof HashingAlgorithm){
            customHashAlgo = (HashingAlgorithm<Integer>) rawSet;
        }else {
            javaHash = (HashSet<Integer>) rawSet;
        }
        javaTimer.start();
        for(int element: arr) {
            if(javaHash!=null)
                javaHash.add(element);
            else
                customHashAlgo.add(element);
        }
        if(javaHash!=null)
            return javaHash.size();
        return customHashAlgo.size();
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
