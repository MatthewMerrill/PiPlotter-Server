package mattmerr47.piplot.render;

import java.awt.Graphics;

import javax.swing.JComponent;


public class Pen extends JComponent {
	
	private Easel easel;
	private boolean drawing = false;
	
	public Pen(Easel easel){
		this.easel = easel;
	}
	
	@Override
	public void paint(Graphics g) {
		int x = this.getX();
		int y = this.getY();
		
		g.drawOval(x-2, y-2, 4, 4);
	}
	
	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}
	public boolean isDrawing(){
		return drawing;
	}

}
