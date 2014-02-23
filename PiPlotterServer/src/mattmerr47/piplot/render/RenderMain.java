package mattmerr47.piplot.render;

import java.io.IOException;

import javax.swing.JFrame;

import mattmerr47.piplot.render.Easel;
import mattmerr47.piplot.render.RenderTracer;

public class RenderMain {

    
	public static void main(String[] args) throws IOException, InterruptedException {	    
        
		//File file = new File("D:/stuff/PiPlotReceived.svg");
		/*
		try {
				
			ServerSocket serverSocket =
					new ServerSocket(4747);
	        System.out.println(serverSocket.getInetAddress());
	        System.out.println(serverSocket.getLocalSocketAddress());
	        
	        Socket clientSocket = serverSocket.accept();    
	        System.out.println("found client.");
	        PrintWriter out =
	        		new PrintWriter(clientSocket.getOutputStream(), true);                   
            OutputStream os = new FileOutputStream(file);
	        InputStream is = (clientSocket.getInputStream());
	        
            byte[] buffer = new byte[1024];
            int bytesRead;
            //read from is to buffer
            while((bytesRead = is.read(buffer)) !=-1){
                os.write(buffer, 0, bytesRead);
            }
            out.println("you a'ight bruthah. you a'ight.");
            is.close();
            //flush OutputStream to write any buffered data to file
            os.flush();
            os.close();
            
	        serverSocket.close();
	        
	    } catch (IOException e) {
	        System.out.println("Exception caught when trying to listen on port "
	            + 4747 + " or listening for a connection");
	        System.out.println(e.getMessage());
	        
	        System.exit(0);
	    }//*/
		
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
		//*/
	}
	
	
}