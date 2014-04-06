package mattmerr47.piplot.draw;

import mattmerr47.piplot.io.drawdata.DrawData;
import mattmerr47.piplot.io.path.Point;
import mattmerr47.piplot.io.plotter.IPenPlotter;

public class DrawMain {

	private static IPenPlotter plotter;
	
	public static void main(String[] args) throws InterruptedException {
		
		plotter = new PenPlotter(14, 16, 2.5, 6);
		
		plotter.gotoPosition(new Point(0,0));
	}//*/
	/*
	public static void main(String[] args) {
		
		Plotter plotter = new Plotter(5);
		CurveProvider cp = new CurveProvider();
		plotter.setMultiCurve(cp);
		plotter.
		
	}*/
	
	public static void drawImage(DrawData data) {
		
	}
	
	/*private static ServerListener sl = new ServerListener(){
		
		@Override
		public void onCompletion(DrawData received) {
			drawImage(received);
		}
		
	};*/

}
