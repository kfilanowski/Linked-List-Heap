import java.io.FileNotFoundException;

/**
 * Creates a Heap object and calls the go function which will perform a 
 * min heap on the file that is passed in.
 * @author Kevin Filanowski
 * @author Jake Ginn
 * @version 12/4/2018
 */
public class Driver {
    /** Heap graph. */
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
    private void run(String filename) throws FileNotFoundException {
        heap.go(filename);
    }

    /**
     * The main method of the program to run.
     * @param args - The filename to read.
     */
    public static void main(String[] args) {
        Driver driver = new Driver();
        try {
            driver.run(args[0]);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
