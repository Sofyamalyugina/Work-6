#include <iostream>
#include <vector>
#include <algorithm>
#include <string>

using namespace std;

/**
 * Сортировка бусинами (градитационная сортировка)
 * Визуализирует процесс как падение бусин через вертикальные стержни
 * @param arr - вектор для сортировки
 */
void beadSort(vector<int>& arr) {
    if (arr.empty()) return;
    
    // Находим максимальный элемент для определения количества "уровней"
    int maxVal = *max_element(arr.begin(), arr.end());
    int n = arr.size();
    
    cout << "Начало сортировки бусинами:" << endl;
    cout << "Максимальное значение: " << maxVal << endl;
    cout << "Количество элементов: " << n << endl;
    
    // Создаем матрицу для представления бусин
    // matrix[i][j] = true означает, что на позиции i,j есть бусина
    vector<vector<bool>> beads(n, vector<bool>(maxVal, false));
    
    // 1. РАЗМЕЩЕНИЕ БУСИН - заполняем матрицу согласно исходному массиву
    cout << "\n1. Размещение бусин:" << endl;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < arr[i]; j++) {
            beads[i][j] = true;
        }
    }
    
    // Выводим начальное состояние
    cout << "Начальное расположение бусин:" << endl;
    for (int j = maxVal - 1; j >= 0; j--) {
        for (int i = 0; i < n; i++) {
            cout << (beads[i][j] ? "● " : "○ ");
        }
        cout << endl;
    }
    
    // 2. "ПАДЕНИЕ" БУСИН - гравитация заставляет бусины падать вниз
    cout << "\n2. Процесс падения бусин:" << endl;
    for (int j = 0; j < maxVal; j++) {
        int sum = 0;
        
        // Считаем количество бусин в текущем столбце
        for (int i = 0; i < n; i++) {
            if (beads[i][j]) {
                sum++;
                beads[i][j] = false; // временно убираем бусины
            }
        }
        
        // Размещаем бусины внизу столбца (эффект гравитации)
        for (int i = n - sum; i < n; i++) {
            beads[i][j] = true;
        }
        
        // Выводим промежуточное состояние
        if (j < 5) { // показываем только первые несколько шагов для наглядности
            cout << "Шаг " << j + 1 << ":" << endl;
            for (int level = maxVal - 1; level >= 0; level--) {
                for (int i = 0; i < n; i++) {
                    cout << (beads[i][level] ? "● " : "○ ");
                }
                cout << endl;
            }
            cout << endl;
        }
    }
    
    // 3. СБОР РЕЗУЛЬТАТА - подсчитываем бусины в каждой строке
    cout << "\n3. Финальное расположение бусин:" << endl;
    for (int j = maxVal - 1; j >= 0; j--) {
        for (int i = 0; i < n; i++) {
            cout << (beads[i][j] ? "● " : "○ ");
        }
        cout << endl;
    }
    
    // Преобразуем обратно в массив
    for (int i = 0; i < n; i++) {
        arr[i] = 0;
        for (int j = 0; j < maxVal; j++) {
            if (beads[i][j]) {
                arr[i]++;
            }
        }
    }
}

/**
 * Оптимизированная версия сортировки бусинами
 * Без визуализации, только алгоритм
 */
void beadSortOptimized(vector<int>& arr) {
    if (arr.empty()) return;
    
    int maxVal = *max_element(arr.begin(), arr.end());
    int n = arr.size();
    
    vector<vector<bool>> beads(n, vector<bool>(maxVal, false));
    
    // Размещаем бусины
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < arr[i]; j++) {
            beads[i][j] = true;
        }
    }
    
    // Применяем гравитацию
    for (int j = 0; j < maxVal; j++) {
        int count = 0;
        
        // Считаем бусины в столбце
        for (int i = 0; i < n; i++) {
            if (beads[i][j]) {
                count++;
            }
        }
        
        // Перемещаем бусины вниз
        for (int i = 0; i < n; i++) {
            beads[i][j] = (i >= n - count);
        }
    }
    
    // Собираем результат
    for (int i = 0; i < n; i++) {
        arr[i] = 0;
        for (int j = 0; j < maxVal; j++) {
            if (beads[i][j]) {
                arr[i]++;
            }
        }
    }
}

/**
 * Альтернативная реализация с использованием векторов
 * Более эффективное использование памяти
 */
void beadSortAlternative(vector<int>& arr) {
    if (arr.empty()) return;
    
    int maxVal = *max_element(arr.begin(), arr.end());
    int n = arr.size();
    
    // Вектор для подсчета бусин на каждом уровне
    vector<int> counts(maxVal, 0);
    
    // Подсчитываем бусины на каждом уровне
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < arr[i]; j++) {
            counts[j]++;
        }
    }
    
    // Восстанавливаем отсортированный массив
    for (int i = 0; i < n; i++) {
        arr[i] = 0;
        for (int j = 0; j < maxVal; j++) {
            if (counts[j] > i) {
                arr[i]++;
            }
        }
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
 * Визуализация процесса сортировки в упрощенном виде
 */
void visualizeBeadSort(const vector<int>& arr) {
    if (arr.empty()) return;
    
    int maxVal = *max_element(arr.begin(), arr.end());
    int n = arr.size();
    
    cout << "\nВизуализация сортировки:" << endl;
    cout << "Исходный массив: ";
    for (int num : arr) cout << num << " ";
    cout << endl;
    
    // Верхняя граница
    cout << "┌";
    for (int i = 0; i < n; i++) cout << "───";
    cout << "┐" << endl;
    
    // Бусины
    for (int level = maxVal; level >= 1; level--) {
        cout << "│";
        for (int i = 0; i < n; i++) {
            if (arr[i] >= level) {
                cout << " ● ";
            } else {
                cout << "   ";
            }
        }
        cout << "│ " << level << endl;
    }
    
    // Нижняя граница
    cout << "└";
    for (int i = 0; i < n; i++) cout << "───";
    cout << "┘" << endl;
    
    // Номера позиций
    cout << " ";
    for (int i = 0; i < n; i++) {
        cout << " " << i + 1 << " ";
    }
    cout << endl;
}

// Тестирование
int main() {
    cout << "=== ТЕСТ 1: Базовая сортировка бусинами ===" << endl;
    vector<int> test1 = {3, 1, 4, 1, 5, 2};
    printVector(test1, "Исходный массив");
    visualizeBeadSort(test1);
    
    beadSort(test1);
    printVector(test1, "Отсортированный массив");
    visualizeBeadSort(test1);
    
    cout << "\n=== ТЕСТ 2: Оптимизированная версия ===" << endl;
    vector<int> test2 = {5, 2, 7, 1, 3, 6, 4};
    printVector(test2, "Исходный массив");
    
    beadSortOptimized(test2);
    printVector(test2, "Отсортированный массив");
    
    cout << "\n=== ТЕСТ 3: Альтернативная реализация ===" << endl;
    vector<int> test3 = {2, 5, 1, 8, 3, 4, 7, 6};
    printVector(test3, "Исходный массив");
    
    beadSortAlternative(test3);
    printVector(test3, "Отсортированный массив");
    
    cout << "\n=== ТЕСТ 4: С дубликатами ===" << endl;
    vector<int> test4 = {3, 3, 2, 2, 1, 1, 4, 4};
    printVector(test4, "Исходный массив");
    
    beadSortOptimized(test4);
    printVector(test4, "Отсортированный массив");
    
    cout << "\n=== ТЕСТ 5: Один элемент ===" << endl;
    vector<int> test5 = {5};
    printVector(test5, "Исходный массив");
    
    beadSortOptimized(test5);
    printVector(test5, "Отсортированный массив");
    
    cout << "\n=== ТЕСТ 6: Уже отсортированный массив ===" << endl;
    vector<int> test6 = {1, 2, 3, 4, 5};
    printVector(test6, "Исходный массив");
    
    beadSortOptimized(test6);
    printVector(test6, "Отсортированный массив");
    
    return 0;
}