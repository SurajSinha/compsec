package Server;

public class Record {
	//Personnummer
	//Namn på patient
	//Namn på sköterska
	//Namn på doktor
	//Sjukhus
	//Diagnos
	//Number;Name;Division;Doctor;Nurse;Diagnose;
	
	
	
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
		//Number;Name;Divison;Doctor;Nurse;Diagnose;
		return patientPersonnummer + ";" + patientNamn + ";" + sjukhus + ";" + doktor + ";" + sköterska + ";" + diagnos; 
	}
	
	public void editNurse(String sköterska){
		this.sköterska = sköterska;
	}
	
	public void editDiagnose(String diagnos){
		this.diagnos = diagnos;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((diagnos == null) ? 0 : diagnos.hashCode());
//		result = prime
//				* result
//				+ ((patientPersonnummer == null) ? 0 : patientPersonnummer
//						.hashCode());
//		return result;
//	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Record other = (Record) obj;
		if (diagnos == null) {
			if (other.diagnos != null)
				return false;
		} else if (patientPersonnummer == null) {
			if (other.patientPersonnummer != null)
				return false;
		} else if (!diagnos.equals(other.diagnos) || !patientPersonnummer.equals(other.patientPersonnummer))
			return false;
//		if (patientPersonnummer == null) {
//			if (other.patientPersonnummer != null)
//				return false;
//		} else if (!patientPersonnummer.equals(other.patientPersonnummer))
//			return false;
		return true;
	}
	
	/*public static void main(String[] args){
		Record r = new Record("19881113-1058","Kalle Andersson","Lunds Universitetssjukhus", "Per Injektion", "Annika Plåstra", "Patienten har visat symptom av malaria.\nAntibiotika utskivet.");
		System.out.println(r.toString());
	}*/
	
}
