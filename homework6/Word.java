import java.util.HashSet;
import java.util.Set;
/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6 Word Class
 * A class containing the word(String), frequency, and a set of integers indicating the lines in which the word shows.
 *
 * Andrew ID: hungfanl
 * @author  Leo Lin
 */
public class Word implements Comparable<Word> {
    /**
     * word stored in the class.
     */
    private String word;
    /**
     * The set storing all the lines.
     */
    private Set<Integer> index;
    /**
     * Number of words in the line in which the word shows.
     */
    private int frequency;

    /**
     * Constructor with a word.
     * @param newWord The word to be stored
     */
    public Word(String newWord) {
        word = newWord;
        index = new HashSet<>();
        frequency = 1;
    }

    /**
     * Setter of the word.
     * @param newWord The new word to be set
     */
    public void setWord(String newWord) {
        word = newWord;
    }

    /**
     * Getter of the word.
     * @return the word in the class
     */
    public String getWord() {
        return word;
    }

    /**
     * Setter of the frequency.
     * @param freq The new frequency to be set
     */
    public void setFrequency(int freq) {
        frequency = freq;
    }

    /**
     * Getter of the frequency.
     * @return the frequency in the class
     */
    public int getFrequency() {
        return frequency;
    }


    /**
     * Add a line to the index.
     * @param line The integer to be added to the set
     */
    public void addToIndex(Integer line) {
        index.add(line);
    }


    /**
     * Getter of the frequency.
     * @return the set that records the lines in which the word shows
     */
    public Set<Integer> getIndex() {
        return index;
    }

    @Override
    public int compareTo(Word o) {
        return word.compareTo(o.word);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(word).append(" ").append(frequency).append(" [");
        Object[] array = index.toArray();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            if (i != array.length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
