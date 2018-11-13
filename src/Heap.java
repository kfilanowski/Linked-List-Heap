import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

/**
 * // TODO
 * @author Kevin Filanowski
 * @author Jake Guinn
 */
public class Heap {
	/** Temporary storage for the path nodes */
    private ArrayList<PathNode> tempPath;

    /**
     * Default constructor to intialize a heap tree object.
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
        ArrayList<Integer> path;

        while (in.hasNextLine()) {
            path = new ArrayList<Integer>();
            line = in.nextLine().trim(); // Grabs one line from the input file.
            // We need to extract each integer from line and place it into the arraylist path.
            String[] arr = line.split("\\s");
            for (String s : arr) {
                path.add(Integer.parseInt(s));
            }
            // Then we need to add a new PathNode to tempPath and pass in the path arraylist.
            tempPath.add(new PathNode(path));
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
     * @return A reference to the last node placed in the tree.
     */
    private PathNode buildCompleteTree(int index, int parent) {
        // Temporary variable to hold the current node to be inserted.
        PathNode current = tempPath.get(index);

        // We do not assign a parent to the root node.
        if (index != parent) {
            current.setParent(tempPath.get(parent));
            // Set current to be parent's left or right child.
            if (current.getParent().getLeft() == null) { // Left Child.
                current.getParent().setLeft(current);
            } else {                                     // Right Child.
                current.getParent().setRight(current);
                parent++;
            }
        }

        // Base case, Returns the very last node inserted in this graph.
        if (index + 1 >= tempPath.size()) {
            return current;
        }

        // Recursive call to continue building the binary tree.
        return buildCompleteTree(index+1, parent);
    }
    
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
        System.out.println("Running Heap..");
        Heap heap = new Heap();
        try {
            heap.readPaths("input.txt");
            PathNode node = heap.buildCompleteTree(0, 0);
            System.out.println("Node retrieved is: " + (node.getPath().size()-1));

            // TESTING TREE. Works!
            PathNode node_parent = node.getParent();
            PathNode node_grandparent = node_parent.getParent();
            PathNode node_root = node_grandparent.getParent();
            PathNode root_left = node_root.getLeft();
            PathNode root_left_2 = root_left.getLeft();
            PathNode root_left_3 = root_left_2.getLeft();
            PathNode root_left_2_right = root_left_2.getRight();
            PathNode root_right = node_root.getRight();
            PathNode root_right_2 = root_right.getRight();
            PathNode root_right_2_parent = root_right_2.getParent();
            PathNode root_right_2_parent_left = root_right_2_parent.getLeft();
            System.out.println(node_parent);
            System.out.println(node_grandparent);
            System.out.println(node_root);
            System.out.println(root_left);
            System.out.println(root_left_2);
            System.out.println(root_left_3);
            System.out.println(root_left_2_right);
            System.out.println(root_right);
            System.out.println(root_right_2);
            System.out.println(root_right_2_parent);
            System.out.println(root_right_2_parent_left);
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
