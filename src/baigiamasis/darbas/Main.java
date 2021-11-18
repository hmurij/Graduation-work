package baigiamasis.darbas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.border.MatteBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class Main extends JFrame{
	private JMenuBar menuBar;
	private ArrayViewTab arrayViewTab;
	private CompareViewTab compareViewTab;
	//private JSpinner numberOfElementsPerRow;
	private JTabbedPane tabbedPane;
	
	private JMenuItem saveItem;
	
	public Main() { 
		tabbedPane = new JTabbedPane();
		
		arrayViewTab = new ArrayViewTab(tabbedPane);
		compareViewTab = new CompareViewTab(tabbedPane);
		
		// setting up menu
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("Failas");
		saveItem = arrayViewTab.getSaveItem();
		saveItem.setEnabled(false);
		saveItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser( );
				
				int option = chooser.showSaveDialog(Main.this);

				if (option == JFileChooser.APPROVE_OPTION){
					FileWriter outStream;
					try {
						outStream = new FileWriter(chooser.getSelectedFile());
						PrintWriter saveWriter = new PrintWriter(outStream);
						arrayViewTab.getSouthPanel().getLogField().write(saveWriter);
					
						saveWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} // end of if
			}});
		fileMenu.add(saveItem);
		fileMenu.addSeparator();
		JMenuItem exitItem = new JMenuItem("Išeiti");
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0); 			
			}});
		fileMenu.add(exitItem);
		
		menuBar.add(fileMenu);
		menuBar.setBorder(new MatteBorder(0, 0, 1, 0, Color.gray));
		
		JMenu helpMenu = new JMenu("Pagalba");
		JMenuItem aboutItem = new JMenuItem("Informacija apie Kūrėja");
		aboutItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new InformacijaApieKureja(Main.this);
			}});
		JMenuItem helpItem = new JMenuItem("Vartotojo Vadovas");
		helpItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new VartotojoVadovas(tabbedPane.getSelectedIndex(), Main.this);
			}});
		helpMenu.add(helpItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutItem);
				
		menuBar.add(helpMenu);
		
		
		this.setJMenuBar(menuBar);
		
		//tabbedPane = new JTabbedPane();
		
				
		tabbedPane.add("Vizualziacija", arrayViewTab);
		tabbedPane.add("Palyginimas", compareViewTab);
		
		//tabbedPane.setEnabledAt(1, false);
		
				
		tabbedPane.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent arg0) {
							
				if(tabbedPane.getSelectedComponent().equals(arrayViewTab)){
					//System.out.println("arrayVewTab is selected");
					
					if(arrayViewTab.getSouthPanel().getLogField().getCaretPosition() > 0)
						saveItem.setEnabled(true);
				}
				else if(tabbedPane.getSelectedComponent().equals(compareViewTab)){
					//System.out.println("compareViewTab is selected");
					saveItem.setEnabled(false);
					
				} // end of else if
			} // end of stateChanged
			
		});
				
				
		this.setContentPane(tabbedPane);
		this.setTitle("Rikiavimo algoritmų lyginamoji analizė");
				
		setSize(790, 600);
		
		//System.out.println(this.getGraphicsConfiguration().getBounds().width + " " +
		//		this.getGraphicsConfiguration().getBounds().height);
		
		this.setLocation((this.getGraphicsConfiguration().getBounds().width - 790)/2, 
						 (this.getGraphicsConfiguration().getBounds().height - 600)/2);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	} // end of default constructor

	public static void main(String[] args) {
		
		//int [] array = {5, 3, 6, 2, 8, 1, 9, 7, 4};
		//int [] array = {3, 2, 1, 20, 15, 9, 4, 5, 8, 12, 11, 13, 17, 18};
		//int [] array = {2};
		
		new Main();
		
		//for(int i = 0; i < array.length; i++)
		//	System.out.printf("%3d", array[i]);
		
		
		
		

	} // end of main
	
	@SuppressWarnings("unused")
	private void quickSort(int [] sortArray, int first, int last){
		int pivotIndex = first;
		if(first < last){
			pivotIndex = partition(sortArray, first, last);
			quickSort(sortArray, first, pivotIndex-1);
			quickSort(sortArray, pivotIndex+1, last);
		} // end of if
	} // end of quickSort
	
	private int partition(int [] sortArray, int first, int last){
		choosePivot(sortArray, first, last);

		int pivot = sortArray[first];

		int lastS1 = first;           
		int firstUnknown = first + 1; 
		                                 
		for(; firstUnknown <= last; ++firstUnknown){
			if(sortArray[firstUnknown] < pivot){
				++lastS1;
				
				if(lastS1 != firstUnknown)
					swap(sortArray, firstUnknown, lastS1);
			}  // end of if
		}  // end of for

		
		if(first != lastS1)
			swap(sortArray, first, lastS1);
		
		return lastS1;
	} // end of partition
	
	private void choosePivot(int [] sortArray, int first, int last){
		int pivotIndex = first;

		int average=(sortArray[first] + sortArray[(first+last)/2] + sortArray[last])/3;
		
		for(int index = first + 1; index <= last; index++){
			if(average <= sortArray[index]){
				pivotIndex = index; 
				break;
		    } // end of if
		} // end of for
		  
		if(pivotIndex != first)
			swap(sortArray, first, pivotIndex);
	} // end of choosePivot
	
	void mergeSort(int [] theArray, int first, int last){
		if (first < last){
			int mid = (first + last)/2; 
			mergeSort(theArray, first, mid);
			mergeSort(theArray, mid+1, last);
			merge(theArray, first, mid, last);
		} // End of if
	} // end of mergeSort
	
	void merge(int [] theArray, int first , int mid, int last){
		int [] tempArray = new int[theArray.length]; 
		int first1 = first; 
		int last1 = mid;
		int first2 = mid + 1 ; 
		int last2 = last; 
		int index = first1; 
	
		for(; (first1 <= last1) && (first2 <= last2); ++ index){
			if(theArray[first1] < theArray[first2]){
				tempArray[index] = theArray[first1];
				++first1;
			} // end of if
			else{
				tempArray[index] = theArray[first2];
				++first2;
			} // end of else
		} // end of for
	
		for(; first1 <= last1; ++first1, ++index)
			tempArray[index] = theArray[first1];
	
		for(; first2 <= last2; ++first2, ++index)
			tempArray[index] = theArray[first2];
		
		for (index = first; index <= last; ++index)
			theArray[index] = tempArray[index];
	} // end of merge
	
	void insertionSort(int [] theArray){
		for(int unsorted = 1; unsorted < theArray.length; ++unsorted){
			int nextltem = theArray[unsorted];
			int loc = unsorted;
			for(;(loc > 0) && (theArray[loc - 1] > nextltem) ;--loc)
				theArray[loc] = theArray[loc - 1];
			theArray[loc] = nextltem;
		} // end of for 
	} // end of insertionSort 
	
	void bubbleSort(int [] theArray){
		boolean sorted = false; 
		for (int pass = 1; (pass < theArray.length) && !sorted; ++pass){
			sorted = true; 
				for(int index = 0; index < theArray.length - pass; ++index){
					int nextIndex = index + 1;
					if (theArray[index] > theArray[nextIndex]){
						swap(theArray, index, nextIndex);
						sorted = false;
					} // end of if
				} // end of for
		} // end of for
	} // end of bubbleSort
	
	void selectionSort(int [] theArray){
		for (int first = 0; first < theArray.length - 1; first++)	{
			int smalest = indexOfSmallest(theArray, first);
			swap(theArray, smalest, first);
		} // end of for
	} // end of selectionSort
	
	int indexOfSmallest(int [] theArray, int first){
		int indexSoFar = first;
		for (int currentIndex = first + 1; currentIndex < theArray.length; currentIndex++)
			if(theArray[indexSoFar] > theArray[currentIndex])
				indexSoFar = currentIndex;

		return indexSoFar;
	} // end of indexOfSmallest
	
	void swap(int [] theArray, int i, int j){
		int temp = theArray[i];
		theArray[i] = theArray[j];
		theArray[j] = temp;
	} // end of swap
} // end of class test
