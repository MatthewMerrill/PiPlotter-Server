package mattmerr47.piplot.render;

import java.awt.Color;

import mattmerr47.piplot.ITracer;

public class RenderTracer implements ITracer {
	
	private static final int x = 0;	
	private static final int y = 1;
	
	private Easel easel;
	private boolean marking;
	
	public RenderTracer(Easel easel) {
		this.easel = easel;
	}
	
	public void sleep(long millis) {
		 /*/*/try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}//*/
	}

	@Override
	public boolean getMarking() {
		return marking;
	}

	@Override
	public void setMarking(boolean marking) {
		this.marking = marking;
	}

	@Override
	public void hoverTo(double[] to) {
		double[] penpos = easel.getPenPosition();
		if (penpos[x] == to[x] && penpos[y] == to[y])
			return;
		
		double[] from = easel.getPenPosition();
		double dx = to[x] - from[x];
		double dy = to[y] - from[y];
		double distance = Math.sqrt(dx*dx + dy*dy);
		
		System.out.println("from: " + from[x] + ", " + from[y]);
		System.out.println("[dx] = " + dx);
		System.out.println("[dy] = " + dy);
		System.out.println("[distance] = " + distance);
		
		for (double i = 0; i <= distance*5; i++) {
			easel.setPenPosition(from[x] + dx*i/(distance*5), from[y] + dy*i/(distance*5), "hover");
			sleep(10);
			easel.repaint();
		}
/*
		penpos = easel.getPenPosition();
		if (penpos[x] != to[x] || penpos[y] != to[y]) {
			System.out.println("[hover]I done messed up!");
			hoverTo(to);
		}
	*/}

	@Override
	public void line(double[] from, double[] to) {
		double[] penpos = easel.getPenPosition();
		if (penpos[x] != from[x] || penpos[y] != from[y])
			hoverTo(from);
		
		double dx = to[x] - from[x];
		double dy = to[y] - from[y];
		double distance = Math.sqrt(dx*dx + dy*dy);
		for (int i = 0; i <= distance*5; i++) {
			double[] pos = new double[]{from[x] + dx*i/(distance*5), from[y] + dy*i/(distance*5)};
			easel.setPenPosition(pos[x], pos[y], "line");
			easel.paper.setRGB((int)pos[x], (int)pos[y], Color.BLACK.getRGB());
			sleep(10);
			easel.repaint();
		}
	}
	@Override
	public void bezier(double[] start, double[] end, double[][] controlPoints) {
		
		for (double t = 0; t <= 1000; t++) {
			 double[] p1 = getPointAlongLine(t, start, controlPoints[0]);
			 double[] p2 = getPointAlongLine(t, controlPoints[0], end);
			 
			 double[] p3 = getPointAlongLine(t, p1, p2);
			 //System.out.println("found point: " + p3[x] + ", " + p3[y]);
			 easel.paper.setRGB((int)Math.round(p3[x]), (int)Math.round(p3[y]), Color.BLACK.getRGB());
			 easel.setPenPosition(p3[x], p3[y], "bezier");
			 
			 sleep(10);
			 
			 easel.repaint();
		}
		//printSinusoid(easel, 8, 6);
	}
	
	
	public static void printSinusoid(Easel easel, double a, double b){
		
		
		for (int i = 0; i < 360; i++) {
			double r = (a * Math.cos(Math.toRadians(i))) + (b * Math.sin(Math.toRadians(i)));
			easel.paper.setRGB(i + 50, easel.getHeight() - 50 - (int)(Math.round(r)), Color.BLACK.getRGB());
		}
		
		easel.repaint();
	}
	
	public static double[] getPointAlongLine(double t, double[] start, double[] end) {
		
		double slope = (start[y] - end[y]) / (start[x] - end[x]);
		
		//System.out.println(slope);
		
		double dx = ((end[x] - start[x]) * t/1000);
		
		double px = start[x] + dx;
		double py = start[y] + (dx * slope);
		
		return new double[]{px, py};
		
	}


	@Override
	public void circle(double[] center, double radius) {
		hoverTo(new double[]{center[x]+radius, center[y]});
		
		for(double i = 0; i<=360; i+=360/(radius*15)) {
			line(easel.getPenPosition(), new double[]{center[x] + radius*Math.cos(Math.toRadians(i)), center[y] + radius*Math.sin(Math.toRadians(i))});
			sleep(10);
		}
		
	}


	@Override
	public void ellipse(double[] center, double radiusH, double radiusV) {
		// TODO Auto-generated method stub
		
	}

}