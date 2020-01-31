import java.util.ArrayList;
import java.util.Arrays;

import BreezySwing.GBFrame;

public class InsertionSortGUI extends GBFrame {

	public static void main(String[] args) {
		ArrayList<Integer> arr = new ArrayList<Integer>();

		arr.add(-1);
		arr.add(0);
		arr.add(13);
		arr.add(8);
		arr.add(5);

		Sorter<Integer> s = new Sorter<Integer>(arr);
		
		for(Integer i : s.getList()) {
			System.out.println(i);
		}


	}

}
