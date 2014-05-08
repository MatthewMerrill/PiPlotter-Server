package mattmerr47.piplot.draw;

import java.io.File;
import java.io.IOException;

import mattmerr47.piplot.io.PositionHelper;
import mattmerr47.piplot.io.Server;
import mattmerr47.piplot.io.ServerListener;
import mattmerr47.piplot.io.drawdata.DrawData;
import mattmerr47.piplot.io.drawdata.FileHandler;
import mattmerr47.piplot.io.path.Path;
import mattmerr47.piplot.io.path.Point;

public class DrawMain {

	private static PenPlotter plotter;
	private static PositionHelper posHelper;
	
	public static void main(String[] args) throws InterruptedException, IOException {

		
		
		plotter = new PenPlotter(11, 14, 2.5, 6);
		posHelper = plotter.posHelper;
		
		if (args.length >= 1) {
			
			if (args[0].equalsIgnoreCase("server") || args[0].equalsIgnoreCase("s")) {
				
				Server server = new Server(new ServerListener(){
					@Override
					public void onCompletion(DrawData received) {
						drawImage(received);
					}
				}, null);
				server.listenForClients();
				
			} else if (args[0].equalsIgnoreCase("servoTest")) {

				System.out.println("Will loop until infinity. Press 'Ctrl + C' to stop.");
				while (true) {
					plotter.setMarking(true);
					plotter.setMarking(false);
				}
				
			} else if (args[0].equalsIgnoreCase("file") && args.length >= 2) {
				
				DrawData data = FileHandler.readFrom(new File(args[1]));
				drawImage(data);
				
			} else if (args[0].equalsIgnoreCase("flower")) {
				
				System.out.println("Will loop until infinity. Press 'Ctrl + C' to stop.");
				posHelper.flower();
				
			}
		} else {
			
			System.out.println("arguments:");
			System.out.println("  [server]     - Starts a server at 192.168.42.1.");
			System.out.println("  [servoTest]     - Tests Servo wiring. Servo should lift pen up and down.");
			System.out.println("  [file] [path to file]     - Opens and draws data from file.");
			System.out.println("  [flower]     - Draws the looping flowery thing. Good for conventions and any presentations longer than 1.5 hours.");
			
		}
	}
	
	public static void drawImage(DrawData data) {
		for (Path path : data.getPaths()) {	
			path.draw(posHelper);			
		}
		posHelper.moveTo(new Point(0,0));
		plotter.clear();
		System.exit(0);
	}
	
	
}
