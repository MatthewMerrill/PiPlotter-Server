package mattmerr47.piplot.render;

import java.io.IOException;

import javax.swing.JFrame;

import mattmerr47.piplot.render.Easel;
import mattmerr47.piplot.render.RenderTracer;

public class RenderMain {

	public static void main(String[] args) throws IOException, InterruptedException {	    
		
		JFrame frame = new JFrame();
		
		final Easel easel = new Easel(640, 820, 20, 20);
		frame.add(easel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("PiPlot Concept Render");
		frame.setVisible(true);
		frame.pack();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				RenderTracer rt = new RenderTracer(easel);
				rt.bezier(new double[]{150, 150}, new double[]{500, 500}, new double[][]{new double[]{600, 0}});
				//rt.line(new double[]{0, 0}, new double[]{50, 50});
				rt.circle(new double[]{200, 400}, 100);
				rt.hoverTo(new double[]{0,0});
			}}).run();
	}
	
}