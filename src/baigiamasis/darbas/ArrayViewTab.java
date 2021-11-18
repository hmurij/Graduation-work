package baigiamasis.darbas;

import java.awt.BorderLayout;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

//public class ArrayViewTab extends JPanel{
@SuppressWarnings("serial")
public class ArrayViewTab extends JSplitPane{
	
	private SortArrayViewPanel sortArrayViewPanel;
	private JScrollPane scroll;
	private ToolBar toolBar;
	private SouthPanel southPanel;
	private JMenuItem saveItem;
	
	private JPanel upperPanel;
	//private JPanel lowerPanel;
	
	private ExecutorService runner;
	
	public ArrayViewTab(JTabbedPane tabbedPane){
				
		//setLayout(new BorderLayout());
		saveItem = new JMenuItem("Išsaugoti Kaip...");
		
		southPanel = new SouthPanel(saveItem);
		
		//add(southPanel, BorderLayout.SOUTH);

				
		sortArrayViewPanel = new SortArrayViewPanel(southPanel, tabbedPane, saveItem);
		scroll = new JScrollPane(sortArrayViewPanel, 
								 JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				 				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		
		//add(scroll, BorderLayout.CENTER);
		
		toolBar = sortArrayViewPanel.returnToolBar();
		
		//add(toolBar, BorderLayout.NORTH);
		
		upperPanel = new JPanel();
		upperPanel.setLayout(new BorderLayout());
		upperPanel.add(toolBar, BorderLayout.NORTH);
		upperPanel.add(scroll);
		
		
		this.setLeftComponent(upperPanel);
		this.setOrientation(VERTICAL_SPLIT);
		this.setRightComponent(southPanel);
		this.setDividerSize(3);
		this.setDividerLocation(290);
		
		runner = Executors.newFixedThreadPool(1);
		
		runner.execute(sortArrayViewPanel);
				
		runner.shutdown();
			
	} // end of default constructor
	
	public SouthPanel getSouthPanel(){
		return southPanel;
	} // end of getSouthPanel
	
	public JMenuItem getSaveItem(){
		return saveItem;
	}


} // end of ArrayViewTab
