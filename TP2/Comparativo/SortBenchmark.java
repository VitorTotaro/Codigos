import java.util.Random;

class SortMetrics {
    public long comparisons = 0;
    public long movements = 0;
}

public class SortBenchmark {

    public static void selectionSort(int[] array, SortMetrics metrics) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                metrics.comparisons++;
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }
            if (i != minIdx) {
                swap(array, i, minIdx, metrics);
            }
        }
    }

    public static void insertionSort(int[] array, SortMetrics metrics) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0) {
                metrics.comparisons++;
                if (array[j] > key) {
                    array[j + 1] = array[j];
                    metrics.movements++;
                    j--;
                } else {
                    break;
                }
            }
            array[j + 1] = key;
            metrics.movements++;
        }
    }

    public static void bubbleSort(int[] array, SortMetrics metrics) {
        int n = array.length;
        boolean swapped;
        for (int i = 0; i < n - 1; i++) {
            swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                metrics.comparisons++;
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1, metrics);
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
    }

    public static void quicksort(int[] array, int low, int high, SortMetrics metrics) {
        if (low < high) {
            int pi = partition(array, low, high, metrics);
            quicksort(array, low, pi - 1, metrics);
            quicksort(array, pi + 1, high, metrics);
        }
    }

    private static int partition(int[] array, int low, int high, SortMetrics metrics) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            metrics.comparisons++;
            if (array[j] < pivot) {
                i++;
                swap(array, i, j, metrics);
            }
        }
        swap(array, i + 1, high, metrics);
        return i + 1;
    }

    private static void swap(int[] array, int i, int j, SortMetrics metrics) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        metrics.movements += 3; // cada swap envolve 3 atribuições
    }

    public static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(100000);
        }
        return array;
    }


    public static void main(String[] args) {
        int[] sizes = {100, 1000, 10000, 100000};

        for (int size : sizes) {
            int[] original = generateRandomArray(size);

            System.out.println("\n--- Tamanho do vetor: " + size + " ---");

            runBenchmark("Selection Sort", original, SortBenchmark::selectionSort);
            runBenchmark("Insertion Sort", original, SortBenchmark::insertionSort);
            runBenchmark("Bubble Sort", original, SortBenchmark::bubbleSort);
            runBenchmark("Quicksort", original, (arr, metrics) -> quicksort(arr, 0, arr.length - 1, metrics));
        }
    }

    interface SortAlgorithm {
        void sort(int[] array, SortMetrics metrics);
    }

    public static void runBenchmark(String name, int[] original, SortAlgorithm algorithm) {
        int[] array = original.clone();
        SortMetrics metrics = new SortMetrics();
        long start = System.currentTimeMillis();
        algorithm.sort(array, metrics);
        long end = System.currentTimeMillis();
        System.out.printf("%s: Tempo = %dms, Comparações = %d, Movimentações = %d%n",
                name, (end - start), metrics.comparisons, metrics.movements);
    }
}

