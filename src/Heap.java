import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

/**
 * // TODO pls 
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
    private void readPaths(String inputFile) throws FileNotFoundException {
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
     * Recursively builds a complete binary tree. Places PathNode objects in 
     * tempPath into a complete binary tree in order of appearance in the text file. 
     *
     * @param index  Index of the current node in tempPath.
     * @param parent Parent of the current node.
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
            current.setIsLastNode(true);
            return getHighestRoot(current);
        }

        // Recursive call to continue building the binary tree.
        return buildCompleteTree(index+1, parent);
    }

    /**
     * Recursive method that sets isLevelEnd.
     * This is the left node of every level in the tree.
     * @param root Root of the subtree.
     */
    private void setLevelEnd(PathNode root) {
        root.setIsLevelEnd(true);
        // Goes until the the left subtree is empty
        if(root.getLeft() != null){
            setLevelEnd(root.getLeft());
        }
    }

    /**
     * Gets the highest root of the entire tree using any node currently in the
     * tree as input.
     * @param node - Any node in the tree.
     * @return - The root of the entire tree.
     */
    private PathNode getHighestRoot(PathNode node) {
        while (node.getParent() != null) {
            node = node.getParent();
        }
        return node;
    }

    /**
     * Recursive method that sets the "generation" link of PathNode objects from left-to-right.
     * generation is a term I use to indicate nodes on the same level (these may be siblings or
     * cousins)
     * @param root Root of the subtree.
     */
    private void setGenerationLinks(PathNode root) {
        // Set highest root's generation to null.
        if (root.getParent() == null) {
            root.setGeneration(null);
        }
        // Sibling links.
        if(root.getLeft() != null && root.getRight() != null){
            root.getLeft().setGeneration(root.getRight());

            // Gets the cousin link.
            if(root.getGeneration() != null && root.getGeneration().getLeft() != null){
                root.getRight().setGeneration(root.getGeneration().getLeft());
            }else{
                setGenerationLinks(root.getLeft());
            }
            
            // Sets the generations from left to right.
            setGenerationLinks(root.getLeft());
            setGenerationLinks(root.getRight());
        }
    }

    /**
     * Swaps the data for now 
     * @param one: the root of the tree/subtree we are at
     * @param two: the min child of the parent 
     */
    private void swap(PathNode root, PathNode child) {
        // Swapping isLastNode
        if (child.getIsLastNode()) {
            child.setIsLastNode(false);
            root.setIsLastNode(true);
        }

        PathNode tempChild;

        // Swapping links
        if (root.getLeft() == child) {
            tempChild = root.getRight();
            root.getRight().setParent(child);
            root.setLeft(child.getLeft());
            root.setRight(child.getRight());
            child.setRight(tempChild);
            child.setLeft(root);
            if (root.getParent() != null) {
                if (root.getParent().getLeft() == root) {
                    root.getParent().setLeft(child);
                } else {
                    root.getParent().setRight(child);
                }
            }
            child.setParent(root.getParent());
            root.setParent(child);
        } else {
            // Swapping IsLevelEnd
            if (root.getIsLevelEnd()) {
                child.setIsLevelEnd(true);
                root.setIsLevelEnd(false);
            }
            tempChild = root.getLeft();
            root.getLeft().setParent(child);
            root.setLeft(child.getLeft());
            root.setRight(child.getRight());
            child.setLeft(tempChild);
            child.setRight(root);
            if (root.getParent() != null) {
                if (root.getParent().getLeft() == root) {
                    root.getParent().setLeft(child);
                } else {
                    root.getParent().setRight(child);
                }
            }
            child.setParent(root.getParent());
            root.setParent(child);
        }
    }

    /**
     *Performs a min Heap with the smallest PathNode being at the top and every child
     *of every node is less than the parent of the child.
     * @param root: The node we start the Min Heap on
     */
    private PathNode heapify(PathNode root) {
        // Holds the smallest PathNode of a parent.
        PathNode min = null;
        // Boolean flag to determine if a swap occured.
        boolean swapped = false;

        System.out.println("root is: " + root); // DEBUGGING. REMOVE AT FINAL PRODUCT. //
        // We need to make sure children are not null to compare them.
        if (root.getLeft() != null) {
            if (root.getRight() != null) {
                if (root.getLeft().getSize() <= root.getRight().getSize()) {
                    min = root.getLeft();
                } else if (root.getLeft().getSize() > root.getRight().getSize()) {
                    min = root.getRight();
                }
            } else {
                min = root.getLeft();
            }
        }
        System.out.println("min is: " + min); // DEBUGGING. REMOVE AT FINAL PRODUCT. //
       
        // Heapify the rightmost siblings first.
        if (root.getGeneration() != null && root.getGeneration().getLeft() != null) {
            System.out.println("\n next generation"); // DEBUGGING. REMOVE AT FINAL PRODUCT. //
            heapify(root.getGeneration());
        }
        
        // Swap PathNodes if child is smaller than parent node.
        if (min != null && root.getSize() > min.getSize()) {
            swap(root,min);
            swapped = true;
            setGenerationLinks(getHighestRoot(min));
            System.out.println("swapped root and min: " + root + "  " + min);
            //System.out.println(printTreeLevels(min));
            System.out.println(printTreeLevels(getHighestRoot(min)));
            System.out.println("root's new parent: " + root.getParent());
            System.out.println("root's left and right child: " + root.getLeft() + " , " + root.getRight());
            System.out.println("roots generation: " + root.getGeneration());

            System.out.println("Child's new parent: " + min.getParent());
            System.out.println("Childs's left and right child: " + min.getLeft() + " , " + min.getRight());
            System.out.println("Child's generation: " + min.getGeneration());
            if (root.getLeft() != null) {
                heapify(root);
            }
        }

        // base case 
        // This is here so we dont get null pointer for the top of the heap
        if (swapped && min.getParent() == null) {
            System.out.println("first base case reached");
            return min;
        }else if (!swapped && root.getParent() == null) {
            System.out.println("second base case reached");
            return root;
        }

        if (swapped && min.getIsLevelEnd()) {
            // should be min if we swapped links and set level end properly
            System.out.println("\ngoing to swapped node's parent");
            if (min.getParent() != null) {
                heapify(min.getParent());
            } else {
                System.out.println("Final root returned is: " + root);
                System.out.println("left child: " + root.getLeft());
                System.out.println("right child: " + root.getRight());
                return root;
            }
        } else if (!swapped && root.getIsLevelEnd()) {
            // should be root if we swapped links and set level end properly
            System.out.println("\ngoing to mins's parent");
            if (root.getParent() != null)
                heapify(root.getParent());
        }
        return root;
    }   

    /**
     * 
     * @param root
     */
    private PathNode startHeapify(PathNode root) {
        setGenerationLinks(root);
        setLevelEnd(root);
        // Gets the parent of the left-most PathNode in the left-most
        // subtree for starting the heapify process.
        while (root.getLeft() != null) {
            root = root.getLeft();
        }
        // Go to the parent. This is our starting node for heapify.
        root = root.getParent();
        return getHighestRoot(heapify(root));
    }
    
    /**
     * Prints the path lengths from left-to-right at each level in the tree in the form specified
     * by the instructions.
     * @param root Root of the whole tree to begin printing from.
     * @return A formatted string showing the the path lengths from left to
     * right of each level in the tree.
     */
    private String printTreeLevels(PathNode root) {
        // String builder to append all content to.
        StringBuilder levels = new StringBuilder();
        // PathNode to keep track of the generations.
        PathNode generation;
        // To keep track of levels.
        int i = 0;
        // Root node.
        levels.append(String.format("Root:%15s\n", root));
        root = root.getLeft();
        generation = root;
        // While there is a level:
        while (root != null) {
            levels.append(String.format("Level %d: ", ++i));
            // While there are generations:
            while (generation != null) {
                levels.append(" " + generation);
                generation = generation.getGeneration();
                if (generation != null) {
                    levels.append("--> ");
                }
            }
            levels.append("\n");
            root = root.getLeft();
            generation = root;
        }
        return levels.toString();
    }

    /**
     * Just a way to test Heap. DELETE THIS WHEN FINISHED.
     */
    public static void main(String[] args) {
        System.out.println("Running Heap... :D");
        Heap heap = new Heap();
        try {
            heap.readPaths("input.txt");
            PathNode root_node = heap.buildCompleteTree(0, 0);
            heap.setGenerationLinks(root_node);
            heap.setLevelEnd(root_node);

            // TESTING PRINTTREELEVELS
            System.out.println("\n\nTesting printTreeLevels \n*********************************************");
            System.out.println(heap.printTreeLevels(root_node));
            System.out.println("Ending printTreeLevels \n*********************************************");
            
            // TESTING HEAPIFY
            root_node = heap.startHeapify(root_node);
            heap.setGenerationLinks(root_node);
            // TESTING PRINTTREELEVELS
            System.out.println("\n\nTesting printTreeLevels HEAPIFIED \n*********************************************");
            System.out.println(heap.printTreeLevels(root_node));
            System.out.println("Ending printTreeLevels HEAPIFIED\n*********************************************");
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
