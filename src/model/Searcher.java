package model;

import // `java.util.ArrayList` is a class that provides a resizable array implementation in Java. It
// is used in this code to create a new ArrayList that will contain the elements within a
// specified range.
java.util.ArrayList;

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
	public ArrayList<Integer> binarySearchByRange(K[] array, K first, K last) {
		int firstTemp = binarySearchRange(array, first, true);
		int lastTemp = binarySearchRange(array, last, false);
		if (firstTemp ==-2) {
			firstTemp = -1;
		}else if (firstTemp == -1) {
			firstTemp = 0;
		}
		if(lastTemp == -1){
			lastTemp = array.length-1;
		}
		if (lastTemp == -2) {
			firstTemp = -1;
		}
		if (firstTemp != -1 ) {
			ArrayList<Integer> newArrayList = new ArrayList<>();
			for (int index = firstTemp; index <= lastTemp; index++) {
				newArrayList.add(index);
			}
			return newArrayList;
		}else{
			return null;
		}
	}

	public ArrayList<Integer> binarySearchStringRange(K[] array, K[] array2, K first, K last) {
		int firstTemp = binarySearchRange(array, first, true);
		int lastTemp = binarySearchRange(array2, last, false);
		if (firstTemp ==-2) {
			firstTemp = -1;
		}else if (firstTemp == -1) {
			firstTemp = 0;
		}
		if(lastTemp == -1){
			lastTemp = array.length-1;
		}
		if (lastTemp == -2) {
			firstTemp = -1;
		}
		if (firstTemp != -1 ) {
			ArrayList<Integer> newArrayList = new ArrayList<>();
			for (int index = firstTemp; index <= lastTemp; index++) {
				newArrayList.add(index);
			}
			return newArrayList;
		}else{
			return null;
		}
	}

	/**
	 * This is a Java function that performs binary search on an array to find the index of a target
	 * value, and can optionally return the index of the first or last occurrence of the target value
	 * depending on a boolean parameter.
	 * 
	 * @param arr an array of elements of type K, which is the type parameter of the array
	 * @param target The value that we are searching for in the array.
	 * @param lower A boolean parameter that determines whether the binary search should return the index
	 * of the first occurrence of the target value (if it exists) or the index of the last occurrence of
	 * the target value (if it exists). If lower is true, the method will return the index of the first
	 * occurrence (i.e
	 * @return The method returns an integer representing the index of the target element in the array if
	 * it is found, or the index of the closest element to the target based on the specified range (lower
	 * or upper). If the target is outside the range of the array, it returns -1.
	 */
	public int binarySearchRange(K[] arr, K target, boolean lower) {
		int left = 0;
		int right = arr.length - 1;
		int index = -1;

		// Verificar si el valor objetivo está fuera del rango del arreglo
		if (lower && (target.compareTo(arr[right]) > 0)) {
			return -2;
		}
		if (!lower && target.compareTo(arr[left]) < 0) {
			return -2;
		}
		if (target.compareTo(arr[left]) < 0 || target.compareTo(arr[right]) > 0) {
			return -1;
		}

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
}
