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
	private void sort() {
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
	
	//finds the mode(s)
	public static ArrayList<Integer> getModes(ArrayList<Integer> baseList) throws NoModeException{
		ArrayList<Integer> key = new ArrayList<Integer>();
		ArrayList<Integer> value = new ArrayList<Integer>();
		
		for(int i : baseList) {
			if(!key.contains(i)) {
				key.add(i);
				value.add(0);
			}
		}
		for(int i : baseList) {
			int current = key.indexOf(i);
			value.set(current, value.get(current) + 1);
		}
		
		ArrayList<Integer> modes = new ArrayList<Integer>();
		
		int highestOcc = 0;
		
		for(int i = 0; i < value.size(); i++) {
			if(value.get(i) > highestOcc) {
				modes.clear();
				modes.add(key.get(i));
				highestOcc = value.get(i);
			}else if (value.get(i) == highestOcc) {
				modes.add(key.get(i));
			}
		}
		
		if(highestOcc == 1 && baseList.size() >  1) {
			throw new NoModeException("No Mode in List");
		}
		
		return modes;
	}
	
	//finds the median
	public static double getMedian(ArrayList<Integer> sorted) {
		if(sorted.size() % 2 == 0) {
			//even -> 2 nums at middle
			int num1 = sorted.get(sorted.size()/2);
			int num2 = sorted.get(sorted.size()/2-1);
			return (num1 + num2)/2.0;
		}else {
			//odd -> 1 num at middle
			return sorted.get(sorted.size()/2);
		}
	}
	
	//calculates average
	public static double getAverage(ArrayList<Integer> baseList) {
		double total = 0;
		for(Integer i : baseList) {
			total += i;
		}
		return total/(double)baseList.size();
	}
	
	//calculates standard deviation
	public static double getStandardDeviation(ArrayList<Integer> baseList) {
		double mean = getAverage(baseList);
		
		double total = 0;
		for(int i : baseList) {
			total += Math.pow(i - mean,2);
		}
		double secondMean = total / baseList.size();
		
		return Math.sqrt(secondMean);
	}
	
}
