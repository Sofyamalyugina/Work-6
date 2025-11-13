import java.util.Arrays;

class ExponentialSearch {
    
    /**
     * Вспомогательная функция - бинарный поиск
     * @param arr отсортированный массив
     * @param left левая граница поиска
     * @param right правая граница поиска
     * @param target искомый элемент
     * @return индекс элемента или -1 если не найден
     */
    private static int binarySearch(int[] arr, int left, int right, int target) {
        while (left <= right) {
            // Вычисляем средний индекс, избегая переполнения
            int mid = left + (right - left) / 2;
            
            // Если элемент найден в середине
            if (arr[mid] == target) {
                return mid;
            }
            
            // Если искомый элемент меньше среднего, ищем в левой половине
            if (arr[mid] > target) {
                right = mid - 1;
            } 
            // Иначе ищем в правой половине
            else {
                left = mid + 1;
            }
        }
        
        // Элемент не найден
        return -1;
    }
    
    /**
     * Экспоненциальный поиск
     * @param arr отсортированный массив
     * @param target искомый элемент
     * @return индекс элемента или -1 если не найден
     */
    public static int exponentialSearch(int[] arr, int target) {
        int n = arr.length;
        
        // Если массив пустой
        if (n == 0) {
            return -1;
        }
        
        // Если искомый элемент - первый элемент массива
        if (arr[0] == target) {
            return 0;
        }
        
        // Находим диапазон, в котором может находиться элемент
        // Увеличиваем индекс в геометрической прогрессии (1, 2, 4, 8, ...)
        int i = 1;
        while (i < n && arr[i] <= target) {
            i *= 2; // Экспоненциальное увеличение границы поиска
        }
        
        // Определяем границы для бинарного поиска
        // Левая граница - предыдущая найденная граница / 2
        // Правая граница - минимум из найденной границы и размера массива
        int left = i / 2;
        int right = Math.min(i, n - 1);
        
        // Выполняем бинарный поиск в найденном диапазоне
        return binarySearch(arr, left, right, target);
    }
    
    /**
     * Демонстрация работы экспоненциального поиска
     */
    public static void demonstrateExponentialSearch() {
        int[] arr = {2, 3, 4, 10, 15, 18, 20, 23, 25, 30, 35, 40, 45, 50};
        int target = 23;
        
        System.out.println("Массив: " + Arrays.toString(arr));
        System.out.println("Ищем элемент: " + target);
        
        int result = exponentialSearch(arr, target);
        
        if (result != -1) {
            System.out.println("Элемент найден на позиции: " + result);
        } else {
            System.out.println("Элемент не найден в массиве");
        }
    }
    
    /**
     * Тестирование различных случаев
     */
    public static void testExponentialSearch() {
        // Тестовые случаи
        int[][] testCases = {
            {1, 3, 5, 7, 9, 11, 13, 15},           // обычный случай
            {2, 4, 6, 8, 10},                      // четные числа
            {5},                                    // один элемент
            {},                                     // пустой массив
            {10, 20, 30, 40, 50, 60, 70, 80, 90},  // больший массив
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10}        // последовательные числа
        };
        
        int[] targets = {7, 6, 5, 1, 90, 3};
        
        for (int i = 0; i < testCases.length; i++) {
            System.out.println("\nТест " + (i + 1) + ":");
            System.out.println("Массив: " + Arrays.toString(testCases[i]));
            System.out.println("Ищем: " + targets[i]);
            
            int result = exponentialSearch(testCases[i], targets[i]);
            
            if (result != -1) {
                System.out.println("Найден на позиции: " + result);
            } else {
                System.out.println("Не найден");
            }
        }
    }
    
    /**
     * Сравнение с Arrays.binarySearch()
     */
    public static void compareWithBuiltIn() {
        int[] arr = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50};
        int target = 30;
        
        System.out.println("\n=== Сравнение с встроенным binarySearch ===");
        System.out.println("Массив: " + Arrays.toString(arr));
        System.out.println("Ищем: " + target);
        
        // Наш экспоненциальный поиск
        long startTime = System.nanoTime();
        int ourResult = exponentialSearch(arr, target);
        long ourTime = System.nanoTime() - startTime;
        
        // Встроенный бинарный поиск
        startTime = System.nanoTime();
        int builtInResult = Arrays.binarySearch(arr, target);
        long builtInTime = System.nanoTime() - startTime;
        
        System.out.println("Экспоненциальный поиск:");
        System.out.println("  Результат: " + ourResult + ", Время: " + ourTime + " нс");
        
        System.out.println("Встроенный binarySearch:");
        System.out.println("  Результат: " + builtInResult + ", Время: " + builtInTime + " нс");
    }
    
    /**
     * Поиск несуществующего элемента
     */
    public static void searchNonExistent() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target = 15; // Элемента нет в массиве
        
        System.out.println("\n=== Поиск несуществующего элемента ===");
        System.out.println("Массив: " + Arrays.toString(arr));
        System.out.println("Ищем: " + target);
        
        int result = exponentialSearch(arr, target);
        
        if (result == -1) {
            System.out.println("Элемент не найден (ожидаемый результат)");
        } else {
            System.out.println("Ошибка: элемент найден на позиции " + result);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Демонстрация экспоненциального поиска ===");
        demonstrateExponentialSearch();
        
        System.out.println("\n=== Тестирование различных случаев ===");
        testExponentialSearch();
        
        compareWithBuiltIn();
        
        searchNonExistent();
        
        // Дополнительный пример с большим массивом
        System.out.println("\n=== Пример с большим массивом ===");
        int[] largeArray = new int[1000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i * 2; // Четные числа от 0 до 1998
        }
        
        int target = 750;
        System.out.println("Большой массив (1000 элементов), ищем: " + target);
        
        int result = exponentialSearch(largeArray, target);
        System.out.println("Результат: " + result);
        if (result != -1) {
            System.out.println("Проверка: arr[" + result + "] = " + largeArray[result]);
        }
    }
}