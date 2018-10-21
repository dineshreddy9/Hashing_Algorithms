package dxt161330;

import java.util.Arrays;

/**
 * This is a driver class that essentially runs tests (like a JUnit test class).
 * Motivation to write such a class is to eliminate adding JUnit or TestNG dependency.
 *
 * To add a new test case,
 * 1.Create a test function.
 * 2.Call the test function from main.
 * @author Kautil & Dinesh
 */
public class TestMain {
    private RobinHood<Integer> robinHood;
    private DoubleHashing<Integer> doubleHashing;
    private HashingAlgorithm<Integer> currentAlgo;
    public TestMain(RobinHood<Integer> robinHood, DoubleHashing<Integer> doubleHashing){
        this.robinHood = robinHood;
        this.doubleHashing = doubleHashing;
    }
    /**
     * the Main here runs the individual test functions.
     * @param args this argument is added just to maintain consistency with java spec.
     */
    public static void main(String[] args) {
        try {
            assert false;
            System.out.println("Please use the -ea jvm-option. Ex: java -ea dxt161330.TestMain");
            System.exit(0);
        }catch (AssertionError error){
            System.out.println("-ea option enabled good to go");
        }

        TestMain tester = new TestMain(new RobinHood<>(),new DoubleHashing<>());
        for (HashingAlgorithm<Integer> algo: Arrays.asList(tester.robinHood,tester.doubleHashing)) {
            tester.currentAlgo = algo;
            tester.testAdd();
            tester.testRemove();
            tester.testContains();
            tester.testResize();
            System.out.println(algo.getClass().getName() + " passed");
        }
        tester.testNegativeHash();
        tester.testOnHashConflict();
        System.out.println("All Tests passed");
    }

    private void testNegativeHash() {
        RobinHood<Integer> rbNegative = new RobinHood<>();
        HashingAlgorithm<Integer> doubleHash = new DoubleHashing<>();
        for (HashingAlgorithm<Integer> algo:Arrays.asList(rbNegative,doubleHash)) {
            for (int i = 0; i > -5; --i) {
                assert algo.add(i);
            }
            assert algo.size() == 5;
            for (int i = 0; i > -5; --i) {
                assert algo.contains(i);
            }
            System.out.println(algo.getClass().getName() + " Negative Hash test passed");
        }
    }

    private void testAdd() {
        for(int i=1;i<16;++i){
            assert currentAlgo.add(i);
        }
        assert !currentAlgo.add(1); //duplicate add
        assert !currentAlgo.add(7); //duplicate add
        assert currentAlgo.size() == 15;
    }

    private void testRemove() {
        assert currentAlgo.remove(5)==5;
        assert currentAlgo.remove(10)==10;
        assert currentAlgo.remove(13)==13;
    }

    private void testContains() {
        for(int i=1;i<16;++i){
            if(i==5||i==10||i==13){
                assert !currentAlgo.contains(i);
            }else {
                assert currentAlgo.contains(i);
            }
        }
    }

    private void testOnHashConflict() {
        HashingAlgorithm<DualHashClass> rb = new RobinHood<>();
        HashingAlgorithm<DualHashClass> doubleHash = new DoubleHashing<>();
        for (HashingAlgorithm<DualHashClass> algo:Arrays.asList(rb,doubleHash)) {
            for (int i = 0; i < 16; i++) {
                assert algo.add(new DualHashClass(i));
            }
            for (int i = 0; i < 16; i++) {
                assert algo.contains(new DualHashClass(i));
            }
            DualHashClass obj13 = new DualHashClass(13);
            DualHashClass obj5 = new DualHashClass(5);
            assert algo.remove(obj13).equals(obj13);
            assert algo.remove(obj5).equals(obj5);
            for (int i = 0; i < 16; ++i) {
                if (i == 5 || i == 13) {
                    assert !algo.contains(new DualHashClass(i));
                } else {
                    assert algo.contains(new DualHashClass(i));
                }
            }
            algo.add(new DualHashClass(17));
            assert !algo.add(new DualHashClass(3)); //duplicate add
            assert !algo.add(new DualHashClass(15)); //duplicate add
            System.out.println(algo.getClass().getName() + " HashConflict test passed");
        }
    }

    private void testResize() {
        currentAlgo.add(5);
        currentAlgo.add(10);
        currentAlgo.add(13);
        currentAlgo.add(16);
        currentAlgo.add(17);
        assert currentAlgo.size() == 17;
        assert currentAlgo.capacity == 64;
        testRemove();
        testContains();
    }

    /**
     * Runs the given function inside a try-catch block to capture and assert that given exception is raised
     * @param function function that needs to be run inside the try block
     * @param expectedEx the exception class type the function should throw
     */
    private void checkException(Runnable function, Class<?> expectedEx){
        try {
            function.run();
            assert false;
        }catch (Exception e){
            assert e.getClass() == expectedEx;
        }
    }

    /**
     * A test class that has only two hashCodes for all objects to test how different algorithm can handle these cases
     */
    class DualHashClass {
        private int data;
        public DualHashClass(int intVal){
            this.data = intVal;
        }

        @Override
        public boolean equals(Object other){
            if (!(other instanceof DualHashClass)){
                return false;
            }
            return this.data==((DualHashClass) other).data;
        }

        @Override
        public String toString(){
            return Integer.toString(data);
        }

        @Override
        public int hashCode(){
            return data%2; //odd return 0 even return 1
        }
    }
}
