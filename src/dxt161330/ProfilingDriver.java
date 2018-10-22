package dxt161330;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class ProfilingDriver {
    public static void main(String[] args) throws IOException {
        HashSet<Integer> javaSet = new HashSet<>();
        DoubleHashing<Integer> doubleHashedSet = new DoubleHashing<>();
        RobinHood<Integer> robinHoodSet = new RobinHood<>();
        File file = new File("dxt161330/benchmarkInputs/input1.txt");
        System.out.println("ANALYSIS OF DIFFERENT HASHING ALGORITHMS");
        Timer benchMark = timeOperations(new Scanner(file),javaSet);
        System.out.println(benchMark);

        Timer doubleHashTime = timeOperations(new Scanner(file),doubleHashedSet);
        System.out.println(doubleHashTime);

        Timer robinHoodTime = timeOperations(new Scanner(file),robinHoodSet);
        System.out.println(robinHoodTime);

    }
    private static Timer timeOperations(Scanner sc,Object rawSet){
        Timer javaTimer = new Timer();
        HashingAlgorithm<Integer> customHashAlgo = null;
        HashSet<Integer> javaHash = null;
        if(rawSet instanceof HashingAlgorithm){
            customHashAlgo = (HashingAlgorithm<Integer>) rawSet;
        }else {
            javaHash = (HashSet<Integer>) rawSet;
        }
        String operation;
        int operand;
        javaTimer.start();
        while (!((operation = sc.next()).equals("End"))) {
            operand = sc.nextInt();
            switch (operation) {
                case "Add": {
                    if(javaHash!=null)
                        javaHash.add(operand);
                    else
                        customHashAlgo.add(operand);
                    break;
                }
                case "Remove": {
                    if(javaHash!=null)
                        javaHash.remove(operand);
                    else
                        customHashAlgo.remove(operand);
                    break;
                }
                case "Contains":{
                    if(javaHash!=null)
                        javaHash.contains(operand);
                    else
                        customHashAlgo.contains(operand);
                    break;
                }
            }
        }
        if(javaHash!=null)
            System.out.println(javaHash.getClass() + " unique values estimate " + javaHash.size());
        else
            System.out.println(customHashAlgo.getClass() + " unique values estimate " + customHashAlgo.size());
        return javaTimer.end();
    }
}
