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

	
	
	private static final int PORT = 12345;
	private static final int REQUEST = 2;
	private static final int LOGIN = 1;
	private static final int ADD = 3;
	private static final int EDIT = 4;

	private OutputStream out;
	private InputStream in;

	private Database db;
	private Passwords pw;
	private Log log;
	public NetworkServer(Database db,Passwords pw,Log l) {
		this.db=db;
		this.pw=pw;
		this.log=l;
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
				User user=null;
				try {
					System.out.println("server running");
					SSLSocket client = (SSLSocket) s.accept();
					
					System.out.println("accept");
					out = client.getOutputStream();
					in = client.getInputStream();

					SSLSession session = client.getSession();
					
					X509Certificate cert = (X509Certificate) session
							.getPeerCertificateChain()[0];
					String subject = cert.getSubjectDN().getName();	
					
					
					HashMap<String,String> certdata=bb(subject);
					
					String name=certdata.get("CN"); //Namn
					String pn=certdata.get("O"); //personnummer
					String sjukhus=certdata.get("L"); //sjukhus
					String level=certdata.get("ST"); //nivå
					int al;
					try {
						al=Integer.parseInt(level);
					} catch (NumberFormatException e1) {						
						continue;
					}
					
					
					if(al==User.DOCTOR_LEVEL || al==User.NURSE_LEVEL)
						user=new User(pn,name, al,sjukhus);
					else
						user=new User(pn,name, al);
					
					MenuSystem ms = new MenuSystem(user,db,log);
				
					boolean isLoggedIn=false;
					//
					
					out.write(pw.hasPassword(pn)? NetworkClient.CONNECT_OK: NetworkClient.CONNECT_NO_PASSWORD);		 

					boolean running=true;
					while (running) {
						
						int type = in.read();
						
						if(type==-1)
						{
							throw new IOException("IOException");
							
						}
						
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
								log.addEvent("LOGIN SUCCESSFUL", user.getName() + " (" + user.getPersonNumber()+") logged in successfully");
								isLoggedIn=true;
								out.write(1);
							}
							else
							{
								try {
									Thread.sleep(2000);
								} catch (InterruptedException e) {									
									e.printStackTrace();
								}
								log.addEvent("LOGIN FAIL", user.getName() + " (" + user.getPersonNumber()+") failed to login");
								out.write(0);								
							}

							break;
						case REQUEST:

							if(!isLoggedIn) s.close();
							
							String rstr = readStr();
							int id=Integer.parseInt(rstr);
							
							if(id==10000)
							{
								out.write(5);
								writeStr(ms.getMenu());
								
							}
							else
							{	
								
								
								String text;
								String cmd=ms.command(id);
								text=cmd;
								
								
								
								text+="\n"+ms.getMenu();	
								
								System.out.println(ms.currentLocation());
								if(ms.currentLocation() == -5)
									text+="\nVill du ta bort denna journal? (J/N)";
								
								
								if(ms.currentLocation() == 4)
								{
									out.write(1);
								}
								else if(ms.currentLocation() == -2)
								{
									out.write(2);
								}
								else if(cmd.equalsIgnoreCase("EXIT"))
								{
									log.addEvent("LOGOFF SUCCESSFUL", user.getName() + " (" + user.getPersonNumber()+") successfully logged out");
									out.write(6);
									running=false;
									break;
									
								}
								else
								{
									out.write(5);
								}
								
								writeStr(text);							
							}
							break;

						case ADD:
							
							String ppn=readStr();
							String pname=readStr();
							String ps=readStr();
							String pd=readStr();
							if(ms.currentLocation()==4) db.add(user, new Record(ppn, pname, sjukhus, name, ps, pd));
							
							break;
						case EDIT:							
							String diagnos=readStr();
							if(ms.currentLocation()!=-2)break;
							int file=ms.getCurrentRecord();
							ArrayList<Record> list=db.viewAllEditable(user);							
							try
							{
								Record r=list.get(file);
								r.editDiagnose(diagnos);
								db.edit(user, r);
							}catch(IndexOutOfBoundsException e)
							{
								
							}
							break;
							
						default:
							break;
						}
					}
				} catch (IOException e) {
					log.addEvent("CLIENT DISCONNECT", user.getName() + " (" + user.getPersonNumber()+") disconnected unexpectedly");
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
		writeInt(data.length);
		out.write(data);
		
	}
	private String readStr() throws IOException
	{
		int len=readInt();//in.read();
		if(len==-1)throw new IOException();
		return new String(read(len));
		
	}
	
	private int read() throws IOException {
		int len=(in.read());		
		if(len==-1)throw new IOException("IOException");
		return len;
	}
	
	private int readInt() throws IOException {
		
		int a=read();
		int b=read();
		int c=read();
		int d=read();
		
		int len=((a)<<24) | ((b)<<16) | ((c)<<8) | (d);		
		return len;
	}
	private void writeInt(int i) throws IOException {
		out.write(i>>24);
		out.write(i>>16);	
		out.write(i>>8);	
		out.write(i);			
	}
	
	private byte[] read(int len) throws IOException {
		byte[] b = new byte[len];
		int bytesread = 0;
		while (bytesread < len) {
			int read=in.read(b, bytesread, len - bytesread);
			if(len==-1)throw new IOException("IOException");
			bytesread+=read;
		}
		return b;
	}

}
