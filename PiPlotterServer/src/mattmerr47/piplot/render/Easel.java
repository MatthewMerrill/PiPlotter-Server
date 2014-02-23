package mattmerr47.piplot.render;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class Easel extends JPanel {
	
	private static final long serialVersionUID = 5632719759297956296L;
	
	public final int width;
	public final int height;
	public final int xPad;
	public final int yPad;
	
	private Stepper[] steppers;
	private Pen pen;
	public BufferedImage paper;
	
	public Easel(int width, int height, int xPad, int yPad){
		
		this.width = width;
		this.height = height;
		this.xPad = xPad;
		this.yPad = yPad;
		
		this.setMinimumSize(new Dimension(width, height));
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
		
		this.setBackground(Color.GRAY);
		
		steppers = new Stepper[]{ new Stepper(this), new Stepper(this)};
		pen = new Pen(this);
		paper = new BufferedImage(width - (2*xPad), height - yPad, BufferedImage.TYPE_INT_RGB);
		
		paper.getGraphics().setColor(Color.WHITE);
		paper.getGraphics().fillRect(0, 0, width - (2*xPad), height - yPad);
		paper.getGraphics().setColor(Color.BLACK);
		
		setPenPosition(30, 427);
		
		
	}
	
	public Stepper[] getSteppers(){
		return steppers;
	}
	public Pen getPen(){
		return pen;
	}
	
	public void setPenPosition(double x, double y) {
		setPenPosition(x, y, null);
	}
	public void setPenPosition(double x, double y, String source) {
		
		x += xPad;
		y += yPad;
		
		Stepper s0 = steppers[0];
		Stepper s1 = steppers[1];
		
		double b = Math.sqrt( (x*x) + (y*y) );
		double a = Math.sqrt( Math.pow(width - x, 2) + (y * y));
		
		s0.setLength(b);
		s1.setLength(a);
		
		steppers = new Stepper[]{s0, s1};
		System.out.println( (source==null?"":"[" + source + "]") + "set to " + (x-xPad) + ", " + (y-yPad) + " and strings to " + b + ", " + a);
		
	}
	public double[] getPenPosition(){

		double b = steppers[0].getLength();
		double a = steppers[1].getLength();
		double c = width;
		
		/*
		 * A____c____B
		 *  \   |   /
		 *  b\  |  /a
		 *    \ | /
		 *     \|/
		 *      V
		 *      C
		 * 
		 * Law of cosines: a^2 = b^2 + c^2 - 2bc*cosA
		 * therefore: A = Arccos((a^2 - b^2 - c^2) / 2bc)
		 */
		
		double A = Math.acos( (a*a - b*b - c*c) / (-2*b*c) );
		
		double x = b * Math.cos(A) - xPad;
		double y = b * Math.sin(A) - yPad;
		
		return new double[]{x, y};
	}

    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	g.drawImage(paper, xPad, yPad, null);
    	
    	double[] p = getPenPosition();
    	Graphics2D g2 = (Graphics2D) g;
    	g2.setColor(Color.BLUE.darker());
    	
    	String length = Double.valueOf(steppers[0].getLength()).toString();
    	g2.drawChars(length.toCharArray(), 0, length.toCharArray().length, 100, 30);
    	length = Double.valueOf(steppers[1].getLength()).toString();
    	g2.drawChars(length.toCharArray(), 0, length.toCharArray().length, 400, 30);
    	
    	Shape s = new Line2D.Double(0, 0, p[0] + xPad, p[1] + yPad);
    	g2.draw(s);
    	s = new Line2D.Double(width, 0, p[0] + xPad, p[1] + yPad);
    	g2.draw(s);
    }
}
