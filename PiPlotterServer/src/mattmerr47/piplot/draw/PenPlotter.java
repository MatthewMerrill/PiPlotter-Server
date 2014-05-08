package mattmerr47.piplot.draw;

import mattmerr47.piplot.io.Logger;
import mattmerr47.piplot.io.Logger.LEVEL;
import mattmerr47.piplot.io.PositionHelper;
import mattmerr47.piplot.io.path.Point;
import mattmerr47.piplot.io.plotter.IPenPlotter;
import mattmerr47.piplot.io.plotter.IStepperMotor;

public class PenPlotter implements IPenPlotter {
	
	protected PositionHelper posHelper;
	
	public final double width;
	public final double height;
	
	private StepperMotor left;
	private StepperMotor right;
	private ServoMotor servo;
	
	public final double xPad;
	public final double yPad;
	public final double iPad = "< nexus7".length(); // LOL

	boolean t = true;
	public PenPlotter(double width, double height, double xPad, double yPad) {
		
		this.width = width;
		this.height = height;
		
		this.xPad = xPad;
		this.yPad = yPad;

		Logger.setDisplayLevel(LEVEL.NORMAL);
		
		left = new StepperMotor(4096, StepperMotor.HALF_STEP_SEQUENCE, StepperMotor.pins1);
		right = new StepperMotor(4096, StepperMotor.HALF_STEP_SEQUENCE, StepperMotor.pins2);
		servo = new ServoMotor();
		
		posHelper = new PositionHelper(this, left, right);
	}
	
	@Override
	public void gotoPosition(Point pos) {
		posHelper.line(pos, 20, 1/5.0);
	}
	@Override
	public Point getPosition() {
		return posHelper.getPenPosition();
	}
	
	///* Not functional yet - Sorry!
	@Override
	public void setMarking(boolean marking) {
		try {
			servo.setMarking(marking);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean getMarking() {
		return servo.getMarking();
	}//*/

	@Override
	public double wheelDistance() {
		return 16;
	}
	@Override
	public double wheelDiameter() {
		return 2.2;
	}

	@Override
	public double paperWidth() {
		return width;
	}
	@Override
	public double paperHeight() {
		return height;
	}

	@Override
	public IStepperMotor[] getSteppers() {
		return new IStepperMotor[]{left, right};
	}

	@Override
	public double getXPad() {
		return this.xPad;
	}
	@Override
	public double getYPad() {
		return this.yPad;
	}
	
	public void clear(){
		servo.clear();
	}

}
