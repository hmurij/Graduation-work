package baigiamasis.darbas;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class SouthPanel extends JPanel{
	private JTextArea logField;
	private LegendPanel legendPanel;
	private JScrollPane scroll;
	
	private JMenuItem saveItem;
	
	private int caretPosition = 0;
	
	public SouthPanel(JMenuItem saveItem){
		this.saveItem = saveItem;
		
		setLayout(new BorderLayout());
		
		legendPanel = new LegendPanel();
		add(legendPanel, BorderLayout.NORTH);
		
		logField = new JTextArea();
		logField.setBorder(new EmptyBorder(1, 5, 1, 1));
		logField.setEditable(false);
				
		scroll = new JScrollPane(logField, 
				 				 JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				 				 JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setPreferredSize(new Dimension(640, 100));
		
		add(scroll, BorderLayout.CENTER);
		
		
	} // end of default constructor
	
	public void appendText(String newLine){
		logField.append(newLine);
		caretPosition = caretPosition + newLine.length();
		logField.setCaretPosition(caretPosition-1);
			
	} // end of appendText
	
	public JTextArea getLogField(){
		return logField;
	} // end of getLogField
	
	public void clearLog(){
		logField.setText("");
		caretPosition = 0;
		
		saveItem.setEnabled(false);
	} // end of clearLog

} // end of Class SouthPanel
