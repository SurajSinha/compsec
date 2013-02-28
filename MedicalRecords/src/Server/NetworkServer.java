package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.security.cert.X509Certificate;

import Client.NetworkClient;

public class NetworkServer {

	
	class RequestResult 
	{
		public int i;
		public String Text;
		
	
	}
	
	private static final int PORT = 12345;
	private static final int REQUEST = 2;
	private static final int LOGIN = 1;
	private static final int ADD = 3;
	private static final int EDIT = 4;

	OutputStream out;
	InputStream in;

	Database db;
	Passwords pw;
	
	public NetworkServer(Database db,Passwords pw) {
		this.db=db;
		this.pw=pw;
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
					System.out.println("server running");
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
					String name=certdata.get("CN"); //Namn
					String pn=certdata.get("O"); //personnummer
					String sjukhus=certdata.get("L"); //sjukhus
					String level=certdata.get("ST"); //nivå
					int al;
					try {
						al=User.DOCTOR_LEVEL; //Integer.parseInt(level);
					} catch (NumberFormatException e1) {						
						continue;
					}
					
					User user=new User(pn,name, al);
				
					boolean isLoggedIn=false;
					//
					
					out.write(pw.hasPassword(pn)? NetworkClient.CONNECT_OK: NetworkClient.CONNECT_NO_PASSWORD);		 

					while (true) {
						int type = in.read();
						
						//System.out.println(str);
						switch (type) {
						case LOGIN:							
							String str = readStr();
							
							if(!pw.hasPassword(pn))
							{
								pw.add(pn, str);
							}
							
							if(pw.validateUser(pn, str))
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
								writeStr("hello");
								

							} else {
								out.write(0);
							}
							break;

						case ADD:
							
							String ppn=readStr();
							String pname=readStr();
							String ps=readStr();
							String pd=readStr();
							db.add(user, new Record(ppn, pname, sjukhus, name, ps, pd));
							break;
						case EDIT:							
							String diagnos=readStr();
							int file=0;
							ArrayList<Record> list=db.viewAllEditable(user);							
							try
							{
								Record r=list.get(file);
								r.editDiagnose(diagnos);
							}catch(IndexOutOfBoundsException e)
							{
								
							}
							break;
							
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
	private byte[] read(int len) throws IOException {
		byte[] b = new byte[len];
		int bytesread = 0;
		while (bytesread < len) {
			bytesread+=in.read(b, bytesread, len - bytesread);
		}
		return b;
	}

}
