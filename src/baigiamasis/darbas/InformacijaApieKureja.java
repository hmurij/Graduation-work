package baigiamasis.darbas;

import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class InformacijaApieKureja extends JDialog{
	
	InformacijaApieKureja(Main main){
		
		this.setSize(240, 100);
		this.setTitle("Informacija apie kūrėja");
		this.setLocation(main.getLocationOnScreen().x + 30, 
						 main.getLocationOnScreen().y + 20);
		
		JPanel informacijosPanele = new JPanel();
		informacijosPanele.setLayout(new GridLayout(4, 1));
		informacijosPanele.add(new JLabel("Grupe: PK15"));
		informacijosPanele.add(new JLabel("Kūrėjas: Dmitrij Jafišov"));
		informacijosPanele.add(new JLabel("Mob. Tel: +37065790398"));
		informacijosPanele.add(new JLabel("El. Paštas: dmitrij_jafisov@yahoo.co.uk"));
		informacijosPanele.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		this.setContentPane(informacijosPanele);
		this.setResizable(false);
		this.setModal(true);
		this.setVisible(true);
		
	} // end of default constructor

} // InformacijaApieKureja