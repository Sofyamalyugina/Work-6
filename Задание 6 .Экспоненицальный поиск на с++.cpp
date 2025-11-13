#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

// Бинарный поиск - вспомогательная функция для экспоненциального поиска
int binarySearch(const vector<int>& arr, int left, int right, int target) {
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        // Если элемент найден в середине
        if (arr[mid] == target)
            return mid;
        
        // Если искомый элемент меньше среднего, ищем в левой половине
        if (arr[mid] > target)
            right = mid - 1;
        // Иначе ищем в правой половине
        else
            left = mid + 1;
    }
    
    // Элемент не найден
    return -1;
}

// Экспоненциальный поиск
int exponentialSearch(const vector<int>& arr, int target) {
    int n = arr.size();
    
    // Если массив пустой
    if (n == 0)
        return -1;
    
    // Если искомый элемент - первый элемент массива
    if (arr[0] == target)
        return 0;
    
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
    int right = min(i, n - 1);
    
    // Выполняем бинарный поиск в найденном диапазоне
    return binarySearch(arr, left, right, target);
}

// Функция для демонстрации работы алгоритма
void demonstrateExponentialSearch() {
    vector<int> arr = {2, 3, 4, 10, 15, 18, 20, 23, 25, 30, 35, 40, 45, 50};
    int target = 23;
    
    cout << "Массив: ";
    for (int num : arr) {
        cout << num << " ";
    }
    cout << "\nИщем элемент: " << target << endl;
    
    int result = exponentialSearch(arr, target);
    
    if (result != -1) {
        cout << "Элемент найден на позиции: " << result << endl;
    } else {
        cout << "Элемент не найден в массиве" << endl;
    }
}

// Функция для тестирования различных случаев
void testExponentialSearch() {
    vector<vector<int>> testCases = {
        {1, 3, 5, 7, 9, 11, 13, 15},           // обычный случай
        {2, 4, 6, 8, 10},                      // четные числа
        {5},                                    // один элемент
        {},                                     // пустой массив
        {10, 20, 30, 40, 50, 60, 70, 80, 90}   // больший массив
    };
    
    vector<int> targets = {7, 6, 5, 1, 90};
    
    for (int i = 0; i < testCases.size(); i++) {
        cout << "\nТест " << i + 1 << ":" << endl;
        cout << "Массив: ";
        for (int num : testCases[i]) {
            cout << num << " ";
        }
        cout << "\nИщем: " << targets[i] << endl;
        
        int result = exponentialSearch(testCases[i], targets[i]);
        
        if (result != -1) {
            cout << "Найден на позиции: " << result << endl;
        } else {
            cout << "Не найден" << endl;
        }
    }
}

int main() {
    cout << "=== Демонстрация экспоненциального поиска ===" << endl;
    demonstrateExponentialSearch();
    
    cout << "\n=== Тестирование различных случаев ===" << endl;
    testExponentialSearch();
    
    return 0;
}