#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>

using namespace std;

/**
 * Блочная сортировка для чисел с плавающей запятой в диапазоне [0, 1)
 * @param arr - вектор для сортировки
 */
void bucketSort(vector<double>& arr) {
    // Проверка на пустой вектор
    if (arr.empty()) {
        return;
    }
    
    // 1. СОЗДАНИЕ КОРЗИН
    // Количество корзин обычно равно количеству элементов
    int numberOfBuckets = arr.size();
    
    // Создаем вектор корзин (каждая корзина - это вектор чисел)
    vector<vector<double>> buckets(numberOfBuckets);
    
    cout << "Создано " << numberOfBuckets << " корзин" << endl;
    
    // 2. РАСПРЕДЕЛЕНИЕ ЭЛЕМЕНТОВ ПО КОРЗИНАМ
    cout << "Распределение элементов по корзинам:" << endl;
    for (int i = 0; i < arr.size(); i++) {
        // Вычисляем индекс корзины для текущего элемента
        // Умножаем на количество корзин, чтобы получить индекс в диапазоне [0, numberOfBuckets-1]
        int bucketIndex = static_cast<int>(arr[i] * numberOfBuckets);
        
        // Добавляем элемент в соответствующую корзину
        buckets[bucketIndex].push_back(arr[i]);
        
        printf("Элемент %.2f добавлен в корзину %d\n", arr[i], bucketIndex);
    }
    
    // 3. СОРТИРОВКА КАЖДОЙ КОРЗИНЫ
    cout << "\nСортировка корзин:" << endl;
    for (int i = 0; i < numberOfBuckets; i++) {
        if (!buckets[i].empty()) {
            cout << "Корзина " << i << " до сортировки: ";
            for (double num : buckets[i]) {
                printf("%.2f ", num);
            }
            cout << endl;
            
            // Сортируем корзину используя стандартную сортировку
            sort(buckets[i].begin(), buckets[i].end());
            
            cout << "Корзина " << i << " после сортировки: ";
            for (double num : buckets[i]) {
                printf("%.2f ", num);
            }
            cout << endl;
        }
    }
    
    // 4. СБОРКА ОТСОРТИРОВАННОГО ВЕКТОРА
    int index = 0;
    cout << "\nСборка отсортированного вектора:" << endl;
    
    // Проходим по всем корзинам в порядке возрастания индексов
    for (int i = 0; i < numberOfBuckets; i++) {
        for (double value : buckets[i]) {
            arr[index++] = value;
            printf("Добавлен элемент %.2f из корзины %d\n", value, i);
        }
    }
}

/**
 * Универсальная блочная сортировка для произвольных целых чисел
 * @param arr - вектор целых чисел для сортировки
 */
void bucketSortForIntegers(vector<int>& arr) {
    if (arr.empty()) {
        return;
    }
    
    // Находим минимальное и максимальное значения
    int minValue = arr[0];
    int maxValue = arr[0];
    for (int i = 1; i < arr.size(); i++) {
        if (arr[i] < minValue) {
            minValue = arr[i];
        } else if (arr[i] > maxValue) {
            maxValue = arr[i];
        }
    }
    
    cout << "Минимальное значение: " << minValue << endl;
    cout << "Максимальное значение: " << maxValue << endl;
    
    // Вычисляем диапазон значений
    int range = maxValue - minValue + 1;
    
    // Определяем количество корзин (можно настроить в зависимости от данных)
    int numberOfBuckets = static_cast<int>(sqrt(arr.size()));
    if (numberOfBuckets == 0) numberOfBuckets = 1;
    
    cout << "Количество корзин: " << numberOfBuckets << endl;
    
    // Создаем корзины
    vector<vector<int>> buckets(numberOfBuckets);
    
    // Распределяем элементы по корзинам
    cout << "\nРаспределение элементов по корзинам:" << endl;
    for (int i = 0; i < arr.size(); i++) {
        // Вычисляем индекс корзины с учетом минимального значения
        int bucketIndex = ((arr[i] - minValue) * numberOfBuckets) / range;
        // Обеспечиваем, чтобы индекс был в допустимых пределах
        if (bucketIndex >= numberOfBuckets) {
            bucketIndex = numberOfBuckets - 1;
        }
        
        buckets[bucketIndex].push_back(arr[i]);
        cout << "Элемент " << arr[i] << " добавлен в корзину " << bucketIndex << endl;
    }
    
    // Сортируем каждую корзину
    cout << "\nСортировка корзин:" << endl;
    for (int i = 0; i < numberOfBuckets; i++) {
        if (!buckets[i].empty()) {
            cout << "Корзина " << i << " до сортировки: ";
            for (int num : buckets[i]) {
                cout << num << " ";
            }
            cout << endl;
            
            sort(buckets[i].begin(), buckets[i].end());
            
            cout << "Корзина " << i << " после сортировки: ";
            for (int num : buckets[i]) {
                cout << num << " ";
            }
            cout << endl;
        }
    }
    
    // Собираем отсортированный вектор
    int index = 0;
    for (int i = 0; i < numberOfBuckets; i++) {
        for (int value : buckets[i]) {
            arr[index++] = value;
        }
    }
}

/**
 * Вспомогательная функция для вывода вектора
 */
template<typename T>
void printVector(const vector<T>& arr, const string& message) {
    cout << message << ": ";
    for (const T& num : arr) {
        cout << num << " ";
    }
    cout << endl;
}

/**
 * Вспомогательная функция для вывода вектора double с форматированием
 */
void printDoubleVector(const vector<double>& arr, const string& message) {
    cout << message << ": ";
    for (double num : arr) {
        printf("%.2f ", num);
    }
    cout << endl;
}

// Тестирование
int main() {
    cout << "=== ТЕСТ 1: Сортировка дробных чисел [0, 1) ===" << endl;
    vector<double> testArray1 = {0.42, 0.32, 0.33, 0.52, 0.37, 0.47, 0.51, 0.12, 0.89, 0.75};
    printDoubleVector(testArray1, "Исходный вектор");
    
    bucketSort(testArray1);
    printDoubleVector(testArray1, "Отсортированный вектор");
    
    cout << "\n=== ТЕСТ 2: Сортировка целых чисел ===" << endl;
    vector<int> testArray2 = {45, 12, 67, 23, 89, 34, 78, 56, 91, 23};
    printVector(testArray2, "Исходный вектор");
    
    bucketSortForIntegers(testArray2);
    printVector(testArray2, "Отсортированный вектор");
    
    cout << "\n=== ТЕСТ 3: Сортировка с отрицательными числами ===" << endl;
    vector<int> testArray3 = {-5, 12, -3, 0, 7, -8, 15, -1, 4};
    printVector(testArray3, "Исходный вектор");
    
    bucketSortForIntegers(testArray3);
    printVector(testArray3, "Отсортированный вектор");
    
    cout << "\n=== ТЕСТ 4: Сортировка с дубликатами ===" << endl;
    vector<double> testArray4 = {0.25, 0.75, 0.25, 0.50, 0.75, 0.10, 0.90};
    printDoubleVector(testArray4, "Исходный вектор");
    
    bucketSort(testArray4);
    printDoubleVector(testArray4, "Отсортированный вектор");
    
    return 0;
}
