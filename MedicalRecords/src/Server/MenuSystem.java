package Server;

import java.util.ArrayList;

public class MenuSystem {
	
	/* Menu:
	 * 1. List available records.
	 * 2. Edit available records.
	 * 3.
	 * 4. Create new record.
	 * 5. Delete a record.
	 * 6.
	 * 7. View log.
	 * 8.
	 * 9. 
	 * 0. Exit
	 */
	
	private int level;
	private int location, prevLocation;
	private User currentUser;
	private Database db;
	private Log log;
	private ArrayList<Record> lastList;
	
	public MenuSystem(User u, Database db, Log l){
		this.currentUser = u;
		this.db = db;
		this.level = u.getLevel();
		this.log = l;
		location = 0;
	}
	
	public String getMenu(ArrayList<Record> list){
		//lastList = list;
		//OK -> 1
		String mainHeader = "Huvudmeny:\n"+
							"--------------------------\n";
		String recordHeader = "Lista över journaler:\n"+
							  "--------------------------\n";
		String logHeader = "Log:\n"+
							"--------------------------\n";
	
		if(location == 0){
			switch (level){
				case User.PATIENT_LEVEL:
					return mainHeader + 
							"1. Visa alla journaler.\n" +
							"0. Avsluta.\n";
				case User.NURSE_LEVEL:
					return mainHeader + 
							"1. Visa alla journaler.\n" +
							"2. Redigera journaler.\n" +
							"0. Avsluta.\n";
				case User.DOCTOR_LEVEL:
					return mainHeader + 
							"1. Visa alla journaler.\n" +
							"2. Redigera journaler.\n" +
							"4. Skapa ny journal.\n" +
							"0. Avsluta.\n";
				case User.GOVERNMENT_LEVEL:
					return mainHeader + 
							"1. Visa alla journaler.\n" +
							"5. Ta bort journal.\n" +
							"7. Visa logfil.\n" +
							"0. Avsluta.\n";
				default:
					return "Error!";
			}
		}else if(location == 1){
			return recordHeader + db.displayString(db.viewAll(currentUser));
		}else if(location == 2){
			return recordHeader + db.displayString(db.viewAllEditable(currentUser));
		}else if(location == 5){
			return recordHeader + db.displayString(db.viewAll(currentUser));
		}else if(location == 7){
			return logHeader + log.load();
		}
		return "";
	}
	
	public String command(int val){
		ArrayList<Record> tempList;
		
		if(isValidOption(val) && location == 0){
			if(val == 0){
				if(location == 0){
					//exit
				}else if(location == -1){
					location = prevLocation;
				}else{
					location = 0;
				}
			}else{
				
				if(location == 0){
					location = val;
					prevLocation = 0;
				}else if(location == 1){
					tempList = db.viewAll(currentUser);
					if(val > tempList.size() || val < 1){
						return "Felaktigt kommando!";
					}else{
						location = -1;
						prevLocation = 1;
						return db.viewSpecific(currentUser, tempList.get(val-1));
					}
				}else if(location == 2){
					tempList = db.viewAllEditable(currentUser);
					if(val > tempList.size() || val < 1){
						return "Felaktigt kommando";
					}else{
						return db.viewSpecific(currentUser, tempList.get(val-1));
					}
				}else if(location == 4){
					
				}else if(location == 5){
					tempList = db.viewAll(currentUser);
					if(val > tempList.size() || val < 1){
						return "Felaktigt kommando";
					}else{
						return db.viewSpecific(currentUser, tempList.get(val-1));
					}
				}
				

			}
		
		}else{
			return "Felaktigt kommando!";
		}
		return "";
	}
	
	public int currentLocation(){
		return location;
	}

	private boolean isValidOption(int val){
		switch (val){
			case 1:
				return true;
			case 2:
				if(level == User.NURSE_LEVEL || level == User.DOCTOR_LEVEL){
					return true;
				}
				return false;
			case 3:
				return false;
			case 4:
				if(level == User.DOCTOR_LEVEL){
					return true;
				}
				return false;
			case 5:
				if(level == User.GOVERNMENT_LEVEL){
					return true;
				}
				return false;
			case 6:
				return false;
			case 7:
				if(level == User.GOVERNMENT_LEVEL){
					return true;
				}
				return false;
			case 8:
				return false;
			case 9:
				return false;
			case 0:
				return true;
			default:
				return false;
		}
	}

	
	
}
