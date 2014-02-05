package tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Yupeng Lu
 *
 * @param <V> type of the object of the tree's node value
 */
public class Tree<V> {
    
    private V value;
    private ArrayList<Tree<V>> child = new ArrayList<Tree<V>>();
    
    /**
     * Constructs object Tree, by either giving the root value,
     * or giving the root value and its children
     * @param value The value of root
     * @param children Children Trees that is adding to the root
     */
    @SafeVarargs
    public Tree(V value, Tree<V>... children) {
        this.value = value;
        for (Tree<V> i : children) {
            this.addChild(i);
        }
    }
    
    /**
     * @return The value of the node.
     */
    public V getValue() {
    	if (this.value == null) return null;
        return value;
    }
    
    /**
     * @return The first child of this tree.
     */
    public Tree<V> firstChild() {
    	if (this.isLeaf()) return null;
        return child.get(0);
    }
    
    /**
     * @return The last child of this tree (or null if this tree is a leaf).
     */
    public Tree<V> lastChild() {
    	if (this.isLeaf()) return null;
        return child.get(child.size() - 1);
    }
    
    /**
     * @return The number of (immediate) children of this node.
     */
    public int numberOfChildren() {
        return child.size();
    }
    
    /**
     * @param index the index of the child trying to get
     * @return The index-th child of this tree (counting from zero, as with arrays)
     * @throws NoSuchElementException if index is less than zero or greater than 
     * or equal to the number of children.
     */
    public Tree<V> child(int index) throws NoSuchElementException {
        if (index < 0 || index >= child.size())
            throw new NoSuchElementException();
        return child.get(index);
    }
    
    /**
     * @return An iterator for the children of this tree. 
     */
    public Iterator<Tree<V>> children() {
        return child.iterator();
    }
    
    /**
     * @return False if this node has children.
     */
    public boolean isLeaf() {
        return this.numberOfChildren() == 0;
    }
    
    /**
     * Tests whether this tree contains the given tree(node).
     * The root of this tree is included in the recursive search.
     * @param node The Tree is going to test.
     * @return True if this tree contains the given node
     */
    private boolean contains(Tree<V> node) {
        Iterator<Tree<V>> childIterator = children();
        if (this == node) return true;
        while (childIterator.hasNext()) {
            Tree<V> tempNode = (Tree<V>) childIterator.next();
            if (node == tempNode) return true;
        }
        return false;
    }
    
    /**
     * Compares this tree with given tree to see whether they are equal
     * @return True if (1) the given object is a Tree,
     * and (2) the value fields of the two trees are equal,
     * and (3) each child of one Tree equals the corresponding child of the other Tree.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tree<?>)) {
            return false;
        } else {
            if (((Tree<?>) object).isLeaf()) {
            	if (((Tree<?>) object).getValue() == null || this.value == null)
            		return this.value == null;
            	else
            		return (((Tree<?>) object).getValue().equals(this.value));
            } else {
                // if two nodes have different value, then return false
            	if (((Tree<?>) object).getValue() == null || this.value == null)
            		return this.value == null;
            	else
            		if (! value.equals(((Tree<?>) object).getValue())) return false;
            	
                if (! (this.numberOfChildren() == ((Tree<?>) object).numberOfChildren()))
                    return false;
                
                for (int i = 0; i < numberOfChildren(); i++) {
                    if (!this.child(i).equals(((Tree<?>) object).child(i))) return false;
                }
                return true;
            }
        }
    }
    
    /**
     * @return A multiline representation of this Tree.
     * Each line contains the toString() representation of the value in that node,
     * terminated with a newline (\n). Each child is indented two spaces under its parent.
     */
    @Override
    public String toString() {
        return toStringHelper(1);
    }
    
    /**
     * toString helper method
     * @param depth Used to decide how many spaces in front
     * @return Formatted String
     */
    private String toStringHelper(int depth) {
        String result = "";
        if (this.getValue() != null) {
        	result = getValue().toString();
        }
        result += "\n";
        for (int i = 0; i < this.numberOfChildren(); i++) {
        	for (int j = 0; j < depth; j++) {
        		result += "  ";
        	}
            result += this.child(i).toStringHelper(depth + 1);
        }
        return result;
    }
    
    /**
     * Sets the value in this node of the Tree.
     * @param value An object of Class V
     */
    public void setValue(V value) {
        this.value = value;
    }
    
    /**
     * Adds newChild as the new last child of this tree,
     * provided that the resultant tree is valid.
     * @param newChild the new tree that is going to add
     * @throws IllegalArgumentException when the resultant tree is not valid
     */
    public void addChild(Tree<V> newChild) throws IllegalArgumentException {
        if (newChild.contains(this)) throw new IllegalArgumentException();
        child.add(newChild);
    }
    
    /**
     * Adds newChild as the new index-th child of this tree (counting from zero),
     * provided that the resultant tree is valid. 
     * @param index the index of the newChild
     * @param newChild the tree that is going to add
     * @throws IllegalArgumentException when the resultant tree is not valid
     */
    public void addChild(int index, Tree<V> newChild) throws IllegalArgumentException {
        if (newChild.contains(this)) throw new IllegalArgumentException();
        if (index < 0 || index >= this.numberOfChildren()) throw new IllegalArgumentException();
        child.add(index, newChild);
    }
    
    /**
     * Adds each Tree in children as a new child of this tree node,
     * after any existing children, provided that the resultant tree is valid
     * @param children An array of Tree<V> that is going to add
     * @throws IllegalArgumentException when the resultant tree is not valid
     */
    @SafeVarargs
    public final void addChildren(Tree<V>... children ) throws IllegalArgumentException {
        for (int i = 0; i < children.length; i++) {
            if (children[i].contains(this)) throw new IllegalArgumentException();
            this.addChild(children[i]);
        }
    }
    
    /**
     * Removes and returns the index-th child of this tree,
     * or throws a NoSuchElementException if the index is illegal. 
     * @param index the index of child want to remove
     * @return The index-th child of the tree
     * @throws NoSuchElementException when index is illegal
     */
    public Tree<V> removeChild(int index) throws NoSuchElementException {
        if (index < 0 || index >= this.numberOfChildren()) throw new NoSuchElementException();
        return (this.child.remove(index));
    }
}
