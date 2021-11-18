package baigiamasis.darbas;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class CompareViewLegendPanel extends JPanel{
	private JLabel stepCounterLegend;	
	private JLabel compareCounterLegend;
	private JLabel moveCounterLegend;
	
	public CompareViewLegendPanel(){
		setBorder(new MatteBorder(1, 1, 1, 1, Color.gray));
		
		stepCounterLegend = new JLabel("");
		stepCounterLegend.setBackground(new Color(0, 128, 255));
		stepCounterLegend.setOpaque(true);
		stepCounterLegend.setPreferredSize(new Dimension(15, 15));
		stepCounterLegend.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK), 
			      				    new BevelBorder(BevelBorder.RAISED)));
		add(stepCounterLegend);
		add(new JLabel("- Žingsniai"));
		
		compareCounterLegend = new JLabel("");
		compareCounterLegend.setBackground(new Color(255, 255, 0));
		compareCounterLegend.setOpaque(true);
		compareCounterLegend.setPreferredSize(new Dimension(15, 15));
		compareCounterLegend.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK), 
			      				    new BevelBorder(BevelBorder.RAISED)));
		add(compareCounterLegend);
		add(new JLabel("- Palyginimai"));
		
		moveCounterLegend = new JLabel("");
		moveCounterLegend.setBackground(new Color(255, 70, 70));
		moveCounterLegend.setOpaque(true);
		moveCounterLegend.setPreferredSize(new Dimension(15, 15));
		moveCounterLegend.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK), 
			      				    new BevelBorder(BevelBorder.RAISED)));
		add(moveCounterLegend);
		add(new JLabel("- Perkėlimai"));
	} // end of constructor
	

} // end of CompareViewLegendPanel
