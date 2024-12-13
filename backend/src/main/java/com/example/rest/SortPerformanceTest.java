package com.example.rest;

import com.example.controller.tda.LinkedList;
import java.util.Random;


public class SortPerformanceTest {

    public static void main(String[] args) {
        // Definir tamaños de las listas
        int[] sizes = {10000, 20000, 25000};

        // Imprimir cabecera de la tabla
        System.out.printf("%-15s%-20s%-20s%-20s%n", "Tamaño", "QuickSort (ms)", "MergeSort (ms)", "ShellSort (ms)");

        // Probar para cada tamaño
        for (int size : sizes) {
            System.out.printf("%-15d", size);

            // Generar lista aleatoria
            LinkedList<Integer> randomList = generateRandomLinkedList(size);

            // Medir y mostrar tiempos de ejecución para cada método
            long quickSortTime = measureExecutionTime(randomList, "quick", size);
            long mergeSortTime = measureExecutionTime(randomList, "merge", size);
            long shellSortTime = measureExecutionTime(randomList, "shell", size);

            // Imprimir los tiempos en la tabla
            System.out.printf("%-20d%-20d%-20d%n", quickSortTime, mergeSortTime, shellSortTime);
        }
    }

    // Método para generar una LinkedList de números aleatorios
    public static LinkedList<Integer> generateRandomLinkedList(int size) {
        LinkedList<Integer> list = new LinkedList<>();
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            list.add(rand.nextInt(100000)); // Números aleatorios entre 0 y 100000
        }
        return list;
    }

    // Medir el tiempo de ejecución de un método de ordenación
    public static long measureExecutionTime(LinkedList<Integer> originalList, String method, int size) {
        // Hacer una copia de la lista original
        LinkedList<Integer> listCopy = generateRandomLinkedList(size);

        // Medir el tiempo antes de la ordenación
        long startTime = System.nanoTime();

        // Ordenar según el método especificado
        switch (method) {
            case "quick":
                quickSort(listCopy);
                break;
            case "merge":
                mergeSort(listCopy);
                break;
            case "shell":
                shellSort(listCopy);
                break;
        }

        // Medir el tiempo después de la ordenación
        long endTime = System.nanoTime();

        // Calcular el tiempo de ejecución en milisegundos
        return (endTime - startTime) / 1000000; // en milisegundos
    }

    // QuickSort
    public static void quickSort(LinkedList<Integer> list) {
        Integer[] array = list.toArray(new Integer[0]);
        list.quickSort(array, 0, array.length - 1, LinkedList.ASC); // Utilizando el método QuickSort de LinkedList
        list.toList(array); // Actualizar la lista
    }

    // MergeSort
    public static void mergeSort(LinkedList<Integer> list) {
        Integer[] array = list.toArray(new Integer[0]);
        list.mergeSort(array, 0, array.length - 1, LinkedList.ASC); // Utilizando el método MergeSort de LinkedList
        list.toList(array); // Actualizar la lista
    }

    // ShellSort
    public static void shellSort(LinkedList<Integer> list) {
        Integer[] array = list.toArray(new Integer[0]);
        list.shellSort(array, LinkedList.ASC); // Utilizando el método ShellSort de LinkedList
        list.toList(array); // Actualizar la lista
    }
}
