package model;

import java.util.ArrayList;

public class Searcher<K extends Comparable<K>> {

	public Searcher() {
	}

	/**
	 * This function performs a binary search on an array to find elements within a
	 * specified range and
	 * returns their indices in an ArrayList.
	 * 
	 * @param array an array of elements of type K, which is the type of the
	 *              elements being searched.
	 * @param first The first element of the range to search for in the array.
	 * @param last  The parameter "last" is a value of type K that represents the
	 *              upper bound of the range
	 *              to be searched in the binary search algorithm. It is used in the
	 *              method "binarySearchByRange" to
	 *              find the index of the last occurrence of the element in the
	 *              array that is less than or equal
	 * @return The method is returning an ArrayList of integers that represent the
	 *         indices of the elements
	 *         in the input array that fall within the range specified by the input
	 *         parameters "first" and "last".
	 *         If no elements are found within the range, the method returns null.
	 */
	public ArrayList<Integer> binarySearchByRange(K[] array, K first, K last) {
		int firstTemp = binarySearchRange(array, first, true);
		int lastTemp = binarySearchRange(array, last, false);
		if (firstTemp == -2) {
			firstTemp = -1;
		} else if (firstTemp == -1) {
			firstTemp = 0;
		}
		if (lastTemp == -1) {
			lastTemp = array.length - 1;
		}
		if (lastTemp == -2) {
			firstTemp = -1;
		}
		if (firstTemp != -1) {
			ArrayList<Integer> newArrayList = new ArrayList<>();
			for (int index = firstTemp; index <= lastTemp; index++) {
				newArrayList.add(index);
			}
			return newArrayList;
		} else {
			return null;
		}
	}

	/**
	 * This function performs a binary search on two arrays of a given type to find
	 * a range of values
	 * between a specified first and last value, and returns an ArrayList of the
	 * indices of the values
	 * found within that range.
	 * 
	 * @param array  An array of type K (generic type) that is sorted in ascending
	 *               order.
	 * @param array2 The second array to be searched for the range of values.
	 * @param first  The first element to search for in the range.
	 * @param last   The parameter "last" is a value of type K, which is the last
	 *               element of the range to be
	 *               searched in the binary search algorithm. It is used to
	 *               determine the upper bound of the range to be
	 *               searched in the "binarySearchStringRange" method.
	 * @return The method is returning an ArrayList of integers. If the input is
	 *         invalid, it returns null.
	 */
	public ArrayList<Integer> binarySearchStringRange(K[] array, K[] array2, K first, K last) {
		int firstTemp = binarySearchRange(array, first, true);
		int lastTemp = binarySearchRange(array2, last, false);
		if (firstTemp == -2) {
			firstTemp = -1;
		} else if (firstTemp == -1) {
			firstTemp = 0;
		}
		if (lastTemp == -1) {
			lastTemp = array.length - 1;
		}
		if (lastTemp == -2) {
			firstTemp = -1;
		}
		if (firstTemp != -1) {
			ArrayList<Integer> newArrayList = new ArrayList<>();
			for (int index = firstTemp; index <= lastTemp; index++) {
				newArrayList.add(index);
			}
			return newArrayList;
		} else {
			return null;
		}
	}

	/**
	 * This function performs a binary search on an array to find the index of a
	 * target value, and can
	 * also return the index of the lower or upper bound of the range of values
	 * equal to the target.
	 * 
	 * @param arr    an array of elements of type K, which is the type parameter of
	 *               the array
	 * @param target The value that we are searching for in the array.
	 * @param lower  A boolean value indicating whether to search for the lower
	 *               bound of the target range
	 *               (true) or the upper bound of the target range (false).
	 * @return The method returns an integer value that represents the index of the
	 *         target element in the
	 *         array. If the target element is not found in the array, the method
	 *         returns -1. If the target
	 *         element is outside the range of the array, the method returns -2.
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
	 * The `binarySearch` method is performing a binary search on an array of
	 * elements of type `K` to find the index of a target value `goal`. It returns
	 * the index of the target element in the array, or -1
	 * if the target element is not found in the array.
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
