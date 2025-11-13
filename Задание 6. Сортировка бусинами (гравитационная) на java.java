import java.util.Arrays;

public class Main {
    
    /**
     * Простая и эффективная версия сортировки бусинами
     */
    public static void beadSort(int[] arr) {
        if (arr == null || arr.length == 0) return;
        
        // Находим максимальный элемент
        int maxVal = arr[0];
        for (int num : arr) {
            if (num > maxVal) maxVal = num;
        }
        
        int n = arr.length;
        
        // Создаем и заполняем матрицу бусин
        boolean[][] beads = new boolean[n][maxVal];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < arr[i]; j++) {
                beads[i][j] = true;
            }
        }
        
        // Применяем гравитацию - бусины падают вниз
        for (int level = 0; level < maxVal; level++) {
            int beadCount = 0;
            
            // Подсчитываем бусины в столбце
            for (int column = 0; column < n; column++) {
                if (beads[column][level]) {
                    beadCount++;
                    beads[column][level] = false;
                }
            }
            
            // Размещаем бусины внизу
            for (int column = n - beadCount; column < n; column++) {
                beads[column][level] = true;
            }
        }
        
        // Подсчитываем бусины в каждой строке
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
     * Визуализация массива в простом виде
     */
    public static void visualizeSimple(int[] arr, String title) {
        System.out.println("\n" + title);
        System.out.println("Массив: " + Arrays.toString(arr));
        
        int maxVal = 0;
        for (int num : arr) {
            if (num > maxVal) maxVal = num;
        }
        
        for (int level = maxVal; level >= 1; level--) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] >= level) {
                    System.out.print(" * ");
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println(" | " + level);
        }
        
        for (int i = 0; i < arr.length; i++) {
            System.out.print("---");
        }
        System.out.println();
        
        for (int i = 0; i < arr.length; i++) {
            System.out.print(" " + (i + 1) + " ");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        int[] numbers = {3, 1, 4, 1, 5, 2};
        
        System.out.println("До сортировки: " + Arrays.toString(numbers));
        visualizeSimple(numbers, "До сортировки");
        
        beadSort(numbers);
        
        System.out.println("\nПосле сортировки: " + Arrays.toString(numbers));
        visualizeSimple(numbers, "После сортировки");
    }
}