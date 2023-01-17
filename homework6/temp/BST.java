import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;
import java.util.Queue;
import java.util.LinkedList;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6 BST Class.
 * A class containing The BST structure of nodes and the comparator.
 *
 * @param <T> Generic type
 *
 * Andrew ID: hungfanl
 * @author  Leo Lin
 */
public class BST<T extends Comparable<T>> implements Iterable<T>, BSTInterface<T> {
    /**
     * The root of the tree structure.
     */
    private Node<T> root;

    /**
     * The comparator used to compare the nodes.
     */
    private Comparator<T> comparator;

    /**
     * Constructor without a comparator.
     */
    public BST() {
        this(null);
    }

    /**
     * Constructor with a comparator.
     * @param comp The comparator used to compare the nodes
     */
    public BST(Comparator<T> comp) {
        comparator = comp;
        root = null;
    }

    /**
     * Return the comparator.
     * @return the comparator of the class
     */
    public Comparator<T> comparator() {
        return comparator;
    }

    /**
     * Return the data of the root.
     * @return the data of the root
     */
    public T getRoot() {
        if (root == null) {
            return null;
        }
        return root.data;
    }

    /**
     * Return the height of the BST.
     * @return the height of the BST
     */
    public int getHeight() {
        if (root == null) {
            return 0;
        }
        return getHeight(root) - 1;
    }

    /**
     * Return the height from the node to the bottom of the structure.
     * @param node the starting node
     * @return the height from the node to the bottom of the structure
     */
    private int getHeight(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    /**
     * Return the number of nodes of the BST.
     * @return the number of nodes of the BST
     */
    public int getNumberOfNodes() {
        return getNumberOfNodes(root);

    }

    /**
     * Return the number of nodes from the node to the bottom of the structure.
     * @param node the starting node
     * @return the number of nodes from the node to the bottom of the structure
     */
    private int getNumberOfNodes(Node<T> node) {
        if (node == null) {
            return 0;
        }
        return getNumberOfNodes(node.left) + getNumberOfNodes(node.right) + 1;
    }

    @Override
    public T search(T toSearch) {
        if (toSearch == null) {
            return null;
        }
        return search(root, toSearch);
    }

    /**
     * Return the data if it's found in the BST, null if not.
     * @param node the starting node
     * @param toSearch the target object
     * @return the data if it's found in the BST, null if not
     */
    private T search(Node<T> node, T toSearch) {
        if (node == null) {
            return null;
        }
        int result;
        if (comparator == null) {
            result = toSearch.compareTo(node.data);
        } else {
            result = comparator.compare(toSearch, node.data);
        }
        if (result == 0) {
            return node.data;
        } else if (result < 0) {
            return search(node.left, toSearch);
        } else {
            return search(node.right, toSearch);
        }
    }

    @Override
    public void insert(T toInsert) {
        if (root == null) {
            root = new Node<T>(toInsert);
        } else {
            insert(root, toInsert);
        }
    }

    /**
     * Store the data if it's not found in the structure.
     * @param node the starting node
     * @param toInsert the target object
     */
    private void insert(Node<T> node, T toInsert) {
        int result;
        if (comparator == null) {
            result = toInsert.compareTo(node.data);
        } else {
            result = comparator.compare(toInsert, node.data);
        }
        if (result > 0) {
            if (node.right == null) {
                node.right = new Node<T>(toInsert);
            } else {
                insert(node.right, toInsert);
            }
        } else if (result < 0) {
            if (node.left == null) {
                node.left = new Node<T>(toInsert);
            } else {
                insert(node.left, toInsert);
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new InOrderIterator();
    }

    /**
     * private iterator class.
     */
    private class InOrderIterator implements Iterator<T> {
        /**
         * The queue that stores all the data in order.
         */
        private Queue<T> q;

        /**
         * put all the data in the queue orderly.
         */
        InOrderIterator() {
            Stack<Node<T>> s = new Stack<>();
            q = new LinkedList<>();
            if (root == null) {
                return;
            }
            Node<T> cur = root;
            while (!s.isEmpty() || cur != null) {
                while (cur.left != null) {
                    s.push(cur);
                    cur = cur.left;
                }
                q.offer(cur.data);
                while (cur.right == null && !s.isEmpty()) {
                    cur = s.pop();
                    q.offer(cur.data);
                }
                if (cur.right != null) {
                    cur = cur.right;
                } else {
                    cur = null;
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !q.isEmpty();
        }

        @Override
        public T next() {
            if (q.isEmpty()) {
                return null;
            }
            return q.poll();
        }
    }

    /**
     * private node class.
     * @param <T> Generic type
     */
    private static class Node<T> {
        /**
         * The data stored in the node.
         */
        private T data;
        /**
         * left node of the node.
         */
        private Node<T> left;
        /**
         * right node of the node.
         */
        private Node<T> right;

        /**
         * Constructor without a left or right node.
         * @param d the data to be stored
         */
        Node(T d) {
            this(d, null, null);
        }

        /**
         * Constructor with a left or right node.
         * @param d the data to be stored
         * @param l the left node
         * @param r the right node
         */
        Node(T d, Node<T> l, Node<T> r) {
            data = d;
            left = l;
            right = r;
        }
    }

}
