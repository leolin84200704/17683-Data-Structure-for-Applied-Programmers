import java.util.Comparator;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 6 Index Class
 * A comparator class that compares the word and then the frequency.
 *
 * Andrew ID: hungfanl
 * @author  Leo Lin
 */
public class AlphaFreq implements Comparator<Word> {

    @Override
    public int compare(Word o1, Word o2) {
        if (o1.getWord().compareTo(o2.getWord()) != 0) {
            return o1.getWord().compareTo(o2.getWord());
        }
        return Integer.compare(o1.getFrequency(), o2.getFrequency());
    }


}
