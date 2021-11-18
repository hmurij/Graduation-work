package baigiamasis.darbas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class SortArrayViewPanel extends JPanel implements Runnable{
	private int [] sortArray;
	private int size = 0;
	private JLabel [] label;
	private long sleepTime;
	private Random generator;
	private int currentSearchAlgorithm;
	private Calendar startTime;
	private Calendar stopTime;
	private Calendar stepTime;
	private int stepCounter = 0;
	private int compareCounter = 0;
	private int moveCounter = 0;
	int currentWidth;
	
	private boolean sorted = false;
	private boolean canSort = false;
	private boolean stop = false;
	
	private ToolBar toolBar;
	private SouthPanel southPanel;
	private JTabbedPane tabbedPane;
	private JMenuItem saveItem;
	
	public SortArrayViewPanel(SouthPanel southPanel, JTabbedPane tabbedPane, JMenuItem saveItem){
		
		this.tabbedPane = tabbedPane;
		this.saveItem = saveItem;

		setBorder(new EmptyBorder(2, 2, 2, 2));
    	generator = new Random();
    	this.southPanel = southPanel;
    	toolBar = new ToolBar(this);
    	toolBar.disableSortButton();
    	toolBar.disablePauseButton();
    	toolBar.disableStopButton();
    	toolBar.disableUnsortButton();
    	currentSearchAlgorithm = 0;


	} // end of default constructor

	
	public void run() {
		while(true){ // infinite loop
			
			while(!canSort){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
						
			if(!sorted && canSort){
				startTime = Calendar.getInstance();
				southPanel.appendText(startTime.get(Calendar.HOUR_OF_DAY) + ":" +
							          startTime.get(Calendar.MINUTE) + ":" +
							          startTime.get(Calendar.SECOND) + ":" +
							          startTime.get(Calendar.MILLISECOND) + 
							          " - Pradėtas rikiavimas masyvo iš " +
							          size + " elementu, naudojamas " + 
							          toolBar.getSortAlgorithm().getSelectedItem() + 
							          " algoritmas.\n");			
								
				toolBar.disableArraySize();
				toolBar.disableSortAlgorithm();
				toolBar.disableSortButton();
				toolBar.enableStopButton();
				toolBar.disableUnsortButton();
				
				tabbedPane.setEnabledAt(1, false);
				
				switch(currentSearchAlgorithm){
					case 0: selectionSort(); break; 
					case 1: insertionSort(); break;
					case 2: bubbleSort(); break;
					case 3: biDirectionalBubbleSort(); break;
					case 4: mergeSort(0, sortArray.length - 1); break;
					case 5: quickSort(0, sortArray.length - 1); break;
				
				} // end of switch
				
				tabbedPane.setEnabledAt(1, true);
				
				stopTime = Calendar.getInstance();
				
				if(!stop){
				southPanel.appendText(stopTime.get(Calendar.HOUR_OF_DAY) + ":" +
									  stopTime.get(Calendar.MINUTE) + ":" +
									  stopTime.get(Calendar.SECOND) + ":" +
									  stopTime.get(Calendar.MILLISECOND) + 
									  " - Užbaigtas rikiavimas masyvo iš " +
									  size + " elementu, naudojant " + 
									  toolBar.getSortAlgorithm().getSelectedItem() + 
									  " algoritma.\n");
				
				southPanel.appendText("----------------------------------------------------------------------------" +
									  "----------------------------------------\n" +
						              "Masyvas buvio išrikiuotas per ");
				
				if(startTime.get(Calendar.MINUTE) > stopTime.get(Calendar.MINUTE))
					stopTime.set(Calendar.HOUR_OF_DAY, stopTime.get(Calendar.HOUR_OF_DAY) - 1);
				
				if(startTime.get(Calendar.HOUR_OF_DAY) > stopTime.get(Calendar.HOUR_OF_DAY))
					southPanel.appendText(String.valueOf((stopTime.get(Calendar.HOUR_OF_DAY) + 24) - 
							              startTime.get(Calendar.HOUR_OF_DAY)) + " Valandu ");
				else if(startTime.get(Calendar.HOUR_OF_DAY) <= stopTime.get(Calendar.HOUR_OF_DAY))
					southPanel.appendText(String.valueOf(stopTime.get(Calendar.HOUR_OF_DAY) - 
							              startTime.get(Calendar.HOUR_OF_DAY)) + " Valandu ");	
				
				if(startTime.get(Calendar.SECOND) > stopTime.get(Calendar.SECOND))
					stopTime.set(Calendar.MINUTE, stopTime.get(Calendar.MINUTE) - 1);
				
				if(startTime.get(Calendar.MINUTE) > stopTime.get(Calendar.MINUTE))
					southPanel.appendText(String.valueOf((stopTime.get(Calendar.MINUTE) + 60) - 
							              startTime.get(Calendar.MINUTE)) + " Minučiu ");
				else if(startTime.get(Calendar.MINUTE) <= stopTime.get(Calendar.MINUTE))
					southPanel.appendText(String.valueOf(stopTime.get(Calendar.MINUTE) - 
							              startTime.get(Calendar.MINUTE)) + " Minučiu ");
				
				if(startTime.get(Calendar.MILLISECOND) > stopTime.get(Calendar.MILLISECOND))
					stopTime.set(Calendar.SECOND, stopTime.get(Calendar.SECOND) - 1);
								
				if(startTime.get(Calendar.SECOND) > stopTime.get(Calendar.SECOND))
					southPanel.appendText(String.valueOf((stopTime.get(Calendar.SECOND) + 60) - 
							              startTime.get(Calendar.SECOND)) + " Sekundžiu ");
				else if(startTime.get(Calendar.SECOND) <= stopTime.get(Calendar.SECOND))
					southPanel.appendText(String.valueOf(stopTime.get(Calendar.SECOND) - 
							              startTime.get(Calendar.SECOND)) + " Sekundžiu ");
				
				if(startTime.get(Calendar.MILLISECOND) > stopTime.get(Calendar.MILLISECOND))
					southPanel.appendText(String.valueOf((stopTime.get(Calendar.MILLISECOND) + 1000) - 
							              startTime.get(Calendar.MILLISECOND)) + " Millisekundžiu.\n");
				else if(startTime.get(Calendar.MILLISECOND) <= stopTime.get(Calendar.MILLISECOND))
					southPanel.appendText(String.valueOf(stopTime.get(Calendar.MILLISECOND) - 
							              startTime.get(Calendar.MILLISECOND)) + " Millisekundžiu.\n");
				southPanel.appendText("Buvo atlikti " + stepCounter + " Žingsniai " + compareCounter + " Palyginimai " +
									  moveCounter + " Perkėlimai.");
				}
				else
					southPanel.appendText(stopTime.get(Calendar.HOUR_OF_DAY) + ":" +
										  stopTime.get(Calendar.MINUTE) + ":" +
						                  stopTime.get(Calendar.SECOND) + ":" +
						                  stopTime.get(Calendar.MILLISECOND) + 
						                  " - Rikiavimas nutrauktas");
				
				stepCounter = 0;
				compareCounter = 0;
				moveCounter = 0;
				
				toolBar.enableArraySize();
				toolBar.enableSortAlgorithm();
				toolBar.disableSortButton();
				toolBar.disablePauseButton();
				toolBar.disableStopButton();
				toolBar.enableUnsortButton();
				saveItem.setEnabled(true);
					
				sorted = true;
				canSort = false;
				stop = false;
			}
			
		} // end of infinite loop
	} // end of run
	
	
	private void selectionSort(){
		// beginning of selection sort
		int smallest;
		int temp;
		
		
		for(int i = 0; i < sortArray.length; i++){
			
			sleep();
									
			smallest = i;
			
			if(stop)
				break;
			
			for(int j = i+1; j < sortArray.length; j++){
				
				sleep();
				if(stop)
					break;
								
				label[j].setBackground(Color.ORANGE);
				
				sleep();
				
				if(stop)
					break;
				
				label[smallest].setBackground(Color.ORANGE);
				
				sleep();
				
				if(stop)
					break;
				
				stepCounter++;
				compareCounter++;
				if(size <= 100){
					stepTime = Calendar.getInstance();
					southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
										  stepTime.get(Calendar.MINUTE) + ":" +
										  stepTime.get(Calendar.SECOND) + ":" +
										  stepTime.get(Calendar.MILLISECOND) +
										  " - Žingsnis Nr " + stepCounter +
										  " - Palyginimas Nr " + compareCounter +
										  " - Elementas [" + sortArray[smallest] +
										  "] palyginamas su elementu [" + sortArray[j] +
									  	  "]\n");
				} // end of if
				
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				
				
				if(sortArray[j] < sortArray[smallest]){
					
					sleep();
					if(stop)
						break;
					
					label[smallest].setBackground(Color.RED);
					
					sleep();
					
					if(stop)
						break;
					
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					smallest = j;
				} // end of if
				else{
					sleep();
					if(stop)
						break;
					
					label[j].setBackground(Color.red);
					
					sleep();
					
					if(stop)
						break;
					
					try {
						Thread.sleep(sleepTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				sleep();
				
				if(stop)
					break;
				
			} // end of for
			
			
			if(i != smallest){
				stepCounter++;
				moveCounter++;
				
				sleep();
				
				if(stop)
					break;
				
				if(size <= 100){
					stepTime = Calendar.getInstance();
					southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
									      stepTime.get(Calendar.MINUTE) + ":" +
									      stepTime.get(Calendar.SECOND) + ":" +
									      stepTime.get(Calendar.MILLISECOND) +
									      " - Žingsnis Nr " + stepCounter +
									      " - Perkėlimas Nr " + moveCounter +
				          			      " - Elementas [" + sortArray[smallest] +
									      "] ir elementas [" + sortArray[i] +
									      "] keičiasi vietomis\n");
				} // end of if
				
				sleep();
				
				if(stop)
					break;
								
				label[i].setBackground(Color.yellow);
				
				sleep();
				
				if(stop)
					break;
				
				label[smallest].setBackground(Color.yellow);
				
				sleep();
				
				if(stop)
					break;
				
				try {
					Thread.sleep(sleepTime*2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				temp = sortArray[i];
								
				
				
				sortArray[i] = sortArray[smallest];
				sleep();
				if(stop)
					break;
				
				label[i].setText(String.valueOf(sortArray[smallest]));
				sleep();
				if(stop)
					break;
								
				sortArray[smallest] = temp;
				sleep();
				if(stop)
					break;
				label[smallest].setText(String.valueOf(temp));
				sleep();
				if(stop)
					break;
				
				try {
					Thread.sleep(sleepTime*2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
						
				sleep();
				if(stop)
					break;
				label[i].setBackground(Color.GREEN);
				sleep();
				if(stop)
					break;
				label[smallest].setBackground(Color.red);
				sleep();
				if(stop)
					break;
								
				try {
					Thread.sleep(sleepTime*2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} // end of if
			else if(i == smallest){
				sleep();
				if(stop)
					break;
				label[i].setBackground(Color.GREEN);
				sleep();
				if(stop)
					break;
				
				try {
					Thread.sleep(sleepTime*2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} // end of else
			
			sleep();
			if(stop)
				break;
			
		} // end of for
		
	} // end of slectionSort

	private void  insertionSort(){
		int insert; 
		
		if(size == 1)
			label[0].setBackground(Color.green);

		
		for(int next = 1; next < sortArray.length; next++){
			insert = sortArray[next];  
			
			sleep();
			if(stop)
				break;
			
			label[next].setBackground(Color.orange);
			
			sleep();
			if(stop)
				break;
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			int moveItem = next;
			
			
		
		    while(moveItem > 0 && sortArray[moveItem - 1] > insert){
		    	stepCounter++;
		    	compareCounter++;
		    			    	
		    	if(size <= 100){
					stepTime = Calendar.getInstance();
					southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
										  stepTime.get(Calendar.MINUTE) + ":" +
										  stepTime.get(Calendar.SECOND) + ":" +
										  stepTime.get(Calendar.MILLISECOND) +
										  " - Žingsnis Nr " + stepCounter +
										  " - Palyginimas Nr " + compareCounter +
										  " - Elementas [" + sortArray[moveItem] +
										  "] palyginamas su elementu [" + sortArray[moveItem - 1] +
									  	  "]\n");
				} // end of if
		    	
		    	sleep();
				if(stop)
					break;
		    	
		    	label[moveItem - 1].setBackground(Color.orange);
		    	
		    	sleep();
				if(stop)
					break;
				
		    	try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		    	
				sleep();
				if(stop)
					break;
				
				label[moveItem].setBackground(Color.yellow);
				
				sleep();
				if(stop)
					break;
				
				label[moveItem - 1].setBackground(Color.yellow);
				
				sleep();
				if(stop)
					break;
				
				try {
					Thread.sleep(sleepTime*2);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
		    	sortArray[moveItem] = sortArray[moveItem - 1];
		    	sortArray[moveItem - 1] = insert; 
		    	
		    	stepCounter++;
		    	moveCounter++;
		    	
		    	if(size <= 100){
					stepTime = Calendar.getInstance();
					southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
									      stepTime.get(Calendar.MINUTE) + ":" +
									      stepTime.get(Calendar.SECOND) + ":" +
									      stepTime.get(Calendar.MILLISECOND) +
									      " - Žingsnis Nr " + stepCounter +
									      " - Perkėlimas Nr " + moveCounter +
				          			      " - Elementas [" + sortArray[moveItem] +
									      "] ir elementas [" + insert +
									      "] keičiasi vietomis\n");
				} // end of if
		    	
		    	sleep();
				if(stop)
					break;
		    			    	
		    					
		    	label[moveItem].setText(label[moveItem-1].getText());
		    	label[moveItem - 1].setText(String.valueOf(insert));
		    	
		    	sleep();
				if(stop)
					break;
				
		    	try {
					Thread.sleep(sleepTime*2);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
		    	
				sleep();
				if(stop)
					break;
				
				label[moveItem].setBackground(Color.green);
				
				sleep();
				if(stop)
					break;
				
				label[moveItem - 1].setBackground(Color.orange);
				
				sleep();
				if(stop)
					break;
				
				try {
					Thread.sleep(sleepTime*2);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				
		        moveItem--;                             
		    } // end while      
		    
		    if(moveItem - 1 >= 0){
		    	stepCounter++;
		    	compareCounter++;
		    	
		    	if(size <= 100){
					stepTime = Calendar.getInstance();
					southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
										  stepTime.get(Calendar.MINUTE) + ":" +
										  stepTime.get(Calendar.SECOND) + ":" +
										  stepTime.get(Calendar.MILLISECOND) +
										  " - Žingsnis Nr " + stepCounter +
										  " - Palyginimas Nr " + compareCounter +
										  " - Elementas [" + sortArray[moveItem] +
										  "] palyginamas su elementu [" + sortArray[moveItem - 1] +
									  	  "]\n");
				} // end of if
		    	
		    					
		    	label[moveItem - 1].setBackground(Color.orange);
		    	
		    	sleep();
				if(stop)
					break;
				
		    	try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				sleep();
				if(stop)
					break;
				
				label[moveItem - 1].setBackground(Color.green);
				
				sleep();
				if(stop)
					break;
				
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		    	
		    
		    } // end of if
			
		    sleep();
			if(stop)
				break;
			
			label[moveItem].setBackground(Color.green);
			
			sleep();
			if(stop)
				break;
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	
		    
		} // end for                                               
	} // end of insertionSort

	private void bubbleSort(){
		for(int i = sortArray.length - 1; i >= 0; i--){
			boolean swapped = false;
		    
			for(int j = 0; j < i; j++){
				
				sleep();
				if(stop)
					break;
				
				label[j].setBackground(Color.orange);
				sleep(1);
				
				sleep();
				if(stop)
					break;
				
				
				label[j+1].setBackground(Color.orange);
				
				sleep();
				if(stop)
					break;
				
				stepCounter++;
				compareCounter++;
				
				if(size <= 100){
					stepTime = Calendar.getInstance();
					southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
							  stepTime.get(Calendar.MINUTE) + ":" +
							  stepTime.get(Calendar.SECOND) + ":" +
							  stepTime.get(Calendar.MILLISECOND) +
							  " - Žingsnis Nr " + stepCounter +
							  " - Palyginimas Nr " + compareCounter +
							  " - Elementas [" + sortArray[j] +
							  "] palyginamas su elementu [" + sortArray[j+1] +
						  	  "]\n");
				} // end of if
				
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				sleep();
				if(stop)
					break;
					
		    	if(sortArray[j] > sortArray[j+1]){
		    		label[j].setBackground(Color.yellow);
		    		label[j+1].setBackground(Color.yellow);
		    		
		    				    		
		    		stepCounter++;
		    		moveCounter++;
		    		
		    		if(size <= 100){
						stepTime = Calendar.getInstance();
						southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
										      stepTime.get(Calendar.MINUTE) + ":" +
										      stepTime.get(Calendar.SECOND) + ":" +
										      stepTime.get(Calendar.MILLISECOND) +
										      " - Žingsnis Nr " + stepCounter +
										      " - Perkėlimas Nr " + moveCounter +
					          			      " - Elementas [" + sortArray[j] +
										      "] ir elementas [" + sortArray[j+1] +
										      "] keičiasi vietomis\n");
					} // end of if
		    		
		    		
		    		
		    		try {
						Thread.sleep(sleepTime*2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					sleep();
					if(stop)
						break;
		    		
		    		int temp = sortArray[j];
		    		sortArray[j] = sortArray[j+1];
		    		label[j].setText(String.valueOf(sortArray[j+1]));
		    		
		    		sortArray[j+1] = temp;
		    		label[j+1].setText(String.valueOf(temp));
		    		
		    		sleep();
					if(stop)
						break;
		    		
		    		try {
						Thread.sleep(sleepTime*2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					sleep();
					if(stop)
						break;
					
					label[j+1].setBackground(Color.orange);
					
					sleep();
					if(stop)
						break;
		    		
		    		swapped = true;
		    	} // end of if
		    	
		    	label[j].setBackground(Color.red);
		    	
		    	sleep();
				if(stop)
					break;
		    	
		    	try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			
		    } // end of for
			
			if(!swapped && !stop){
		    	for(int j = 0; j <= i; j++)
		    		label[j].setBackground(Color.green);
		    	
		    		sleep();
		    		if(stop)
		    			break;
		    	
		    	try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				return;
			} // end of if
			else if(!stop){
				label[i].setBackground(Color.green);

				sleep();
				if(stop)
					break;
				
				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} // end of else
		} // end of for
	} // end of bubbleSort
	
	private void biDirectionalBubbleSort(){
		if(size == 1)
			label[0].setBackground(Color.green);
		
		int firstIndex = 0;
		int lastIndex = sortArray.length - 1;
		
		int temp;

		boolean swap;
		
		while((firstIndex < lastIndex) && !stop){
			
			swap = false;
			
			for(int index = firstIndex; index < lastIndex; index++){
				stepCounter++;
				compareCounter++;
				
				label[index].setBackground(Color.orange);
				sleep(1);
				label[index+1].setBackground(Color.orange);
				
				if(size <= 100){
					stepTime = Calendar.getInstance();
					southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
							  stepTime.get(Calendar.MINUTE) + ":" +
							  stepTime.get(Calendar.SECOND) + ":" +
							  stepTime.get(Calendar.MILLISECOND) +
							  " - Žingsnis Nr " + stepCounter +
							  " - Palyginimas Nr " + compareCounter +
							  " - Elementas [" + sortArray[index] +
							  "] palyginamas su elementu [" + sortArray[index+1] +
						  	  "]\n");
				} // end of if
				
				sleep(1);
				
				sleep();
				if(stop)
					break;
				
				if(sortArray[index] > sortArray[index+1]){
					stepCounter++;
					moveCounter++;
										
					
					label[index].setBackground(Color.yellow);
					label[index+1].setBackground(Color.yellow);
					
					sleep();
					if(stop)
						break;
					
					if(size <= 100){
						stepTime = Calendar.getInstance();
						southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
										      stepTime.get(Calendar.MINUTE) + ":" +
										      stepTime.get(Calendar.SECOND) + ":" +
										      stepTime.get(Calendar.MILLISECOND) +
										      " - Žingsnis Nr " + stepCounter +
										      " - Perkelimas Nr " + moveCounter +
					          			      " - Elementas [" + sortArray[index] +
										      "] ir elementas [" + sortArray[index+1] +
										      "] keičiasi vietomis\n");
					} // end of if
					
					sleep(2);
								
					sleep();
					if(stop)
						break;
					
					temp = sortArray[index];
					sortArray[index] = sortArray[index+1];
					sortArray[index+1] = temp;
					
					label[index].setText(String.valueOf(sortArray[index]));
					label[index+1].setText(String.valueOf(sortArray[index+1]));
					sleep(2);
					
					sleep();
					if(stop)
						break;
					
					swap = true;
					
					label[index+1].setBackground(Color.orange);
					
					sleep();
					if(stop)
						break;
				} // end of if
				
				
				label[index].setBackground(Color.red);
				sleep(1);
				
				sleep();
				if(stop)
					break;
			} // end of for
			
			if(!swap && !stop){
				for(int index = firstIndex; index <= lastIndex; index++){
					label[index].setBackground(Color.green);
					
					sleep();
					if(stop)
						break;
				}
				
				return;
			} // end of if
			else if(!stop){
				label[lastIndex].setBackground(Color.green);
				sleep(1);
				
				swap = false;
			} // end of else
			
			lastIndex--;
			
			if(!stop){
			for(int index = lastIndex; index > firstIndex; index--){
				stepCounter++;
				compareCounter++;
				
				label[index].setBackground(Color.orange);
				sleep(1);
				label[index-1].setBackground(Color.orange);
				
				sleep();
				if(stop)
					break;
				
				if(size <= 100){
					stepTime = Calendar.getInstance();
					southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
							  stepTime.get(Calendar.MINUTE) + ":" +
							  stepTime.get(Calendar.SECOND) + ":" +
							  stepTime.get(Calendar.MILLISECOND) +
							  " - Žingsnis Nr " + stepCounter +
							  " - Palyginimas Nr " + compareCounter +
							  " - Elementas [" + sortArray[index] +
							  "] palyginamas su elementu [" + sortArray[index-1] +
						  	  "]\n");
				} // end of if
				
				sleep(1);
				
				sleep();
				if(stop)
					break;
				
				
				if(sortArray[index-1] > sortArray[index]){
					stepCounter++;
					moveCounter++;
					
					sleep();
					if(stop)
						break;
					
					label[index].setBackground(Color.yellow);
					label[index-1].setBackground(Color.yellow);
					
					sleep();
					if(stop)
						break;
					
					if(size <= 100){
						stepTime = Calendar.getInstance();
						southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
										      stepTime.get(Calendar.MINUTE) + ":" +
										      stepTime.get(Calendar.SECOND) + ":" +
										      stepTime.get(Calendar.MILLISECOND) +
										      " - Žingsnis Nr " + stepCounter +
										      " - Perkelimas Nr " + moveCounter +
					          			      " - Elementas [" + sortArray[index] +
										      "] ir elementas [" + sortArray[index-1] +
										      "] keičiasi vietomis\n");
					} // end of if
					
					sleep(2);
					
					sleep();
					if(stop)
						break;
					
					temp = sortArray[index];
					sortArray[index] = sortArray[index-1];
					sortArray[index-1] = temp;
					
					label[index].setText(String.valueOf(sortArray[index]));
					label[index-1].setText(String.valueOf(sortArray[index-1]));
					
					sleep(1);
					
					sleep();
					if(stop)
						break;
					
					swap = true;
					
					label[index-1].setBackground(Color.orange);
					
					sleep();
					if(stop)
						break;
				} // end of if
				
				label[index].setBackground(Color.red);
				sleep(1);
				
				sleep();
				if(stop)
					break;
				
			} // end of for
			} // end of if
			
			if(!swap && !stop){
				for(int index = lastIndex; index >= firstIndex; index--){
					label[index].setBackground(Color.green);
					
					sleep();
					if(stop)
						break;
				}
				return;
			} // end of if
			else if(!stop){
				label[firstIndex].setBackground(Color.green);
				sleep(1);
				
				sleep();
				if(stop)
					break;
			} // end of else
			
			firstIndex++;
			
			
		} // end of while
		
	} // end of Bi-directional Bubble Sort

	private void mergeSort(int first, int last){
		if((first < last) && !stop){
			int mid = (first + last)/2; 
		    mergeSort(first, mid);
		    
		    if(!stop)
		    	mergeSort(mid+1, last);

		    if(!stop)
		    	merge(first, mid, last);
		} // end of if
	} // end of mergeSort
	
	private void merge(int first, int mid, int last){
		int [] tempArray = new int[size];

		int first1 = first;
		int last1 = mid;
		int first2 = mid + 1;
		int last2 = last; 

		int index = first1;
		
		for(; (first1 <= last1) && (first2 <= last2); ++index){  
			stepCounter++;
			compareCounter++;
			
			label[first1].setBackground(Color.orange);
			label[first2].setBackground(Color.orange);
			
			if(size <= 100){
				stepTime = Calendar.getInstance();
				southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
						  stepTime.get(Calendar.MINUTE) + ":" +
						  stepTime.get(Calendar.SECOND) + ":" +
						  stepTime.get(Calendar.MILLISECOND) +
						  " - Žingsnis Nr " + stepCounter +
						  " - Palyginimas Nr " + compareCounter +
						  " - Elementas [" + sortArray[first1] +
						  "] palyginamas su elementu [" + sortArray[first2] +
					  	  "]\n");
			} // end of if
			
			sleep(1);
			
			sleep();
			if(stop)
				break;
			
			if (sortArray[first1] < sortArray[first2]){
				label[first1].setBackground(Color.red);
		        sleep(1);
		        
		        sleep();
				if(stop)
					break;
				
				tempArray[index] = sortArray[first1];
		        ++first1;
		    }
		    else{
		    	label[first2].setBackground(Color.red);
		        sleep(1);
		        
		        sleep();
				if(stop)
					break;
		    	
		    	tempArray[index] = sortArray[first2];
		    	++first2;
		    } // end of if
		} // end of for
		
		if((first2 <= last2) && !stop)
			label[first2].setBackground(Color.red);
		else
			label[first1].setBackground(Color.red);
		
		sleep(1);
		
		sleep();
		

		for(; first1 <= last1; ++first1, ++index)
			tempArray[index] = sortArray[first1];

		for(; first2 <= last2; ++first2, ++index)
			tempArray[index] = sortArray[first2];
		
		for(index = first; index <= last; ++index){
			label[index].setBackground(Color.yellow);
			sleep(2);
			
			sleep();
			if(stop)
				break;
		}
		
		for(index = first; index <= last; ++index){
			stepCounter++;
			moveCounter++;
			
		    sortArray[index] = tempArray[index];
		   
		    if(size <= 100){
				stepTime = Calendar.getInstance();
				southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
								      stepTime.get(Calendar.MINUTE) + ":" +
								      stepTime.get(Calendar.SECOND) + ":" +
								      stepTime.get(Calendar.MILLISECOND) +
								      " - Žingsnis Nr " + stepCounter +
								      " - Perkėlimas Nr " + moveCounter +
			          			      " - Perkėlimas elementas [" + sortArray[index] + "]\n");
			} // end of if
		    sleep(1);
		    
		    sleep();
			if(stop)
				break;
		    
		    label[index].setText(String.valueOf(sortArray[index]));
		    sleep(2);
		    
		    sleep();
			if(stop)
				break;
		    
		    label[index].setBackground(Color.green);
		    sleep(2);
		    
		    sleep();
			if(stop)
				break;
		   
		} // end of for
	} // end of merge
	
	private void quickSort(int first, int last){
		int pivotIndex = first;
		if((first < last) && !stop){
			pivotIndex = partition(first, last);
			
			if(!stop)
				quickSort(first, pivotIndex-1);
			
			if(!stop)
				quickSort(pivotIndex+1, last);
		} // end if
	} // end of quickSort
	
	private void choosePivot(int first, int last){
		int pivotIndex = first;

		int average=(sortArray[first] + sortArray[(first+last)/2] + sortArray[last])/3;
		
		for(int index=first; index<=last; index++){
			label[index].setBackground(Color.red);
			
			sleep();
			if(stop)
				break;
			//sleep(1);
		} // end of for

		label[pivotIndex].setBackground(Color.orange);
		sleep(1);
		sleep();
		
		for(int index = first + 1; index <= last; index++){
			  stepCounter++;
			  compareCounter++;
			  
			  label[index].setBackground(Color.orange);
			  
			  if(size <= 100){
					stepTime = Calendar.getInstance();
					southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
							  stepTime.get(Calendar.MINUTE) + ":" +
							  stepTime.get(Calendar.SECOND) + ":" +
							  stepTime.get(Calendar.MILLISECOND) +
							  " - Žingsnis Nr " + stepCounter +
							  " - Palyginimas Nr " + compareCounter +
							  " - Elementas [" + sortArray[pivotIndex] +
							  "] palyginamas su elementu [" + sortArray[index] +
						  	  "]\n");
				} // end of if
			  
			  sleep(1);
			  
			  sleep();
			  if(stop)
				  break;
			  
			  			  
		      if(average <= sortArray[index]){
		    	  pivotIndex = index; break;
		      } // end of if
		      
		      label[index].setBackground(Color.red);
		      sleep(1);
		      
		      sleep();
			  if(stop)
				  break;
		} // end of for
		  
		if(pivotIndex != first){
			stepCounter++;
			moveCounter++;
			
			label[first].setBackground(Color.yellow);
			label[pivotIndex].setBackground(Color.yellow);
			
			if(size <= 100){
				stepTime = Calendar.getInstance();
				southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
								      stepTime.get(Calendar.MINUTE) + ":" +
								      stepTime.get(Calendar.SECOND) + ":" +
								      stepTime.get(Calendar.MILLISECOND) +
								      " - Žingsnis Nr " + stepCounter +
								      " - Perkėlimas Nr " + moveCounter +
			          			      " - Elementas [" + sortArray[first] +
								      "] ir elementas [" + sortArray[pivotIndex] +
								      "] keičiasi vietomis\n");
			} // end of if
			
			sleep(2);
			
			sleep();
			if(stop)
				return;
			
			swap(first, pivotIndex);
			label[first].setText(String.valueOf(sortArray[first]));
			label[pivotIndex].setText(String.valueOf(sortArray[pivotIndex]));
			sleep(2);
			
			sleep();
			if(stop)
				return;
			
			label[first].setBackground(Color.red);
			label[pivotIndex].setBackground(Color.red);
			sleep(2);
			
			sleep();
			if(stop)
				return;
			
		} // end of if
		else{
			label[pivotIndex].setBackground(Color.red);
			sleep(1);
			
			sleep();
			if(stop)
				return;
		} // end of else
			
	} // end of choosePivot
	
	private int partition(int first, int last){
		choosePivot(first, last);

		int pivot = sortArray[first];

		int lastS1 = first;           
		int firstUnknown = first + 1; 
		
		label[first].setBackground(Color.orange);
		sleep(1);
		
		sleep();
		if(stop)
			return 0;
		                                 
		for(; firstUnknown <= last; ++firstUnknown){
			stepCounter++;
			compareCounter++;
			
			label[firstUnknown].setBackground(Color.orange);
			
			if(size <= 100){
				stepTime = Calendar.getInstance();
				southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
						  stepTime.get(Calendar.MINUTE) + ":" +
						  stepTime.get(Calendar.SECOND) + ":" +
						  stepTime.get(Calendar.MILLISECOND) +
						  " - Žingsnis Nr " + stepCounter +
						  " - Palyginimas Nr " + compareCounter +
						  " - Elementas [" + sortArray[first] +
						  "] palyginamas su elementu [" + sortArray[firstUnknown] +
					  	  "]\n");
			} // end of if
			
			sleep(1);
			
			sleep();
			if(stop)
				break;
			
			if(sortArray[firstUnknown] < pivot){
				++lastS1;
				
				if(lastS1 != firstUnknown){
					stepCounter++;
					moveCounter++;
					
					label[lastS1].setBackground(Color.yellow);
					label[firstUnknown].setBackground(Color.yellow);
					
					if(size <= 100){
						stepTime = Calendar.getInstance();
						southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
										      stepTime.get(Calendar.MINUTE) + ":" +
										      stepTime.get(Calendar.SECOND) + ":" +
										      stepTime.get(Calendar.MILLISECOND) +
										      " - Žingsnis Nr " + stepCounter +
										      " - Perkėlimas Nr " + moveCounter +
					          			      " - Elementas [" + sortArray[lastS1] +
										      "] ir elementas [" + sortArray[firstUnknown] +
										      "] keičiasi vietomis\n");
					} // end of if
					
					sleep(2);
					
					sleep();
					if(stop)
						break;
				
					swap(firstUnknown, lastS1);
					label[firstUnknown].setText(String.valueOf(sortArray[firstUnknown]));
					label[lastS1].setText(String.valueOf(sortArray[lastS1]));
					sleep(2);
					
					sleep();
					if(stop)
						break;
				
					label[lastS1].setBackground(Color.green);
					sleep(2);
					
					sleep();
					if(stop)
						break;
				} // end of if
			}  // end of if
			
			label[firstUnknown].setBackground(Color.green);
			sleep(2);
			
			sleep();
			if(stop)
				break;
		}  // end of for

		
		if(first != lastS1){
			stepCounter++;
			moveCounter++;
		
			label[lastS1].setBackground(Color.yellow);
			label[first].setBackground(Color.yellow);
			
			if(size <= 100){
				stepTime = Calendar.getInstance();
				southPanel.appendText(stepTime.get(Calendar.HOUR_OF_DAY) + ":" +
								      stepTime.get(Calendar.MINUTE) + ":" +
								      stepTime.get(Calendar.SECOND) + ":" +
								      stepTime.get(Calendar.MILLISECOND) +
								      " - Žingsnis Nr " + stepCounter +
								      " - Perkėlimas Nr " + moveCounter +
			          			      " - Elementas [" + sortArray[first] +
								      "] ir elementas [" + sortArray[lastS1] +
								      "] keičiasi vietomis\n");
			} // end of if
			
			sleep(2);
			
			sleep();
			if(stop)
				return 0;
		
			if(!stop){
				swap(first, lastS1);
				label[first].setText(String.valueOf(sortArray[first]));
				label[lastS1].setText(String.valueOf(sortArray[lastS1]));
				sleep(2);
			}
			
			sleep();
			if(stop)
				return 0;
		
			if(!stop){
				label[lastS1].setBackground(Color.green);
				label[first].setBackground(Color.green);
				sleep(2);
			}
			
			
			sleep();
			if(stop)
				return 0;
		} // end of if
		else{
			label[first].setBackground(Color.green);
			sleep(2);
			
			sleep();
			if(stop)
				return 0;
		} // end of else
		
		
		return lastS1;
	} // end of partition
	
	void swap(int index1, int index2){
		int temp = sortArray[index1];
		sortArray[index1] = sortArray[index2];
		sortArray[index2] = temp;
	} // end of swap
	
	private void sleep(int sleepTimeMultiplier){
		try {
			Thread.sleep(sleepTime * sleepTimeMultiplier);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	} // end of sleep
	
	
	private void createArray(){
		sortArray = new int[size];
		
		for(int i = 0; i < sortArray.length; i++)
			sortArray[i] = generator.nextInt(99);
			//sortArray[i] = i+1;
		
		label = new JLabel[sortArray.length];
		
		for(int i = 0; i < sortArray.length; i++){
			label[i] = new JLabel(String.valueOf(sortArray[i]));
			label[i].setHorizontalAlignment(JLabel.CENTER);
			label[i].setOpaque(true);
			label[i].setBackground(Color.red);
			label[i].setPreferredSize(new Dimension(20, 20));
			label[i].setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK), 
											      new BevelBorder(BevelBorder.RAISED)));
		} // end of for
	} // end of createArray
	
	private void addLabels(){
		//System.out.println("width: " + currentWidth);
		if(size <= 100)
			setLayout(new GridLayout(0, 10, 2, 2));
		else if(size <= 500)
			setLayout(new GridLayout(0, 20, 2, 2));
		else if(size > 500){
			//System.out.println("currentWidth/22=" + currentWidth/22);
			setLayout(new GridLayout(0, 45, 2, 2));
			
		}
		
		if(size > 0)
			for(JLabel l: label)
				add(l);
		
	} // end of addLabels
	
	private void removeLabels(){
		if(size != 0){
			
			for(JLabel l: label)
				this.remove(l);
			
			repaint();
		} // end of if
	} // end of removeLabels
	
	private void sleep(){
		
		while(!canSort && !stop){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	} // end of sleep
	
	public void stopSort(){
		stop = true;
	} // end of stopSort
	
	public void setSize(int newSize){
		removeLabels();
		size = newSize;
		createArray();
		addLabels();
		this.revalidate();
		sorted = false;
		
		if(size > 0)
			toolBar.enableSortButton();
		else
			toolBar.disableSortButton();
		
		toolBar.disableUnsortButton();
		
		southPanel.clearLog();
		
		// System.out.println("size: " + size);
	} // end of setSize
	
	public void startSort(){
		if(size > 0)
			canSort = true;
		
		toolBar.enablePauseButton();
		toolBar.disableSortButton();
	} // end of startSort 
	
	public void pauseSort(){
		if(canSort)
			canSort = false;
		
		toolBar.disablePauseButton();
		toolBar.enableSortButton();
	}
	
	
	
	public void unsortArray(){
		removeLabels();
		createArray();
		addLabels();
		this.revalidate();
		sorted = false;
		canSort = false;
		toolBar.enableSortButton();
		toolBar.disableUnsortButton();
		southPanel.clearLog();
	} // end of unsortArray
	
	public void setSleepTime(double newSleepTime){
		sleepTime = (long) (newSleepTime*1000);
	} // end of setSleepTime
	
	public void setSearchAlgorithm(int currentSearchAlgorithm){
		this.currentSearchAlgorithm = currentSearchAlgorithm;
	} // end of setSearchAlgorithm
	
	public ToolBar returnToolBar(){
		return toolBar;
	} // end of returnToolBar

	
} // end of class SortArrayViewPanel
