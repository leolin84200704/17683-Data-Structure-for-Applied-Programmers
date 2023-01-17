/**
 * This class represents an ArrayList to the user by using only Array.
 * It includes basic function of an arraylist including add, search, getSize, getCapacity, display and removeDups
 * @author Leo Lin, AndrewID: hungfanl
 */
public class MyArray {
    /**
     * The stringArray is used to store all the data pass by the user.
     */
    private String[] stringArray;
    /**
     * pointer represents the position(starting at 0) for the next input to be stored.
     */
    private int pointer = 0;
    /**
     * magicnumber is the length of the array in case the user does not insert one, it is static and finalized.
     */
    private final int magicnumber = 10;
    /**
     * Initiate the array with the length of the magic number.
     * Worst-case running time complexity O(1)
     */
    MyArray() {
        this.stringArray = new String[magicnumber];
    }
    /**
     * Initiate the array with the length that user provides.
     * Worst-case running time complexity O(1)
     * @param size The length of the array assigned by the user.
     */
    MyArray(int size) {
        this.stringArray = new String[size];
    }
    /**
     * add function will determine if the string includes only english letter.
     * If so, it will store the string in the array.
     * Worst-case running time complexity O(n) (n equals the number of strings in the list)
     * @param  s The string user input.
     */
    public void add(String s) {
        if (s == null) {
            return;
        }
        s = s.replaceAll(" ", "");
        if (s.length() == 0 || !isWord(s)) {
            return;
        }
        if (pointer == stringArray.length) {
            if (pointer == 0) {
                stringArray = new String[1];
            } else {
                String[] newStringArray = new String[stringArray.length * 2];
                System.arraycopy(stringArray, 0, newStringArray, 0, stringArray.length);
                stringArray = newStringArray;
            }
        }
        stringArray[pointer] = s;
        pointer++;
    }
    /**
     * search function will determine if the string is in the array.
     * If so, return true, otherwise return false.
     * Worst-case running time complexity O(n) (n equals the number of strings in the list)
     * @param  key The string user input to be searched.
     * @return if the array contains "key"
     */
    public boolean search(String key) {
        if (key == null || key.length() == 0) {
            return false;
        }
        for (int i = 0; i < pointer; i++) {
            if (key.equals(stringArray[i])) {
                return true;
            }
        }
        return false;
    }
    /**
     * Return the number of strings stored in the array.
     * Worst-case running time complexity O(1)
     * @return The number of strings stored in the array
     */
    public int size() {
        return pointer;
    }
    /**
     * Return the length of the array.
     * Worst-case running time complexity O(1)
     * @return The length of the array
     */
    public int getCapacity() {
        return stringArray.length;
    }
    /**
     * Print all the word stored in the array to the console with a space in the middle.
     * Worst-case running time complexity O(n)  (n equals the number of strings in the list)
     */
    public void display() {
        for (int i = 0; i < pointer; i++) {
            if (i != pointer - 1) {
                System.out.print(stringArray[i] + " ");
            } else {
                System.out.println(stringArray[i]);
            }
        }
    }
    /**
     * This function remove duplicate strings in the array.
     * * Worst-case running time complexity O(n^2)  (n equals the number of strings in the list)
     */
    public void removeDups() {
        for (int i = 0; i < pointer; i++) {
            for (int j = i + 1; j < pointer; j++) {
                if (stringArray[i].equals(stringArray[j])) {
                    removeAt(j);
                    j--;
                }
            }
        }
        return;
    }
    /**
     * Remove the string at a particular position in the array.
     * @param position the position that the user decided to remove.
     */
    private void removeAt(int position) {
        for (int i = position; i < pointer - 1; i++) {
            stringArray[i] = stringArray[i + 1];
        }
        pointer--;
    }
    /**
     * Decide if the input string is a valid word(containing only english letter).
     * @param s The string to be decided if it's a valid word(containing only english letter).
     * @return If s is a valid word
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
}