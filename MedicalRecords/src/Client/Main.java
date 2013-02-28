package Client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

	static NetworkClient client=new NetworkClient();
	static public void main(String[] args)
	{
		Scanner s=new Scanner(System.in);
		
		int connectres;
		if((connectres=client.connect("localhost"))>0)
		{
			
			while(true)
			{
				
				boolean loggedin=false;
				
				if(connectres==NetworkClient.CONNECT_NO_PASSWORD)
				{
					
					System.out.println("New user!");
					while(true){
						System.out.println("Enter new password: ");
						String p1=s.nextLine();
						System.out.println("Enter password again: ");
						String p2=s.nextLine();
						if(p1.equals(p2))
						{							
							loggedin=client.login(p1);;
							break;
						}
						else System.out.println("did not match");
					}
					
						
				}
				if(!loggedin)
				{					
					System.out.println("Enter password: ");
					loggedin=client.login(s.nextLine());
				}
				if(loggedin)
				{
					request(s,10000);
					
					while(true)
					{						
						System.out.println("Val (0 för att backa): ");
						String cmd=s.next();						
						int ci=-1;
						try {
							ci = Integer.parseInt(cmd);
						} catch (NumberFormatException e) {	
						}
						if(cmd.equalsIgnoreCase("j")) ci=1;
						if(cmd.equalsIgnoreCase("n")) ci=0;
							
						request(s, ci);				
						
					}				
					
				}
				else
				{
					System.out.println("login failed.");
				}
			}			
		}
		else
		{
			System.out.println("connect failed.");
		}	
		
		
	}

	private static void request(Scanner s, int ci) {
		NetworkClient.RequestResult res=client.request(ci);
		
		if(res.i==5)
		{
			System.out.println(res.Text);							
		}
		if(res.i==1)
		{
			handleNew(s);
		}
		if(res.i==2)
		{
			System.out.println(res.Text);
			handleEdit(s);
		}
		if(res.i==6)
		{
			System.exit(0);
		}
	}
	
	static private void handleNew(Scanner s)
	{
		System.out.println("Personnummer: ");
		s.nextLine();
		String pn=s.nextLine();
		
		System.out.println("Namn: ");
		String name=s.nextLine();
		
		System.out.println("Sköterska: ");
		String s2=s.nextLine();
		
		System.out.println("Diagnos: ");
		String d=s.nextLine();
		
		System.out.println("Bekräfta(j/n)");
		if(s.next().equalsIgnoreCase("j"))		
			client.addNew(pn,name,s2,d);
		
		request(s,0);
	}
	
	static private void handleEdit(Scanner s)
	{
				
		System.out.println("Ny diagnos: ");
		s.nextLine();
		String d=s.nextLine();		
		
		System.out.println("Bekräfta(j/n)");
		if(s.next().equalsIgnoreCase("j"))	
			client.edit(d);
		request(s,0);
	}
	
	
	
	
}
