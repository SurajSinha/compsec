package Client;

import java.util.Scanner;

public class Main {

	static public void main(String[] args)
	{
		
		NetworkClient client=new NetworkClient();
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
						String res=client.request(s.next());
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
	
	
}
