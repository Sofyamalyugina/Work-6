#include <iostream>
#include <vector>
#include <cmath>

using namespace std;

/**
 * Простая версия поиска скачками
 */
int jumpSearchSimple(const vector<int>& arr, int target) {
    int n = arr.size();
    if (n == 0) return -1;
    
    // Определяем размер прыжка
    int jumpSize = sqrt(n);
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

int main() {
    vector<int> numbers = {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25};
    int target = 15;
    
    cout << "Массив: ";
    for (int num : numbers) cout << num << " ";
    cout << endl;
    cout << "Ищем: " << target << endl;
    
    int result = jumpSearchSimple(numbers, target);
    
    if (result != -1) {
        cout << "Элемент найден на позиции " << result << endl;
    } else {
        cout << "Элемент не найден" << endl;
    }
    
    return 0;
}