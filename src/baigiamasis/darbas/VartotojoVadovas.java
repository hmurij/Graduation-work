package baigiamasis.darbas;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class VartotojoVadovas extends JDialog{
	public VartotojoVadovas(int tabbedPaneSelectedIndex, Main main){
		this.setTitle("Vartotojo Vadovas");
		this.setLocation(main.getLocationOnScreen().x + 30, 
					     main.getLocationOnScreen().y + 20);
		//super.getLocationOnScreen();
		//this.setLocation(this.getParent().getLocationOnScreen().x + 30, this.getParent().getLocationOnScreen().y + 20);
		//System.out.println(this.getParent().getLocationOnScreen().getX());
		//System.out.println(main.getGraphicsConfiguration().getBounds().width + " " + 
		//		main.getGraphicsConfiguration().getBounds().height);
		//System.out.println(main.getLocationOnScreen().x);
				
		JPanel pagalbosPanele = new JPanel();
		pagalbosPanele.setLayout(null);
		
		if(tabbedPaneSelectedIndex == 0){
			this.setSize(405, 310);
			
			JLabel label;

			label = new JLabel("1. Pasirinkite -");
			//label.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));
			label.setSize(label.getPreferredSize());
			label.setLocation(3, 9);
			pagalbosPanele.add(label);

			label = new JLabel(new ImageIcon("images/masyvoDydis.JPG"));
			//label.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));
			label.setSize(label.getPreferredSize());
			label.setLocation(90, 2);
			pagalbosPanele.add(label);
			
			label = new JLabel("2. Pasirinkite -");
			label.setSize(label.getPreferredSize());
			label.setLocation(3, 47);
			pagalbosPanele.add(label);
			
			label = new JLabel(new ImageIcon("images/rikiavimoAlgoritmas.JPG"));
			label.setSize(label.getPreferredSize());
			label.setLocation(90, 40);
			pagalbosPanele.add(label);
			
			label = new JLabel("3. Pasirinkite -");
			label.setSize(label.getPreferredSize());
			label.setLocation(3, 89);
			pagalbosPanele.add(label);
			
			label = new JLabel(new ImageIcon("images/delsymas.JPG"));
			label.setSize(label.getPreferredSize());
			label.setLocation(90, 80);
			pagalbosPanele.add(label);
			
			label = new JLabel("nuo 0 iki 2 sekundžių");
			label.setSize(label.getPreferredSize());
			label.setLocation(205, 89);
			pagalbosPanele.add(label);
			
			label = new JLabel("pastaba - Vykstant rikiavimui Delsima galima reguliuoti pagal");
			label.setSize(label.getPreferredSize());
			label.setLocation(3, 120);
			pagalbosPanele.add(label);
			
			label = new JLabel("poreikius");
			label.setSize(label.getPreferredSize());
			label.setLocation(58, 140);
			pagalbosPanele.add(label);
			
			label = new JLabel("4. Spauskite ");
			label.setSize(label.getPreferredSize());
			label.setLocation(3, 164);
			pagalbosPanele.add(label);
			
			label = new JLabel(new ImageIcon("images/rikiuoti.PNG"));
			label.setSize(label.getPreferredSize());
			label.setLocation(75, 158);
			pagalbosPanele.add(label);
			
			label = new JLabel("kad paleisti rikiavimo procesą");
			label.setSize(label.getPreferredSize());
			label.setLocation(135, 164);
			pagalbosPanele.add(label);
			
			label = new JLabel("Norint laikinai sustabdyti rikiavimą spauskite");
			label.setSize(label.getPreferredSize());
			label.setLocation(3, 191);
			pagalbosPanele.add(label);
			
			label = new JLabel(new ImageIcon("images/pauze.JPG"));
			label.setSize(label.getPreferredSize());
			label.setLocation(260, 185);
			pagalbosPanele.add(label);
			
			label = new JLabel("Norint visiškai sustabdyti rikiavimą spauskite");
			label.setSize(label.getPreferredSize());
			label.setLocation(3, 221);
			pagalbosPanele.add(label);
			
			label = new JLabel(new ImageIcon("images/nutraukti.JPG"));
			label.setSize(label.getPreferredSize());
			label.setLocation(263, 215);
			pagalbosPanele.add(label);
			
			label = new JLabel("Norint išsaugoti ataskaita spauskite");
			label.setSize(label.getPreferredSize());
			label.setLocation(3, 252);
			pagalbosPanele.add(label);
			
			label = new JLabel(new ImageIcon("images/failas.JPG"));
			label.setSize(label.getPreferredSize());
			label.setLocation(210, 249);
			pagalbosPanele.add(label);
			
			label = new JLabel("ir");
			label.setSize(label.getPreferredSize());
			label.setLocation(260, 252);
			pagalbosPanele.add(label);
			
			label = new JLabel(new ImageIcon("images/issaugotKaip.PNG"));
			label.setSize(label.getPreferredSize());
			label.setLocation(270, 247);
			pagalbosPanele.add(label);
			
			//pagalbosPanele.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));			
			//pagalbosPanele.setBorder(new EmptyBorder(5, 5, 5, 5));
			
			
			//pagalbosPanele.add(new JLabel("2. Pasirinkite rikiavomo algoritma"));
			//pagalbosPanele.add(new JLabel(new ImageIcon("rikiavimoAlgoritmas.JPG")));
			//pagalbosPanele.add(new JLabel("3. Nustatykite Delsyma nuo 0 iki 2 sekunziu"));
			//pagalbosPanele.add(new JLabel("4. Paleiskit rikiavimo processa paspaudzius migtuka 'Rikioti'"));
		} // end of if
		else if(tabbedPaneSelectedIndex == 1){
			this.setSize(343, 173);
			
			JLabel label;

			label = new JLabel("1. Pasirinkite -");
			label.setSize(label.getPreferredSize());
			label.setLocation(3, 9);
			pagalbosPanele.add(label);
			
			label = new JLabel(new ImageIcon("images/rikiavimoAlgoritmas.JPG"));
			label.setSize(label.getPreferredSize());
			label.setLocation(90, 2);
			pagalbosPanele.add(label);
			
			label = new JLabel("2. Pasirinkite -");
			//label.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));
			label.setSize(label.getPreferredSize());
			label.setLocation(3, 47);
			pagalbosPanele.add(label);

			label = new JLabel(new ImageIcon("images/masyvoDydis.JPG"));
			//label.setBorder(new MatteBorder(1, 1, 1, 1, Color.red));
			label.setSize(label.getPreferredSize());
			label.setLocation(90, 40);
			pagalbosPanele.add(label);
			
			label = new JLabel("3. Spauskite ");
			label.setSize(label.getPreferredSize());
			label.setLocation(3, 84);
			pagalbosPanele.add(label);
			
			label = new JLabel(new ImageIcon("images/rikiuoti.PNG"));
			label.setSize(label.getPreferredSize());
			label.setLocation(75, 77);
			pagalbosPanele.add(label);
			
			label = new JLabel("kad paleisti rikiavimo procesą");
			label.setSize(label.getPreferredSize());
			label.setLocation(135, 84);
			pagalbosPanele.add(label);
			
			label = new JLabel("Norint visiškai sustabdyti rikiavimą spauskite");
			label.setSize(label.getPreferredSize());
			label.setLocation(3, 116);
			pagalbosPanele.add(label);
			
			label = new JLabel(new ImageIcon("images/nutraukti.JPG"));
			label.setSize(label.getPreferredSize());
			label.setLocation(263, 110);
			pagalbosPanele.add(label);

			
		} // end of else if
		
		//this.pack();
		this.setContentPane(pagalbosPanele);
		this.setResizable(false);
		this.setModal(true);
		this.setVisible(true);
			
	} // end of constructor

} // end of class VartotojoVadovas
