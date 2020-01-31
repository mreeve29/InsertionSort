import java.util.ArrayList;

public class Sorter {
	private ArrayList<Integer> sorted;
	
	public Sorter() {
		sorted = new ArrayList<Integer>();
	}
	
	public ArrayList<Integer> sort(ArrayList<Integer> arr) {
		for(int i = 1; i < arr.size(); i++) {
			int num = arr.get(i);
			int pos = i;
			
			while(pos > 0 && arr.get(pos-1) > num) {
				arr.set(pos, arr.get(pos-1));
				pos--;
			}
			
			arr.set(pos, num);
			
			
		}
		return arr;
		
	}
	
	public ArrayList<Integer> realSort(ArrayList<Integer> arr){
		ArrayList<Integer> sorted = new ArrayList<Integer>();
		if(arr.size() == 0)return sorted;
		sorted.add(arr.get(0));
		
		for(int i = 1; i < arr.size(); i++) {
			int num = arr.get(i);
			
		}
		
		
		return sorted;
	}
	
	
	
	
}
