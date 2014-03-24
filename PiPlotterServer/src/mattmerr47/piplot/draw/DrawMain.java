package mattmerr47.piplot.draw;

import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;

import mattmerr47.piplot.MotorTest;
import mattmerr47.piplot.io.Server;
import mattmerr47.piplot.io.ServerListener;
import mattmerr47.piplot.io.drawdata.DrawData;
import mattmerr47.piplot.io.path.Point;

public class DrawMain {

	private static PenPlotter plotter;
	
	public static void main(String[] args) throws InterruptedException {
		
		plotter = new PenPlotter(14, 16, 1, 5.5);
		//plotter.gotoPosition(new Point(5,5));
		plotter.circle();
		/*for (GpioPinDigitalOutput pin : MotorTest.pins1) {
			GpioFactory.getInstance().setState(PinState.HIGH, pin);
		}
		Thread.sleep(30000);
		for (GpioPinDigitalOutput pin : MotorTest.pins1) {
			GpioFactory.getInstance().setState(PinState.LOW, pin);
		}*/
		/*
		Server s = new Server(sl, plotter);
		s.listenForClients();
		*/
		plotter.gotoPosition(new Point(0,0));
	}
	
	public static void drawImage(DrawData data) {
		
	}
	
	private static ServerListener sl = new ServerListener(){
		
		@Override
		public void onCompletion(DrawData received) {
			drawImage(received);
		}
		
	};

}
