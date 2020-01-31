import java.util.ArrayList;
import java.util.Arrays;

import BreezySwing.GBFrame;

public class InsertionSortGUI extends GBFrame{

	
	
	public static void main(String[] args) {
		int ar[] = {-3, 1, -4, 10, 3, 8, 5};
		ArrayList<Integer> arr = new ArrayList<Integer>();
		
		arr.add(-1);
		arr.add(0);
		arr.add(13);
		arr.add(8);
		arr.add(5);
		
		Sorter s = new Sorter();
		
		arr = s.realSort(arr);
		
		for(Integer i : arr) {
			System.out.println(i);
		}
		
		

	}

}
