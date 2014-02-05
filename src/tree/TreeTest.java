package tree;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

public class TreeTest {

    Tree<String> tree;
    
    @Before
    public void setUp() throws Exception {
        tree = new Tree<String>("3");
        tree.addChild(new Tree<String>("11"));
        Tree<String> subTree = new Tree<String>("42");
        subTree.addChild(new Tree<String>("110"));
        tree.addChild(subTree);
        tree.addChildren(new Tree<String>("24"), new Tree<String>("35"));
    }

    @Test
    public void testTree() {
        Tree<String> tree2 = new Tree<String>("1234", new Tree<String>("55555"));
        tree.child(1).child(0).addChild(tree2);
        assertEquals("3\n  11\n  42\n    110\n      1234\n        55555\n  24\n  35\n", tree.toString());
    }

    @Test
    public void testGetValue() {
        assertEquals("3", tree.getValue());
    }

    @Test
    public void testFirstChild() {
        assertEquals(new Tree<String>("11"), tree.firstChild());
    }

    @Test
    public void testLastChild() {
        assertEquals(new Tree<String>("35"), tree.lastChild());
    }

    @Test
    public void testNumberOfChildren() {
        assertEquals(4, tree.numberOfChildren());
    }

    @Test
    public void testChild() {
        assertEquals(new Tree<String>("24"), tree.child(2));
    }

    @Test
    public void testChildren() {
        Iterator<Tree<String>> iterator = tree.children();
        String test = "";
        while (iterator.hasNext()) {
            test += iterator.next().getValue() + " ";
        }
        assertEquals("11 42 24 35 ", test);
    }

    @Test
    public void testIsLeaf() {
        assertTrue(tree.child(0).isLeaf());
        assertFalse(tree.child(1).isLeaf());
    }

    @Test
    public void testEqualsObject() {
        Tree<String> tree3 = new Tree<String>("42");
        assertTrue(tree.child(1).equals(tree3));
    }

    @Test
    public void testToString() {
        assertEquals("3\n  11\n  42\n    110\n  24\n  35\n", tree.toString());
    }

    @Test
    public void testSetValue() {
        Tree<String> treeFour = new Tree<String>("3", tree.child(0), tree.child(1), tree.child(2), tree.child(3));
        assertTrue(tree.equals(treeFour));
        tree.setValue("8");
        assertFalse(tree.equals(treeFour));
        assertEquals("8\n  11\n  42\n    110\n  24\n  35\n", tree.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddChildThrowException() {
        tree.addChild(tree);
    }
    
    @Test
    public void testAddChildTreeOfV() {
        Tree<String> tempTree = new Tree<String>("48");
        tree.addChild(tempTree);
        assertEquals("3\n  11\n  42\n    110\n  24\n  35\n  48\n", tree.toString());
    }

    @Test
    public void testAddChildIntTreeOfV() {
        Tree<String> tempTree = new Tree<String>("48");
        tree.addChild(1, tempTree);
        assertEquals("3\n  11\n  48\n  42\n    110\n  24\n  35\n", tree.toString());
        tree.child(2).addChild(0, tempTree);
        assertEquals("3\n  11\n  48\n  42\n    48\n    110\n  24\n  35\n", tree.toString());
    }

    @Test
    public void testAddChildren() {
        Tree<String> tempTreeOne = new Tree<String>("48");
        Tree<String> tempTreeTwo = new Tree<String>("58");
        Tree<String> tempTreeThree = new Tree<String>("68");
        tree.addChildren(tempTreeOne, tempTreeTwo, tempTreeThree);
        assertEquals("3\n  11\n  42\n    110\n  24\n  35\n  48\n  58\n  68\n", tree.toString());
    }

    @Test
    public void testRemoveChild() {
        Tree<String> tempTree = tree.removeChild(1);
        assertEquals("3\n  11\n  24\n  35\n", tree.toString());
        assertEquals("42\n  110\n", tempTree.toString());
    }

//    @Test
//    public void testObject() {
//        fail("Not yet implemented");
//    }
}
