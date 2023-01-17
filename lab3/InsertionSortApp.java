/**
 * 17683 Data Structures for Application Programmers.
 * Lab 3 Simple Sorting and Stability.
 * Insertion Sort Implementation.
 *
 * Andrew ID:
 * @author
 */
public class InsertionSortApp {
    /**
     * test insertion sort and its stability.
     * @param args arguments
     */
    public static void main(String[] args) {
        Employee[] list = new Employee[10];

        // employee data : first name, last name, zip
        list[0] = new Employee("Patty", "Evans", 15213);
        list[1] = new Employee("Doc", "Smith", 15214);
        list[2] = new Employee("Lorraine", "Smith", 15216);
        list[3] = new Employee("Paul", "Smith", 15216);
        list[4] = new Employee("Tom", "Yee", 15216);
        list[5] = new Employee("Sato", "Hashimoto", 15218);
        list[6] = new Employee("Henry", "Stimson", 15215);
        list[7] = new Employee("Jose", "Vela", 15211);
        list[8] = new Employee("Minh", "Vela", 15211);
        list[9] = new Employee("Lucinda", "Craswell", 15210);

        System.out.println("Before Insertion Sorting: ");
        for (Employee e : list) {
            System.out.println(e);
        }
        System.out.println("");

        insertionSort(list, "last");

        System.out.println("After Insertion Sorting by last name: ");
        for (Employee e : list) {
            System.out.println(e);
        }
        System.out.println("");

        insertionSort(list, "zip");

        System.out.println("After Insertion Sorting by zip code: ");
        for (Employee e : list) {
            System.out.println(e);
        }

    }

    /**
     * Sorts employees either by last name or zip using Insertion Sort.
     * @param list list of employee objects
     * @param key key param value should be either "last" or "zip"
     */
    public static void insertionSort(Employee[] list, String key) {
        // TODO implement insertion sort here
        if (key.equals("last")) {
            for (int out = 1; out < list.length; out++) {
                int in = out;
                Employee temp = list[out];
                while (in > 0 && list[in-1].getLastName().compareToIgnoreCase(list[out].getLastName()) > 0) {
                    in--;
                }
                if (out != in) {
                    System.arraycopy(list, in, list, in + 1, out - in);
                    list[in] = temp;
                }
            }
        } else if (key.equals("zip")) {
            for (int out = 1; out < list.length; out++) {
                int in = out;
                Employee temp = list[out];
                while (in > 0 && list[in-1].getZipCode() > list[out].getZipCode()) {
                    in--;
                }
                if (out != in) {
                    System.arraycopy(list, in, list, in + 1, out - in);
                    list[in] = temp;
                }
            }
        }

    }
}
