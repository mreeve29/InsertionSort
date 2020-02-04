import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

import BreezySwing.GBFrame;
import BreezySwing.IntegerField;

public class InsertionSortGUI extends GBFrame{

	private JLabel addLabel = addLabel("Enter number to add:",1,1,1,1);
	private IntegerField numField = addIntegerField(0,1,2,1,1);
	
	private JButton addButton = addButton("Add Number",2,2,1,1);
	
	private JLabel listLabel = addLabel("Current numbers in list:",3,1,2,1);
	private JTextArea currentListArea = addTextArea("",4,1,2,1);
	
	private JLabel sortedListLabel = addLabel("Sorted list:",5,1,2,1);
	private JTextArea sortedListArea = addTextArea("",6,1,2,1);
	
	private JMenuItem meanMI = addMenuItem("Compute","Mean");
	private JMenuItem medianMI = addMenuItem("Compute","Median");
	private JMenuItem modeMI = addMenuItem("Compute","Mode");
	private JMenuItem stdMI = addMenuItem("Compute","Standard Deviation");
	
	
	private ArrayList<Integer> baseList;
	private ArrayList<Integer> sortedList;
	private final byte MAX_NUMS = 25;
	
	public void buttonClicked(JButton button) {
		if(button == addButton) {
			addNumber(numField);
		}
	}
	
	public void menuItemSelected(JMenuItem menuItem){
		if(menuItem == meanMI) {
			messageBox("The mean is " + getAverage());
		}else if(menuItem == medianMI) {
			messageBox("The median is " + getMedian());
		}else if(menuItem == modeMI) {
			ArrayList<Integer> modes;
			try {
				modes = getModesNew();
				String str = "";
				for(int i : modes) {
					str += "" + i + " ";
				}
				messageBox("The mode is " + str);
			}catch (NoModeException e) {
				messageBox("There is no mode");
			}
		}else if (menuItem == stdMI) {
			messageBox("The standard deviation is " + getStandardDeviation());
		}
	}
	
	private void updateListArea() {
		String listStr = "";
		for(Integer i : baseList) {
			listStr += i.toString() + ",";
		}
		listStr = listStr.substring(0, listStr.length()-1);
		currentListArea.setText(listStr);
		
		String sortedStr = "";
		for (Integer i : new Sorter<Integer>(baseList).getSortedList()) {
			sortedStr += i.toString() + ",";
		}
		sortedStr = sortedStr.substring(0, sortedStr.length()-1);
		sortedListArea.setText(sortedStr);
		
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
	
	private ArrayList<Integer> getModes() throws NoModeException{
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(int i : sortedList) {
			map.put(i, 0);
		}
		for(int i : sortedList) {
			map.put(i, map.get(i)+1);
		}
		
		ArrayList<Integer> modes = new ArrayList<Integer>();
		
		int highestOcc = 0;
		
		for(Entry<Integer, Integer> i : map.entrySet()) {
			if(i.getValue() > highestOcc) {
				modes.clear();
				modes.add(i.getKey());
				highestOcc = i.getValue();
			}else if (i.getValue() == highestOcc) {
				modes.add(i.getKey());
			}
		}
		
		if(highestOcc == 1 && sortedList.size() >  1) {
			throw new NoModeException("No Mode in List");
		}
		
		return modes;
	}
	
	private ArrayList<Integer> getModesNew() throws NoModeException{
		ArrayList<Integer> key = new ArrayList<Integer>();
		ArrayList<Integer> value = new ArrayList<Integer>();
		
		for(int i : sortedList) {
			if(!key.contains(i)) {
				key.add(i);
				value.add(0);
			}
		}
		for(int i = 0; i < sortedList.size(); i++) {
			value.set(i, value.get(i) + 1);
		}
		
		ArrayList<Integer> modes = new ArrayList<Integer>();
		
		int highestOcc = 0;
		
		for(Integer i : value) {
			if(value.get(i) > highestOcc) {
				modes.clear();
				modes.add(key.get(i));
				highestOcc = value.get(i);
			}else if (value.get(i) == highestOcc) {
				modes.add(key.get(i));
			}
		}
		
		if(highestOcc == 1 && sortedList.size() >  1) {
			throw new NoModeException("No Mode in List");
		}
		
		return modes;
	}
	
	private double getStandardDeviation() {
		double mean = getAverage();
		
		double total = 0;
		for(int i : sortedList) {
			total += Math.pow(i - mean,2);
		}
		double secondMean = total / sortedList.size();
		
		return Math.sqrt(secondMean);
	}
	
	KeyListener kl = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {}
		
		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER && numField.hasFocus()) {
				addNumber(numField);
			}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {}
	};
	
	private void addNumber(IntegerField i) {
		if(!i.isValidNumber()) {
			messageBox("Please enter a valid number");
			i.requestFocus();
			i.selectAll();
			return;
		}
		baseList.add(i.getNumber());
		updateListArea();
		i.requestFocus();
		i.selectAll();
		if(baseList.size() >= MAX_NUMS) {
			addButton.setEnabled(false);
		}
		
		Sorter<Integer> s = new Sorter<Integer>(baseList);
		sortedList = s.getSortedList();
	}
	
	
	public static void main(String[] args) {
		new InsertionSortGUI();
	}
	
	public InsertionSortGUI() {
		baseList = new ArrayList<Integer>();
		sortedList = new ArrayList<Integer>();
		
		currentListArea.setEditable(false);
		
		numField.addKeyListener(kl);
		
		setTitle("Insertion Sort");
		setSize(400,400);
		setVisible(true);
	}
	
}
