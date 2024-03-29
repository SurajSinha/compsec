package Server;

public class User {
	
	//Personnummer
	//AccessNivå { 0: Government, 3: Doctor, 6: Nurse, 9: Patient }
	//OM Personal -> Sjukhus
	
	public static final int GOVERNMENT_LEVEL = 0;
	public static final int DOCTOR_LEVEL = 3;
	public static final int NURSE_LEVEL = 6;
	public static final int PATIENT_LEVEL = 9;
	
	private String namn, sjukhus, personnummer;
	private int level;
	
	
	public User(String pnummer, String name, int accessNivå){
		this.personnummer = pnummer;
		this.namn = name;
		this.level = accessNivå;
	}
	
	public User(String pnummer, String name, int accessNivå, String sjukhus){
		this.personnummer = pnummer;
		this.namn = name;
		this.level = accessNivå;
		this.sjukhus = sjukhus;
	}
	
	public int getLevel(){
		return level;
	}
	
	public String getPersonNumber(){
		return personnummer;
	}

	public String getName(){
		return namn;
	}
	
	public String getDivision(){
		return sjukhus;
	}

}
