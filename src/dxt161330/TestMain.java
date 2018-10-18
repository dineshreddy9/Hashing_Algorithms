package dxt161330;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

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
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter size of the table ");
        int size = sc.nextInt();
        // give size=16 for testing
        RobinHood<Integer> rb = new RobinHood<>(size);

        rb.add(12497);
        rb.add(28754);
        rb.add(34678);
        rb.add(45500);
        rb.add(56699);
        rb.add(67891);
        rb.add(70011);
        rb.add(81209);
        rb.add(99194);
        rb.add(18608);

        System.out.println("hashtable: " + Arrays.toString(rb.table));
        TestMain tester = new TestMain();
        tester.testAdd();
        System.out.println("All Tests passed");
    }

    private void testAdd() {
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
}
