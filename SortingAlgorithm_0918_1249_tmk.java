// 代码生成时间: 2025-09-18 12:49:26
package com.example.sorting;

import java.util.Arrays;
import java.util.Comparator;

public class SortingAlgorithm {
    // Bubble Sort
    public static void bubbleSort(int[] array) {
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < array.length; i++) {
                if (array[i - 1] > array[i]) {
                    // Swap elements
                    int temp = array[i - 1];
                    array[i - 1] = array[i];
                    array[i] = temp;
                    swapped = true;
                }
            }
        } while (swapped);
    }

    // Selection Sort
    public static void selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = array[minIndex];
                array[minIndex] = array[i];
                array[i] = temp;
            }
        }
    }

    // Insertion Sort
    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
    }

    // Merge Sort
    public static void mergeSort(int[] array) {
        if (array.length > 1) {
            mergeSort(array, 0, array.length - 1);
        }
    }

    private static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int middle = (left + right) / 2;
            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);
            merge(array, left, middle, right);
        }
    }

    private static void merge(int[] array, int left, int middle, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = middle + 1, k = 0;
        while (i <= middle && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i <= middle) {
            temp[k++] = array[i++];
        }
        while (j <= right) {
            temp[k++] = array[j++];
        }
        System.arraycopy(temp, 0, array, left, temp.length);
    }

    // Quick Sort
    public static void quickSort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(array, left, right);
            quickSort(array, left, pivotIndex - 1);
            quickSort(array, pivotIndex + 1, right);
        }
    }

    private static int partition(int[] array, int left, int right) {
        int pivot = array[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (array[j] < pivot) {
                i++;
                swap(array, i, j);
            }
        }
        swap(array, i + 1, right);
        return i + 1;
    }

    private static void swap(int[] array, int left, int right) {
        int temp = array[left];
        array[left] = array[right];
        array[right] = temp;
    }

    // Utility method to print the array
    public static void printArray(int[] array) {
        System.out.println(Arrays.toString(array));
    }

    // Main method for testing the sorting algorithms
    public static void main(String[] args) {
        try {
            int[] array = {64, 34, 25, 12, 22, 11, 90};
            System.out.println("Original array: ");
            printArray(array);

            // Test Bubble Sort
            bubbleSort(array.clone());
            System.out.println("Bubble Sort: ");
            printArray(array);

            // Test Selection Sort
            int[] selectionArray = array.clone();
            selectionSort(selectionArray);
            System.out.println("Selection Sort: ");
            printArray(selectionArray);

            // Test Insertion Sort
            int[] insertionArray = array.clone();
            insertionSort(insertionArray);
            System.out.println("Insertion Sort: ");
            printArray(insertionArray);

            // Test Merge Sort
            int[] mergeArray = array.clone();
            mergeSort(mergeArray);
            System.out.println("Merge Sort: ");
            printArray(mergeArray);

            // Test Quick Sort
            int[] quickArray = array.clone();
            quickSort(quickArray);
            System.out.println("Quick Sort: ");
            printArray(quickArray);

        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
}
