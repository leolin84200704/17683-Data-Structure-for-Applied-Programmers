/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 4: HashTable Implementation with linear probing.
 *
 * Andrew ID: hungfanl
 * @author Leo Lin
 */
public class MyHashTable implements MyHTInterface {

    /**
     * The DataItem array of the table.
     */
    private DataItem[] hashArray;
    /**
     * The boolean array that determines if the hash value exists in the hashArray.
     */
    private boolean[] collide;
    /**
     * The object to be stuff in the array if the user removed from the array.
     */
    private static final DataItem DELETE = new DataItem(null);
    /**
     * The initial array length if user doesn't insert anything.
     */
    private final int magicnumber = 10;
    /**
     * The multiplier of each character when moving to the next one.
     */
    private final int step = 27;
    /**
     * The starting point of checking a prime number.
     */
    private final int primecheckstart = 3;
    /**
     * The number of elements in the array.
     */
    private int elements = 0;
    /**
     * The number of collisions in the array.
     */
    private int collisions = 0;

    MyHashTable() {
        hashArray = new DataItem[magicnumber];
        collide = new boolean[magicnumber];
    }

    MyHashTable(int size) {
        hashArray = new DataItem[size];
        collide = new boolean[size];
    }

    /**
     * Instead of using String's hashCode, you are to implement your own here.
     * You need to take the table length into your account in this method.
     *
     * In other words, you are to combine the following two steps into one step.
     * 1. converting Object into integer value
     * 2. compress into the table using modular hashing (division method)
     *
     * Helper method to hash a string for English lowercase alphabet and blank,
     * we have 27 total. But, you can assume that blank will not be added into
     * your table. Refer to the instructions for the definition of words.
     *
     * For example, "cats" : 3*27^3 + 1*27^2 + 20*27^1 + 19*27^0 = 60,337
     *
     * But, to make the hash process faster, Horner's method should be applied as follows;
     *
     * var4*n^4 + var3*n^3 + var2*n^2 + var1*n^1 + var0*n^0 can be rewritten as
     * (((var4*n + var3)*n + var2)*n + var1)*n + var0
     *
     * Note: You must use 27 for this homework.
     *
     * However, if you have time, I would encourage you to try with other
     * constant values than 27 and compare the results, but it is not required.
     * @param input input string for which the hash value needs to be calculated
     * @return int hash value of the input string
     */
    private int hashFunc(String input) {
        int result = 0;
        for (int i = 0; i < input.length(); i++) {
            result *= step;
            result += input.charAt(i) - 'a' + 1;
            result %= hashArray.length;
        }
        return result;
    }

    /**
     * doubles array length and rehash items whenever the load factor is reached.
     * Note: do not include the number of deleted spaces to check the load factor.
     * Remember that deleted spaces are available for insertion.
     */
    private void rehash() {
        DataItem[] temp = hashArray;
        int newLen = findNextPrime(temp.length * 2);
        hashArray = new DataItem[newLen];
        collide = new boolean[newLen];
        System.out.println("new length is " + newLen);
        elements = 0;
        collisions = 0;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] != null && temp[i].value != null) {
                for (int j = 0; j < temp[i].frequency; j++) {
                    insert(temp[i].value);
                }
            }
        }
    }

    /**
     * Inserts a new String value (word).
     * Frequency of each word to be stored too.
     * @param value String value to add
     */
    @Override
    public void insert(String value) {
        if (!isWord(value)) {
            return;
        }
        int position = findPosition(value);
        if (position == -1) {
            position = hashFunc(value);
            if (collide[position]) {
                collisions++;
            }
            collide[position] = true;
            while (hashArray[position] != null && hashArray[position].value != null && !hashArray[position].value.equals(value)) {
                position = (position + 1) % hashArray.length;
            }
            if (hashArray[position] == null || hashArray[position].value == null) {
                elements++;
                hashArray[position] = new DataItem(value);
            } else {
//            System.out.println(value + "--");
                collisions--;
                hashArray[position].frequency++;
            }
            if (elements > hashArray.length / 2) {
                System.out.print("Rehashing " + elements + " items, ");
                rehash();
            }
        } else {
            hashArray[position].frequency++;
        }
    }

    /**
     * Returns the size, number of items, of the table.
     * @return the number of items in the table
     */
    @Override
    public int size() {
        return elements;
    }

    /**
     * Displays the values of the table.
     * If an index is empty, it shows **
     * If previously existed data item got deleted, then it should show #DEL#
     */
    @Override
    public void display() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] == null) {
                sb.append("**");
            } else if (hashArray[i].value == null) {
                sb.append("#DEL#");
            } else {
                sb.append("[").append(hashArray[i].value).append(", ").append(hashArray[i].frequency).append("]");
            }
            if (i != hashArray.length - 1) {
                sb.append(" ");
            }
        }
        System.out.println(sb);
    }

    /**
     * Returns true if value is contained in the table.
     * @param key String key value to search
     * @return true if found, false if not found.
     */
    @Override
    public boolean contains(String key) {
        if (!isWord(key)) {
            return false;
        }
        int position = hashFunc(key);
        int count = 0;
        while (hashArray[position] != null) {
            if (key.equals(hashArray[position].value)) {
                return true;
            }
            position = (position + 1) % hashArray.length;
            count++;
            if (count >= hashArray.length) {
                break;
            }
        }
        return false;
    }

    /**
     * Find the position of the inserted key.
     * @param key String to be found
     * @return -1 if the array doesn't contain key, the position of the key otherwise
     */
    private int findPosition(String key) {
        if (!contains(key)) {
            return -1;
        }
        int position = hashFunc(key);
        while (hashArray[position] != null) {
            if (key.equals(hashArray[position].value)) {
                return position;
            }
            position = (position + 1) % hashArray.length;
        }
        return -1;
    }

    /**
     * Returns the number of collisions in relation to insert and rehash.
     * When rehashing process happens, the number of collisions should be properly updated.
     *
     * The definition of collision is "two different keys map to the same hash value."
     * Be careful with the situation where you could overcount.
     * Try to think as if you are using separate chaining.
     * "How would you count the number of collisions?" when using separate chaining.
     * @return number of collisions
     */
    @Override
    public int numOfCollisions() {
        return collisions;
    }

    /**
     * Returns the hash value of a String.
     * Assume that String value is going to be a word with all lowercase letters.
     * @param value value for which the hash value should be calculated
     * @return int hash value of a String
     */
    @Override
    public int hashValue(String value) {
        return hashFunc(value);
    }

    /**
     * Returns the frequency of a key String.
     * @param key string value to find its frequency
     * @return frequency value if found. If not found, return 0
     */
    @Override
    public int showFrequency(String key) {
        if (!isWord(key)) {
            return 0;
        }
        int position = hashFunc(key);
        int count = 0;
        while (hashArray[position] != null) {
            if (key.equals(hashArray[position].value)) {
                return hashArray[position].frequency;
            }
            position = (position + 1) % hashArray.length;
            count++;
            if (count >= hashArray.length) {
                break;
            }
        }
        return 0;
    }

    /**
     * Removes and returns removed value.
     * @param key String to remove
     * @return value that is removed. If not found, return null
     */
    @Override
    public String remove(String key) {
        if (!isWord(key)) {
            return null;
        }
        int count = 0;
        int position = hashFunc(key);
        while (hashArray[position] != null) {
            if (key.equals(hashArray[position].value)) {
                elements--;
                hashArray[position] = DELETE;
                return key;
            }
            position = (position + 1) % hashArray.length;
            count++;
            if (count >= hashArray.length) {
                break;
            }
        }
        return null;
    }

    /**
     * Find the smallest prime number that is bigger than or equal to the inserted number.
     * @param i the number that the prime number must be bigger than or equal to
     * @return the next prime number, -1 if overflow
     */
    private int findNextPrime(int i) {
        while (true) {
            if (i < 0) {
                return -1;
            }
            if (isPrime(i)) {
                return i;
            }
            i++;
        }
    }

    /**
     * Determines if a number is a prime number.
     * @param i number to be comfirmed
     * @return true if i is a prime number, false otherwise
     */
    private boolean isPrime(int i) {
        if (i < 2 || i % 2 == 0) {
            return false;
        }
        if (i == 2) {
            return true;
        }
        for (int j = 0; j < Math.sqrt(i); j += 2) {
            if (j < primecheckstart) {
                j++;
                continue;
            }
            if (i % j == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Determines if s is a true word(consists of only lower-case alphabet).
     * @param s String to be confirmed
     * @return true if s is a word, false otherwise
     */
    private boolean isWord(String s) {
        if (s == null) {
            return false;
        }
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < 'a' || s.charAt(i) > 'z') {
                return false;
            }
        }
        return true;
    }
    /**
     * private static data item nested class.
     */
    private static class DataItem {
        /**
         * String value.
         */
        private String value;
        /**
         * String value's frequency.
         */
        private int frequency;

        DataItem(String s) {
            value = s;
            frequency = 1;
        }

    }

}
