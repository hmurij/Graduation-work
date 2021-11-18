package baigiamasis.darbas;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.MatteBorder;

@SuppressWarnings("serial")
public class LegendPanel extends JPanel{
	private JLabel unsortedLegend;	
	private JLabel comparedLegend;
	private JLabel moveLegend;
	private JLabel sortedElement;
	
	public LegendPanel(){
		setBorder(new MatteBorder(1, 1, 0, 1, Color.gray));
		
		unsortedLegend = new JLabel("");
		unsortedLegend.setBackground(Color.red);
		unsortedLegend.setOpaque(true);
		unsortedLegend.setPreferredSize(new Dimension(15, 15));
		unsortedLegend.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK), 
			      				 new BevelBorder(BevelBorder.RAISED)));
		add(unsortedLegend);
		
		add(new JLabel("- Ne Rikiuotas Elementas"));
		
		comparedLegend = new JLabel("");
		comparedLegend.setBackground(Color.orange);
		comparedLegend.setOpaque(true);
		comparedLegend.setPreferredSize(new Dimension(15, 15));
		comparedLegend.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK), 
			      				 new BevelBorder(BevelBorder.RAISED)));
		add(comparedLegend);
		
		add(new JLabel("- Palyginamas Elementas"));
		
		moveLegend = new JLabel("");
		moveLegend.setBackground(Color.yellow);
		moveLegend.setOpaque(true);
		moveLegend.setPreferredSize(new Dimension(15, 15));
		moveLegend.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK), 
			      			 new BevelBorder(BevelBorder.RAISED)));
		add(moveLegend);
		
		add(new JLabel("- Perkėlimas Elementas"));
		
		sortedElement = new JLabel("");
		sortedElement.setBackground(Color.green);
		sortedElement.setOpaque(true);
		sortedElement.setPreferredSize(new Dimension(15, 15));
		sortedElement.setBorder(new CompoundBorder(new MatteBorder(1, 1, 1, 1, Color.BLACK), 
			      			 new BevelBorder(BevelBorder.RAISED)));
		add(sortedElement);
		
		add(new JLabel("- Išrikiuotas Elementas"));
			
	} // end of default constructor

} // end of class LegendPanel
