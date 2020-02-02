import java.util.ArrayList;
import java.util.Arrays;

import BreezySwing.*;
import javax.swing.*;

public class InsertionSortGUI extends GBFrame {

	private JLabel addLabel = addLabel("Enter number to add:",1,1,1,1);
	private IntegerField numField = addIntegerField(0,1,2,1,1);
	
	private JButton addButton = addButton("Add Number",2,2,1,1);
	
	private JLabel listLabel = addLabel("Current numbers in list:",3,1,2,1);
	private JTextArea currentListArea = addTextArea("",4,1,2,1);
	
	private JMenuItem meanMI = addMenuItem("Compute","Mean");
	private JMenuItem medianMI = addMenuItem("Compute","Median");
	private JMenuItem modeMI = addMenuItem("Compute","Mode");
	
	
	private ArrayList<Integer> baseList;
	private ArrayList<Integer> sortedList;
	private final byte MAX_NUMS = 25;
	
	public void buttonClicked(JButton button) {
		if(button == addButton) {
			if(!numField.isValidNumber()) {
				messageBox("Please enter a valid number");
				return;
			}
			baseList.add(numField.getNumber());
			updateListArea();
			numField.requestFocus();
			numField.selectAll();
			if(baseList.size() >= MAX_NUMS) {
				addButton.setEnabled(false);
			}
			
			Sorter<Integer> s = new Sorter<Integer>(baseList);
			sortedList = s.getSortedList();
			
		}
	}
	
	public void menuItemSelected(JMenuItem menuItem){
		if(menuItem == meanMI) {
			messageBox("The mean is " + getAverage());
		}else if(menuItem == medianMI) {
			messageBox("The median is " + getMedian());
		}else if(menuItem == modeMI) {
			int mode;
			try {
				mode = getMode();
				messageBox("The mode is " + mode);
			}catch (NoModeException e) {
				messageBox("There is no mode");
			}
		}
	}
	
	private void updateListArea() {
		String listStr = "";
		for(Integer i : baseList) {
			listStr += i.toString() + ",";
		}
		listStr = listStr.substring(0, listStr.length()-1);
		currentListArea.setText(listStr);
	}

	private double getAverage() {
		double total = 0;
		for(Integer i : baseList) {
			total += i;
		}
		return total/(double)baseList.size();
	}
	
	private double getMedian() {
		if(baseList.size() % 2 == 0) {
			//even -> 2 nums at middle
			int num1 = baseList.get(baseList.size()/2);
			int num2 = baseList.get(baseList.size()/2-1);
			return (num1 + num2)/2.0;
		}else {
			//odd -> 1 num at middle
			return baseList.get(baseList.size()/2);
		}
	}
	
	//FIX ME
	private int getMode() throws NoModeException {
		int maxNum = sortedList.get(0);
		int lastNum = maxNum;
		int maxCount = 1;
		int count = maxCount;
		
		for(int i = 1; i < sortedList.size(); i++) {
			if(sortedList.get(i) == lastNum) {
				count++;
			}else {
				count = 1;
				lastNum = sortedList.get(i);
			}
			if(maxCount < count) {
				maxCount = count;
				maxNum = lastNum;
			}
		}
		
		if(maxCount == 1 && sortedList.size() > 1){
			throw new NoModeException("There is no mode in list");
		}
		
		return maxNum;
		
	}
	
	
	public static void main(String[] args) {
		new InsertionSortGUI();
	}
	
	public InsertionSortGUI() {
		baseList = new ArrayList<Integer>();
		sortedList = new ArrayList<Integer>();
		
		currentListArea.setEditable(false);
		
		setTitle("Insertion Sort");
		setSize(400,400);
		setVisible(true);
	}

}
