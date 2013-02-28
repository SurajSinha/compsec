package Server;

import java.util.ArrayList;
import java.util.Scanner;

public class Test {
	public static void main(String[] args){
		
		
		Passwords pw=new Passwords();
		System.out.println(pw.hasPassword("1234"));
		pw.add("1234", Passwords.hash("1111"));
		System.out.println(pw.hasPassword("1234"));
		System.out.println(pw.validateUser("1234", Passwords.hash("2222")));
		System.out.println(pw.validateUser("1234", Passwords.hash("1111")));
		//return;
		
		Log l = new Log();
		Database db = new Database(l);
		User u = new User("7911156673", "Willhelm EKG", User.GOVERNMENT_LEVEL, "MAS");
		
		
		ArrayList<Record> list = new ArrayList<Record>();

		MenuSystem ms = new MenuSystem(u,db,l);
		String out;
		Scanner scan = new Scanner(System.in);
		
		do{
			if(ms.currentLocation() == -5){
				System.out.print("Vill du ta bort denna journal? (J/N)");
				String val = scan.next();
				if(val.equalsIgnoreCase("J")){
					ms.command(1);
				}else if(val.equalsIgnoreCase("N")){
					ms.command(0);
				}
				
			}else if(ms.currentLocation() == -4){
				//Wait for new journal or cancel
			}else if(ms.currentLocation() == -2){
				//Wait for edit or cancel
			}
			
			System.out.print(ms.getMenu());
			if(ms.currentLocation() != 0){
				System.out.print("Val (0 = föregående meny): ");
			}else{
				System.out.print("Val: ");
			}
			
			int val = scan.nextInt();
			out = ms.command(val);
			System.out.println(out);
		}while(!out.equals("EXIT"));
		
		//Record r = new Record("7101056617", "Nisse Johansson", "LUS", "Per Injektion", "Anna Plåster", "Exem.");
		//db.add(u, r);
		//list = db.viewAll(u);
		//System.out.println(db.displayString(list));
		//System.out.println("------------------------------");
		//list = db.viewAllEditable(u);
		//System.out.println(db.displayString(list));
		
		//db.delete(u, new Record("8403127819", "Per Johansson", "MAS", "Per Injektion", "Annika Krycka","Stukat finger."));
		
		//list = db.viewAll(u);
		//System.out.println(db.displayString(list));
		
		//System.out.println(list.get(0).toString());
		
		//l.addEvent("TEST", "TEST #2");
		//System.out.println(l.load());
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
