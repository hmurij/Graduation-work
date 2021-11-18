package baigiamasis.darbas;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ToolBar extends JToolBar{
	private JSpinner arraySize;
	private JSpinner sleepTime;
	private JButton sortButton;
	private JButton pauseButton;
	private JButton stopButton;
	private JButton unsortButton;
	private JComboBox sortAlgorithm;

	public ToolBar(final SortArrayViewPanel sortArrayViewPanel){
		this.setFloatable(false);
					
		JLabel arraySizeLabel = new JLabel("Masyvo Dydis:");
		this.add(arraySizeLabel);
		
		arraySize = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 10));
		arraySize.setToolTipText("Įveskite Masyvo Dydi");
		arraySize.setMaximumSize(new Dimension(80, 25));
		arraySize.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				sortArrayViewPanel.setSize((Integer)arraySize.getValue());
			}}); 
		add(arraySize);
		
		this.addSeparator();
		
		JLabel algorithmLabel = new JLabel("Algoritmas:");
		add(algorithmLabel);
		
		String [] algortighm = {"Selection Sort", "Insertion Sort", 
								"Bubble Sort", "Bi-directional Bubble Sort", 
								"Merge Sort", "Quick Sort"};
		sortAlgorithm = new JComboBox(algortighm);
		sortAlgorithm.setMaximumSize(new Dimension(150, 25));
		sortAlgorithm.setToolTipText("Pasirinkite Rikiavomo Algoritma");
		sortAlgorithm.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				sortArrayViewPanel.setSearchAlgorithm(sortAlgorithm.getSelectedIndex());
			}});
		add(sortAlgorithm);
		
		addSeparator();
		
		JLabel sleepTimeLabel = new JLabel("Delsimas:");
		this.add(sleepTimeLabel);
		
		sleepTime = new JSpinner(new SpinnerNumberModel(0, 0, 2, 0.01));
		sleepTime.setToolTipText("Įveskite Delsima");
		//sleepTime.setSize(sleepTime.getPreferredSize());
		sleepTime.setMinimumSize(new Dimension(45, 25));
		sleepTime.setMaximumSize(new Dimension(55, 25));
		sleepTime.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				sortArrayViewPanel.setSleepTime((Double)sleepTime.getValue());
			}}); 
		add(sleepTime);
		
		this.addSeparator();
		
		sortButton = new JButton("Rikiuoti");
		
		//sortButton = new JButton(new ImageIcon("sort.JPG"));
		//sortButton.setBounds(1, 1, 1, 1);
		//sortButton.setBorder(new MatteBorder(1, 1, 1, 1, Color.gray));
		//sortButton.setBorderPainted(false);
				
		sortButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				sortArrayViewPanel.startSort();
			}});
		add(sortButton);		
		
		this.addSeparator();
		
		pauseButton = new JButton("Pauze");
		pauseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				sortArrayViewPanel.pauseSort();
			}});
		add(pauseButton);
		
		this.addSeparator();
		
		stopButton = new JButton("Nutraukti");
		stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				sortArrayViewPanel.stopSort();
			}});
		add(stopButton);
		
		this.addSeparator();
		
		
		
		unsortButton = new JButton("Išrikiuoti");
		unsortButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				sortArrayViewPanel.unsortArray();
			}});
		add(unsortButton);
			
	} // end of default constructor
	

	public void disableArraySize(){
		arraySize.setEnabled(false);
	} // end of disableArraySize
	
	public void enableArraySize(){
		arraySize.setEnabled(true);
	} // end of enableArraySize
	
	public void disableSortButton(){
		sortButton.setEnabled(false);
	} // end of disableSortButton
	
	public void enableSortButton(){
		sortButton.setEnabled(true);
	} // end of enableSortButton
	
	public void disableUnsortButton(){
		unsortButton.setEnabled(false);
	} // end of disableUnsortButton
	
	public void enablePauseButton(){
		pauseButton.setEnabled(true);
	} // end of enablePauseButton
	
	public void disablePauseButton(){
		pauseButton.setEnabled(false);
	} // end of disablePauseButton 
	
	public void enableStopButton(){
		stopButton.setEnabled(true);
	} // end of enableStopButton
	
	public void disableStopButton(){
		stopButton.setEnabled(false);
	} // end of disableStopButton
	
	public void enableUnsortButton(){
		unsortButton.setEnabled(true);
	} // end of enableUnsortButton
	
	public void disableSortAlgorithm(){
		sortAlgorithm.setEnabled(false);
	} // end of disableSortAlgorithm
	
	public void enableSortAlgorithm(){
		sortAlgorithm.setEnabled(true);
	} // end of disableSortAlgorithm
	
	public JComboBox getSortAlgorithm(){
		return sortAlgorithm;
	} // end of getSortAlgorithm

	
} // end of class ToolBar
