package compare_arrays;

/*
Напишите обобщенный метод compareArrays(), который принимает два массива и возвращает true, если они одинаковые,
и false в противном случае. Массивы могут быть любого типа данных, но должны иметь одинаковую длину и
содержать элементы одного типа по парно по индексам.
*/

public class Main {
    public static void main(String[] args) {
        Object[] arr1 = {321, 123};
        Short[] arr2 = {321, 123};
        System.out.println(compareArrays(arr1, arr2));// false

        String[] arr3 = {"007", "321", "123"};
        String[] arr4 = {"007", "321", "123"};
        System.out.println(compareArrays(arr3, arr4)); // true
    }

    private static  <T, U> boolean compareArrays(T[] arr1, U[] arr2) {
        if (arr1.length != arr2.length) {
            throw new IllegalArgumentException("Массивы должны быть одинакого размера!");
        }
        for (int i = 0; i < arr1.length; i++) {
            if (!arr1[i].equals(arr2[i])) {
                return false;
            }
        }
        return true;
    }
}
