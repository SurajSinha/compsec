package Server;

public class Test {
	public static void main(String[] args){
		Log l = new Log();
		Database db = new Database(l);
		User u = new User("9999999999", "Malmö Kommun", User.GOVERNMENT_LEVEL);
		//Record(int pnummer, String pnamn, String sjukhus, String doktor, String sköterska, String diagnos){
		Record r = new Record("7810256413", "Pelle Karlsson", "MAS", "Per Injektion", "Anna Plåster", "Näsblod.");
		db.add(u, r);
		db.save();
		System.out.println(r);
		r = new Record("8403127818", "Per Johansson", "MAS", "Per Injektion", "Anna Plåster", "Stukat finger.");
		db.add(u, r);
		db.save();
		
	}
}
