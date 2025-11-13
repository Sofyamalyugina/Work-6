import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    
    /**
     * Поиск скачками (Jump Search) в отсортированном массиве
     * @param arr - отсортированный массив для поиска
     * @param target - искомый элемент
     * @return индекс элемента или -1 если не найден
     */
    public static int jumpSearch(int[] arr, int target) {
        int n = arr.length;
        
        // Проверка на пустой массив
        if (n == 0) {
            System.out.println("Массив пуст");
            return -1;
        }
        
        System.out.println("=== НАЧАЛО ПОИСКА СКАЧКАМИ ===");
        System.out.println("Ищем элемент: " + target);
        System.out.println("Размер массива: " + n);
        System.out.println("Массив: " + Arrays.toString(arr));
        
        // Определяем оптимальный размер прыжка (квадратный корень из n)
        int jumpSize = (int) Math.sqrt(n);
        System.out.println("Размер прыжка: " + jumpSize);
        
        // 1. ЭТАП: ПРЫЖКИ ПО МАССИВУ
        System.out.println("\n1. ЭТАП: ПРЫЖКИ ПО МАССИВУ");
        int prev = 0;
        int step = jumpSize;
        
        // Прыгаем по массиву, пока не найдем блок, где может быть элемент
        while (step < n && arr[step] <= target) {
            System.out.println("Прыжок к индексу " + step + " (значение: " + arr[step] + ")");
            prev = step;
            step += jumpSize;
            
            // Если вышли за границы, устанавливаем step на последний элемент
            if (step >= n) {
                step = n - 1;
                System.out.println("Достигнут конец массива, устанавливаем step = " + step);
            }
        }
        
        System.out.println("Блок для поиска: индексы от " + prev + " до " + step);
        
        // 2. ЭТАП: ЛИНЕЙНЫЙ ПОИСК В НАЙДЕННОМ БЛОКЕ
        System.out.println("\n2. ЭТАП: ЛИНЕЙНЫЙ ПОИСК В БЛОКЕ");
        for (int i = prev; i <= step && i < n; i++) {
            System.out.println("Проверяем индекс " + i + " (значение: " + arr[i] + ")");
            
            if (arr[i] == target) {
                System.out.println("✓ Элемент найден на позиции " + i);
                return i;
            }
            
            // Если текущий элемент больше искомого, значит элемента нет в массиве
            if (arr[i] > target) {
                System.out.println("✗ Элемент " + arr[i] + " > " + target + ", прекращаем поиск");
                break;
            }
        }
        
        System.out.println("✗ Элемент не найден");
        return -1;
    }
    
    /**
     * Рекурсивная версия поиска скачками
     */
    public static int jumpSearchRecursive(int[] arr, int target, int prev, int step, int jumpSize) {
        int n = arr.length;
        
        // Базовый случай: вышли за границы
        if (prev >= n) {
            return -1;
        }
        
        // Если нашли элемент в текущей позиции
        if (arr[prev] == target) {
            return prev;
        }
        
        // Если текущий элемент больше искомого, делаем линейный поиск назад
        if (arr[prev] > target) {
            // Линейный поиск в предыдущем блоке
            int start = Math.max(0, prev - jumpSize + 1);
            for (int i = start; i < prev; i++) {
                if (arr[i] == target) {
                    return i;
                }
                if (arr[i] > target) {
                    break;
                }
            }
            return -1;
        }
        
        // Определяем следующий прыжок
        int nextStep = Math.min(prev + jumpSize, n - 1);
        
        // Если следующий прыжок выходит за границы или элемент между прыжками, делаем линейный поиск
        if (nextStep == n - 1 || arr[nextStep] > target) {
            // Линейный поиск в текущем блоке
            for (int i = prev + 1; i <= nextStep; i++) {
                if (arr[i] == target) {
                    return i;
                }
                if (arr[i] > target) {
                    break;
                }
            }
            return -1;
        }
        
        // Рекурсивно переходим к следующему прыжку
        return jumpSearchRecursive(arr, target, nextStep, nextStep, jumpSize);
    }
    
    /**
     * Вспомогательная функция для рекурсивного поиска
     */
    public static int jumpSearchRecursiveWrapper(int[] arr, int target) {
        System.out.println("\n=== РЕКУРСИВНЫЙ ПОИСК СКАЧКАМИ ===");
        if (arr.length == 0) return -1;
        
        int jumpSize = (int) Math.sqrt(arr.length);
        return jumpSearchRecursive(arr, target, 0, 0, jumpSize);
    }
    
    /**
     * Поиск скачками с возвратом всех вхождений
     * @param arr - отсортированный массив
     * @param target - искомый элемент
     * @return список индексов, где найден элемент
     */
    public static List<Integer> jumpSearchAll(int[] arr, int target) {
        List<Integer> results = new ArrayList<>();
        int n = arr.length;
        
        if (n == 0) return results;
        
        System.out.println("\n=== ПОИСК ВСЕХ ВХОЖДЕНИЙ ===");
        System.out.println("Ищем все вхождения элемента: " + target);
        
        int jumpSize = (int) Math.sqrt(n);
        int prev = 0;
        int step = jumpSize;
        
        // Находим блок, где может быть элемент
        while (step < n && arr[step] <= target) {
            prev = step;
            step += jumpSize;
            if (step >= n) step = n - 1;
        }
        
        // Линейный поиск в найденном блоке
        for (int i = prev; i <= step && i < n; i++) {
            if (arr[i] == target) {
                results.add(i);
                System.out.println("Найдено вхождение на позиции " + i);
            } else if (arr[i] > target) {
                break;
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("Вхождения не найдены");
        } else {
            System.out.println("Всего найдено вхождений: " + results.size());
        }
        
        return results;
    }
    
    /**
     * Сравнение эффективности поиска скачками и линейного поиска
     */
    public static void compareSearchMethods(int[] arr, int target) {
        System.out.println("\n=== СРАВНЕНИЕ ЭФФЕКТИВНОСТИ ===");
        
        // Поиск скачками
        int jumpComparisons = 0;
        int n = arr.length;
        int jumpSize = (int) Math.sqrt(n);
        int prev = 0;
        int step = jumpSize;
        
        // Этап прыжков
        while (step < n && arr[step] <= target) {
            jumpComparisons++;
            prev = step;
            step += jumpSize;
            if (step >= n) step = n - 1;
        }
        
        // Этап линейного поиска
        for (int i = prev; i <= step && i < n; i++) {
            jumpComparisons++;
            if (arr[i] == target || arr[i] > target) {
                break;
            }
        }
        
        // Линейный поиск
        int linearComparisons = 0;
        for (int i = 0; i < n; i++) {
            linearComparisons++;
            if (arr[i] == target || arr[i] > target) {
                break;
            }
        }
        
        System.out.println("Поиск скачками: " + jumpComparisons + " сравнений");
        System.out.println("Линейный поиск: " + linearComparisons + " сравнений");
        System.out.println("Эффективность: " + (linearComparisons - jumpComparisons) + " сравнений");
    }
    
    /**
     * Визуализация процесса поиска
     */
    public static void visualizeSearch(int[] arr, int target) {
        System.out.println("\n=== ВИЗУАЛИЗАЦИЯ ПОИСКА ===");
        System.out.print("Массив:    ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        
        System.out.print("Индексы:   ");
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("%2d ", i);
        }
        System.out.println();
        
        int n = arr.length;
        int jumpSize = (int) Math.sqrt(n);
        int prev = 0;
        int step = jumpSize;
        
        // Визуализация прыжков
        System.out.print("Прыжки:    ");
        for (int i = 0; i < n; i++) {
            if (i == prev) {
                System.out.print("P  "); // Prev
            } else if (i == step) {
                System.out.print("S  "); // Step
            } else if (i % jumpSize == 0) {
                System.out.print("J  "); // Jump point
            } else {
                System.out.print(".  ");
            }
        }
        System.out.println();
        
        System.out.println("Легенда: P - начало блока, S - конец блока, J - точки прыжков");
    }
    
    /**
     * Простая версия поиска скачками для практического использования
     */
    public static int jumpSearchSimple(int[] arr, int target) {
        int n = arr.length;
        if (n == 0) return -1;
        
        // Определяем размер прыжка
        int jumpSize = (int) Math.sqrt(n);
        int prev = 0;
        
        // Прыгаем по массиву
        int step = jumpSize;
        while (step < n && arr[step] <= target) {
            prev = step;
            step += jumpSize;
            if (step >= n) step = n - 1;
        }
        
        // Линейный поиск в найденном блоке
        for (int i = prev; i <= step && i < n; i++) {
            if (arr[i] == target) {
                return i;
            }
            if (arr[i] > target) {
                break;
            }
        }
        
        return -1;
    }
    
    /**
     * Поиск скачками с пользовательским размером прыжка
     */
    public static int jumpSearchWithCustomStep(int[] arr, int target, int customJumpSize) {
        int n = arr.length;
        if (n == 0) return -1;
        
        System.out.println("Поиск с пользовательским прыжком: " + customJumpSize);
        
        int prev = 0;
        int step = customJumpSize;
        
        // Прыгаем по массиву
        while (step < n && arr[step] <= target) {
            prev = step;
            step += customJumpSize;
            if (step >= n) step = n - 1;
        }
        
        // Линейный поиск в найденном блоке
        for (int i = prev; i <= step && i < n; i++) {
            if (arr[i] == target) {
                return i;
            }
            if (arr[i] > target) {
                break;
            }
        }
        
        return -1;
    }
    
    // Тестирование
    public static void main(String[] args) {
        // Создаем отсортированный массив для тестирования
        int[] sortedArray = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29};
        
        System.out.println("Тестовый массив: " + Arrays.toString(sortedArray));
        System.out.println();
        
        // Тест 1: Поиск существующего элемента
        System.out.println("=== ТЕСТ 1: ПОИСК СУЩЕСТВУЮЩЕГО ЭЛЕМЕНТА ===");
        int target1 = 17;
        int result1 = jumpSearch(sortedArray, target1);
        System.out.println("Результат: " + result1);
        
        // Тест 2: Поиск несуществующего элемента
        System.out.println("\n=== ТЕСТ 2: ПОИСК НЕСУЩЕСТВУЮЩЕГО ЭЛЕМЕНТА ===");
        int target2 = 16;
        int result2 = jumpSearch(sortedArray, target2);
        System.out.println("Результат: " + result2);
        
        // Тест 3: Поиск первого элемента
        System.out.println("\n=== ТЕСТ 3: ПОИСК ПЕРВОГО ЭЛЕМЕНТА ===");
        int target3 = 1;
        int result3 = jumpSearch(sortedArray, target3);
        System.out.println("Результат: " + result3);
        
        // Тест 4: Поиск последнего элемента
        System.out.println("\n=== ТЕСТ 4: ПОИСК ПОСЛЕДНЕГО ЭЛЕМЕНТА ===");
        int target4 = 29;
        int result4 = jumpSearch(sortedArray, target4);
        System.out.println("Результат: " + result4);
        
        // Тест 5: Простая версия
        System.out.println("\n=== ТЕСТ 5: ПРОСТАЯ ВЕРСИЯ ===");
        int target5 = 13;
        int result5 = jumpSearchSimple(sortedArray, target5);
        System.out.println("Результат: " + result5);
        
        // Тест 6: Поиск всех вхождений (в массиве с дубликатами)
        System.out.println("\n=== ТЕСТ 6: ПОИСК ВСЕХ ВХОЖДЕНИЙ ===");
        int[] arrayWithDuplicates = {1, 2, 2, 2, 3, 4, 4, 5, 5, 5, 5, 6, 7};
        System.out.println("Массив с дубликатами: " + Arrays.toString(arrayWithDuplicates));
        
        List<Integer> allResults = jumpSearchAll(arrayWithDuplicates, 5);
        System.out.println("Найдено позиций: " + allResults.size());
        
        // Тест 7: Сравнение эффективности
        compareSearchMethods(sortedArray, 17);
        
        // Тест 8: Визуализация
        visualizeSearch(sortedArray, 17);
        
        // Тест 9: Поиск с пользовательским прыжком
        System.out.println("\n=== ТЕСТ 9: ПОИСК С ПОЛЬЗОВАТЕЛЬСКИМ ПРЫЖКОМ ===");
        int result9 = jumpSearchWithCustomStep(sortedArray, 17, 4);
        System.out.println("Результат: " + result9);
        
        // Тест 10: Поиск в маленьком массиве
        System.out.println("\n=== ТЕСТ 10: ПОИСК В МАЛЕНЬКОМ МАССИВЕ ===");
        int[] smallArray = {2, 4, 6, 8, 10};
        int result10 = jumpSearch(smallArray, 6);
        System.out.println("Результат: " + result10);
    }
}