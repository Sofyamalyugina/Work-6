#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

/**
 * Переворачивает часть вектора от начала до указанного индекса
 * @param arr - вектор для переворота
 * @param index - индекс, до которого переворачиваем (включительно)
 */
void flip(vector<int>& arr, int index) {
    int start = 0;
    while (start < index) {
        swap(arr[start], arr[index]);
        start++;
        index--;
    }
}

/**
 * Находит индекс максимального элемента в части вектора
 * @param arr - вектор
 * @param n - размер части вектора для поиска
 * @return индекс максимального элемента
 */
int findMaxIndex(const vector<int>& arr, int n) {
    int maxIndex = 0;
    for (int i = 1; i < n; i++) {
        if (arr[i] > arr[maxIndex]) {
            maxIndex = i;
        }
    }
    return maxIndex;
}

/**
 * Блинная сортировка
 * @param arr - вектор для сортировки
 */
void pancakeSort(vector<int>& arr) {
    int n = arr.size();
    
    // Начинаем с полного размера вектора и постепенно уменьшаем
    for (int currentSize = n; currentSize > 1; currentSize--) {
        // Находим индекс максимального элемента в несортированной части
        int maxIndex = findMaxIndex(arr, currentSize);
        
        // Если максимальный элемент не на своем месте (не в конце текущей части)
        if (maxIndex != currentSize - 1) {
            cout << "Максимальный элемент: " << arr[maxIndex] 
                 << " на позиции: " << maxIndex << endl;
            
            // Если максимальный элемент не в начале, переворачиваем до него
            if (maxIndex != 0) {
                cout << "Переворот до максимального элемента: ";
                flip(arr, maxIndex);
                
                // Выводим промежуточное состояние
                for (int num : arr) cout << num << " ";
                cout << endl;
            }
            
            // Переворачиваем всю текущую часть, чтобы поставить максимальный элемент на правильное место
            cout << "Переворот всей текущей части: ";
            flip(arr, currentSize - 1);
            
            // Выводим промежуточное состояние
            for (int num : arr) cout << num << " ";
            cout << endl;
        }
        
        cout << "Текущий размер для сортировки: " << currentSize - 1 << endl;
        cout << "------------------------" << endl;
    }
}

/**
 * Вспомогательная функция для вывода вектора
 */
void printVector(const vector<int>& arr, const string& message) {
    cout << message << ": ";
    for (int num : arr) {
        cout << num << " ";
    }
    cout << endl;
}

/**
 * Упрощенная версия блинной сортировки (без подробного вывода)
 */
void simplePancakeSort(vector<int>& arr) {
    int n = arr.size();
    
    for (int size = n; size > 1; size--) {
        // Находим индекс максимального элемента
        int maxIndex = 0;
        for (int i = 1; i < size; i++) {
            if (arr[i] > arr[maxIndex]) {
                maxIndex = i;
            }
        }
        
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
 */
void pancakeSortRecursive(vector<int>& arr, int n) {
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

// Тестирование
int main() {
    cout << "=== ТЕСТ 1: Демонстрация работы алгоритма ===" << endl;
    vector<int> testArray1 = {23, 10, 20, 11, 12, 6, 7};
    printVector(testArray1, "Исходный вектор");
    
    pancakeSort(testArray1);
    printVector(testArray1, "Отсортированный вектор");
    
    cout << "\n=== ТЕСТ 2: Простая версия ===" << endl;
    vector<int> testArray2 = {64, 34, 25, 12, 22, 11, 90};
    printVector(testArray2, "Исходный вектор");
    
    simplePancakeSort(testArray2);
    printVector(testArray2, "Отсортированный вектор");
    
    cout << "\n=== ТЕСТ 3: Рекурсивная версия ===" << endl;
    vector<int> testArray3 = {3, 6, 1, 8, 4, 5};
    printVector(testArray3, "Исходный вектор");
    
    pancakeSortRecursive(testArray3, testArray3.size());
    printVector(testArray3, "Отсортированный вектор");
    
    cout << "\n=== ТЕСТ 4: Уже отсортированный массив ===" << endl;
    vector<int> testArray4 = {1, 2, 3, 4, 5};
    printVector(testArray4, "Исходный вектор");
    
    simplePancakeSort(testArray4);
    printVector(testArray4, "Отсортированный вектор");
    
    cout << "\n=== ТЕСТ 5: Массив в обратном порядке ===" << endl;
    vector<int> testArray5 = {5, 4, 3, 2, 1};
    printVector(testArray5, "Исходный вектор");
    
    simplePancakeSort(testArray5);
    printVector(testArray5, "Отсортированный вектор");
    
    return 0;
}