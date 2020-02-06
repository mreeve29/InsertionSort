import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
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

	
	//class objects
	//swing elements
	private JLabel addLabel = addLabel("Enter number to add(press enter key to add):",1,1,1,1);
	private IntegerField numField = addIntegerField(0,1,2,1,1);
	
	private JButton addButton = addButton("Add Number",2,2,1,1);
	
	private JLabel listLabel = addLabel("Current numbers in list:",3,1,2,1);
	private JTextArea currentListArea = addTextArea("",4,1,2,1);
	
	private JButton quitButton = addButton("Quit",5,2,1,1);
	
	private JMenuItem resetMI = addMenuItem("File","Reset");
	
	private JMenuItem sortMI = addMenuItem("View","Sorted List");
	
	private JMenuItem meanMI = addMenuItem("Compute","Mean");
	private JMenuItem medianMI = addMenuItem("Compute","Median");
	private JMenuItem modeMI = addMenuItem("Compute","Mode");
	private JMenuItem stdMI = addMenuItem("Compute","Standard Deviation");
	
	
	private ArrayList<Integer> baseList;
	private final byte MAX_NUMS = 25;//max number of numbers that can be entered
	
	//button event listener
	public void buttonClicked(JButton button) {
		if(button == addButton) {
			addNumber(numField);
		}else if(button == quitButton) {
			System.exit(1);
		}
	}
	
	//menu event listener
	public void menuItemSelected(JMenuItem menuItem){
		if(menuItem == resetMI) {
			baseList.clear();
			updateListArea();
			addButton.setEnabled(true);
		}else if(menuItem == sortMI) {
			ArrayList<Integer> sorted = new Sorter<Integer>(baseList).getSortedList();
			if(sorted.size() == 0) {
				messageBox("There is nothing in the list");
			}else {
				messageBox("Sorted List: " + sorted);
			}
		}else if(menuItem == meanMI) {
			if(baseList.size() == 0) {
				messageBox("There is nothing in the list");
			}else {
				messageBox("The mean is " + round(getAverage()));
			}
		}else if(menuItem == medianMI) {
			if(baseList.size() == 0) {
				messageBox("There is nothing in the list");
			}else {
				messageBox("The median is " + getMedian());
			}
		}else if(menuItem == modeMI) {
			if(baseList.size() == 0) {
				messageBox("There is nothing in the list");
				return;
			}
			ArrayList<Integer> modes;
			try {
				modes = getModes();
				messageBox("Mode: " + modes);
			}catch (NoModeException e) {
				messageBox("There is no mode");
			}
		}else if (menuItem == stdMI) {
			if(baseList.size() == 0) {
				messageBox("There is nothing in the list");
			}else {
				messageBox("The standard deviation is " + round(getStandardDeviation()));
			}
		}
	}
	
	//updates the text area as numbers are entered
	private void updateListArea() {
		String listStr = "";
		for(Integer i : baseList) {
			listStr += i.toString() + ",";
		}
		if(listStr.length() > 1) {
			listStr = listStr.substring(0, listStr.length()-1);
		}
		currentListArea.setText(listStr);
	}

	//calculates average
	private double getAverage() {
		double total = 0;
		for(Integer i : baseList) {
			total += i;
		}
		return total/(double)baseList.size();
	}
	
	//finds the median
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
	
	//finds the mode(s)
	private ArrayList<Integer> getModes() throws NoModeException{
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
	
	//calculates standard deviation
	private double getStandardDeviation() {
		double mean = getAverage();
		
		double total = 0;
		for(int i : baseList) {
			total += Math.pow(i - mean,2);
		}
		double secondMean = total / baseList.size();
		
		return Math.sqrt(secondMean);
	}
	
	//listens for enter key presses
	private KeyListener kl = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {}
		
		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER && numField.hasFocus()) {
				if(baseList.size() >= MAX_NUMS) {
					messageBox("Cannot add any more numbers, max is 25");
					return;
				}
				addNumber(numField);
			}
		}
		
		@Override
		public void keyPressed(KeyEvent e) {}
	};
	
	//adds number to array & error checks
	//this is a method because the user can add a number either by clicking the button or pressing enter
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
	}
	
	//rounds decimals
	private String round(double d) {
		DecimalFormat df = new DecimalFormat("##.##");
		return df.format(d);
	}
	
	//main class
	public static void main(String[] args) {
		new InsertionSortGUI();
	}
	
	//constructor
	public InsertionSortGUI() {
		baseList = new ArrayList<Integer>();
		
		currentListArea.setEditable(false);
		currentListArea.setFont(new Font("Sans-Serif",0,16));
		
		numField.addKeyListener(kl);
		
		setTitle("Insertion Sort");
		setSize(600,400);
		setVisible(true);
	}
	
}
