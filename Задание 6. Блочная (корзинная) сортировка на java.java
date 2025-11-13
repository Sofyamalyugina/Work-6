import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    
    /**
     * Блочная сортировка для чисел с плавающей запятой в диапазоне [0, 1)
     * @param arr - массив для сортировки
     */
    public static void bucketSort(double[] arr) {
        // Проверка на пустой массив
        if (arr == null || arr.length == 0) {
            return;
        }
        
        // 1. СОЗДАНИЕ КОРЗИН
        // Количество корзин обычно равно количеству элементов
        int numberOfBuckets = arr.length;
        
        // Создаем список корзин (каждая корзина - это список чисел)
        List<List<Double>> buckets = new ArrayList<>(numberOfBuckets);
        
        // Инициализируем каждую корзину пустым списком
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets.add(new ArrayList<>());
        }
        
        // 2. РАСПРЕДЕЛЕНИЕ ЭЛЕМЕНТОВ ПО КОРЗИНАМ
        System.out.println("Распределение элементов по корзинам:");
        for (int i = 0; i < arr.length; i++) {
            // Вычисляем индекс корзины для текущего элемента
            // Умножаем на количество корзин, чтобы получить индекс в диапазоне [0, numberOfBuckets-1]
            int bucketIndex = (int) (arr[i] * numberOfBuckets);
            
            // Добавляем элемент в соответствующую корзину
            buckets.get(bucketIndex).add(arr[i]);
            
            System.out.printf("Элемент %.2f добавлен в корзину %d\n", arr[i], bucketIndex);
        }
        
        // 3. СОРТИРОВКА КАЖДОЙ КОРЗИНЫ
        System.out.println("\nСортировка корзин:");
        for (int i = 0; i < numberOfBuckets; i++) {
            if (!buckets.get(i).isEmpty()) {
                System.out.printf("Корзина %d до сортировки: %s\n", i, buckets.get(i));
                // Сортируем корзину используя быструю сортировку (Collections.sort)
                Collections.sort(buckets.get(i));
                System.out.printf("Корзина %d после сортировки: %s\n", i, buckets.get(i));
            }
        }
        
        // 4. СБОРКА ОТСОРТИРОВАННОГО МАССИВА
        int index = 0;
        System.out.println("\nСборка отсортированного массива:");
        
        // Проходим по всем корзинам в порядке возрастания индексов
        for (int i = 0; i < numberOfBuckets; i++) {
            List<Double> bucket = buckets.get(i);
            
            // Копируем элементы из текущей корзины в исходный массив
            for (double value : bucket) {
                arr[index++] = value;
                System.out.printf("Добавлен элемент %.2f из корзины %d\n", value, i);
            }
        }
    }
    
    /**
     * Универсальная блочная сортировка для произвольных целых чисел
     * @param arr - массив целых чисел для сортировки
     */
    public static void bucketSortForIntegers(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        
        // Находим минимальное и максимальное значения
        int minValue = arr[0];
        int maxValue = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < minValue) {
                minValue = arr[i];
            } else if (arr[i] > maxValue) {
                maxValue = arr[i];
            }
        }
        
        System.out.println("Минимальное значение: " + minValue);
        System.out.println("Максимальное значение: " + maxValue);
        
        // Вычисляем диапазон значений
        int range = maxValue - minValue + 1;
        
        // Определяем количество корзин (можно настроить в зависимости от данных)
        int numberOfBuckets = (int) Math.sqrt(arr.length);
        if (numberOfBuckets == 0) numberOfBuckets = 1;
        
        System.out.println("Количество корзин: " + numberOfBuckets);
        
        // Создаем корзины
        List<List<Integer>> buckets = new ArrayList<>(numberOfBuckets);
        for (int i = 0; i < numberOfBuckets; i++) {
            buckets.add(new ArrayList<>());
        }
        
        // Распределяем элементы по корзинам
        System.out.println("\nРаспределение элементов по корзинам:");
        for (int i = 0; i < arr.length; i++) {
            // Вычисляем индекс корзины с учетом минимального значения
            int bucketIndex = ((arr[i] - minValue) * numberOfBuckets) / range;
            // Обеспечиваем, чтобы индекс был в допустимых пределах
            bucketIndex = Math.min(bucketIndex, numberOfBuckets - 1);
            
            buckets.get(bucketIndex).add(arr[i]);
            System.out.printf("Элемент %d добавлен в корзину %d\n", arr[i], bucketIndex);
        }
        
        // Сортируем каждую корзину
        System.out.println("\nСортировка корзин:");
        for (int i = 0; i < numberOfBuckets; i++) {
            if (!buckets.get(i).isEmpty()) {
                System.out.printf("Корзина %d до сортировки: %s\n", i, buckets.get(i));
                Collections.sort(buckets.get(i));
                System.out.printf("Корзина %d после сортировки: %s\n", i, buckets.get(i));
            }
        }
        
        // Собираем отсортированный массив
        int index = 0;
        for (int i = 0; i < numberOfBuckets; i++) {
            for (int value : buckets.get(i)) {
                arr[index++] = value;
            }
        }
    }
    
    /**
     * Вспомогательный метод для вывода массива
     */
    public static void printArray(double[] arr, String message) {
        System.out.print(message + ": ");
        for (double num : arr) {
            System.out.printf("%.2f ", num);
        }
        System.out.println();
    }
    
    public static void printArray(int[] arr, String message) {
        System.out.print(message + ": ");
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }
    
    // Тестирование
    public static void main(String[] args) {
        System.out.println("=== ТЕСТ 1: Сортировка дробных чисел [0, 1) ===");
        double[] testArray1 = {0.42, 0.32, 0.33, 0.52, 0.37, 0.47, 0.51, 0.12, 0.89, 0.75};
        printArray(testArray1, "Исходный массив");
        
        bucketSort(testArray1);
        printArray(testArray1, "Отсортированный массив");
        
        System.out.println("\n=== ТЕСТ 2: Сортировка целых чисел ===");
        int[] testArray2 = {45, 12, 67, 23, 89, 34, 78, 56, 91, 23};
        printArray(testArray2, "Исходный массив");
        
        bucketSortForIntegers(testArray2);
        printArray(testArray2, "Отсортированный массив");
        
        System.out.println("\n=== ТЕСТ 3: Сортировка с отрицательными числами ===");
        int[] testArray3 = {-5, 12, -3, 0, 7, -8, 15, -1, 4};
        printArray(testArray3, "Исходный массив");
        
        bucketSortForIntegers(testArray3);
        printArray(testArray3, "Отсортированный массив");
    }
}