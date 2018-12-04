/**
 * @author Kevin Filanowski
 * @author Jake Guinn
 */
public class Driver {
    Heap heap;

    /**
     * Default constructor for Driver. Initializes a heap.
     */
    public Driver() {
        heap = new Heap();
    }

    /**
     * Runs the go method in Heap, which performs various operations
     * such as reading in input, building a tree, heapifing it, and
     * displaying the results before and after the heapify.
     */
    private void run() {
        heap.go();
    }

    /**
     * The main method of the program to run.
     * @param args - No arguments needed.
     */
    public static void main(String[] args) {
        Driver driver = new Driver();
        driver.run();
    }
}
