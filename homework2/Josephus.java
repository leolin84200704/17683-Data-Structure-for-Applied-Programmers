import java.util.ArrayDeque;
import java.util.LinkedList;

/**
 * I would choose the third method (playWithLLAt) when there are a lot of people in the group.
 * The reason is that the other two methods requires a lot of rearranging the position,
 * which takes a lot of time, but the playWithLLAt method can utilize math to complete the removal,
 * which is very efficient.
 * 17683 Data Structures for Application Programmers.
 * Homework Assignment 2 Solve Josephus problem using different data structures
 * and different algorithms and compare running times.
 *
 * Andrew ID: hungfanl
 * @author  Leo Lin
 */
public class Josephus {

    /**
     * Uses ArrayDeque class as Queue/Deque to find the survivor's position.
     *
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithAD(int size, int rotation) {
        // TODO your implementation here
        ArrayDeque<Integer> ad = new ArrayDeque<>(size);
        if (rotation <= 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < size + 1; i++) {
            ad.offer(i);
        }
        while (size > 1) {
            for (int i = 0; i < (rotation - 1) % size; i++) {
                ad.offer(ad.poll());
            }
            ad.poll();
            size--;
        }
        return ad.poll();
    }

    /**
     * Uses LinkedList class as Queue/Deque to find the survivor's position.
     *
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLL(int size, int rotation) {
        // TODO your implementation here
        LinkedList<Integer> ll = new LinkedList<>();
        if (rotation <= 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < size + 1; i++) {
            ll.addLast(i);
        }
        while (size > 1) {
            for (int i = 0; i < (rotation - 1) % size; i++) {
                ll.addLast(ll.removeFirst());
            }
            ll.removeFirst();
            size--;
        }
        return ll.get(0);
    }

    /**
     * Uses LinkedList class to find the survivor's position.
     *
     * However, do NOT use the LinkedList as Queue/Deque
     * Instead, use the LinkedList as "List"
     * That means, it uses index value to find and remove a person to be executed in the circle
     *
     * Note: Think carefully about this method!!
     * When in doubt, please visit one of the office hours!!
     *
     * @param size Number of people in the circle that is bigger than 0
     * @param rotation Elimination order in the circle. The value has to be greater than 0
     * @return The position value of the survivor
     */
    public int playWithLLAt(int size, int rotation) {
        // TODO your implementation here
        LinkedList<Integer> ll = new LinkedList<>();
        if (rotation <= 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 1; i < size + 1; i++) {
            ll.add(i);
        }
        int remove = rotation - 1;
        while (size > 1) {
            while (remove >= size) {
                remove -= size;
            }
            ll.remove(remove);
            remove += (rotation - 1);
            size--;
        }
        return ll.get(0);
    }

}
