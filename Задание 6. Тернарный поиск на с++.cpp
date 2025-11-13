#include <iostream>
#include <vector>
#include <cmath>
#include <iomanip>

using namespace std;

/**
 * Тернарный поиск для нахождения позиции элемента в отсортированном массиве
 * @param arr отсортированный массив
 * @param target искомый элемент
 * @return индекс элемента или -1 если не найден
 */
int ternarySearch(const vector<int>& arr, int target) {
    int left = 0;
    int right = arr.size() - 1;
    
    // Пока есть область для поиска
    while (left <= right) {
        // Вычисляем две точки, делящие область на три равные части
        int mid1 = left + (right - left) / 3;
        int mid2 = right - (right - left) / 3;
        
        cout << "Диапазон: [" << left << ", " << right << "] "
             << "mid1: " << mid1 << " (" << arr[mid1] << "), "
             << "mid2: " << mid2 << " (" << arr[mid2] << ")" << endl;
        
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
int ternarySearchRecursive(const vector<int>& arr, int target, int left, int right) {
    // Базовый случай - границы пересеклись
    if (left > right) {
        return -1;
    }
    
    // Вычисляем точки деления
    int mid1 = left + (right - left) / 3;
    int mid2 = right - (right - left) / 3;
    
    cout << "Рекурсия - Диапазон: [" << left << ", " << right << "] "
         << "mid1: " << mid1 << ", mid2: " << mid2 << endl;
    
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
double ternarySearchMin(double (*func)(double), double left, double right, double eps = 1e-9) {
    // Пока интервал достаточно большой
    while (right - left > eps) {
        // Вычисляем две точки деления
        double mid1 = left + (right - left) / 3.0;
        double mid2 = right - (right - left) / 3.0;
        
        // Вычисляем значения функции в точках деления
        double f1 = func(mid1);
        double f2 = func(mid2);
        
        cout << fixed << setprecision(6)
             << "Интервал: [" << left << ", " << right << "] "
             << "mid1: " << mid1 << " (f=" << f1 << "), "
             << "mid2: " << mid2 << " (f=" << f2 << ")" << endl;
        
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

// Пример функции для поиска минимума
double exampleFunction(double x) {
    return (x - 2) * (x - 2) + 3; // Минимум в x = 2, f(2) = 3
}

// Другая тестовая функция
double anotherFunction(double x) {
    return -x * x + 4 * x + 1; // Максимум в x = 2, f(2) = 5
}

/**
 * Демонстрация работы тернарного поиска на массиве
 */
void demonstrateArraySearch() {
    vector<int> arr = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25};
    int target = 15;
    
    cout << "=== Тернарный поиск в массиве ===" << endl;
    cout << "Массив: ";
    for (int num : arr) {
        cout << num << " ";
    }
    cout << "\nИщем элемент: " << target << endl << endl;
    
    int result = ternarySearch(arr, target);
    
    if (result != -1) {
        cout << "\nЭлемент найден на позиции: " << result << endl;
    } else {
        cout << "\nЭлемент не найден" << endl;
    }
}

/**
 * Демонстрация рекурсивной версии
 */
void demonstrateRecursiveSearch() {
    vector<int> arr = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
    int target = 12;
    
    cout << "\n=== Рекурсивный тернарный поиск ===" << endl;
    cout << "Массив: ";
    for (int num : arr) {
        cout << num << " ";
    }
    cout << "\nИщем элемент: " << target << endl << endl;
    
    int result = ternarySearchRecursive(arr, target, 0, arr.size() - 1);
    
    if (result != -1) {
        cout << "\nЭлемент найден на позиции: " << result << endl;
    } else {
        cout << "\nЭлемент не найден" << endl;
    }
}

/**
 * Демонстрация поиска минимума функции
 */
void demonstrateFunctionMin() {
    cout << "\n=== Поиск минимума функции f(x) = (x-2)² + 3 ===" << endl;
    
    double minPoint = ternarySearchMin(exampleFunction, 0.0, 5.0, 1e-6);
    double minValue = exampleFunction(minPoint);
    
    cout << fixed << setprecision(6);
    cout << "\nНайден минимум в точке: " << minPoint << endl;
    cout << "Значение функции в минимуме: " << minValue << endl;
    cout << "Ожидаемый минимум: x = 2.0, f(x) = 3.0" << endl;
}

/**
 * Сравнение с бинарным поиском
 */
void compareWithBinarySearch() {
    vector<int> arr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
    int target = 7;
    
    cout << "\n=== Сравнение количества итераций ===" << endl;
    cout << "Массив из 15 элементов, ищем: " << target << endl;
    
    // Тернарный поиск
    cout << "\nТернарный поиск:" << endl;
    int result1 = ternarySearch(arr, target);
    
    // Бинарный поиск для сравнения
    cout << "\nБинарный поиск:" << endl;
    int left = 0, right = arr.size() - 1;
    int iterations = 0;
    
    while (left <= right) {
        iterations++;
        int mid = left + (right - left) / 2;
        
        cout << "Итерация " << iterations << ": [" << left << ", " << right 
             << "] mid: " << mid << " (" << arr[mid] << ")" << endl;
        
        if (arr[mid] == target) {
            cout << "Найден на позиции: " << mid << endl;
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
void testVariousCases() {
    cout << "\n=== Тестирование различных случаев ===" << endl;
    
    // Тест 1: Элемент в начале
    vector<int> arr1 = {5, 10, 15, 20, 25, 30};
    cout << "\nТест 1 - поиск первого элемента (5):" << endl;
    cout << "Результат: " << ternarySearch(arr1, 5) << " (ожидается: 0)" << endl;
    
    // Тест 2: Элемент в конце
    vector<int> arr2 = {1, 4, 7, 10, 13, 16, 19};
    cout << "\nТест 2 - поиск последнего элемента (19):" << endl;
    cout << "Результат: " << ternarySearch(arr2, 19) << " (ожидается: 6)" << endl;
    
    // Тест 3: Элемент в середине
    vector<int> arr3 = {2, 4, 6, 8, 10, 12, 14};
    cout << "\nТест 3 - поиск среднего элемента (8):" << endl;
    cout << "Результат: " << ternarySearch(arr3, 8) << " (ожидается: 3)" << endl;
    
    // Тест 4: Элемент не существует
    vector<int> arr4 = {1, 3, 5, 7, 9};
    cout << "\nТест 4 - поиск несуществующего элемента (4):" << endl;
    cout << "Результат: " << ternarySearch(arr4, 4) << " (ожидается: -1)" << endl;
    
    // Тест 5: Пустой массив
    vector<int> arr5 = {};
    cout << "\nТест 5 - поиск в пустом массиве:" << endl;
    cout << "Результат: " << ternarySearch(arr5, 5) << " (ожидается: -1)" << endl;
}

/**
 * Поиск максимума функции (для демонстрации)
 */
void demonstrateFunctionMax() {
    cout << "\n=== Поиск максимума функции f(x) = -x² + 4x + 1 ===" << endl;
    
    // Для поиска максимума просто инвертируем функцию
    auto invertedFunction = [](double x) { return -anotherFunction(x); };
    
    double maxPoint = ternarySearchMin(invertedFunction, 0.0, 4.0, 1e-6);
    double maxValue = anotherFunction(maxPoint);
    
    cout << fixed << setprecision(6);
    cout << "\nНайден максимум в точке: " << maxPoint << endl;
    cout << "Значение функции в максимуме: " << maxValue << endl;
    cout << "Ожидаемый максимум: x = 2.0, f(x) = 5.0" << endl;
}

int main() {
    cout << "=== ДЕМОНСТРАЦИЯ ТЕРНАРНОГО ПОИСКА ===" << endl;
    
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
    
    return 0;
}