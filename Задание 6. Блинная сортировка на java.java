import java.util.Arrays;

public class Main {
    
    /**
     * Переворачивает часть массива от начала до указанного индекса
     * @param arr - массив для переворота
     * @param index - индекс, до которого переворачиваем (включительно)
     */
    private static void flip(int[] arr, int index) {
        int start = 0;
        // Меняем элементы местами от начала до указанного индекса
        while (start < index) {
            int temp = arr[start];
            arr[start] = arr[index];
            arr[index] = temp;
            start++;
            index--;
        }
    }
    
    /**
     * Находит индекс максимального элемента в части массива
     * @param arr - массив
     * @param n - размер части массива для поиска
     * @return индекс максимального элемента
     */
    private static int findMaxIndex(int[] arr, int n) {
        int maxIndex = 0;
        // Проходим по всем элементам несортированной части
        for (int i = 1; i < n; i++) {
            if (arr[i] > arr[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }
    
    /**
     * Блинная сортировка с подробным выводом процесса
     * @param arr - массив для сортировки
     */
    public static void pancakeSort(int[] arr) {
        int n = arr.length;
        System.out.println("Начало блинной сортировки:");
        System.out.println("Исходный массив: " + Arrays.toString(arr));
        System.out.println();
        
        // Начинаем с полного размера массива и постепенно уменьшаем
        for (int currentSize = n; currentSize > 1; currentSize--) {
            // Находим индекс максимального элемента в несортированной части
            int maxIndex = findMaxIndex(arr, currentSize);
            
            System.out.println("Текущий размер для сортировки: " + currentSize);
            System.out.println("Максимальный элемент: " + arr[maxIndex] + 
                             " на позиции: " + maxIndex);
            
            // Если максимальный элемент не на своем месте (не в конце текущей части)
            if (maxIndex != currentSize - 1) {
                
                // Если максимальный элемент не в начале, переворачиваем до него
                if (maxIndex != 0) {
                    System.out.println("Переворот до максимального элемента (индекс " + 
                                     maxIndex + ")");
                    flip(arr, maxIndex);
                    System.out.println("После переворота до максимума: " + 
                                     Arrays.toString(arr));
                }
                
                // Переворачиваем всю текущую часть, чтобы поставить максимальный элемент на правильное место
                System.out.println("Переворот всей текущей части (индекс " + 
                                 (currentSize - 1) + ")");
                flip(arr, currentSize - 1);
                System.out.println("После полного переворота: " + 
                                 Arrays.toString(arr));
            } else {
                System.out.println("Максимальный элемент уже на правильной позиции");
            }
            
            System.out.println("------------------------");
        }
        
        System.out.println("Сортировка завершена!");
    }
    
    /**
     * Упрощенная версия блинной сортировки (без подробного вывода)
     * @param arr - массив для сортировки
     */
    public static void simplePancakeSort(int[] arr) {
        int n = arr.length;
        
        for (int size = n; size > 1; size--) {
            // Находим индекс максимального элемента
            int maxIndex = findMaxIndex(arr, size);
            
            // Переворачиваем до максимального элемента (если он не в начале)
            if (maxIndex != 0) {
                flip(arr, maxIndex);
            }
            
            // Переворачиваем всю текущую часть
            flip(arr, size - 1);
        }
    }
    
    /**
     * Рекурсивная версия блинной сортировки
     * @param arr - массив для сортировки
     * @param n - размер текущей части массива
     */
    public static void pancakeSortRecursive(int[] arr, int n) {
        // Базовый случай: массив из 1 элемента уже отсортирован
        if (n == 1) return;
        
        // Находим индекс максимального элемента
        int maxIndex = findMaxIndex(arr, n);
        
        // Переворачиваем до максимального элемента (если нужно)
        if (maxIndex != 0) {
            flip(arr, maxIndex);
        }
        
        // Переворачиваем всю текущую часть
        flip(arr, n - 1);
        
        // Рекурсивно сортируем оставшуюся часть
        pancakeSortRecursive(arr, n - 1);
    }
    
    /**
     * Вспомогательный метод для вывода массива
     */
    private static void printArray(int[] arr, String message) {
        System.out.println(message + ": " + Arrays.toString(arr));
    }
    
    // Тестирование
    public static void main(String[] args) {
        System.out.println("=== ТЕСТ 1: Демонстрация работы алгоритма ===");
        int[] testArray1 = {23, 10, 20, 11, 12, 6, 7};
        printArray(testArray1, "Исходный массив");
        
        pancakeSort(testArray1);
        printArray(testArray1, "Отсортированный массив");
        
        System.out.println("\n=== ТЕСТ 2: Простая версия ===");
        int[] testArray2 = {64, 34, 25, 12, 22, 11, 90};
        printArray(testArray2, "Исходный массив");
        
        simplePancakeSort(testArray2);
        printArray(testArray2, "Отсортированный массив");
        
        System.out.println("\n=== ТЕСТ 3: Рекурсивная версия ===");
        int[] testArray3 = {3, 6, 1, 8, 4, 5};
        printArray(testArray3, "Исходный массив");
        
        pancakeSortRecursive(testArray3, testArray3.length);
        printArray(testArray3, "Отсортированный массив");
    }
}