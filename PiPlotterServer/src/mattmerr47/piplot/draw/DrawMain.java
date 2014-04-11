package mattmerr47.piplot.draw;

import java.io.File;

import mattmerr47.piplot.io.PositionHelper;
import mattmerr47.piplot.io.drawdata.DrawData;
import mattmerr47.piplot.io.drawdata.FileHandler;
import mattmerr47.piplot.io.path.Path;

public class DrawMain {

	private static PenPlotter plotter;
	private static PositionHelper posHelper;
	
	public static void main(String[] args) throws InterruptedException {

		plotter = new PenPlotter(11, 14, 2.5, 6);
		posHelper = plotter.posHelper;
		
		if (args.length >= 1) {
			DrawData data = FileHandler.readFrom(new File(args[0]));
			drawImage(data);
		} else {
			System.out.println("Will loop until infinity. Press 'Ctrl + C' to stop.");
			posHelper.flower();
		}
	}
	
	public static void drawImage(DrawData data) {
		for (Path path : data.getPaths()) {	
			path.draw(posHelper);			
		}	
	}
}
