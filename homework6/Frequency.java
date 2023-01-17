import java.util.Comparator;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6 Index Class
 * A comparator class that compares the frequency.
 *
 * Andrew ID: hungfanl
 * @author  Leo Lin
 */
public class Frequency implements Comparator<Word> {

    @Override
    public int compare(Word o1, Word o2) {
        return Integer.compare(o2.getFrequency(), o1.getFrequency());
    }
}
