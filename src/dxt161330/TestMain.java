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
        tester.testAdd();
        tester.testRemove();
        tester.testContains();
        tester.testOnSingleHash();
        tester.testResize();
        tester.testNegativeHash();
        System.out.println("All Tests passed");
    }

    private void testNegativeHash() {
        RobinHood<Integer> rbNegative = new RobinHood<>();
        for(int i=0;i>-5;--i){
            assert rbNegative.add(i);
        }
        assert rbNegative.size() == 5;
        for(int i=0;i>-5;--i){
            assert rbNegative.contains(i);
        }
    }

    private void testAdd() {
        for(int i=1;i<16;++i){
            assert robinHood.add(i);
        }
        assert robinHood.size() == 15;
    }

    private void testRemove() {
        assert robinHood.remove(5)==5;
        assert robinHood.remove(10)==10;
        assert robinHood.remove(13)==13;
    }

    private void testContains() {
        for(int i=1;i<16;++i){
            if(i==5||i==10||i==13){
                assert !robinHood.contains(i);
            }else {
                assert robinHood.contains(i);
            }
        }
    }

    private void testOnSingleHash() {
        RobinHood<DualHashClass> rb = new RobinHood<>();
        for (int i = 0; i < 16; i++) {
            assert  rb.add(new DualHashClass(i));
        }
        for (int i = 0; i < 16; i++) {
            assert rb.contains(new DualHashClass(i));
        }
        DualHashClass obj13 = new DualHashClass(13);
        DualHashClass obj5 = new DualHashClass(5);
        assert rb.remove(obj13).equals(obj13);
        assert rb.remove(obj5).equals(obj5);
        for(int i=0;i<16;++i){
            if(i==5||i==13){
                assert !rb.contains(new DualHashClass(i));
            }else {
                assert rb.contains(new DualHashClass(i));
            }
        }
        rb.add(new DualHashClass(17));
    }

    private void testResize() {
        robinHood.add(5);
        robinHood.add(10);
        robinHood.add(13);
        robinHood.add(16);
        robinHood.add(17);
        assert robinHood.size() == 17;
        assert robinHood.capacity == 64;
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
