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
            return current;
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
     * Recursive method that sets the "generation" link of PathNode objects from left-to-right.
     * generation is a term I use to indicate nodes on the same level (these may be siblings or
     * cousins)
     * @param root Root of the subtree.
     */
    private void setGenerationLinks(PathNode root) {
        // sibling link
        if(root.getLeft() != null && root.getRight() != null){
            root.getLeft().setGeneration(root.getRight());

            // gets the cousin link 
            if(root.getGeneration() != null && root.getGeneration().getLeft() != null){
                root.getRight().setGeneration(root.getGeneration().getLeft());
            }else{
                setGenerationLinks(root.getLeft());
            }
            
            // sets the generations from left to right
            setGenerationLinks(root.getLeft());
            setGenerationLinks(root.getRight());
        }
    }

    /**
     * Swaps the data for now 
     * @param one: the root of the tree/subtree we are at
     * @param two: the min child of the parent 
     */
    private void swap(PathNode one, PathNode two) {
        ArrayList<Integer> temp = one.getPath();
        one.path = two.path;
        two.path = temp;
    }


    /**
     *Performs a min Heap with the smallest PathNode being at the top and every child
     *of every node is less than the parent of the child.
     * @param root: The node we start the Min Heap on
     */
    private PathNode heapify(PathNode root) {
        PathNode min = null;
        boolean swapped = false;
        System.out.println("root is: " + root);
        // we need to make sure children are not null
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
        
        System.out.println("min is: " + min);
       
        if (root.getGeneration() != null) {
            System.out.println("\nnext generation");
            // Heapify to the rightmost sibling first
            heapify(root.getGeneration());
        }
        if (min != null && root.getSize() > min.getSize()) {
            swap(root,min);
            System.out.println("swapped root and min" + root + "  " + min);
            swapped = true;
            setGenerationLinks(min);
            if (root.getLeft() != null) {
                // should be root, but its min since we didnt swap links
                heapify(min);
            }
        }

        //WHY IS THIS HERE AGAIN?
        // base case 
        //This is here so we dont get null pointer for the top of the heap
        if (swapped && min.getParent() == null) {
            return min;
        }else if (!swapped && root.getParent() == null) {
            return root;
        }

        if (swapped && min.getIsLevelEnd()) {
            // should be min if we swapped links and set level end properly
            System.out.println("\ngoing to root's parent");
            if (root.getParent() != null) {
                heapify(root.getParent());
            } else {
                System.out.println("Final root returned is: " + root);
                System.out.println("left child: " + root.getLeft());
                System.out.println("right child: " + root.getRight());
                return root;
            }
        } else if (!swapped && root.getIsLevelEnd()) {
            // should be root if we swapped links and set level end properly
            System.out.println("\ngoing to mins's parent");
            //if (min.getParent() != null)
                heapify(root.getParent());
        }
        return root;
        
    }   

    /**
     * Gets the parent of the left-most PathNode in the left-most subtree for
     * starting the heapify process.
     * @param root
     * @return
     */
    private PathNode startHeapNode(PathNode root) {
        // Goes until the the left subtree is empty
        if (root.getLeft() != null) {
            startHeapNode(root.getLeft());
        }
        return root.getParent();
    }

    private PathNode startHeapify(PathNode root) {
        return heapify(startHeapNode(root));
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
        System.out.println("Running Heap...");
        Heap heap = new Heap();
        try {
            heap.readPaths("input.txt");
            PathNode node = heap.buildCompleteTree(0, 0);
            PathNode root_node = node.getParent().getParent().getParent();
            heap.setGenerationLinks(root_node);
            heap.setLevelEnd(root_node);
            PathNode root = heap.heapify(root_node.getLeft().getLeft());
            System.out.println(heap.printTreeLevels(root.getParent().getParent()));
            

            /*
            System.out.println("Node retrieved is: " + (node.getPath().size()-1));

            // TESTING get Last Node
            System.out.println("is the node we retrieved the last node? " + node.getIsLastNode());

            // TESTING SETGENERATIONLINKS
            System.out.println("\n\nTesting setGenerationLinks \n*********************************************");
            PathNode root_node = node.getParent().getParent().getParent();
            PathNode root_node2 = root_node.getLeft().getLeft();
            PathNode root_node3 = root_node.getLeft().getRight();
            PathNode root_node4 = root_node.getRight().getLeft();
            PathNode root_node5 = root_node2.getLeft();
            PathNode root_node6 = root_node2.getRight();
            PathNode root_nullcheck = root_node4.getLeft();
            heap.setGenerationLinks(root_node);
            System.out.println("node \t" + root_node.getLeft().getGeneration());
            System.out.println("node2 \t" + root_node2.getGeneration());
            System.out.println("node3 \t" + root_node3.getGeneration());
            System.out.println("node4 \t" + root_node4.getGeneration());
            System.out.println("node5 \t" + root_node5.getGeneration());
            System.out.println("node6 \t" + root_node6.getGeneration());
            //System.out.println("node7 \t" + root_nullcheck.getGeneration());
            System.out.println("*********************************************\nEnding setGenerationLinks");
                
            // TESTING PRINTTREELEVELS
            System.out.println("\n\nTesting printTreeLevels \n*********************************************");
            System.out.println(heap.printTreeLevels(node.getParent().getParent().getParent()));
            System.out.println("\n\nEnding printTreeLevels \n*********************************************");

            // TESTING ISLEVELEND
            System.out.println("\n\nTesting setLevelEnd \n****************************************");
            PathNode rootLevel = node.getParent().getParent().getParent();
            heap.setLevelEnd(rootLevel);
            System.out.println(rootLevel.getIsLevelEnd());
            System.out.println(rootLevel.getLeft().getIsLevelEnd());
            System.out.println(rootLevel.getLeft().getLeft().getIsLevelEnd());
            System.out.println(rootLevel.getLeft().getLeft().getLeft().getIsLevelEnd());
            System.out.println("*****************************************\nEnding setLevelEnd\n\n");

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
            */
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
}
