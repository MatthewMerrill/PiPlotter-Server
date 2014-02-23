package mattmerr47.piplot;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

	public static void main(String[] args) {
		try {
			File file = new File("D:/stuff/duck.svg");
			   // HttpClient httpclient = new DefaultHttpClient();
			    Socket socket = new Socket("192.168.2.23", 4747);
			    OutputStream outputStream = socket.getOutputStream();
			   // File f = new File(path);
			    byte [] buffer = new byte[(int)file.length()];
			    FileInputStream fis = new FileInputStream(file);
			    BufferedInputStream bis = new BufferedInputStream(fis);
			    bis.read(buffer,0,buffer.length);
			    outputStream.write(buffer,0,buffer.length);
			    outputStream.flush();
			    /*InputStreamEntity reqEntity = new InputStreamEntity(
			            new FileInputStream(file), -1);
			    reqEntity.setContentType("binary/octet-stream");
			    reqEntity.setChunked(true); // Send in multiple parts if needed
			    httppost.setEntity(reqEntity);
			    HttpResponse response = httpclient.execute(httppost);
			    //Do something with response...
	*/
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
