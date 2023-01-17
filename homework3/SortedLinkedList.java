/**
 * 17683 Data Structures for Application Programmers.
 * Homework 3 SortedLinkedList implementation with Recursion.
 *
 * This class allows user to perform a SortedLinkedList.
 *
 * @author Leo Lin
 */
public class SortedLinkedList implements MyListInterface {

    /**
     * Dummy head of SortedLinkedList.
     */
    private Node head;

    /**
     * Default constructor.
     */
    SortedLinkedList() {
        head = new Node(null, null);
    }

    /**
     * Constructor that allows the user to insert a String array,
     * this class will add the Strings to the LinkedList.
     *
     * @param unsorted the array to be input.
     */
    SortedLinkedList(String[] unsorted) {
        head = new Node(null, null);
        addArray(unsorted, 0);
    }

    /**
     * This method examine and add all the Strings in the array into the LinkedList.
     *
     * @param unsorted the array to be input.
     * @param position the position of the Array that needs to be input.
     */
    private void addArray(String[] unsorted, int position) {
        if (position == unsorted.length) {
            return;
        }
        add(unsorted[position]);
        addArray(unsorted, position + 1);
    }

    /**
     * This method examine and add an input Strings into the LinkedList.
     *
     * @param value the string to be input.
     */
    @Override
    public void add(String value) {
        if (value == null || !isWord(value)) {
            return;
        }
        addHelper(value, head);
    }

    /**
     * This method examines if the input value is duplicate and finds the right place to insert.
     *
     * @param value the string to be input.
     * @param n the candidate node before the string
     */
    private void addHelper(String value, Node n) {
        if (n.next == null || value.compareToIgnoreCase(n.next.data) < 0) {
            n.next = new Node(value, n.next);
        } else if (value.compareToIgnoreCase(n.next.data) > 0) {
            addHelper(value, n.next);
        }
    }

    /**
     * This method examines the number of strings in the array.
     *
     * @return the number of strings input in the array.
     */
    @Override
    public int size() {
        return sizeHelper(head, -1);
    }

    /**
     * This method examines if the node is the end node, if not pass on the next node.
     *
     * @param n current node.
     * @param position current number of strings.
     * @return the number of Strings input in the array.
     */
    private int sizeHelper(Node n, int position) {
        if (n == null) {
            return position;
        }
        return sizeHelper(n.next, position + 1);
    }

    /**
     * This method displays all the data in the LinkedList.
     *
     */
    @Override
    public void display() {
        StringBuilder sb = new StringBuilder("[");
        if (head.next == null) {
            sb.append("]");
        } else {
            displayHelper(sb, head.next);
        }
        System.out.println(sb.toString());

    }

    /**
     * This method add the data of current node into the StringBuilder.
     *
     * @param sb the StringBuilder that contains all the strings in the LinkedList.
     * @param n current node.
     */
    private void displayHelper(StringBuilder sb, Node n) {
        if (n.next == null) {
            sb.append(n.data).append("]");
            return;
        }
        sb.append(n.data).append(", ");
        displayHelper(sb, n.next);
    }

    /**
     * This method examines if a particular string is in the LinkedList.
     *
     * @param key the string to be examined.
     * @return if key is in the LinkedList
     */
    @Override
    public boolean contains(String key) {
        return containsHelper(head, key);
    }

    /**
     * This method examines if a particular string is in this node in the LinkedList.
     *
     * @param n current node to be examined.
     * @param key the string to be examined.
     * @return if key is in the LinkedList
     */
    private boolean containsHelper(Node n, String key) {
        if (n.next == null || key.compareToIgnoreCase(n.next.data) < 0) {
            return false;
        } else if (key.compareToIgnoreCase(n.next.data) > 0) {
            return containsHelper(n.next, key);
        }
        return true;
    }

    /**
     * This method examines if the LinkedList has no data stored in it.
     *
     * @return if the LinkedList has no data stored in it.
     */
    @Override
    public boolean isEmpty() {
        return head.next == null;
    }

    /**
     * This method removes the first node in the LinkedList.
     *
     * @return the value removed.
     */
    @Override
    public String removeFirst() {
        if (head.next == null) {
           return null;
        }
        String result = head.next.data;
        head.next = head.next.next;
        return result;
    }

    /**
     * This method removes the node designated by the user in the LinkedList.
     *
     * @param index the position to be removed.
     * @return the value removed.
     */
    @Override
    public String removeAt(int index) {
        if (index < 0) {
            throw new RuntimeException("Incorrect input.");
        }
        return removeAtHelper(head, 0, index);
    }

    /**
     * This method removes the node at a particular position in the LinkedList.
     *
     * @param n current node.
     * @param position the position of n.
     * @param index the position to be removed.
     * @return the value removed.
     */
    private String removeAtHelper(Node n, int position, int index) {
        if (n.next == null) {
            return null;
        }
        if (position == index) {
            String result = n.next.data;
            if (n.next.next == null) {
                n.next = null;
            } else {
                n.next = n.next.next;
            }
            return result;
        }
        return removeAtHelper(n.next, position + 1, index);
    }

    /**
     * This method examines if the string is a word.
     *
     * @param s the string to be examined.
     * @return if the string is a word.
     */
    private static boolean isWord(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
                return false;
            }
        }
        return true;
    }

    /**
     * A static node class consist of a String that stores the data.
     * @author Leo
     */
    private static class Node {
        /**
         * Data stored in Node.
         */
        private String data;
        /**
         * Next node reference.
         */
        private Node next;

        /**
         * Constructor.
         * @param d word to be stored.
         * @param n next node.
         */
        Node(String d, Node n) {
            data = d;
            next = n;
        }
    }
}
