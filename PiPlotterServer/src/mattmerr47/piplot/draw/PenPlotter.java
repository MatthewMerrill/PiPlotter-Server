package mattmerr47.piplot.draw;

//import mattmerr47.piplotter.MotorTest;
import mattmerr47.piplot.draw.Logger.LEVEL;
import mattmerr47.piplot.io.path.Point;
import mattmerr47.piplot.io.plotter.IPenPlotter;
import mattmerr47.piplot.io.plotter.IStepperMotor;

public class PenPlotter implements IPenPlotter {
	
	protected PositionHelper posHelper;
	
	public final double width;
	public final double height;
	
	private StepperMotor left;
	private StepperMotor right;
	
	public final double xPad;
	public final double yPad;
	public final double iPad = "< nexus7".length(); // LOL

	public PenPlotter(double width, double height, double xPad, double yPad) {
		
		this.width = width;
		this.height = height;
		
		this.xPad = xPad;
		this.yPad = yPad;
		
		left = new StepperMotor(4096, StepperMotor.HALF_STEP_SEQUENCE, StepperMotor.pins1);
		right = new StepperMotor(4096, StepperMotor.HALF_STEP_SEQUENCE, StepperMotor.pins2);
		
		posHelper = new PositionHelper(this, left, right);
		
		Logger.setDisplayLevel(LEVEL.DEBUG_ROUGH);
		
		Logger.addDepthLayer("Lines");
		gotoPosition(new Point(-2,2));
		gotoPosition(new Point(2,2));
		gotoPosition(new Point(2,4));
		gotoPosition(new Point(-2,4));
		gotoPosition(new Point(-2,2));
		Logger.removeDepthLayer();
		
		Logger.addDepthLayer("Circle");
		posHelper.circle(new Point(-2, 4), 1.5);
		Logger.removeDepthLayer();
		
		//Logger.addDepthLayer("Sector");
		//posHelper.sector(new Point(-3.5, 4), 1.5, 180, -90);
		//Logger.removeDepthLayer();
		
		Logger.addDepthLayer("Bezier");
		posHelper.bezier(new Point(-2,2), new Point(2,4), new Point[]{new Point(2,2), new Point(-2,4)});
		Logger.removeDepthLayer();
	}
	
	public void circle(){
		//posHelper.circle(new Point(6,6), 2);
	}
	@Override
	public void gotoPosition(Point pos) {
		posHelper.line(pos, 20, 1/5.0);
	}

	@Override
	public Point getPosition() {
		//TODO: lol
		return null;//stepperHelper.getPenPosition();
	}

	@Override
	public void setMarking(boolean penDown) {
		// TODO: This
		
	}

	@Override
	public boolean getMarking() {
		// TODO: asdf
		return true;
	}

	@Override
	public double wheelDistance() {
		// TODO Auto-generated method stub
		return 1/0;
	}

	@Override
	public double wheelDiameter() {

		//double circumference = 7.05;
		//Pi = circumference / diameter
		return 2.2;//circumference / Math.PI;
	}

	@Override
	public double paperWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	@Override
	public double paperHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public IStepperMotor[] getSteppers() {
		return new IStepperMotor[]{left, right};
	}

	@Override
	public double getXPad() {
		// TODO Auto-generated method stub
		return this.xPad;
	}

	@Override
	public double getYPad() {
		// TODO Auto-generated method stub
		return this.yPad;
	}

}
