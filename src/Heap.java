import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

public class Heap {
	/** Temporary storage for the paths starting at tempPath[1]. */
    private ArrayList<PathNode> tempPath;

    /**
     * 
     */
    public Heap() {
        tempPath = new ArrayList<PathNode>();
    }

    /**
     * Reads inputFile given at the command line and places the contents of each line into the
     * path field found in each PathNode object. The order is the same as found in the text file.
     * Adds the PathNode object to tempPath starting at tempPath[1].
     * @param inputFile Name of the input file to be read.
     * @throws FileNotFoundException if the input file cannot be found.
     */
    public void readPaths(String inputFile) throws FileNotFoundException {
        // The input file.
        File readFile = new File(inputFile);
        // Scans the input file.
        Scanner in = new Scanner(readFile);
        // Used to hold a line in the input file.
        String line;
        // Used to store the path and pass into a PathNode.
        ArrayList<Integer> path = new ArrayList<Integer>();

        while (in.hasNextLine()) {
            line = in.nextLine().trim(); // Grabs one line from the input file.
            // We need to extract each integer from line and place it into the arraylist path.
            String[] arr = line.split("\\s");
            for (String s : arr) {
                path.add(Integer.parseInt(s));
            }
            // Then we need to add a new PathNode to tempPath and pass in the path arraylist.
            tempPath.add(new PathNode(path));
            path.clear();
        }
        in.close();
    }
    /**
     * Recursively builds a complete binary tree. Places PathNode objects in tempPath into a
     * complete binary tree in order of appearance in the text file. The left child of a parent
     * located at tempPath[index] is found at tempPath[2 * index] and the right child is found at
     * tempPath[(2 * index) + 1].
     *
     * @param index     Index of the current node in tempPath.
     * @param parent    Parent of the current node.
     * @return A reference to the node just placed in the tree.
     */
    // PathNode buildCompleteTree(int index, int parent) {
    	
    // }
    
    /**
     * Recursive method that sets isLevelEnd.
     * @param root Root of the subtree.
     */
    // setLevelEnd(PathNode root) {
    	
    // }
    
    /**
     * Recursive method that sets the "generation" link of PathNode objects from left-to-right.
     * generation is a term I use to indicate nodes on the same level (these may be siblings or
     * cousins)
     * @param root Root of the subtree.
     */
    // setGenerationLinks(PathNode root) {
    	
    // }
    
    /**
     * Prints the path lengths from left-to-right at each level in the tree in the form specified
     * by the instructions.
     * @param root Root of the whole tree to begin printing from.
     */
    // printTreeLevels(PathNode root) {
    	
    // }

    /**
     * Just a way to test Heap. DELETE THIS WHEN FINISHED.
     */
    public static void main(String[] args) {
        System.out.println("Testing Heap..");
        Heap heap = new Heap();
        try {
        heap.readPaths("input.txt");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
