import java.util.ArrayList;

public class Sorter<T extends Comparable<T>> {
	private ArrayList<T> sorted;
	
	public Sorter(ArrayList<T> arr) {
		sorted = arr;
		sort();
	}
	
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
	
	public ArrayList<T> getSortedList(){
		return sorted;
	}
	
}
