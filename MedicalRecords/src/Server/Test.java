package Server;

import java.util.ArrayList;

public class Test {
	public static void main(String[] args){
		Log l = new Log();
		Database db = new Database(l);
		User u = new User("7911156673", "Anna Plåster", User.DOCTOR_LEVEL, "LUS");
		
		
		db.load();
		ArrayList<Record> list = new ArrayList<Record>();
		System.out.println(db.viewAll(u));
		//System.out.println(list);
		
		/*//Record(int pnummer, String pnamn, String sjukhus, String doktor, String sköterska, String diagnos){
		Record r = new Record("7810256413", "Pelle Karlsson", "MAS", "Per Injektion", "Anna Plåster", "Näsblod.");
		db.add(u, r);
		db.save();
		System.out.println(r);
		r = new Record("8403127818", "Per Johansson", "MAS", "Per Injektion", "Anna Plåster", "Stukat finger.");
		db.add(u, r);
		db.save();*/
		
	}
}
