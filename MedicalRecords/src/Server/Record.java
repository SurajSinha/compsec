package Server;

public class Record {
	//Personnummer
	//Namn på patient
	//Namn på sköterska
	//Namn på doktor
	//Sjukhus
	//Diagnos
	//Number;Name;Nurse;Doctor;Division;Diagnose;
	
	
	
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
	protected String patientNamn, sjukhus, doktor, sköterska, patientPersonnummer;
	private String diagnos;
	
	public Record(String pnummer, String pnamn, String sjukhus, String doktor, String sköterska, String diagnos){
		this.patientPersonnummer = pnummer;
		this.patientNamn = pnamn;
		this.sköterska = sköterska;
		this.doktor = doktor;
		this.sjukhus = sjukhus;
		this.diagnos = diagnos;
	}
	
	public String toString(){
		return "Journal för "+ patientNamn + "(" + patientPersonnummer + ")\n" +
				"------------------------------------------------------" +"\n" +
				"Sjukhus: " + sjukhus +"\n" +
				"Doktor: " + doktor +"\n" +
				"Sköterska: " + sköterska +"\n" +
				"------------------------------------------------------" +"\n" +
				"Diagnos:\n" + diagnos +"\n" +
				"------------------------------------------------------";
	}
	
	public String toSaveString(){
		//Number;Name;Nurse;Doctor;Division;Diagnose;
		return patientPersonnummer + ";" + patientNamn + ";" + sköterska + ";" + doktor + ";" + sjukhus + ";" + diagnos; 
	}
	
	public void editNurse(String sköterska){
		this.sköterska = sköterska;
	}
	
	public void editDiagnose(String diagnos){
		this.diagnos = diagnos;
	}
	
	/*public static void main(String[] args){
		Record r = new Record("19881113-1058","Kalle Andersson","Lunds Universitetssjukhus", "Per Injektion", "Annika Plåstra", "Patienten har visat symptom av malaria.\nAntibiotika utskivet.");
		System.out.println(r.toString());
	}*/
	
}
