package Server;

import java.util.ArrayList;

public class Database {
	
	private final String fileName = "database.db";
	
	private Log log;
	private ArrayList<Record> list;
	
	public Database(Log l){
		this.list = new ArrayList<Record>();
		this.log = l;
		load();
	}
	
	public void add(User u, Record r){
		list.add(r);
		log.addEvent("ADD", u.getName() + " (" + u.getPersonNumber()+") added a record for " + r.patientNamn + " (" + r.patientPersonnummer + ")" );
	}
	
	public void delete(User u, int index, Record r){
		list.remove(index);
		log.addEvent("DELETE", u.getName() + " (" + u.getPersonNumber()+") added a record for " + r.patientNamn + " (" + r.patientPersonnummer + ")" );
	}
	public ArrayList<Record> viewAll(User u){
		ArrayList<Record> temp = new ArrayList<Record>();
		
		switch (u.getLevel()){
			case User.GOVERNMENT_LEVEL:
				return list;
			case User.DOCTOR_LEVEL:
				for(int i=0;i<list.size();i++){
					Record tempRecord = list.get(i);
					if(tempRecord.sjukhus.equalsIgnoreCase(u.getDivision())){
						temp.add(tempRecord);
					}
				}
				return temp;
			case User.NURSE_LEVEL:
				for(int i=0;i<list.size();i++){
					Record tempRecord = list.get(i);
					if(tempRecord.sköterska.equalsIgnoreCase(u.getName())){
						temp.add(tempRecord);
					}
				}
				return temp;
			case User.PATIENT_LEVEL:
				for(int i=0;i<list.size();i++){
					Record tempRecord = list.get(i);
					if(tempRecord.patientPersonnummer == u.getPersonNumber()){
						temp.add(tempRecord);
					}
				}
				return temp;
			default:
				return null;
		}
			
	}
	
	public ArrayList<Record> viewAllEditable(User u){
		ArrayList<Record> temp = new ArrayList<Record>();
		return temp;
	}
	
	public void load(){}
	public void save(){}
	
}
