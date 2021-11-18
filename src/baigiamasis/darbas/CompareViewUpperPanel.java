package baigiamasis.darbas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class CompareViewUpperPanel extends JPanel{
	private CompareViewToolBar compareViewToolBar;
	private JPanel compareViewCenterPanel;
	private CompareViewSubPanel [] compareViewSubPanelArray;
	private JScrollPane scroll;
	
	private ExecutorService runner;
	
	public CompareViewUpperPanel(JTabbedPane tabbedPane, CompareViewChartPanel compareViewChartPanel){
		setLayout(new BorderLayout());
		
		compareViewToolBar = new CompareViewToolBar(tabbedPane, compareViewChartPanel);
		add(compareViewToolBar, BorderLayout.NORTH);
		
		compareViewSubPanelArray = compareViewToolBar.returnCompareViewSubPanelArray();
		
		compareViewCenterPanel = new JPanel();
		compareViewCenterPanel.setBorder(new MatteBorder(2, 2, 0, 0, Color.LIGHT_GRAY));
		compareViewCenterPanel.setLayout(new GridLayout(0, 2, 0, 0));
		for(int i = 0; i < 6; i++)
			compareViewCenterPanel.add(compareViewSubPanelArray[i]);
		
		scroll = new JScrollPane(compareViewCenterPanel, 
				 				 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				 				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		add(scroll);
		
		runner = Executors.newFixedThreadPool(6);
		
		for(int i = 0; i < 6; i++)
			runner.execute(compareViewSubPanelArray[i]);
				
		runner.shutdown();
	} // end of default constructor

} // end of CompareViewUpperPanel
