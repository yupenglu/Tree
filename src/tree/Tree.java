package tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Tree<V> {
    
    private V value;
    private ArrayList<Tree<V>> child = new ArrayList<Tree<V>>();
    
    @SafeVarargs
    public Tree(V value, Tree<V>... children) {
        this.value = value;
        for (Tree<V> i : children) {
            this.addChild(i);
        }
    }
    
    public V getValue() {
        return value;
    }
    
    public Tree<V> firstChild() {
        return child.get(0);
    }
    
    public Tree<V> lastChild() {
        return child.get(child.size() - 1);
    }
    
    public int numberOfChildren() {
        return child.size();
    }
    
    public Tree<V> child(int index) throws NoSuchElementException {
        if (index < 0 || index >= child.size())
            throw new NoSuchElementException();
        return child.get(index);
    }
    
    public Iterator<Tree<V>> children() {
        return child.iterator();
    }
    
    public boolean isLeaf() {
        return this.numberOfChildren() == 0;
    }
    
    private boolean contains(Tree<V> node) {
        Iterator<Tree<V>> childIterator = children();
        if (this == node) return true;
        while (childIterator.hasNext()) {
            Tree<V> tempNode = (Tree<V>) childIterator.next();
            if (node == tempNode) return true;
        }
        return false;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Tree<?>)) {
            return false;
        } else {
            if (((Tree<?>) object).isLeaf()) {
                return (((Tree<?>) object).value == value);
            } else {
                // if two nodes have different value, then return false
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
    
    @Override
    public String toString() {
//        if (getValue() == null)           
//            return "";
//        } else {
//            System.out.println("is null");
        return toStringHelper(1);
    }
    
    private String toStringHelper(int depth) {
        String result;
        result = getValue().toString();
        result += "\n";
        for (int i = 0; i < this.numberOfChildren(); i++) {
            for (int j = 0; j < depth; j++) {
                result += "  ";
            }
            result += this.child(i).toStringHelper(depth + 1);
        }
        return result;
    }
    
    public void setValue(V value) {
        this.value = value;
    }
    
    public void addChild(Tree<V> newChild) throws IllegalArgumentException {
        if (newChild.contains(this)) throw new IllegalArgumentException();
        child.add(newChild);
    }
    
    public void addChild(int index, Tree<V> newChild) throws IllegalArgumentException {
        if (newChild.contains(this)) throw new IllegalArgumentException();
        if (index < 0 || index >= this.numberOfChildren()) throw new IllegalArgumentException();
        child.add(index, newChild);
    }
    
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
     * @return the index-th child of the tree
     * @throws NoSuchElementException when index is illegal
     */
    public Tree<V> removeChild(int index) throws NoSuchElementException {
        if (index < 0 || index >= this.numberOfChildren()) throw new NoSuchElementException();
        return (this.child.remove(index));
    }
}
