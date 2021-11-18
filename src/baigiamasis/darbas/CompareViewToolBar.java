package baigiamasis.darbas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class CompareViewToolBar extends JToolBar{
	private JButton sortButton;
	private JButton stopButton;
	
	private CompareViewSubPanel [] compareViewSubPanelArray;
	
	//private CompareViewChartPanel compareViewChartPanel;
	
	public CompareViewToolBar(JTabbedPane tabbedPane, final CompareViewChartPanel compareViewChartPanel){
		//this.compareViewChartPanel = compareViewChartPanel;
		
		compareViewSubPanelArray = new CompareViewSubPanel[6];
		
		for(int i = 0; i < 6; i++)
			compareViewSubPanelArray[i] = new CompareViewSubPanel(this, tabbedPane, compareViewChartPanel);
				
		this.setFloatable(false);
		
		sortButton = new JButton("Rikiuoti");
		sortButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				compareViewChartPanel.clearData();
				//System.out.println("Start");
				for(int i = 0; i < 6; i++)
					compareViewSubPanelArray[i].startSort();
			}});
		sortButton.setEnabled(false);
		add(sortButton);
		
		addSeparator();
		
		stopButton = new JButton("Nutraukti");
		stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < 6; i++){
					if(compareViewSubPanelArray[i].isActive())
						compareViewSubPanelArray[i].stopSort();
				} // end of for
			}});
		stopButton.setEnabled(false);
		add(stopButton);
		
	} // end of default constructor
	
	public CompareViewSubPanel [] returnCompareViewSubPanelArray(){
		return compareViewSubPanelArray; 
	}
	
	public void enableSortButton(){
		sortButton.setEnabled(true);
	} // end of enableSortButton
	
	public void disableSortButton(){
		sortButton.setEnabled(false);
	} // end of disableSortButton
	
	public void enableStopButton(){
		stopButton.setEnabled(true);
	} // end of enableStopButton
	
	public void disableStopButton(){
		stopButton.setEnabled(false);
	} // end of disableStopButton
	
	public void enableComboSpinner(){
		for(int i = 0; i < 6; i++)
			compareViewSubPanelArray[i].enableComboSpiner();
	} // end of enableComboSpinner 
	
	public void disableComboSpinner(){
		for(int i = 0; i < 6; i++)
			compareViewSubPanelArray[i].disableComboSpiner();
	} // end of disableComboSpinner 

} // end of CompareViewToolBar
