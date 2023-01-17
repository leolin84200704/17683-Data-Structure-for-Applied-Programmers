import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 5 Similarity Calculation
 * Calculating the similarity of to maps.
 *
 * Andrew ID: hungfanl
 * @author  Leo Lin
 */
public class Similarity {
    /**
     * Number of words.
     */
    private BigInteger words;
    /**
     * Number of lines.
     */
    private int lines;
    /**
     * The map that contains all the words and its frequency.
     */
    private Map<String, BigInteger> freqMap;
    /**
     * The sum of the frequency of each word to the power of 2.
     */
    private double euclidean;

    /**
     * Constructor with a line.
     * @param string The string to be compared
     */
    public Similarity(String string) {
        lines = 0;
        euclidean = 0;
        words = BigInteger.ZERO;
        freqMap = new HashMap<>();
        if (string == null) {
            return;
        }
        storeLine(string);
        calculateEuclidean();
    }

    /**
     * Constructor with a file.
     * @param file The string to be compared
     */
    public Similarity(File file) {
        lines = 0;
        euclidean = 0;
        words = BigInteger.ZERO;
        freqMap = new HashMap<>();
        if (file == null) {
            return;
        }
        Scanner scanner = null;
        try {
             scanner = new Scanner(file, "latin1");
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line != null) {
                    storeLine(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find the file");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        calculateEuclidean();
    }

    /**
     * Takes a line and put it in the map. Update words, and lines.
     * @param line The line to be parsed and put into the map.
     */
    private void storeLine(String line) {
        lines++;
        String[] wordsFromText = line.split("\\W");
        for (String s: wordsFromText) {
            if (isWord(s)) {
                words = words.add(BigInteger.ONE);
                freqMap.put(s.toLowerCase(), freqMap.getOrDefault(s.toLowerCase(), BigInteger.ZERO).add(BigInteger.ONE));
            }
        }
    }

    /**
     * Calculate the euclidean of freqMap.
     */
    private void calculateEuclidean() {
        BigInteger temp = BigInteger.ZERO;
        for (Map.Entry<String, BigInteger> entry: freqMap.entrySet()) {
            temp = temp.add(entry.getValue().multiply(entry.getValue()));
        }
        euclidean = Math.sqrt(temp.doubleValue());
    }

    /**
     * Calculate the euclidean other map.
     * @param map Other map to be calculated
     * @return euclidean of the map
     */
    private double calculateEuclidean(Map<String, BigInteger> map) {
        BigInteger temp = BigInteger.ZERO;
        if (map == null || map.size() == 0) {
            return 0;
        }
        for (Map.Entry<String, BigInteger> entry: map.entrySet()) {
            temp = temp.add(entry.getValue().multiply(entry.getValue()));
        }
        return Math.sqrt(temp.doubleValue());
    }

    /**
     * Return number of lines.
     * @return number of lines
     */
    public int numOfLines() {
        return lines;
    }

    /**
     * Return number of words.
     * @return number of words
     */
    public BigInteger numOfWords() {
        return words;
    }

    /**
     * Return number of different words.
     * @return number of different words
     */
    public int numOfWordsNoDups() {
        return freqMap.size();
    }

    /**
     * Return euclidean.
     * @return euclidean
     */
    public double euclideanNorm() {
        return euclidean;
    }

    /**
     * Return the dot product of another map and freqMap.
     * @param map Other map to be compared
     * @return the dot product of another map and freqMap
     */
    public double dotProduct(Map<String, BigInteger> map) {
        double result = 0;
        if (map == null) {
            return 0;
        }
        for (Map.Entry<String, BigInteger> entry: freqMap.entrySet()) {
            result += entry.getValue().multiply(map.getOrDefault(entry.getKey(), BigInteger.ZERO)).doubleValue();
        }
        return result;
    }

    /**
     * Return the distance of another map and freqMap.
     * @param map Other map to be compared
     * @return the distance of another map and freqMap
     */
    public double distance(Map<String, BigInteger> map) {
        if (euclidean == 0) {
            return Math.PI / 2;
        }
        double mapEuclidean = calculateEuclidean(map);
        if (mapEuclidean == 0) {
            return Math.PI / 2;
        }
        double sim = dotProduct(map) / euclidean / mapEuclidean;
        if (isSameMap(map)) {
            return 0;
        }
        return Math.acos(sim);
    }

    /**
     * Return freqMap.
     * @return freqMap
     */
    public Map<String, BigInteger> getMap() {
        return freqMap;
    }

    /**
     * Simple private helper method to validate a word.
     * @param string text to check
     * @return true if valid, false if not
     */
    private boolean isWord(String string) {
        if (string.length() == 0) {
            return false;
        }
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) {
                return false;
            }
        }
        return true;
    }

    /**
     * Simple private helper method to validate if another map is identical with freqMap.
     * @param map Other map to be compared
     * @return true if valid, false if not
     */
    private boolean isSameMap(Map<String, BigInteger> map) {
        if (map.size() != freqMap.size()) {
            return false;
        }
        for (Map.Entry<String, BigInteger> entry: map.entrySet()) {
            if (!freqMap.get(entry.getKey()).equals(entry.getValue())) {
                return false;
            }
        }
        return true;
    }

}
