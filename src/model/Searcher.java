package model;

import java.util.ArrayList;

public class Searcher<K extends Comparable<K>> {

	public Searcher() {
	}

	public ArrayList<K> binarySearchByRange(ArrayList<K> arrayList, K[] array, K first ,K last){
		
		int firstF= binarySearch(array, first);

		int lastT= binarySearch(array, last);
		ArrayList<K> newArrayList= new ArrayList<>();
		for (int index = 0; index < array.length; index++) {
			newArrayList.add(array[index]);
		}
		return newArrayList;
	}

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
