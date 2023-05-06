package model;

import java.util.ArrayList;

public class Searcher<K extends Comparable<K>> {

	public Searcher() {
	}

	/**
	 * This function performs a binary search on an array and returns a new ArrayList containing elements
	 * within a specified range.
	 * 
	 * @param array The array parameter is an array of elements of type K that is sorted in ascending
	 * order.
	 * @param first The parameter "first" is a value of type K that represents the lower bound of the
	 * range to be searched for in the given array.
	 * @param last The parameter "last" is a value of type K that represents the upper bound of the range
	 * to be searched in the "array" parameter. The method will return an ArrayList containing all the
	 * elements in the "arrayList" parameter that fall within the range defined by the "first" and "last
	 * @return The method is returning an ArrayList of elements of type K that fall within the range
	 * specified by the first and last parameters.
	 */
	public ArrayList<K> binarySearchByRange( K[] array, K first, K last) {
		int firstFinal, lastFinal = 0;
		int firstTemp = binarySearch(array, first);
		if (array[firstTemp].compareTo(first) >= 0) {
			firstFinal = firstTemp;
			if (firstTemp > 0 && array[firstTemp - 1].compareTo(first) >= 0) {
				firstFinal = firstTemp - 1;
			}
		} else {
			firstFinal = firstTemp + 1;
		}
		int lastTemp = binarySearch(array, last);
		if (array[lastTemp].compareTo(last) <= 0) {
			lastFinal = lastTemp;
			if (lastTemp< array.length-1 && array[lastTemp - 1].compareTo(last) <= 0) {
				lastFinal = lastTemp + 1;
			} 
		} else {
			lastFinal = lastTemp - 1;
		}
		ArrayList<K> newArrayList = new ArrayList<>();
		for (int index = firstFinal; index < lastFinal; index++) {
			newArrayList.add(array[index]);
		}
		return newArrayList;
	}

	/**
	 * The binarySearch function searches for a specific element in a sorted array using the binary search
	 * algorithm.
	 * 
	 * @param arr an array of elements of type K that we want to search through using binary search
	 * algorithm.
	 * @param goal The value that we are searching for in the array. It is of type K, which means it can
	 * be any object that implements the Comparable interface.
	 * @return The method returns an integer value which represents the index of the element in the array
	 * that matches the search goal. If the element is not found, it returns -1.
	 */
	public int binarySearch(K[] arr, K goal) {
		int left = 0; // obtenemos una referencia al puntero inicial
		int right = arr.length - 1; // obtenemos una referencia al puntero final
		while (left <= right) { // repetimos el ciclo mientras estemos en el rango del arreglo
			// calculamos el punto medio del arreglo
			int mid = (right + left) / 2; // mid = left + (right - left)/2;

			// comparamos el elemento central con el valor objetivo
			if (goal.compareTo(arr[mid]) < 0) {
				right = mid - 1;
			} else if (goal.compareTo(arr[mid]) > 0) {
				left = mid + 1;
			}
			// si lo encontramos retornamos la posicion en el arreglo del elemento
			else {
				return mid;
			}
		}
		// si no retornamos -1
		return -1;
	}
}
