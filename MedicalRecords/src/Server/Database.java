package Server;

import java.util.ArrayList;

public class Database {
	private static String fileName = "database.db";
	
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
		if(u.getLevel() == u.GOVERNMENT_LEVEL){
			return list;
		}else if(u.getLevel() == u.DOCTOR_LEVEL){
			for(int i=0;i<list.size();i++){
				Record tempRecord = list.get(i);
				if(tempRecord.sjukhus.equalsIgnoreCase(u.getDivision())){
					temp.add(tempRecord);
				}
			}
		}
			return temp;
	}
	public void load(){}
	public void save(){}
	
}
