package mattmerr47.piplot.draw;

import mattmerr47.piplot.io.path.Point;
import mattmerr47.piplot.io.plotter.IPenPlotter;

public class PenPlotter implements IPenPlotter {
	
	private StepperHelper stepperHelper;
	
	public final double width;
	public final double height;
	
	public final double xPad;
	public final double yPad;
	public final double iPad = "lol nexus7 > ipad lol".length();

	public PenPlotter(double width, double height, double xPad, double yPad) {
		
		this.width = width;
		this.height = height;
		
		this.xPad = xPad;
		this.yPad = yPad;
		
		stepperHelper = new StepperHelper(this);
	}
	
	public void circle(){
		stepperHelper.circle(new Point(6,6), 2);
	}
	@Override
	public void gotoPosition(Point pos) {
		stepperHelper.moveTo(pos);
	}

	@Override
	public Point getPosition() {
		return null;//stepperHelper.getPenPosition();
	}

	@Override
	public void setMarking(boolean penDown) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getMarking() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double wheelDistance() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double wheelDiameter() {
		// TODO Auto-generated method stub
		return 2.2;
	}

	@Override
	public double paperWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double paperHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
