package mattmerr47.piplot.draw;

import java.util.concurrent.CountDownLatch;

import mattmerr47.piplot.draw.Logger.LEVEL;
import mattmerr47.piplot.io.path.Point;
import mattmerr47.piplot.io.plotter.IPenPlotter;
import mattmerr47.piplot.io.plotter.IStepperMotor;

public class PositionHelper {
	
	private final IPenPlotter plotter;
	
	private final IStepperMotor left;
	private final IStepperMotor right;
	
	private final Point posL;
	private final Point posR;
	
	private Point penPos = new Point(0, 0);
	private boolean isMoving = false;
	
	public PositionHelper(IPenPlotter plotter, IStepperMotor left, IStepperMotor right) {
		
		this.plotter = plotter;
		
		this.left = left;
		this.right = right;
		
		posL = new Point(-6, -5);
		posR = new Point(6, -5);
	}
	
	public boolean isMoving(){
		return isMoving;
	}
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//CALCULATIONS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private static final int LEFT = 0;
	private static final int RIGHT = 1;
	
	public double[] calculateDegrees(Point from, Point to) {
		
		//Lengths at Starting Position
		Point leftEye = new Point(from.X - .75, from.Y);
		Point rightEye = new Point(from.X + .75, from.Y);
		
		double lengthLi = leftEye.dist(posL);
		double lengthRi = rightEye.dist(posR);
		
		
		//Lengths at Final Position
		leftEye = new Point(to.X - .75, to.Y);
		rightEye = new Point(to.X + .75, to.Y);	
		
		double lengthLf = leftEye.dist(posL);
		double lengthRf = rightEye.dist(posR);
		
		/* I believe these two methods do the same thing. Remove the first '/' to enable alternate method.
		double degreesL = 360 * ((lengthLf - lengthLi) / (plotter.wheelDiameter() * Math.PI));
		double degreesR = -360 * ((lengthRf - lengthRi) / (plotter.wheelDiameter() * Math.PI));
		/*///
		double degreesL = Math.toDegrees((lengthLf - lengthLi)/(plotter.wheelDiameter()/2));
		double degreesR = -Math.toDegrees((lengthRf - lengthRi)/(plotter.wheelDiameter()/2));
		//*/
		
		return new double[]{degreesL, degreesR};
	}
	
	public double[] calculateIntervals(Point from, Point to, double inchesPerSec) {
	
		double dist = from.dist(to);
		double time = dist / inchesPerSec;
		
		//System.out.println("going" +dist+" inches over a timeframe of "+time+" seconds; inchesPerSec = " + inchesPerSec + "???");
		
		double[] degrees = calculateDegrees(from, to);
		double[] steps = new double[2];
		double[] intervals = new double[2];
		
		steps[LEFT] = (degrees[LEFT]/360) * left.getStepsPerRev();
		steps[RIGHT] = (degrees[RIGHT]/360) * right.getStepsPerRev();
		
		intervals[LEFT] = Math.abs(time/steps[LEFT]) * 1000;
		intervals[RIGHT] = Math.abs(time/steps[RIGHT]) * 1000;
		/*
		System.out.println("L:Doing " + degrees[LEFT] + " in " + steps[LEFT] + " steps with an interval of " + intervals[LEFT] + ".");
		System.out.println("R:Doing " + degrees[RIGHT] + " in " + steps[RIGHT] + " steps with an interval of " + intervals[RIGHT] + ".");
		///Lengths at Starting Position
		Point leftEye = new Point(from.X - .75, from.Y);
		Point rightEye = new Point(from.X + .75, from.Y);
		
		double lengthLi = leftEye.dist(posL);
		double lengthRi = rightEye.dist(posR);
		
		
		//Lengths at Final Position
		leftEye = new Point(to.X - .75, to.Y);
		rightEye = new Point(to.X + .75, to.Y);	
		
		double lengthLf = leftEye.dist(posL);
		double lengthRf = rightEye.dist(posR);
		
		double dlenL = Math.abs(lengthLf - lengthLi);
		double dlenR = Math.abs(lengthRf - lengthRi);

		final double intL = Math.abs((dlenL < dlenR)?(6 * dlenR / dlenL):6);
		final double intR = Math.abs((dlenR < dlenL)?(6 * dlenL / dlenR):6);
		*/
		return intervals;//new double[]{intL,intR};		
	}

	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//SHAPES / DRAWING COMPONENTS
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void moveTo(Point to) {
		moveTo(to, .5);
	}
	
	public void moveTo(Point to, double inchesPerSec) {
		if (penPos == to)
			return;
		
		Point from = penPos;
		
		Logger.print(LEVEL.DEBUG_ROUGH, "Moving from " + from + " to " + to + ".");
		
		final double[] degrees = calculateDegrees(from, to);
		final double[] intervals = calculateIntervals(from, to, inchesPerSec);
		
		isMoving = true;
		
		 final CountDownLatch startSignal = new CountDownLatch(1);
		 final CountDownLatch doneSignal = new CountDownLatch(2);
		 
		 Thread t1 = new Thread(){
				public void run(){

					try {
						startSignal.await();
						
						Logger.print(LEVEL.DEBUG_FINE, "l:"+degrees[LEFT] + "degrees w/ interval:" + intervals[LEFT]);
						left.turn(degrees[LEFT], intervals[LEFT]);
						Logger.print(LEVEL.DEBUG_FINE, "[" + System.currentTimeMillis() + "] left done!");
						
						doneSignal.countDown();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}};
				
			Thread t2 = new Thread(){
				public void run(){
					try {
						startSignal.await();
						
						Logger.print(LEVEL.DEBUG_FINE, "r:"+degrees[RIGHT] + "degrees w/ interval:" + intervals[RIGHT]);
						right.turn(degrees[RIGHT], intervals[RIGHT]);
						Logger.print(LEVEL.DEBUG_FINE, "[" + System.currentTimeMillis() + "] right done!");
						
						doneSignal.countDown();
					} catch (Exception e) {
						e.printStackTrace();
					}		
				}};

		t1.start();
		t2.start();
		 

		Logger.print(LEVEL.DEBUG_ROUGH, "\nall threads starting");
		startSignal.countDown();	  // let all threads proceed
				
		try {
			doneSignal.await();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Logger.print(LEVEL.DEBUG_ROUGH, "done!\n\n");
		
		isMoving = false;
		penPos = to;
	}
	
	public void line(Point to, double segsPerInch, double inchesPerSec) {
		line(penPos, to, segsPerInch, inchesPerSec);
	}
	
	public void line(Point from, Point to, double segsPerInch, double inchesPerSec) {
		
		//TODO: double[] penpos = calculatePenPosition();
		if (penPos != from) {
			//TODO: Lift Pen
			moveTo(from);
		}
		
		double dx = to.X - from.X;
		double dy = to.Y - from.Y;
		double distance = Math.sqrt(dx*dx + dy*dy);
		
		for (double i = 1; i < distance * segsPerInch; i++) {
			
			double percentAlongLine = (i / (distance * segsPerInch)) * 100;
			Point segPos = Point.getPointAlongLine(from, to, percentAlongLine);
			
			moveTo(segPos, inchesPerSec);
		}
		moveTo(to, inchesPerSec);
		
		//TODO: Calculated penPos.
		penPos = to;
	}
	
	public void sector(Point center, double radius, double startAngle, double centralAngle) {
		moveTo(Point.findPointOnCircle(center, radius, startAngle));
		
		double angleChange = (centralAngle >= 0)?1:-1;
		for (double i = startAngle; i <= (startAngle + centralAngle); i += angleChange) {
			this.moveTo(Point.findPointOnCircle(center, radius, i));
		}
	}
	
	public void circle(Point center, double radius) {
		sector(center, radius, 0, 360);
	}
	
	public void bezier(Point start, Point end, Point[] controls) {
		
		for (double t = 0; t <= 1000; t++) {
			line(Point.getCriticalPoint(start, end, controls, t/10), 1, .5);
		}
		
		// TODO: PenPos by how it moved, not where it should be
		penPos = end;
	}

}
