package Server;

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
	private int location;
	
	public MenuSystem(int level){
		this.level = level;
		location = 0;
	}
	
	public String getMenu(){
		//OK -> 1
		String mainHeader = "Huvudmeny:\n"+
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
			return "";
		}
		return "";
	}
	
	public void command(int val){
		
		if(isValidOption()){
			if(location == 0){
				location = val;
			}
		}else{
			
		}
		
	}

	private boolean isValidOption(){
		return false;
	}

	
	
}
