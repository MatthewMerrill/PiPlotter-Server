package mattmerr47.piplot.draw;

import mattmerr47.piplot.MotorTest;
import mattmerr47.piplot.io.path.Point;

public class StepperHelper {
	
	private PenPlotter plotter;
	
	StepperMotor left;
	StepperMotor right;
	
	/**
	 * The position of the left motor. The Left motor is at the point (0,0).
	 * TODO: Make this customizable.
	 */
	private final Point posL;
	/**
	 * The position of the right motor. The right motor is at the point (width,0).
	 * TODO: Make this customizable.
	 */
	private final Point posR;
	
	private boolean isMoving = false;
	private Point point;
	
	public StepperHelper(PenPlotter plotter) {
		
		this.plotter = plotter;
		
		posL = new Point(-plotter.xPad, -plotter.yPad);
		posR = new Point(plotter.width + plotter.xPad, -plotter.yPad);
		
		left = new StepperMotor(2048, StepperMotor.HALF_STEP_SEQUENCE, MotorTest.pins2);		
		right = new StepperMotor(2048, StepperMotor.HALF_STEP_SEQUENCE, MotorTest.pins1);
		point = new Point(0, 0);
	}
	
	public void moveTo(Point to) {
		if (point.equals(to))
			return;
		
		Point from = point;
		
		double lengthLi = dist(posL, from);
		double lengthRi = dist(posR, from);
		
		double lengthLf = dist(posL, to);
		double lengthRf = dist(posR, to);
		
		final double degreesL = -Math.toDegrees((lengthLf - lengthLi)/(plotter.wheelDiameter()/2));
		final double degreesR = Math.toDegrees((lengthRf - lengthRi)/(plotter.wheelDiameter()/2));
		
		System.out.println("L:" +degreesL);
		System.out.println("R:" +degreesR);
		
		double dlenL = Math.abs(lengthLf - lengthLi);
		double dlenR = Math.abs(lengthRf - lengthRi);
		
		final double intL = Math.abs((dlenL < dlenR)?(6 * dlenR / dlenL):6);
		final double intR = Math.abs((dlenR < dlenL)?(6 * dlenL / dlenR):6);
		
		System.out.println("intL:" + intL);
		System.out.println("intR:" + intR);
		
		isMoving = true;
		final Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				left.turn(degreesL, (long)intL);
				System.out.println("left done!");
			}});
		t.start();
		
		right.turn(degreesR, (long)intR);
		System.out.println("right done!");
		while (t.isAlive()){};
		System.out.println("done!");
		isMoving = false;
		point = to;
	}
	
	public void circle(Point center, double radius) {
		this.moveTo(new Point(center.X+radius, center.Y));
		
		for(double i = 0; i<=360; i+=360/(radius*75)) {
			this.moveTo(new Point(center.X + radius*Math.cos(Math.toRadians(i)), center.Y + radius*Math.sin(Math.toRadians(i))));
			//sleep(10);
		}
		
	}
	
	/**
	 * Returns the distance between two points.
	 * Can be used to find a string length.
	 * @param a = point A
	 * @param b = point B
	 * @return Distance from point A to point B
	 */
	public double dist(Point a, Point b) {
		double dx = b.X - a.X;
		double dy = b.Y - a.Y;
		return Math.sqrt( (dx*dx) + (dy*dy) );
	}
	
	public boolean isMoving(){
		return isMoving;
	}

}
