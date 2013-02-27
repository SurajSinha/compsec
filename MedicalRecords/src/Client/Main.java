package Client;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

	static NetworkClient client=new NetworkClient();
	static public void main(String[] args)
	{
		
		if(client.connect("localhost"))
		{
			while(true)
			{
				System.out.println("Enter password: ");
				Scanner s=new Scanner(System.in);
				if(client.login(s.nextLine()))
				{
					while(true)
					{						
						System.out.println("Enter request: ");
						String cmd=s.next();						
						int ci=-1;
						try {
							ci = Integer.parseInt(cmd);
						} catch (NumberFormatException e) {	
						}
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
		
		System.out.println("Bekräfta(j/n");
		if(s.next()=="j")		
			client.addNew(pn,name,s2,d);
	}
	
	static private void handleEdit(Scanner s)
	{
				
		System.out.println("Ny diagnos: ");
		String d=s.nextLine();		
		
		System.out.println("Bekräfta(j/n");
		if(s.next()=="j")		
			client.edit(d);
	}
	
	
	
	
}
