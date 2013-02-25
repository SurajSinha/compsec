package Client;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class NetworkClient {
	private static final int REQUEST = 2;
	private static final int LOGIN = 1;
	private static final int PORT = 12345;
	
	private SSLSocket s;
	private OutputStream out;
	private InputStream in;
	
	public NetworkClient(){	
	}
	public boolean connect(String host){
		 SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();
		 
		try {
			s=(SSLSocket)factory.createSocket(host, PORT);
			out=s.getOutputStream();
			in=s.getInputStream();
			return true;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	public boolean login(String password){
		try {
			out.write(LOGIN);
			byte[] data=(password).getBytes();
			out.write(data.length);
			out.write(data);
			int res=in.read();
			if(res>0)return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	byte[] read(int len) throws IOException {
		byte[] b = new byte[len];
		int bytesread = 0;
		while (bytesread < len) {
			bytesread+=in.read(b, bytesread, len - bytesread);
		}
		return b;
	}
	
	public String request(String name){
		try {
			out.write(REQUEST);
			byte[] data=(name).getBytes();
			out.write(data.length);
			out.write(data);
			int len=in.read();
			if(len>0)
			{
				byte[] res=read(len);
				return new String(res);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
