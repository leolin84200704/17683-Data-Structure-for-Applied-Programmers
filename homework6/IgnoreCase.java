import java.util.Comparator;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6 Index Class
 * A comparator class that compares the word but ignore if the word is uppercase or lower case.
 *
 * Andrew ID: hungfanl
 * @author  Leo Lin
 */
public class IgnoreCase implements Comparator<Word> {
    @Override
    public int compare(Word o1, Word o2) {
        return o1.getWord().compareToIgnoreCase(o2.getWord());
    }

}
