import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Collections;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6 Index Class
 * A class that takes the fileName and build bst structure.
 *
 * Andrew ID: hungfanl
 * @author  Leo Lin
 */
public class Index {
    /**
     * The tree that stores the data.
     */
    private BST<Word> bst;

    /**
     * Build the BST with the info in the give filename.
     * @param fileName the filename
     * @return the BST build with the info in the file
     */
    public BST<Word> buildIndex(String fileName) {
        buildIndex(fileName, null);
        return bst;
    }

    /**
     * Build the BST with the info in the give filename with a comparator.
     * @param fileName the filename
     * @param comparator the comparator used to compare the words
     * @return the BST build with the info in the file
     */
    public BST<Word> buildIndex(String fileName, Comparator<Word> comparator) {
        bst = new BST<>(comparator);
        buildFromFile(fileName, comparator);
        return bst;
    }

    /**
     * Build the BST with the info in the give filename.
     * @param list the arrayList that contains all the nodes
     * @param comparator the comparator used to compare the words
     * @return the BST build with the info in the file
     */
    public BST<Word> buildIndex(ArrayList<Word> list, Comparator<Word> comparator) {
        bst = new BST<>(comparator);
        for (Word word: list) {
            if (comparator instanceof IgnoreCase) {
                word.setWord(word.getWord().toLowerCase());
            }
            if (bst.search(word) == null) {
                bst.insert(word);
            }
        }
        return bst;
    }

    /**
     * Read the file from the with the filename and put the words in the file in the BST.
     * @param fileName the filename
     * @param comparator the comparator used to compare the words
     */
    private void buildFromFile(String fileName, Comparator<Word> comparator) {
        File file = new File(fileName);
        int lineNum = 0;
        Scanner scanner = null;
        try {
            scanner = new Scanner(file, "latin1");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null) {
                    lineNum++;
                    buildLine(line, comparator, lineNum);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    /**
     * Takes a line, parse it, and put the words in the BST.
     * @param line The line to be parsed and put into the BST
     * @param comparator the comparator used to compare Words
     * @param lineNum the lineNum of the line
     */
    private void buildLine(String line, Comparator<Word> comparator, int lineNum) {
        String[] words = line.split("\\W");
        for (String word: words) {
            if (isWord(word)) {
                String wd;
                if (comparator instanceof IgnoreCase) {
                    wd = word.toLowerCase();
                } else {
                    wd = word;
                }
                Word ele = bst.search(new Word(wd));
                if (ele != null) {
                    ele.setFrequency(ele.getFrequency() + 1);
                    if (!ele.getIndex().contains(lineNum)) {
                        ele.addToIndex(lineNum);
                    }
                } else {
                    ele = new Word(wd);
                    ele.addToIndex(lineNum);
                    bst.insert(ele);
                }
            }
        }
    }

    /**
     * Simple private helper method to validate a word.
     * @param word text to check
     * @return true if valid, false if not
     */
    private boolean isWord(String word) {
        if (word.length() == 0) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
                return false;
            }
        }
        return true;
    }

    /**
     * Resort the words in the BST with AlphaFreq comparator.
     * @param tree the original BST
     * @return sorted list
     */
    public ArrayList<Word> sortByAlpha(BST<Word> tree) {
        ArrayList<Word> result = new ArrayList<>();
        Iterator<Word> it = tree.iterator();
        while (it.hasNext()) {
            result.add(it.next());
        }
        Collections.sort(result, new AlphaFreq());
        return result;
    }

    /**
     * Resort the words in the BST with Frequency comparator.
     * @param tree the original BST
     * @return sorted list
     */
    public ArrayList<Word> sortByFrequency(BST<Word> tree) {
        ArrayList<Word> result = new ArrayList<>();
        Iterator<Word> it = tree.iterator();
        while (it.hasNext()) {
            result.add(it.next());
        }
        Collections.sort(result, new Frequency());
        return result;
    }

    /**
     * Returns the words in the tree that has the highest frequency.
     * @param tree the original BST
     * @return the list that contains the result
     */
    public ArrayList<Word> getHighestFrequency(BST<Word> tree) {
        ArrayList<Word> result = new ArrayList<>();
        Iterator<Word> it = tree.iterator();
        int max = 0;
        while (it.hasNext()) {
            Word temp = it.next();
            if (temp.getFrequency() > max) {
                result = new ArrayList<>();
                result.add(temp);
                max = temp.getFrequency();
            } else if (temp.getFrequency() == max) {
                result.add(temp);
            }
        }
        return result;
    }

}
