package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

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
	private static final int ADD = 3;

	OutputStream out;
	InputStream in;

	Database db;
	
	public NetworkServer(Database db) {
		this.db=db;
	}
	
	private HashMap<String,String> bb(String name)
	{
		HashMap<String,String> map=new HashMap<String, String>();
		String[] a=name.split(",");
		for(String s:a)
		{
			String[] ba=s.split("=");
			String key=ba[0].trim();
			String val=ba[1].trim();
			map.put(key, val);
		}
		return map;		
	}
	

	public void aa() {
		SSLServerSocketFactory f = (SSLServerSocketFactory) SSLServerSocketFactory
				.getDefault();

		try {
			SSLServerSocket s = (SSLServerSocket) f.createServerSocket(PORT);
			s.setNeedClientAuth(true);
			while (true) {
				try {
					System.out.println("dfgdf");
					SSLSocket client = (SSLSocket) s.accept();
					System.out.println("accept");
					out = client.getOutputStream();
					in = client.getInputStream();

					SSLSession session = client.getSession();
					System.out.println("getSession");
					X509Certificate cert = (X509Certificate) session
							.getPeerCertificateChain()[0];
					String subject = cert.getSubjectDN().getName();	
					//System.out.println("::"+subject);
					
					HashMap<String,String> certdata=bb(subject);
					
					for(Entry<String, String> e: certdata.entrySet())
					{
						System.out.println(e.getKey()+": "+e.getValue());
					}
					String name=certdata.get("CN");
					String pn=certdata.get("O");
					String sjukhus=certdata.get("L");
					String level=certdata.get("ST");
					int al;
					try {
						al=User.DOCTOR_LEVEL; //Integer.parseInt(level);
					} catch (NumberFormatException e1) {						
						continue;
					}
					
					User user=new User(pn,name, al);
				
					boolean isLoggedIn=false;
					//

					while (true) {
						int type = in.read();
						
						//System.out.println(str);
						switch (type) {
						case LOGIN:							
							String str = readStr();
							if(true)
							{
								isLoggedIn=true;
								out.write(1);
							}
							else out.write(0);

							break;
						case REQUEST:

							String rstr = readStr();
							System.out.println(rstr);
							if (isLoggedIn) {							
								
								String text = "hello";
								if(rstr.equals("ls"))
								{
									//text=db.viewAll(user);
								}
								else
								{
									
								}
								byte[] textdata = (text).getBytes();

								out.write(textdata.length);
								out.write(textdata);
							} else {
								out.write(0);
							}
							break;

						case ADD:
							String ppn=readStr();
							String pname=readStr();
							String ps=readStr();
							String pd=readStr();
							if(user.getLevel()==User.DOCTOR_LEVEL)
							{
								db.add(user, new Record(ppn, pname, sjukhus, name, ps, pd));
								db.save();
							}
							
						default:
							break;
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String readStr() throws IOException
	{
		int len=in.read();
		return new String(read(len));
		
	}
	private byte[] read(int len) throws IOException {
		byte[] b = new byte[len];
		int bytesread = 0;
		while (bytesread < len) {
			bytesread+=in.read(b, bytesread, len - bytesread);
		}
		return b;
	}

}
