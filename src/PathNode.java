import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 */
public class PathNode implements Comparable<PathNode> {
    /** An ArrayList of vertex IDs ordered by appearance in the path. */
    private ArrayList<Integer> path;
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
     * 
     * @param path
     */
    public PathNode(ArrayList<Integer> path) {
        this.path = path;
        isLevelEnd = false;
        isLastNode = false;
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<Integer> getPath() {
        return path;
    }

    public int getSize() {
        return path.size();
    }

    /**
     * 
     * @param left
     */
    public void setLeft(PathNode left) {
        this.left = left;
    }

    /**
     * 
     * @return
     */
    public PathNode getLeft() {
        return left;
    }
    
    /**
     * 
     * @param right
     */
    public void setRight(PathNode right) {
        this.right = right;
    }

    /**
     * 
     * @return
     */
    public PathNode getRight() {
        return right;
    }

    /**
     * 
     * @param parent
     */
    public void setParent(PathNode parent) {
        this.parent = parent;
    }

    /**
     * 
     * @return
     */
    public PathNode getParent() {
        return parent;
    }

    /**
     * 
     * @param node
     */
    public void setGeneration(PathNode node) {
        generation = node;
    }

    /**
     * 
     * @param bool
     */
    public PathNode getGeneration() {
        return generation;
    }

    /**
     * 
     * @return
     */
    public void setIsLevelEnd(Boolean bool) {
        this.isLevelEnd = bool;
    }

    /**
     * 
     * @return
     */
    public boolean getIsLevelEnd() {
        return this.isLevelEnd;
    }

    /**
     * 
     */
    public void setIsLastNode(boolean bool) {
        isLastNode = bool;
    }

    /**
     * 
     * @return
     */
    public boolean getIsLastNode() {
        return isLastNode;
    }


    /**
    * 
    */
    @Override
    public int compareTo(PathNode o) {
        int value = 0;
        if (this.path.size() - 1 < o.path.size() - 1) {
            value = -1;
        } else if (this.path.size() - 1 > o.path.size() - 1) {
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
