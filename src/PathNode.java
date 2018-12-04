import java.util.ArrayList;
import java.util.Arrays;

/**
 * The pathNodes that are used to build a complete binary tree for Heapify
 * @author Kevin Filanowski
 * @author Jake Guinn
 */
public class PathNode implements Comparable<PathNode> {
    /** An ArrayList of vertex IDs ordered by appearance in the path. */
    public ArrayList<Integer> path;
    /** Reference to the left child. */
    private PathNode left;
    /** Reference to the right child. */
    private PathNode right;
    /** Reference to the parent. */
    private PathNode parent;
    /** Reference to the node directly to the right on the same tree level. */
    private PathNode generation; //left to right sibling or cousin
    /** True if the node is last in the level going from left to right. */
    private boolean isLevelEnd;
    /** True if the node is the right-most node in the last level. */
    private boolean isLastNode;

    /**
     * Constructor for PathNode that will initialize the path and set both
     * the isLevelEnd and isLastNode to false.
     * @param path - The data inside the PathNode
     */
    public PathNode(ArrayList<Integer> path) {
        this.path = path;
        isLevelEnd = false;
        isLastNode = false;
    }
    
    /**
     * Gets the data That the PathNode holds
     * @return path - the data inside the PathNode
     */
    public ArrayList<Integer> getPath() {
        return path;
    }
    
    /**
     * Gets the size of the path in the PathNode
     * @return size - the size of the path
     */
    public int getSize() {
        return path.size();
    }

    /**
     * Sets the left Child of the PathNode
     * @param left - the PathNode that will become the left child of the current PathNode
     */
    public void setLeft(PathNode left) {
        this.left = left;
    }

    /**
     * Gets the left child of the current PathNode
     * @return left - the left child of the current PathNode
     */
    public PathNode getLeft() {
        return left;
    }
    
    /**
     * Sets the right Child of the current PathNode
     * @param right- the PathNode that will become the right child of the current PathNode
     */
    public void setRight(PathNode right) {
        this.right = right;
    }

    /**
     * Gets the right child of the current PathNode
     * @return right - the right child of the current PathNode
     */
    public PathNode getRight() {
        return right;
    }

    /**
     * Sets the parent of the current PathNode
     * @param parent - the PathNode that will become the parent of the current PathNode
     */
    public void setParent(PathNode parent) {
        this.parent = parent;
    }

    /**
     * Gets the parent of the current PathNode
     * @return parent- the parent of the current PathNode
     */
    public PathNode getParent() {
        return parent;
    }

    /**
     * Sets the generation link between the current PathNode and its sibling or cousin
     * @param node - the sibling or cousin that is being linked to the current PathNode
     */
    public void setGeneration(PathNode node) {
        generation = node;
    }

    /**
     * Gets the generation which could be a sibling or a cousin
     * @return generation - returns that PathNode that is the next generation from the 
     *                      current PathNode
     */
    public PathNode getGeneration() {
        return generation;
    }

    /**
     * Sets isLevelEnd to true or false
     * @param bool - true or false
     */
    public void setIsLevelEnd(Boolean bool) {
        this.isLevelEnd = bool;
    }

    /**
     * Gets isLevelEnd; True if it is, False if it is not
     * @return True or false
     */
    public boolean getIsLevelEnd() {
        return this.isLevelEnd;
    }

    /**
     * Sets isLevelEnd to true or false
     * @param bool - True or False depending on if it is one of the 
     *               very left PathNodes
     */
    public void setIsLastNode(boolean bool) {
        isLastNode = bool;
    }

    /**
     * Gets if the current PathNode is the last node in the tree
     * @return isLastNode - True or False depending on if it is the 
     *                      last node in the tree
     */
    public boolean getIsLastNode() {
        return isLastNode;
    }


    /**
    * Compares the current PathNode's path size to another PathNode's path size
    @return value - 0 if the sizes are equal, -1 if the current path is shorter,
                    and 1 if the current path is longer than the other.
    */
    @Override
    public int compareTo(PathNode o) {
        int value = 0;
        if (this.path.size() < o.path.size() ) {
            value = -1;
        } else if (this.path.size() > o.path.size()) {
            value = 1;
        }
        return value;
    }

    /**
     * Returns a formatted string containing the size of the path, followed
     * by a set of parenthesis containing the path itself.
     * @return - A formatted String containing the size of the path and the
     * path itself.
     */
    public String toString() {
        return (path.size()-1) + "(" + path.toString().replaceAll("[\\[\\]]", "") + ")";
    }
}
