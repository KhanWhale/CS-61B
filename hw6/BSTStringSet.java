import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Implementation of a BST based String Set.
 * @author Aniruddh Khanwale
 */
public class BSTStringSet implements SortedStringSet, Iterable<String> {
    /** Creates a new empty set. */
    public BSTStringSet() {
        _root = null;
    }

    @Override
    public void put(String s) {
        _root = putHelper(_root, s);
    }

    private Node putHelper(Node n, String s) {
        if (n == null) {
            return new Node(s);
        } else if (s.compareTo(n.s) == 0) {
            return n;
        } else if (s.compareTo(n.s) < 0) {
            Node copy = n;
            copy.left = putHelper(n.left, s);
            return copy;
        } else {
            Node copy = n;
            copy.right = putHelper(n.right, s);
            return copy;
        }
    }

    @Override
    public boolean contains(String s) {
        if (_root == null) {
            return false;
        } else {
            Node myNode = _root;
            while (myNode != null) {
                if (s.compareTo(myNode.s) == 0) {
                    return true;
                } else if (s.compareTo(myNode.s) < 0) {
                    myNode = myNode.left;
                } else {
                    myNode = myNode.right;
                }
            }
            return false;
        }
    }

    @Override
    public List<String> asList() {
        ArrayList<String> myTree = new ArrayList<String>();
        Iterator<String> myIterator = iterator();
        while (myIterator.hasNext()) {
            myTree.add(myIterator.next());
        }
        return myTree;
    }


    /** Represents a single Node of the tree. */
    private static class Node {
        /** String stored in this Node. */
        private String s;
        /** Left child of this Node. */
        private Node left;
        /** Right child of this Node. */
        private Node right;

        /** Creates a Node containing SP. */
        Node(String sp) {
            s = sp;
        }
    }

    /** An iterator over BSTs. */
    private static class BSTIterator implements Iterator<String> {
        /** Stack of nodes to be delivered.  The values to be delivered
         *  are (a) the label of the top of the stack, then (b)
         *  the labels of the right child of the top of the stack inorder,
         *  then (c) the nodes in the rest of the stack (i.e., the result
         *  of recursively applying this rule to the result of popping
         *  the stack. */
        private Stack<Node> _toDo = new Stack<>();

        private boolean bounded = false;
        /** A new iterator over the labels in NODE. */
        BSTIterator(Node node) {
            addTree(node);
        }

        BSTIterator(Node node, String low, String high) {
            _low = low;
            _high = high;
            bounded = true;
            addTree(node);
        }
        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /** Add the relevant subtrees of the tree rooted at NODE. */
        private void addTree(Node node) {
            if (!bounded) {
                while (node != null) {
                    _toDo.push(node);
                    node = node.left;
                }
            } else {
                while (node != null) {
                    if ((_low.compareTo(node.s) <= 0)
                && (_high.compareTo(node.s) > 0)) {
                        _toDo.push(node);
                        node = node.left;
                    } else if ((_low.compareTo(node.s)) > 0) {
                        node = node.right;
                    } else {
                        node = node.left;
                    }
                }
            }
        }
        String _low;
        String _high;
    }

    @Override
    public Iterator<String> iterator() {
        return new BSTIterator(_root);
    }

    @Override
    public Iterator<String> iterator(String low, String high) {
        return new BSTIterator(_root, low, high);
    }


    /** Root node of the tree. */
    private Node _root;
}
