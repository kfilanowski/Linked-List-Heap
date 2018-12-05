import java.util.ArrayList;

/**
 * The pathNodes that are used to build a complete binary tree for Heapify.
 * These are nodes in a linked list, with referencing to its parent, children,
 * and right sibling.
 * @author Kevin Filanowski
 * @author Jake Ginn
 * @version December 2018
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
    /** Reference to the PathNode directly to the right on the same tree level. */
    private PathNode generation; 
    /** True if the PathNode is last in the level going from left to right. */
    private boolean isLevelEnd;
    /** True if the PathNode is the right-most PathNode in the last level. */
    private boolean isLastNode;

    /**
     * Constructor for PathNode that will initialize the path and set both
     * the isLevelEnd and isLastNode to false.
     * @param path - The data inside the PathNode.
     */
    public PathNode(ArrayList<Integer> path) {
        this.path = path;
        isLevelEnd = false;
        isLastNode = false;
    }
    
    /**
     * Gets the data that the PathNode holds, which is an
     * ArrayList of integers.
     * @return The data inside the PathNode.
     */
    public ArrayList<Integer> getPath() {
        return path;
    }
    
    /**
     * Gets the size of the path in the PathNode, which is the size of the
     * ArrayList of integers.
     * @return The size of the path.
     */
    public int getSize() {
        return path.size();
    }

    /**
     * Sets the left child of the PathNode.
     * @param left - The PathNode that will become the left child of the current 
     *               PathNode.
     */
    public void setLeft(PathNode left) {
        this.left = left;
    }

    /**
     * Gets the left child of the current PathNode.
     * @return The left child of the current PathNode.
     */
    public PathNode getLeft() {
        return left;
    }
    
    /**
     * Sets the right child of the current PathNode.
     * @param right - The PathNode that will become the right child of the 
     *                current PathNode.
     */
    public void setRight(PathNode right) {
        this.right = right;
    }

    /**
     * Gets the right child of the current PathNode.
     * @return The right child of the current PathNode.
     */
    public PathNode getRight() {
        return right;
    }

    /**
     * Sets the parent of the current PathNode.
     * @param parent - The PathNode that will become the parent of the current 
     *                 PathNode.
     */
    public void setParent(PathNode parent) {
        this.parent = parent;
    }

    /**
     * Gets the parent of the current PathNode.
     * @return parent - The parent of the current PathNode.
     */
    public PathNode getParent() {
        return parent;
    }

    /**
     * Sets the generation link between the current PathNode and its sibling or cousin.
     * @param node - The sibling or cousin that is being linked to the current 
     *               PathNode.
     */
    public void setGeneration(PathNode node) {
        generation = node;
    }

    /**
     * Gets the generation which could be a sibling or a cousin.
     * @return Returns that PathNode that is the next generation from the 
     *         current PathNode.
     */
    public PathNode getGeneration() {
        return generation;
    }

    /**
     * Sets the isLevelEnd property of the PathNode.
     * @param bool - The boolean value that defines if this PathNode is a level 
     *               end or not.
     */
    public void setIsLevelEnd(Boolean bool) {
        this.isLevelEnd = bool;
    }

    /**
     * Gets the isLevelEnd property.
     * @return True if this PathNode isLevelEnd is set to true, false otherwise.
     */
    public boolean getIsLevelEnd() {
        return this.isLevelEnd;
    }

    /**
     * Sets the isLevelEnd property of the PathNode.
     * @param bool - The boolean value that defines if this PathNode is the last 
     *               PathNode or not.
     */
    public void setIsLastNode(boolean bool) {
        isLastNode = bool;
    }

    /**
     * Gets the isLastNode field value.
     * @return True if the current PathNode is the last PathNode in the tree, 
     *         false otherwise.
     */
    public boolean getIsLastNode() {
        return isLastNode;
    }

    /**
    * Compares the current PathNode's path size to another PathNode's path size.
    * @param other - The other PathNode to compare with.
    * @return 0 if the sizes are equal, less than 0 if the current path is 
    * shorter, and greater than 0 if the current path is longer than the other.
    */
    @Override
    public int compareTo(PathNode other) {
        return this.getSize() - other.getSize();
    }

    /**
     * Returns a formatted string containing the size of the path, followed
     * by a set of parenthesis containing the path itself.
     * @return A formatted String containing the size of the path and the
     * path data itself in parenthesis seperated by commas.
     */
    public String toString() {
        return (path.size()-1) + "(" + path.toString().replaceAll("[\\[\\]]", "") + ")";
    }
}
