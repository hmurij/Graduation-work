package baigiamasis.darbas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CompareViewChartPanel extends JPanel{
	private Lock accessLock = new ReentrantLock(); 
	
	private int [][] data;
	private int size = 0;
	
	//private int mouseX;
	//private int mouseY;
	
	//private Point point = null;
	
	private int maxStep = 0;
	
	//private Shape s;
	
	public CompareViewChartPanel(){
		//add(new JLabel("Grafikas"));
		//this.addMouseMotionListener(this);
		
		data = new int[6][5];
		
		

		
		
	} // end of default constructor
	
	public void paintComponent(Graphics g){
		//accessLock.lock();
		super.paintComponent(g); 
		this.setBackground(Color.white);
		
		Graphics2D g2 = (Graphics2D) g;

		blankChart(g);
		
		if(size > 0)
			paintChart(g2);
				
		//accessLock.unlock();
	} // end of paintComponent
	
	public void paintChart(Graphics2D g2){
		int step = 20 + (getWidth() - 30)/12;
		
		//System.out.println("Paint size: " + size);
		
		String algorithmName = null;
		int numberOffset = 0;
		int nameOffset = 0;
		
				
		if(size > 0)
			maxStep = data[size - 1][0];
		float maxPixels = getHeight() - 50;
		
		for(int i = 0; i < size; i++){
			
			Font font = new Font("Dialog", Font.BOLD, 12);
			g2.setFont(font);
			
			//System.out.println(g2.getFont().toString());
			
			//System.out.println(this.getGraphics().getFont().toString());

			
			g2.setColor(new Color(0, 128, 255));
			g2.fill3DRect(step - 45, (int)(getHeight() - 40 - ((float)data[i][0]/(float)maxStep)*maxPixels), 
						  30, (int)(((float)data[i][0]/(float)maxStep)*(float)maxPixels), true);
			
			g2.setColor(Color.black);
			g2.rotate(270 * Math.PI / 180, step - 25, getHeight()- 45);
			g2.drawString(Integer.toString(data[i][0]), step - 25, (getHeight() - 45));
			g2.rotate(90 * Math.PI / 180, step - 25, getHeight()- 45);
			
			//g2.drawLine(step - 30, (int)(getHeight() - 40), step - 30, 
			//		   (int)(getHeight() - 40 - ((float)data[i][0]/(float)maxStep)*maxPixels));
			
			//g2.rotate(345 * Math.PI / 180);
			g2.setColor(new Color(255, 255, 0));
			g2.fill3DRect(step - 15, (int)(getHeight() - 40 - ((float)data[i][1]/(float)maxStep)*maxPixels), 
						  30, (int)(((float)data[i][1]/(float)maxStep)*(float)maxPixels), true);
			
			g2.setColor(Color.black);
			g2.rotate(270 * Math.PI / 180, step + 5, getHeight()- 45);
			g2.drawString(Integer.toString(data[i][1]), step + 5, (getHeight() - 45));
			g2.rotate(90 * Math.PI / 180, step + 5, getHeight()- 45);
			
			//g2.drawLine(step, (int)(getHeight() - 40), step, 
			//		   (int)(getHeight() - 40 - ((float)data[i][1]/(float)maxStep)*maxPixels));
			
			g2.setColor(new Color(255, 70, 70));
			g2.fill3DRect(step + 15, (int)(getHeight() - 40 - ((float)data[i][2]/(float)maxStep)*maxPixels), 
						  30, (int)(((float)data[i][2]/(float)maxStep)*(float)maxPixels), true);
			
			g2.setColor(Color.black);
			g2.rotate(270 * Math.PI / 180, step + 35, getHeight()- 45);
			g2.drawString(Integer.toString(data[i][2]), step + 35, (getHeight() - 45));
			g2.rotate(90 * Math.PI / 180, step + 35, getHeight()- 45);
			
			//g2.drawLine(step + 30, (int)(getHeight() - 40), step + 30, 
			//		   (int)(getHeight() - 40 - ((float)data[i][2]/(float)maxStep)*maxPixels));
			
			switch(data[i][3]){
				case 0: algorithmName = "Selection Sort"; break;
				case 1: algorithmName = "Insertion Sort"; break;
				case 2: algorithmName = "Bubble Sort"; break;
				case 3: algorithmName = "Bi-dir. Bubble Sort"; break;
				case 4: algorithmName = "Merge Sort"; break;
				case 5: algorithmName = "Quick Sort"; break;
			} // end of switch
			
			if(data[i][3] == 0)
				nameOffset = 40;
			else if(data[i][3] == 1)
				nameOffset = 38;
			else if(data[i][3] == 2)
				nameOffset = 33;
			else if(data[i][3] == 3)
				nameOffset = 50;
			else if(data[i][3] == 4 || data[i][3] == 5)
				nameOffset = 30;
						
			g2.setColor(Color.black);
			g2.drawString(algorithmName, step - nameOffset, (int)(getHeight() - 27));
			
			
			if(data[i][4] < 10)
				numberOffset = 4;
			else if(data[i][4] < 100)
				numberOffset = 8;
			else if(data[i][4] < 1000)
				numberOffset = 12;
			else if(data[i][4] < 10000)
				numberOffset = 15;
			else if(data[i][4] < 100000)
				numberOffset = 18;
			else if(data[i][4] >= 100000)
				numberOffset = 21;
			
			
			g2.drawString(Integer.toString(data[i][4]), step - numberOffset, (int)(getHeight() - 15));
			
			g2.drawString("Elementu", step - 25, (int)(getHeight() - 5));
			step = step + (getWidth() - 30) / 6;
		} // end of for
	} // end of paintChart
	
	public void blankChart(Graphics g){
		Graphics2D g2 = (Graphics2D) g;

		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.black);
		
		g2.drawLine(20, 10, 20, (int)(getHeight()-30));
		g2.drawLine(18, 20, 20, 10);
		g2.drawLine(22, 20, 20, 10);
		
		g2.drawLine(10, (int)(getHeight()-40), (int)(getWidth()-10), (int)(getHeight()-40));
		g2.drawLine((int)(getWidth()-20), (int)(getHeight()-42), (int)(getWidth()-10), (int)(getHeight()-40));
		g2.drawLine((int)(getWidth()-20), (int)(getHeight()-38), (int)(getWidth()-10), (int)(getHeight()-40));
	} // end of blankChart
	
	public void addData(int steps, int compares, int moves,  int sortAlgorithm, int size){
		accessLock.lock();
		
		class ArrayComparator implements Comparator<int []>{
			public int compare(int[] arg0, int[] arg1) {
				//System.out.println("Comparing " + arg0[0] + " and " + arg1[0]);
				if((arg0[0] - arg1[0]) != 0)
					return arg0[0] - arg1[0];
				
				if((arg0[1] - arg1[1]) != 0)
					return arg0[1] - arg1[1];
				
				return arg0[2] - arg1[2];
			} // end of compare
		} // end of class TimeComparator
		
		this.size++;
		
		//System.out.println("size: "	+ this.size);
		
		data[this.size-1][0] = steps;
		data[this.size-1][1] = compares;
		data[this.size-1][2] = moves;
		data[this.size-1][3] = sortAlgorithm;
		data[this.size-1][4] = size;
		
		if(this.size > 1)
			//Arrays.sort(data, new ArrayComparator());
			Arrays.sort(data, 0, this.size, new ArrayComparator());
		
		/*
		for(int i = 0; i < this.size; i++)
			System.out.printf("Steps: %d Compares: %d Moves: %d Algorithm: %d Size: %d\n",
							  data[i][0], data[i][1], data[i][2], data[i][3], data[i][4]);
		*/
		
		accessLock.unlock();
	} // end of setData
	
	public void resetChart(){
		accessLock.lock();
		Dimension d = new Dimension(this.getSize());
		
		//System.out.println(d.toString());
		
		d.setSize(d.getWidth(), d.getHeight()-1);
		
		this.setSize(d);
		
		this.revalidate();
		accessLock.unlock();
	} // end of clearChart
	
	public void clearData(){
		this.size = 0;
	} // end of clearData
	
	/*
	@Override
	public void mouseDragged(MouseEvent arg0) {

	} // end of mouseDragged

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		
		mouseX = arg0.getX();
		mouseY = arg0.getY();
		
		if(s.contains(arg0.getPoint())){
			System.out.println("arg0.getX(): " + mouseX + " arg0.getY(): " + mouseY);
			point = arg0.getPoint();
			
		}
		
		
		
		//System.out.println("mouseX: " + mouseX + " mouseY: " + mouseY);
		
		//arg0.getPoint();
		//System.out.println("arg0.getX(): " + arg0.getX() + " arg0.getY(): " + arg0.getY());
		//System.out.println("arg0.getX(): " + arg0.getXOnScreen() + " arg0.getY(): " + arg0.getYOnScreen());
	} // end of mouseMoved

	*/

} // end of CompareViewChartPanel
