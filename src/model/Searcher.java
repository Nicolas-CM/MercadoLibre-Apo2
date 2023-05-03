package model;

import java.util.ArrayList;

public class Searcher<K extends Comparable<K>> {

	public Searcher(){
		
	}

    public int binarySearchProduct(ArrayList<K> arr, K goal){
		int left = 0; // obtenemos una referencia al puntero inicial  
		int right = arr.size() - 1; // obtenemos una referencia al puntero final 
        while(left <= right){ // repetimos el siclo mientras estemos en el rango del arreglo  
			// calculamos el punto medio del arreglo 
			int mid = (right + left)/2;  // mid = left + (right - left)/2;

			// comparamos el elemento central con el valor objetivo 
			if(goal.compareTo(arr.get(mid)) <0){
				right = mid - 1;  
			}
			else if(goal.compareTo(arr.get(mid)) > 0){
				left = mid + 1; 
			}
			// si lo encontramos retornamos el elemento 
			else {
				return mid; 
			}
		}

		// si no retornamos -1
		return -1;
        
	}
}
