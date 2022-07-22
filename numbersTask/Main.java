package numbersTask;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>(Arrays.asList(1, 2, 5, 16, -1, -2, 0, 32, 3, 5, 8, 23, 4));
        // 1 и 2
        Iterator<Integer> iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            int value = iterator.next();
            if (value <= 0 || value % 2 != 0) {
                iterator.remove();
            }
        }
        // 3
        Collections.sort(arrayList);
        // 4
        System.out.println(arrayList);
    }
}