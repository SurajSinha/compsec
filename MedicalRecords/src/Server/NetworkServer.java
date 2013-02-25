package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.X509Certificate;

public class NetworkServer {

	private static final int PORT = 12345;
	private static final int REQUEST = 2;
	private static final int LOGIN = 1;

	OutputStream out;
	InputStream in;
	public NetworkServer() {
		// TODO Auto-generated constructor stub
	}
	
	public void aa()
	{
		SSLServerSocketFactory f=(SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
		
		try {
			SSLServerSocket s=(SSLServerSocket) f.createServerSocket(PORT);
			while(true)
			{
				SSLSocket client=(SSLSocket)s.accept();
				
				out=client.getOutputStream();
				in=client.getInputStream();
				
				SSLSession session = client.getSession();
				X509Certificate cert = (X509Certificate)session.getPeerCertificateChain()[0];
				String subject = cert.getSubjectDN().getName();
				//
				 
				
				while(true)
				{
					int type=in.read();
					int len=in.read();
					byte[] data=read(len);
					String str=new String(data);
					
					switch (type) {
					case LOGIN:
						
						int res=1;//
						out.write(res);
						
						break;
					case REQUEST:
						
						if(true)
						{
							String text="hello";
							byte[] textdata=(text).getBytes();
							
							out.write(textdata.length);
							out.write(textdata);
						}
						else
						{
							out.write(0);
						}
						break;

					default:
						break;
					}
				}			
				
				
				
				
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	byte[] read(int len) throws IOException
	{
		byte[] b=new byte[len];
		int bytesread=0;
		while(bytesread<len)
		{
			in.read(b, bytesread, len-bytesread);
		}
		return b;		
	}
	
	
	
	
	
	
	
}
