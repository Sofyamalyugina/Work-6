import java.util.Arrays;

class TernarySearch {
    
    /**
     * Итеративная версия тернарного поиска в отсортированном массиве
     * @param arr отсортированный массив
     * @param target искомый элемент
     * @return индекс элемента или -1 если не найден
     */
    public static int ternarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        
        // Пока есть область для поиска
        while (left <= right) {
            // Вычисляем две точки, делящие область на три равные части
            int mid1 = left + (right - left) / 3;
            int mid2 = right - (right - left) / 3;
            
            System.out.println("Диапазон: [" + left + ", " + right + "] " +
                             "mid1: " + mid1 + " (" + arr[mid1] + "), " +
                             "mid2: " + mid2 + " (" + arr[mid2] + ")");
            
            // Если элемент найден в первой точке деления
            if (arr[mid1] == target) {
                return mid1;
            }
            
            // Если элемент найден во второй точке деления
            if (arr[mid2] == target) {
                return mid2;
            }
            
            // Если искомый элемент меньше первого деления
            if (target < arr[mid1]) {
                // Ищем в левой трети
                right = mid1 - 1;
            }
            // Если искомый элемент больше второго деления
            else if (target > arr[mid2]) {
                // Ищем в правой трети
                left = mid2 + 1;
            }
            // Если элемент между mid1 и mid2
            else {
                // Ищем в средней трети
                left = mid1 + 1;
                right = mid2 - 1;
            }
        }
        
        // Элемент не найден
        return -1;
    }
    
    /**
     * Рекурсивная версия тернарного поиска
     * @param arr отсортированный массив
     * @param target искомый элемент
     * @param left левая граница
     * @param right правая граница
     * @return индекс элемента или -1 если не найден
     */
    public static int ternarySearchRecursive(int[] arr, int target, int left, int right) {
        // Базовый случай - границы пересеклись
        if (left > right) {
            return -1;
        }
        
        // Вычисляем точки деления
        int mid1 = left + (right - left) / 3;
        int mid2 = right - (right - left) / 3;
        
        System.out.println("Рекурсия - Диапазон: [" + left + ", " + right + "] " +
                         "mid1: " + mid1 + ", mid2: " + mid2);
        
        // Проверяем точки деления
        if (arr[mid1] == target) {
            return mid1;
        }
        if (arr[mid2] == target) {
            return mid2;
        }
        
        // Определяем в какой трети продолжать поиск
        if (target < arr[mid1]) {
            // Левая треть
            return ternarySearchRecursive(arr, target, left, mid1 - 1);
        } else if (target > arr[mid2]) {
            // Правая треть
            return ternarySearchRecursive(arr, target, mid2 + 1, right);
        } else {
            // Средняя треть
            return ternarySearchRecursive(arr, target, mid1 + 1, mid2 - 1);
        }
    }
    
    /**
     * Тернарный поиск для вещественных чисел (поиск минимума унимодальной функции)
     * @param func функция
     * @param left левая граница
     * @param right правая граница
     * @param eps точность
     * @return точка минимума
     */
    public static double ternarySearchMin(Function func, double left, double right, double eps) {
        // Пока интервал достаточно большой
        while (right - left > eps) {
            // Вычисляем две точки деления
            double mid1 = left + (right - left) / 3.0;
            double mid2 = right - (right - left) / 3.0;
            
            // Вычисляем значения функции в точках деления
            double f1 = func.calculate(mid1);
            double f2 = func.calculate(mid2);
            
            System.out.printf("Интервал: [%.6f, %.6f] mid1: %.6f (f=%.6f), mid2: %.6f (f=%.6f)%n",
                            left, right, mid1, f1, mid2, f2);
            
            // Для унимодальной функции:
            // Если f(mid1) > f(mid2), минимум в правой части
            // Иначе минимум в левой части
            if (f1 > f2) {
                left = mid1;
            } else {
                right = mid2;
            }
        }
        
        // Возвращаем середину финального интервала
        return (left + right) / 2.0;
    }
    
    /**
     * Интерфейс для математических функций
     */
    @FunctionalInterface
    interface Function {
        double calculate(double x);
    }
    
    // Пример функции для поиска минимума
    static Function exampleFunction = x -> (x - 2) * (x - 2) + 3; // Минимум в x = 2, f(2) = 3
    
    // Другая тестовая функция
    static Function anotherFunction = x -> -x * x + 4 * x + 1; // Максимум в x = 2, f(2) = 5
    
    /**
     * Демонстрация работы тернарного поиска на массиве
     */
    public static void demonstrateArraySearch() {
        int[] arr = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25};
        int target = 15;
        
        System.out.println("=== Тернарный поиск в массиве ===");
        System.out.println("Массив: " + Arrays.toString(arr));
        System.out.println("Ищем элемент: " + target + "\n");
        
        int result = ternarySearch(arr, target);
        
        if (result != -1) {
            System.out.println("\nЭлемент найден на позиции: " + result);
        } else {
            System.out.println("\nЭлемент не найден");
        }
    }
    
    /**
     * Демонстрация рекурсивной версии
     */
    public static void demonstrateRecursiveSearch() {
        int[] arr = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
        int target = 12;
        
        System.out.println("\n=== Рекурсивный тернарный поиск ===");
        System.out.println("Массив: " + Arrays.toString(arr));
        System.out.println("Ищем элемент: " + target + "\n");
        
        int result = ternarySearchRecursive(arr, target, 0, arr.length - 1);
        
        if (result != -1) {
            System.out.println("\nЭлемент найден на позиции: " + result);
        } else {
            System.out.println("\nЭлемент не найден");
        }
    }
    
    /**
     * Демонстрация поиска минимума функции
     */
    public static void demonstrateFunctionMin() {
        System.out.println("\n=== Поиск минимума функции f(x) = (x-2)² + 3 ===");
        
        double minPoint = ternarySearchMin(exampleFunction, 0.0, 5.0, 1e-6);
        double minValue = exampleFunction.calculate(minPoint);
        
        System.out.printf("%nНайден минимум в точке: %.6f%n", minPoint);
        System.out.printf("Значение функции в минимуме: %.6f%n", minValue);
        System.out.println("Ожидаемый минимум: x = 2.0, f(x) = 3.0");
    }
    
    /**
     * Поиск максимума функции
     */
    public static void demonstrateFunctionMax() {
        System.out.println("\n=== Поиск максимума функции f(x) = -x² + 4x + 1 ===");
        
        // Для поиска максимума инвертируем функцию
        Function invertedFunction = x -> -anotherFunction.calculate(x);
        
        double maxPoint = ternarySearchMin(invertedFunction, 0.0, 4.0, 1e-6);
        double maxValue = anotherFunction.calculate(maxPoint);
        
        System.out.printf("%nНайден максимум в точке: %.6f%n", maxPoint);
        System.out.printf("Значение функции в максимуме: %.6f%n", maxValue);
        System.out.println("Ожидаемый максимум: x = 2.0, f(x) = 5.0");
    }
    
    /**
     * Сравнение с бинарным поиском
     */
    public static void compareWithBinarySearch() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int target = 7;
        
        System.out.println("\n=== Сравнение количества итераций ===");
        System.out.println("Массив из 15 элементов, ищем: " + target);
        
        // Тернарный поиск
        System.out.println("\nТернарный поиск:");
        int result1 = ternarySearch(arr, target);
        
        // Бинарный поиск для сравнения
        System.out.println("\nБинарный поиск:");
        int left = 0, right = arr.length - 1;
        int iterations = 0;
        
        while (left <= right) {
            iterations++;
            int mid = left + (right - left) / 2;
            
            System.out.println("Итерация " + iterations + ": [" + left + ", " + right + 
                             "] mid: " + mid + " (" + arr[mid] + ")");
            
            if (arr[mid] == target) {
                System.out.println("Найден на позиции: " + mid);
                break;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }
    
    /**
     * Тестирование различных случаев
     */
    public static void testVariousCases() {
        System.out.println("\n=== Тестирование различных случаев ===");
        
        // Тест 1: Элемент в начале
        int[] arr1 = {5, 10, 15, 20, 25, 30};
        System.out.println("\nТест 1 - поиск первого элемента (5):");
        System.out.println("Результат: " + ternarySearch(arr1, 5) + " (ожидается: 0)");
        
        // Тест 2: Элемент в конце
        int[] arr2 = {1, 4, 7, 10, 13, 16, 19};
        System.out.println("\nТест 2 - поиск последнего элемента (19):");
        System.out.println("Результат: " + ternarySearch(arr2, 19) + " (ожидается: 6)");
        
        // Тест 3: Элемент в середине
        int[] arr3 = {2, 4, 6, 8, 10, 12, 14};
        System.out.println("\nТест 3 - поиск среднего элемента (8):");
        System.out.println("Результат: " + ternarySearch(arr3, 8) + " (ожидается: 3)");
        
        // Тест 4: Элемент не существует
        int[] arr4 = {1, 3, 5, 7, 9};
        System.out.println("\nТест 4 - поиск несуществующего элемента (4):");
        System.out.println("Результат: " + ternarySearch(arr4, 4) + " (ожидается: -1)");
        
        // Тест 5: Пустой массив
        int[] arr5 = {};
        System.out.println("\nТест 5 - поиск в пустом массиве:");
        System.out.println("Результат: " + ternarySearch(arr5, 5) + " (ожидается: -1)");
        
        // Тест 6: Массив из одного элемента
        int[] arr6 = {42};
        System.out.println("\nТест 6 - поиск в массиве из одного элемента:");
        System.out.println("Результат: " + ternarySearch(arr6, 42) + " (ожидается: 0)");
        System.out.println("Результат (несуществующий): " + ternarySearch(arr6, 10) + " (ожидается: -1)");
    }
    
    /**
     * Анализ сложности алгоритма
     */
    public static void analyzeComplexity() {
        System.out.println("\n=== Анализ сложности тернарного поиска ===");
        System.out.println("Временная сложность: O(log₃n)");
        System.out.println("Пространственная сложность (итеративный): O(1)");
        System.out.println("Пространственная сложность (рекурсивный): O(log₃n)");
        System.out.println("\nСравнение с бинарным поиском:");
        System.out.println("- Бинарный поиск: O(log₂n) сравнений");
        System.out.println("- Тернарный поиск: O(2*log₃n) сравнений");
        System.out.println("- На практике разница незначительна");
    }
    
    public static void main(String[] args) {
        System.out.println("=== ДЕМОНСТРАЦИЯ ТЕРНАРНОГО ПОИСКА НА JAVA ===");
        
        // Демонстрация поиска в массиве
        demonstrateArraySearch();
        
        // Демонстрация рекурсивной версии
        demonstrateRecursiveSearch();
        
        // Демонстрация поиска минимума функции
        demonstrateFunctionMin();
        
        // Демонстрация поиска максимума функции
        demonstrateFunctionMax();
        
        // Сравнение с бинарным поиском
        compareWithBinarySearch();
        
        // Тестирование различных случаев
        testVariousCases();
        
        // Анализ сложности
        analyzeComplexity();
    }
}
