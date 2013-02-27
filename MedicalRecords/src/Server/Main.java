package Server;

public class Main {

	
	static public void main(String[] args)
	{
		Log log=new Log();
		Database db=new Database(log);
		
		User u = new User("7911156673", "Anna Plåster", User.DOCTOR_LEVEL, "LUS");
		Record r = new Record("7810256413", "Pelle Karlsson", "MAS", "Per Injektion", "Anna Plåster", "Näsblod.");
		db.add(u, r);
		
		NetworkServer server=new NetworkServer(db); 
		server.aa();
		
		
	}
	
	
	
}
