import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Scanner;

/**
 * The Heap class where we will build the tree, set all of the appopriate links
 * and values to each PathNode in the tree, and perform a min Heap on the 
 * complete tree.
 * @author Kevin Filanowski
 * @author Jake Ginn
 * @version December 2018
 */
public class Heap {
	/** Temporary storage for the PathNodes. */
    private ArrayList<PathNode> tempPath;

    /**
     * Default constructor to intialize the list of PathNodes.
     */
    public Heap() {
        tempPath = new ArrayList<PathNode>();
    }

    /**
     * Reads a file given at the command line and places the contents of each 
     * line into the path field found in each PathNode object. The order is 
     * the same as found in the text file.
     * @param inputFile - Name of the input file to be read.
     * @throws FileNotFoundException - If the input file cannot be read or found.
     */
    private void readPaths(String inputFile) throws FileNotFoundException,
                                                    NumberFormatException {
        // The input file.
        File readFile = new File(inputFile);
        // Scans the input file.
        Scanner in = new Scanner(readFile);
        // Used to hold a line in the input file.
        String line;
        // Used to store the path and pass into a PathNode.
        ArrayList<Integer> path;

        // Store the data in our list of PathNodes.
        while (in.hasNextLine()) {
            path = new ArrayList<Integer>();
            line = in.nextLine().trim(); // Grabs one line from the input file.
            if (line.length() > 0) {
                // We need to extract each integer from line and place it into the
                // arraylist path.
                String[] arr = line.split("\\s+");
                for (String s : arr) {
                    path.add(Integer.parseInt(s));
                }
                // Then we need to add a new PathNode to tempPath and pass in 
                // the path arraylist.
                tempPath.add(new PathNode(path));
            }
        }
        in.close();
        // Takes care of empty input.
        if (tempPath.size() == 0) {
            throw new FileNotFoundException("Input file does not contain enough data.");
        }
        
    }

    /**
     * Recursively builds a complete binary tree. Places PathNode objects in 
     * tempPath into a complete binary tree in order of appearance in the text 
     * file. 
     * @param index - Index of the current PathNode in tempPath.
     * @param parent - Parent of the current PathNode.
     * @return A reference to the last PathNode placed in the tree.
     */
    private PathNode buildCompleteTree(int index, int parent) {
        // Temporary variable to hold the current PathNode to be inserted.
        PathNode current = tempPath.get(index);

        // We do not assign a parent to the root PathNode.
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

        // Base case, Returns the very last PathNode inserted in this graph.
        if (index + 1 >= tempPath.size()) {
            current.setIsLastNode(true);
            return getHighestRoot(current);
        }

        // Recursive call to continue building the binary tree.
        return buildCompleteTree(index+1, parent);
    }

    /**
     * Recursive method that sets isLevelEnd.
     * This is the left PathNode of every level in the tree.
     * @param root - Root of the subtree.
     */
    private void setLevelEnd(PathNode root) {
        root.setIsLevelEnd(true);
        // Goes until the the left subtree is empty
        if(root.getLeft() != null){
            setLevelEnd(root.getLeft());
        }
    }

    /**
     * Gets the highest root of the entire tree using any PathNode 
     * currently in the tree as input.
     * @param node - Any PathNode in the tree.
     * @return The root of the entire tree.
     */
    private PathNode getHighestRoot(PathNode node) {
        while (node.getParent() != null) {
            node = node.getParent();
        }
        return node;
    }

    /**
     * Recursive method that sets the "generation" link of PathNode objects 
     * from left-to-right.Generation is the term used to indicate PathNodes on 
     * the same level. These PathNodes can include sibling and cousins.
     * @param root - Root of the subtree.
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
     * Performs a swap on isLastNode properties, if necessary.
     * @param root  - The root of any subtree.
     * @param child - The child of the root of the subtree.
     */
    private void swapIsLastNode(PathNode root, PathNode child) {
        // Swapping isLastNode if needed.
        if (child.getIsLastNode()) {
            child.setIsLastNode(false);
            root.setIsLastNode(true);
        }
    }

    /**
     * Swaps two PathNode's generation links.
     * @param root  - The root of any subtree.
     * @param child - The child of the root of the subtree.
     */
    private void swapGenerationLinks(PathNode root, PathNode child) {
        // Temporary PathNode to reference an extra Pathnode.
        PathNode tempChild;
        // Swap Generation links.
        tempChild = root.getGeneration();
        root.setGeneration(child.getGeneration());
        child.setGeneration(tempChild);
        if (root.getParent() != null && root.getParent().getLeft() != root) {
            root.getParent().getLeft().setGeneration(child);
            if (root.getLeft() == child) {
                root.getParent().getLeft().getRight().setGeneration(root);
            }
        }
    }

    /**
     * Swaps the root's and child's parents.
     * @param root  - The root of any subtree.
     * @param child - The child of the root of the subtree.
     */
    private void swapParent(PathNode root, PathNode child) {
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

    /**
     * Swaps two Pathnodes, and all links associated with it.
     * @param root - The root of any subtree.
     * @param child - The child of the root of the subtree.
     */
    private void swap(PathNode root, PathNode child) {
        // Swap isLastNode and generation links if necessary.
        swapIsLastNode(root, child);
        swapGenerationLinks(root, child);

        // Temporary PathNode to reference an extra Pathnode.
        PathNode tempChild;
        
        // Swapping links, Left
        if (root.getLeft() == child) {
            tempChild = root.getRight();
            if (root.getRight() != null) {
                root.getRight().setParent(child);
            }
            root.setLeft(child.getLeft());
            root.setRight(child.getRight());
            child.setRight(tempChild);
            child.setLeft(root);
            swapParent(root, child);
        } else { // Swapping links, Right
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
            swapParent(root, child);
        }
    }

    /**
     * Performs a minimum Heap with the smallest PathNode being at the top and
     * every child of every PathNode is less than the parent of the child. On the 
     * case that the children are equal it will always choose the left child to 
     * swap with.
     * It is *highly recommended* that the method startHeapify is used instead,
     * as it takes care of all requirements in order for heapify to run properly.
     * These requirements include:
     * 1) Setting the generation links.
     * 2) Setting the level end properties.
     * 3) Passing into heapify the lowest left PathNode that is not a leaf.
     * @param root - The lowest non-leaf left PathNode to begin the heapify.
     * @return The same PathNode we passed in as a parameter, but the tree
     *         is heapified.
     */
    private PathNode heapify(PathNode root) {
        // Holds the smallest PathNode of a parent.
        PathNode min = null;
        // Boolean flag to determine if a swap occured.
        boolean swapped = false;

        // We need to make sure children are not null to compare them.
        if (root.getLeft() != null) {
            min = root.getLeft();
            if (root.getRight() != null) {
                if (min.compareTo(root.getRight()) > 0) {
                    min = root.getRight();
                }
            }
        }
       
        // Heapify the rightmost siblings first.
        if (root.getGeneration() != null && root.getGeneration().getLeft() != null) {
            heapify(root.getGeneration());
        }
        
        // Swap PathNodes if child is smaller than parent PathNode.
        if (min != null && root.getSize() > min.getSize()) {
            swap(root,min);
            swapped = true;

            // Bubble down the heapify.
            if (root.getLeft() != null) {
                heapify(root);
            }
        }

        // Move up the tree and continue to perform Heapify.
        if (swapped && min.getIsLevelEnd()) {
            // Perform heapify on the child's parent if a swap occured.
            if (min.getParent() != null) {
                heapify(min.getParent());
            } 
        } else if (!swapped && root.getIsLevelEnd()) {
            // Perform heapify on the root's parent if no swap occured.
            if (root.getParent() != null) {
                heapify(root.getParent());
            }
        }
        return root;
    }   

    /**
     * Processes all requirements for heapify to function properly and
     * calls heapify.
     * @param root - The highest PathNode of the tree to heapify.
     * @return The highest PathNode of the heapified tree.
     */
    private PathNode startHeapify(PathNode root) {
        // Pre-requisite processes for heapify.
        setGenerationLinks(root);
        setLevelEnd(root);
        // Gets the parent of the left-most PathNode in the left-most
        // subtree for starting the heapify process.
        while (root.getLeft() != null) {
            root = root.getLeft();
        }
        // Go to the parent. This is our starting PathNode for heapify.
        if (root.getParent() != null) {
            root = root.getParent();
        }

        // Heapify
        root = getHighestRoot(heapify(root));
        setGenerationLinks(root);
        return root;
    }
    
    /**
     * Prints the path lengths from left-to-right at each level in the tree
     * in the form specified by the instructions.
     * @param root - Root of the whole tree to begin printing from.
     * @return A formatted string showing the the path lengths from left to
     *         right of each level in the tree.
     */
    private String printTreeLevels(PathNode root) {
        // String builder to append all content to.
        StringBuilder levels = new StringBuilder();
        // PathNode to keep track of the generations.
        PathNode generation;
        // To keep track of levels.
        int i = 0;
        // Root PathNode.
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
     * Reads the input from the file into a complete binary tree, performs all
     * necessary functions, and prints the tree before Heapify and after Heapify
     * has been performed.
     * @param filename - The name of the file to read and build a graph from.
     */
    public void go(String filename) throws FileNotFoundException,
                                           NumberFormatException {
        // Read input.
        readPaths(filename);
        // Build tree.
        PathNode rootPathNode = buildCompleteTree(0, 0);
        setGenerationLinks(rootPathNode);

        System.out.println("---------- Before Heapify ----------");
        System.out.println(printTreeLevels(rootPathNode));

        // Heapify.
        rootPathNode = startHeapify(rootPathNode);
        
        System.out.println("---------- After Heapify ----------");
        System.out.println(printTreeLevels(rootPathNode));
    }
}
