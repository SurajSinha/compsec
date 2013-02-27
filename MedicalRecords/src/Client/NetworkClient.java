package Client;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class NetworkClient {
	
	
	class RequestResult 
	{
		public int i;
		public String Text;	
	}
	
	private static final int REQUEST = 2;
	private static final int LOGIN = 1;
	private static final int PORT = 12345;
	private static final int ADD = 3;
	private static final int EDIT = 4;
	
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
			byte[] data=hash(password).getBytes();
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
	
	public RequestResult request(int id){
		try {
			out.write(REQUEST);
			out.write(id);
								
			RequestResult r=new RequestResult();
			r.i=in.read();
			r.Text=readStr();
			return r;
			
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void addNew(String pn, String name, String s2, String d) {
		try {
			out.write(ADD);
			writeStr(pn);
			writeStr(name);
			writeStr(s2);
			writeStr(d);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void edit(String d) {		
		try {
			out.write(EDIT);			
			writeStr(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
	}
	
	private void writeStr(String str) throws IOException
	{
		byte[] data=str.getBytes();
		out.write(data.length);
		out.write(data);
		
	}
	private String readStr() throws IOException
	{
		int len=in.read();
		return new String(read(len));
		
	}
	
	
	private String hash(String str)
	{
		byte[] data=(str+"lalalagoodmorningstarshine").getBytes();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");
			 md.update(data);
			 byte[] b=md.digest();
			 return new java.math.BigInteger(1, b).toString(16);			 
			 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	
	}
	
	
	
	
}
