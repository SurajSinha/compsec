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
						
						if(cmd.equals("new"))
						{
							handleNew(s,cmd);
						}
						else
						{
						
							String res=client.request(cmd);
							if(res!=null)
							{
								System.out.println(res);
							}
							else 
							{
								System.out.println("request failed.");
							}
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
	
	static private void handleNew(Scanner s,String cmd)
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
		
		client.addNew(pn,name,s2,d);
	}
	
	
	
	
}
