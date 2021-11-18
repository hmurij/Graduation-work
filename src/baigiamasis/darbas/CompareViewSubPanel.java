package baigiamasis.darbas;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class CompareViewSubPanel extends JPanel implements Runnable{
	
	private JComboBox sortAlgorithm;
	private JSpinner arraySize;
	private JLabel currentStatus;
	private JLabel totalTime;
	private JLabel finalPlace;
	private JLabel stepCounterLabel;
	private JLabel compareCounterLabel;
	private JLabel moveCounterLabel;
	
	private CompareViewToolBar compareViewToolBar;
	private JTabbedPane tabbedPane;
	private CompareViewChartPanel compareViewChartPanel;
	
	private int [] sortArray;
	private int size = 0;
	private Random generator;
	private int currentSearchAlgorithm = 0;
	private static int arrayToSort = 0;
	private static int activeSorts = 0;
	
	private int stepCounter = 0;
	private int compareCounter = 0;
	private int moveCounter = 0;
	
	private boolean start = false;
	private boolean stop = false;
	private boolean active = false;
	
	private Calendar startTime;
	private int startMiliSeconds;
	private Calendar stopTime;
	//private Calendar stepTime;
	
	public CompareViewSubPanel(CompareViewToolBar compareViewToolBar, JTabbedPane tabbedPane,
							   CompareViewChartPanel compareViewChartPanel){
		this.compareViewToolBar = compareViewToolBar;
		this.tabbedPane = tabbedPane;
		this.compareViewChartPanel = compareViewChartPanel;
				
		JPanel selectionPanel = new JPanel();
		//selectionPanel.setBorder(new MatteBorder(1, 1, 1, 1, Color.blue));
		selectionPanel.setLayout(new GridBagLayout());
		//selectionPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		selectionPanel.setBorder(new CompoundBorder(new MatteBorder(0, 0, 2, 0, Color.LIGHT_GRAY), new EmptyBorder(0, 0, 2, 0)));
		
		GridBagConstraints constraints = new GridBagConstraints();
		
		String [] algortighm = {"Selection Sort", "Insertion Sort", 
					 			"Bubble Sort", "Bi-directional Bubble Sort", 
					 			"Merge Sort", "Quick Sort"};
		sortAlgorithm = new JComboBox(algortighm);
		//sortAlgorithm.setBorder(new CompoundBorder(new TitledBorder("Rikiavimo Algoritmas"), new EmptyBorder(0, 1, 0, 2)));
		//sortAlgorithm.setBorder(new TitledBorder("Rikiavimo Algoritmas"));
		sortAlgorithm.setBorder(new EmptyBorder(0, 1, 0, 2));
		sortAlgorithm.setToolTipText("Pasirinkite Rikiavomo Algoritma");
		sortAlgorithm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				currentSearchAlgorithm = sortAlgorithm.getSelectedIndex();
			}});
		constraints.gridx = 0;
		constraints.gridy = 0;
		//constraints.gridwidth = 2;
		constraints.weightx = 0.7;
		constraints.weighty = 1;
		constraints.fill = GridBagConstraints.BOTH;
		selectionPanel.add(sortAlgorithm, constraints);
		
		arraySize = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 10));
		arraySize.setToolTipText("Iveskite Masyvo Dydi");
		arraySize.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				if(size == 0 && (Integer)arraySize.getValue() != 0){
					arrayToSort++;
				} // end of if
				else if(size != 0 && (Integer)arraySize.getValue() == 0){
					arrayToSort--;
				} // end of else if
				
				size = (Integer)arraySize.getValue();
				createArray();
				
				// System.out.println("arrayToSort: " + arrayToSort);
			}}); 
		//arraySize.setBorder(new TitledBorder("Masyvo Dydis"));
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.weightx = 0.3;
		selectionPanel.add(arraySize, constraints);
				
		JPanel leftLabelPanel = new JPanel();
		leftLabelPanel.setLayout(new GridLayout(0, 2));
		//leftLabelPanel.setLayout(new GridBagLayout());
		//constraints = new GridBagConstraints();
		//leftLabelPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		leftLabelPanel.setBorder(new CompoundBorder(new MatteBorder(0, 0, 0, 2, Color.LIGHT_GRAY), new EmptyBorder(0, 2, 0, 2)));
		
		//leftLabelPanel.setMaximumSize(new Dimension(70, 18));
		//leftLabelPanel.setBorder(new MatteBorder(2, 2, 2, 2, Color.green));
		
		
		
		JLabel statusLabel = new JLabel("Būklė");
		//statusLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));
		
		//constraints.gridx = 0;
		//constraints.gridy = 0;
		//constraints.fill = GridBagConstraints.BOTH;
		//constraints.anchor = GridBagConstraints.WEST;
		//constraints.weightx = 0.5;
		//System.out.println(statusLabel.getMaximumSize().toString());
		leftLabelPanel.add(statusLabel);
		
		currentStatus = new JLabel("Tusčias");
		currentStatus.setHorizontalAlignment(JLabel.RIGHT);
		//currentStatus.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));

		leftLabelPanel.add(currentStatus);
		
		JLabel timeLabel = new JLabel("Laikas");
		//timeLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));

		leftLabelPanel.add(timeLabel);
		
		totalTime = new JLabel("00:00:00:000");
		totalTime.setDoubleBuffered(true);
		totalTime.setHorizontalAlignment(JLabel.RIGHT);
		//totalTime.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));
	
		leftLabelPanel.add(totalTime);
		
		JLabel placeLabel = new JLabel("Vieta");
		//placeLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));

		leftLabelPanel.add(placeLabel);
		
		finalPlace = new JLabel(" ");
		finalPlace.setHorizontalAlignment(JLabel.RIGHT);
		//finalPlace.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));
		leftLabelPanel.add(finalPlace);
		
		
			
		JPanel rightLabelPanel = new JPanel();
		
		rightLabelPanel.setLayout(new GridLayout(0, 2));
		//rightLabelPanel.setLayout(new GridBagLayout());
		//constraints = new GridBagConstraints();
		//rightLabelPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		rightLabelPanel.setBorder(new EmptyBorder(0, 3, 0, 2));
		
		JLabel stepLabel = new JLabel("Žingsniai");
		//stepLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));

		rightLabelPanel.add(stepLabel);
		
		stepCounterLabel = new JLabel("0");
		stepCounterLabel.setHorizontalAlignment(JLabel.RIGHT);
		//stepCounterLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));
		rightLabelPanel.add(stepCounterLabel);
		
		JLabel compareLabel = new JLabel("Palyginimai");
		//compareLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));
		rightLabelPanel.add(compareLabel);
		
		compareCounterLabel = new JLabel("0");
		//compareCounter.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));
		compareCounterLabel.setHorizontalAlignment(JLabel.RIGHT);
		rightLabelPanel.add(compareCounterLabel);
		
		JLabel moveLabel = new JLabel("Perkėlimai");
		//moveLabel.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));
		rightLabelPanel.add(moveLabel);
		
		moveCounterLabel = new JLabel("0");
		//moveCounter.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));
		moveCounterLabel.setHorizontalAlignment(JLabel.RIGHT);
		rightLabelPanel.add(moveCounterLabel);
		
		//System.out.println(rightLabelPanel.getMinimumSize().toString());
			
		this.setLayout(new GridBagLayout());
		constraints = new GridBagConstraints();
		
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.gridwidth = 2;
		constraints.weighty = 0.15;
		constraints.fill = GridBagConstraints.BOTH;
		add(selectionPanel, constraints);
				
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.weightx = 0.4;
		constraints.weighty = 0.85;
		constraints.gridwidth = 1;
		add(leftLabelPanel, constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 1;
		constraints.weightx = 0.6;
		add(rightLabelPanel, constraints);
		
		//this.setBorder(new BevelBorder(BevelBorder.RAISED));
		
		this.setBorder(new CompoundBorder(new MatteBorder(0, 0, 2, 2, Color.LIGHT_GRAY), new BevelBorder(BevelBorder.RAISED)));
		
		//this.setBorder(new CompoundBorder(new EmptyBorder(3, 3, 3, 3), new BevelBorder(BevelBorder.RAISED)));
		
	} // end of constructor
	
	public void run() {
		
		while(true){ // infinite loop
		
			while(!start){
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} // end of while
			
			//System.out.print("currentSearchAlgorithm: " + currentSearchAlgorithm);
		
			stepCounterLabel.setText("0");
			compareCounterLabel.setText("0");
			moveCounterLabel.setText("0");
			currentStatus.setText("Rikiuojamas");
			finalPlace.setText(" ");
			
			compareViewToolBar.enableStopButton();
			compareViewToolBar.disableSortButton();
			
			compareViewToolBar.disableComboSpinner();
			
			//System.out.println("arrayToSort: " + arrayToSort);
			
			tabbedPane.setEnabledAt(0, false);
			
			startTime = Calendar.getInstance();
			startMiliSeconds = startTime.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 +
			   				   startTime.get(Calendar.MINUTE) * 60 * 1000 +
			   				   startTime.get(Calendar.SECOND) * 1000 +
			   				   startTime.get(Calendar.MILLISECOND);
			
			switch(currentSearchAlgorithm){
				case 0: selectionSort(); break; 
				case 1: insertionSort(); break;
				case 2: bubbleSort(); break;
				case 3: biDirectionalBubbleSort(); break;
				case 4: mergeSort(0, sortArray.length - 1); break;
				case 5: quickSort(0, sortArray.length - 1); break;
			} // end of switch
			
			compareViewChartPanel.addData(stepCounter, compareCounter, moveCounter,  currentSearchAlgorithm, size);
			//compareViewChartPanel.resetChart();
			
			displayTime();
			
			tabbedPane.setEnabledAt(0, true);
			
			if(!stop)
				currentStatus.setText("Išrikiuotas");
			else if(stop)
				currentStatus.setText("Sustabdytas");
			
			start = false;
			stop = false;
			active = false;
			
			activeSorts--;
			
			finalPlace.setText(Integer.toString(arrayToSort - activeSorts));
			//System.out.println("activeSorts: " + activeSorts);
			
			if(activeSorts == 0){
				compareViewToolBar.enableSortButton();
				compareViewToolBar.disableStopButton();
				compareViewToolBar.enableComboSpinner();
				compareViewChartPanel.resetChart();
			}
			
			stepCounter = 0;
			compareCounter = 0;
			moveCounter = 0;
			
			//displaySortArray();
			unsortArray();
			//displaySortArray();
			
			
		} // end of infinite loop
		
	} // end of run
	
	private void selectionSort(){
		// beginning of selection sort
		int smallest;
		int temp;
				
		for(int i = 0; i < sortArray.length - 1; i++){
			
			smallest = i;
			
			if(stop)
				break;
			
			for(int j = i+1; j < sortArray.length; j++){
				if(stop)
					break;
				
				stepCounter++;
				compareCounter++;
				
				stepCounterLabel.setText(Integer.toString(stepCounter));
				stepCounterLabel.repaint();
				
				compareCounterLabel.setText(Integer.toString(compareCounter));
				compareCounterLabel.repaint();
				
				displayTime();

				if(sortArray[j] < sortArray[smallest]){
					if(stop)
						break;

					smallest = j;
				} // end of if
			} // end of for
			
			
			if(i != smallest){
				if(stop)
					break;
				
				stepCounter++;
				moveCounter++;
				
				stepCounterLabel.setText(Integer.toString(stepCounter));
				stepCounterLabel.repaint();
				
				moveCounterLabel.setText(Integer.toString(moveCounter));
				moveCounterLabel.repaint();
				
				displayTime();
				
				temp = sortArray[i];
				sortArray[i] = sortArray[smallest];
				sortArray[smallest] = temp;
			} // end of if
			
			if(stop)
				break;
		} // end of for
	} // end of slectionSort
	
	private void  insertionSort(){
		int insert; 
		
		for(int next = 1; next < sortArray.length; next++){
			insert = sortArray[next];  

			if(stop)
				break;

			int moveItem = next;
			
			
		
		    while(moveItem > 0 && sortArray[moveItem - 1] > insert){
		    	stepCounter++;
		    	compareCounter++;
		    	
		    	stepCounterLabel.setText(Integer.toString(stepCounter));
				stepCounterLabel.repaint();
				
				compareCounterLabel.setText(Integer.toString(compareCounter));
				compareCounterLabel.repaint();
				
				displayTime();

				if(stop)
					break;
					
		    	sortArray[moveItem] = sortArray[moveItem - 1];
		    	sortArray[moveItem - 1] = insert; 
		    	
		    	stepCounter++;
		    	moveCounter++;
		    	
		    	stepCounterLabel.setText(Integer.toString(stepCounter));
				stepCounterLabel.repaint();
				
				moveCounterLabel.setText(Integer.toString(moveCounter));
				moveCounterLabel.repaint();
				
				displayTime();

				if(stop)
					break;
		    			    	
				
		        moveItem--;                             
		    } // end while      
		    
		    if(moveItem - 1 >= 0){
		    	stepCounter++;
		    	compareCounter++;
		    	
		    	stepCounterLabel.setText(Integer.toString(stepCounter));
				stepCounterLabel.repaint();
				
				compareCounterLabel.setText(Integer.toString(compareCounter));
				compareCounterLabel.repaint();
				
				displayTime();

				if(stop)
					break;
		    } // end of if
		    		    
		} // end for                                               
	} // end of insertionSort
	
	private void bubbleSort(){
		for(int i = sortArray.length - 1; i >= 0; i--){
			boolean swapped = false;
		    
			for(int j = 0; j < i; j++){
				
				if(stop)
					break;
				
				stepCounter++;
				compareCounter++;
				
				stepCounterLabel.setText(Integer.toString(stepCounter));
				stepCounterLabel.repaint();
				
				compareCounterLabel.setText(Integer.toString(compareCounter));
				compareCounterLabel.repaint();
				
				displayTime();
	
				if(stop)
					break;
					
		    	if(sortArray[j] > sortArray[j+1]){
		    		stepCounter++;
		    		moveCounter++;
		    		
		    		stepCounterLabel.setText(Integer.toString(stepCounter));
					stepCounterLabel.repaint();
					
					moveCounterLabel.setText(Integer.toString(moveCounter));
					moveCounterLabel.repaint();
					
					displayTime();

					if(stop)
						break;
		    		
		    		int temp = sortArray[j];
		    		sortArray[j] = sortArray[j+1];
		    		sortArray[j+1] = temp;
		    		
		    		if(stop)
						break;
			    		
		    		swapped = true;
		    	} // end of if
		    	
				if(stop)
					break;

		    } // end of for
			
			if(!swapped || stop)
				break;
		} // end of for
	} // end of bubbleSort
	
	private void biDirectionalBubbleSort(){
		int firstIndex = 0;
		int lastIndex = sortArray.length - 1;
		
		int temp;

		boolean swap;
		
		while((firstIndex < lastIndex) && !stop){
			
			swap = false;
			
			for(int index = firstIndex; index < lastIndex; index++){
				stepCounter++;
				compareCounter++;
				
				stepCounterLabel.setText(Integer.toString(stepCounter));
				stepCounterLabel.repaint();
				
				compareCounterLabel.setText(Integer.toString(compareCounter));
				compareCounterLabel.repaint();
				
				displayTime();
				
				if(stop)
					break;
				
				if(sortArray[index] > sortArray[index+1]){
					stepCounter++;
					moveCounter++;
										
					stepCounterLabel.setText(Integer.toString(stepCounter));
					stepCounterLabel.repaint();
					
					moveCounterLabel.setText(Integer.toString(moveCounter));
					moveCounterLabel.repaint();
					
					displayTime();
					
					if(stop)
						break;
					
					temp = sortArray[index];
					sortArray[index] = sortArray[index+1];
					sortArray[index+1] = temp;
					
					if(stop)
						break;
					
					swap = true;
	
					if(stop)
						break;
				} // end of if

				if(stop)
					break;
			} // end of for
			
			lastIndex--;
			
			if(swap && !stop){
				
				swap = false;
				
			for(int index = lastIndex; index > firstIndex; index--){
				stepCounter++;
				compareCounter++;
				
				stepCounterLabel.setText(Integer.toString(stepCounter));
				stepCounterLabel.repaint();
				
				compareCounterLabel.setText(Integer.toString(compareCounter));
				compareCounterLabel.repaint();
				
				displayTime();
				
				if(stop)
					break;
				
				if(sortArray[index-1] > sortArray[index]){
					stepCounter++;
					moveCounter++;
					
					stepCounterLabel.setText(Integer.toString(stepCounter));
					stepCounterLabel.repaint();
					
					moveCounterLabel.setText(Integer.toString(moveCounter));
					moveCounterLabel.repaint();
					
					displayTime();
					
					if(stop)
						break;
							
					temp = sortArray[index];
					sortArray[index] = sortArray[index-1];
					sortArray[index-1] = temp;
										
					swap = true;

					if(stop)
						break;
				} // end of if

				if(stop)
					break;
				
			} // end of for
			} // end of if
									
			firstIndex++;
			
			if(!swap || stop)
				break;
				
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
			
			stepCounterLabel.setText(Integer.toString(stepCounter));
			stepCounterLabel.repaint();
			
			compareCounterLabel.setText(Integer.toString(compareCounter));
			compareCounterLabel.repaint();
			
			displayTime();
			
			if(stop)
				break;
			
			if (sortArray[first1] < sortArray[first2]){
				if(stop)
					break;
				
				tempArray[index] = sortArray[first1];
		        ++first1;
		    }
		    else{
		    	if(stop)
					break;
		    	
		    	tempArray[index] = sortArray[first2];
		    	++first2;
		    } // end of if
		} // end of for
		

		for(; first1 <= last1; ++first1, ++index){
			tempArray[index] = sortArray[first1];
			if(stop)
				break;
		}

		for(; first2 <= last2; ++first2, ++index){
			tempArray[index] = sortArray[first2];
			if(stop)
				break;
		}
		
		for(index = first; index <= last; ++index){
			stepCounter++;
			moveCounter++;
			
			stepCounterLabel.setText(Integer.toString(stepCounter));
			stepCounterLabel.repaint();
			
			moveCounterLabel.setText(Integer.toString(moveCounter));
			moveCounterLabel.repaint();
			
			displayTime();
			
		    sortArray[index] = tempArray[index];

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
		
		/*
		if((sortArray[first] == sortArray[(first+last)/2]) && (sortArray[first] == sortArray[last]))
			pivotIndex = first;
		
		if(sortArray[first] >= sortArray[(first+last)/2] && sortArray[first] <= sortArray[last])
			pivotIndex = first;
		
		if(sortArray[first] <= sortArray[(first+last)/2] && sortArray[first] >= sortArray[last])
			pivotIndex = first;
		
		if(sortArray[(first+last)/2] >= sortArray[first] && sortArray[(first+last)/2] <= sortArray[last])
			pivotIndex = (first+last)/2;
		
		if(sortArray[(first+last)/2] <= sortArray[first] && sortArray[(first+last)/2] >= sortArray[last])
			pivotIndex = (first+last)/2;
		
		if(sortArray[last] >= sortArray[first] && sortArray[last] <= sortArray[(first+last)/2])
			pivotIndex = last;
		
		if(sortArray[last] <= sortArray[first] && sortArray[last] >= sortArray[(first+last)/2])
			pivotIndex = last;
		*/
		

		
		int average=(sortArray[first] + sortArray[(first+last)/2] + sortArray[last])/3;
		
		for(int index = first + 1; index <= last; index++){
			  
			  stepCounter++;
			  compareCounter++;
			  
			  stepCounterLabel.setText(Integer.toString(stepCounter));
			  stepCounterLabel.repaint();
				
			  compareCounterLabel.setText(Integer.toString(compareCounter));
			  compareCounterLabel.repaint();
				
			  displayTime();
				
			
			  if(stop)
				  break;
			  			  			  
		      if(average <= sortArray[index]){
		    	  pivotIndex = index; 
		    	  break;
		      } // end of if
		      
		     
			  if(stop)
				  break;
		} // end of for
		
		
		if(pivotIndex != first){
			stepCounter++;
			moveCounter++;
			
			stepCounterLabel.setText(Integer.toString(stepCounter));
			stepCounterLabel.repaint();
			
			moveCounterLabel.setText(Integer.toString(moveCounter));
			moveCounterLabel.repaint();
			
			displayTime();
			
			if(stop)
				return;
			
			swap(first, pivotIndex);

			if(stop)
				return;
			
		} // end of if
		
	
	} // end of choosePivot
	
	private int partition(int first, int last){
		choosePivot(first, last);

		int pivot = sortArray[first];

		int lastS1 = first;           
		int firstUnknown = first + 1; 

		if(stop)
			return 0;
		                                 
		for(; firstUnknown <= last; ++firstUnknown){
			stepCounter++;
			compareCounter++;
			
			stepCounterLabel.setText(Integer.toString(stepCounter));
			stepCounterLabel.repaint();
				
			compareCounterLabel.setText(Integer.toString(compareCounter));
			compareCounterLabel.repaint();
				
			displayTime();
			
			if(stop)
				break;
			
			if(sortArray[firstUnknown] < pivot){
				++lastS1;
				
				if(lastS1 != firstUnknown){
					stepCounter++;
					moveCounter++;
					
					stepCounterLabel.setText(Integer.toString(stepCounter));
					stepCounterLabel.repaint();
					
					moveCounterLabel.setText(Integer.toString(moveCounter));
					moveCounterLabel.repaint();
					
					displayTime();
					
					if(stop)
						break;
				
					swap(firstUnknown, lastS1);

					if(stop)
						break;

				} // end of if
			}  // end of if

			if(stop)
				break;
		}  // end of for

		
		if(first != lastS1){
			stepCounter++;
			moveCounter++;
			
			stepCounterLabel.setText(Integer.toString(stepCounter));
			stepCounterLabel.repaint();
			
			moveCounterLabel.setText(Integer.toString(moveCounter));
			moveCounterLabel.repaint();
			
			displayTime();

			if(stop)
				return 0;
		
			if(!stop){
				swap(first, lastS1);
			}
			
			if(stop)
				return 0;
		} // end of if

		return lastS1;
	} // end of partition
	
	void swap(int index1, int index2){
		int temp = sortArray[index1];
		sortArray[index1] = sortArray[index2];
		sortArray[index2] = temp;
	} // end of swap
	
	private void unsortArray(){
		sortArray = new int[size];
		
		generator = new Random();
		
		for(int i = 0; i < sortArray.length; i++)
			sortArray[i] = generator.nextInt(9999);
	} // end of unsortArray
	
	private void createArray(){
		sortArray = new int[size];
		
		generator = new Random();
		
		for(int i = 0; i < sortArray.length; i++)
			sortArray[i] = generator.nextInt(9999);
			//sortArray[i] = i+1;
		
		//displaySortArray();
		
		if(size == 0){
			currentStatus.setText("Tusčias");
			finalPlace.setText(" ");
		}
		else if(size > 0){
			currentStatus.setText("Ne rikiuotas");
			//System.out.println(currentStatus.getMaximumSize().toString());
		}
		
		if(arrayToSort > 0)
			compareViewToolBar.enableSortButton();
		else if(arrayToSort == 0){
			compareViewToolBar.disableSortButton();
			compareViewChartPanel.clearData();
			compareViewChartPanel.resetChart();
			
			//compareViewChartPanel.revalidate();
		}
	} // end of createArray
	
	@SuppressWarnings("unused")
	private void displaySortArray(){
		System.out.print('\n');
		for(int i = 0; i < sortArray.length; i++)
			System.out.printf("%5d", sortArray[i]);
	} // end of displaySortArray
	
	private void displayTime(){
		stopTime = Calendar.getInstance();
		
		int stopMiliseconds, deltaMiliseconds;
		int hours = 0, minutes = 0, seconds = 0, miliseconds = 0;
		
		stopMiliseconds = stopTime.get(Calendar.HOUR_OF_DAY) * 60 * 60 * 1000 +
						  stopTime.get(Calendar.MINUTE) * 60 * 1000 +
						  stopTime.get(Calendar.SECOND) * 1000 +
						  stopTime.get(Calendar.MILLISECOND);
		
		deltaMiliseconds = stopMiliseconds - startMiliSeconds;
		
		miliseconds = deltaMiliseconds % 1000;
		deltaMiliseconds = deltaMiliseconds / 1000;
		seconds = deltaMiliseconds % 60;
		deltaMiliseconds = deltaMiliseconds / 60;
		minutes = deltaMiliseconds % 60;
		deltaMiliseconds = deltaMiliseconds / 60;
		hours = deltaMiliseconds;
		
		
		//System.out.printf("%d:%d:%d:%d\n", hours, minutes, seconds, miliseconds);

		String hoursStr = "00", minutesStr = "00", secondsStr = "00", milisecondsStr = "000";
		
		if(miliseconds == 0)
			milisecondsStr = "000";
		else if(miliseconds < 10)
			milisecondsStr = "00" + Integer.toString(miliseconds);
		else if(miliseconds < 100)
			milisecondsStr = "0" + Integer.toString(miliseconds);
		else
			milisecondsStr = Integer.toString(miliseconds);
		
		if(seconds == 0)
			secondsStr = "00";
		else if(seconds < 10)
			secondsStr = "0" + Integer.toString(seconds);
		else
			secondsStr = Integer.toString(seconds);
		
		if(minutes == 0)
			minutesStr = "00";
		else if(minutes < 10)
			minutesStr = "0" + Integer.toString(minutes);
		else
			minutesStr = Integer.toString(minutes);
		
		if(hours == 0)
			hoursStr = "00";
		else if(minutes < 10)
			hoursStr = "0" + Integer.toString(hours);
		else
			hoursStr = Integer.toString(hours);
		
		//System.out.printf("%s:%s:%s:%s\n", hoursStr, minutesStr, secondsStr, milisecondsStr);
		
		totalTime.setText(hoursStr + ":" + minutesStr + ":" + secondsStr + ":" + milisecondsStr);
		
		totalTime.revalidate();
		
		
		
	} // end of displayTime
	
	public void startSort(){
		if(size > 0){
			start = true;
			active = true;
			activeSorts = arrayToSort;
			//System.out.println("Start true!");
		}
	} // end of startSort 
	
	public void stopSort(){
		if(size > 0){
			stop = true;
			compareViewToolBar.disableStopButton();
		}
	}
	
	public boolean isActive(){
		return active;
	} // end of isActive
	
	public void enableComboSpiner(){
		sortAlgorithm.setEnabled(true);
		arraySize.setEnabled(true);
	} // end of enableComboSpiner

	public void disableComboSpiner(){
		sortAlgorithm.setEnabled(false);
		arraySize.setEnabled(false);
	} // end of enableComboSpiner
	
} // end of class CompareViewSubPanel
