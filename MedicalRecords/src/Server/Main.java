package Server;

public class Main {

	
	static public void main(String[] args)
	{
		Log log=new Log();
		Database db=new Database(log);
		
		NetworkServer server=new NetworkServer(db); 
		server.aa();
		
		
	}
	
	
	
}
