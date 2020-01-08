import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class AWSEnvChecker {

	private static boolean IS_AWS = false;
	private static String INSTANCE_ID;
	
	static {
		
		Socket socket = null;
		
		try {
			
			socket = new Socket();
			socket.setSoTimeout(1000);
			socket.connect(new InetSocketAddress("169.254.169.254", 80), 1000);
			
			OutputStream out = socket.getOutputStream();
		    InputStream in = socket.getInputStream();
		    
		    String request = "GET /latest/meta-data/instance-id HTTP/1.0\r\n"
		            + "Accept: */*\r\nHost: 169.254.169.254\r\n"
		            + "Connection: Close\r\n\r\n";
		    
		    out.write(request.getBytes());
		    out.flush();
		    
		    StringBuffer response=new StringBuffer();
		    byte[] b = new byte[1024];
		    int c;

		    while ((c = in.read(b, 0, 1024)) != -1) {
		      for(int i = 0; i < c; i++)
		        response.append((char)b[i]);
		    }
		        
		    if (response.substring(response.indexOf(" ") + 1,
		        response.indexOf(" ") + 4).equals("200")) {
      
		    	IS_AWS = true;
		    	INSTANCE_ID = response.substring(response.indexOf("\r\n\r\n") + 4);

		    }		 
		    
		} catch (Exception skip) {
		} finally {
			if(socket != null) {
				try { socket.close(); } catch(Exception skip) {}
			}
		}
		
	}
	
	public static boolean isAWS() {
		return IS_AWS;
	}
	
	public static String getInstanceId() {
		return INSTANCE_ID;
	}
	
}
