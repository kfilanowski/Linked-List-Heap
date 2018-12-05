import java.io.FileNotFoundException;

/**
 * The main driver of the program.
 * Creates a Heap object and calls the go function which will perform a 
 * min heap on the file that is passed in.
 * @author Kevin Filanowski
 * @author Jake Ginn
 * @version December 2018
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

    private static final void printUsageAndExit() {
        System.out.println("Usage: java Driver <filename>");
        System.exit(0);
    }

    /**
     * Runs the go method in Heap, which performs various operations
     * such as reading in input, building a tree, heapifing it, and
     * displaying the results before and after the heapify.
     * @param filename - The name of the file to read data from.
     */
    private void run(String filename) throws FileNotFoundException,
                                             NumberFormatException {
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
            printUsageAndExit();
        } catch (NumberFormatException ex) {
            System.out.println(ex.getMessage());
            printUsageAndExit();
        }
    }
}
