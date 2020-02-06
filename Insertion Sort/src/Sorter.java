import java.util.ArrayList;

public class Sorter<T extends Comparable<T>> {
	
	//class object
	private ArrayList<T> sorted;
	
	//constructor
	public Sorter(ArrayList<T> arr) {
		sorted = arr;
		sort();
	}
	
	
	//uses insertion sort to sort given array
	public void sort() {
		for(int i = 1; i < sorted.size(); i++) {
			T num = sorted.get(i);
			int pos = i;
			
			while(pos > 0 && sorted.get(pos-1).compareTo(num) > 0) {
				sorted.set(pos, sorted.get(pos-1));
				pos--;
			}
			
			sorted.set(pos, num);	
		}
	}
	
	//returns sorted array
	public ArrayList<T> getSortedList(){
		return sorted;
	}
	
}
