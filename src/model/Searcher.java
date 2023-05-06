package model;

import java.util.ArrayList;

public class Searcher<K extends Comparable<K>> {

	public Searcher() {
	}

	/**
	 * This function performs a binary search on an array and returns a new
	 * ArrayList containing elements
	 * within a specified range.
	 * 
	 * @param array The array parameter is an array of elements of type K that is
	 *              sorted in ascending
	 *              order.
	 * @param first The parameter "first" is a value of type K that represents the
	 *              lower bound of the
	 *              range to be searched for in the given array.
	 * @param last  The parameter "last" is a value of type K that represents the
	 *              upper bound of the range
	 *              to be searched in the "array" parameter. The method will return
	 *              an ArrayList containing all the
	 *              elements in the "arrayList" parameter that fall within the range
	 *              defined by the "first" and "last
	 * @return The method is returning an ArrayList of elements of type K that fall
	 *         within the range
	 *         specified by the first and last parameters.
	 */
	public ArrayList<K> binarySearchByRange(K[] array, K first, K last) {
		int firstTemp = binarySearch(array, first, true);
		int lastTemp = binarySearch(array, last, false);

		ArrayList<K> newArrayList = new ArrayList<>();
		for (int index = firstTemp; index < lastTemp; index++) {
			newArrayList.add(array[index]);
		}
		return newArrayList;
	}

	/**
	 * The binarySearch function searches for a specific element in a sorted array
	 * using the binary search
	 * algorithm.
	 * 
	 * @param arr  an array of elements of type K that we want to search through
	 *             using binary search
	 *             algorithm.
	 * @param goal The value that we are searching for in the array. It is of type
	 *             K, which means it can
	 *             be any object that implements the Comparable interface.
	 * @return The method returns an integer value which represents the index of the
	 *         element in the array
	 *         that matches the search goal. If the element is not found, it returns
	 *         -1.
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

	public int binarySearch(K[] arr, K target, boolean lower) {
		int left = 0;
		int right = arr.length - 1;
		int index = -1;

		while (left <= right) {
			int mid = (left + right) / 2;

			if (arr[mid].compareTo(target) == 0) {
				index = mid;
				if (lower) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			} else if (arr[mid].compareTo(target) < 0) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}
		}

		// Si no se encontró el valor exacto, ajustar el índice según el límite
		if (index == -1) {
			if (lower) {
				index = left;
			} else {
				index = right;
			}
		}

		return index;
	}
}
