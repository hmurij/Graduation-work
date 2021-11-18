package baigiamasis.darbas;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class CompareViewTab extends JSplitPane{
	private CompareViewUpperPanel compareViewUpperPanel;
	private CompareViewChartPanel compareViewChartPanel;
	
	public CompareViewTab(JTabbedPane tabbedPane){
		
		compareViewChartPanel = new CompareViewChartPanel();
		compareViewUpperPanel = new CompareViewUpperPanel(tabbedPane, compareViewChartPanel);
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new BorderLayout());
		southPanel.add(new CompareViewLegendPanel(), BorderLayout.NORTH);
		southPanel.add(compareViewChartPanel);
				
		this.setLeftComponent(compareViewUpperPanel);
		this.setOrientation(VERTICAL_SPLIT);
		this.setRightComponent(southPanel);
		this.setDividerSize(5);
		this.setDividerLocation(290);
		
	} // end of default constructor
	
} // end of CompareViewTab
