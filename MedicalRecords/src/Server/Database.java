package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
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
		save();
	}
	
	public void delete(User u, Record r){
		list.remove(r);
		log.addEvent("DELETE", u.getName() + " (" + u.getPersonNumber()+") removed a record for " + r.patientNamn + " (" + r.patientPersonnummer + ")" );
		save();
	}
	
	public String viewSpecific(User u, Record r){
		//Record r = list.get(r);
		log.addEvent("VIEW", u.getName() + " (" + u.getPersonNumber()+") viewed the record for " + r.patientNamn + " (" + r.patientPersonnummer + ")" );
		return r.toString();
	}
	public void edit(User u, Record r){		
		log.addEvent("EDIT", u.getName() + " (" + u.getPersonNumber()+") edited a record for " + r.patientNamn + " (" + r.patientPersonnummer + ")" );
		save();
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
					if(tempRecord.sjukhus.equalsIgnoreCase(u.getDivision())){
						temp.add(tempRecord);
					}
				}
				return temp;
			case User.PATIENT_LEVEL:
				for(int i=0;i<list.size();i++){
					Record tempRecord = list.get(i);
					if(tempRecord.patientPersonnummer.equalsIgnoreCase(u.getPersonNumber())){
						temp.add(tempRecord);
					}
				}
				return temp;
			default:
				return null;
		}
			
	}
	
	public String displayString(ArrayList<Record> temp) {
		String header = "#\tPersonnummer\tPatient\t\tSjukhus\t\tDoktor\t\tSköterska\n";
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<temp.size();i++){
			Record r = temp.get(i);
			sb.append((i+1)+"\t"+r.patientPersonnummer+"\t"+r.patientNamn+"\t"+r.sjukhus+"\t\t"+r.doktor+"\t"+r.sköterska+"\n");
		}
		return header + sb;
	}

	public ArrayList<Record> viewAllEditable(User u){
		ArrayList<Record> temp = new ArrayList<Record>();
		switch (u.getLevel()){
			case User.DOCTOR_LEVEL:
				for(int i=0;i<list.size();i++){
					Record tempRecord = list.get(i);
					if(tempRecord.doktor.equalsIgnoreCase(u.getName())){
						temp.add(tempRecord);
					}
				}
				return temp;
			default:
				return null;
		}
	}
	
	private void load(){
		try{
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream datastream = new DataInputStream(fstream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(datastream));
	
			String stringLine = reader.readLine();
			while (stringLine != null) {
				parseLine(stringLine);
				stringLine = reader.readLine();
			}
	
			reader.close();
			datastream.close();
			fstream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	private void parseLine(String stuff){
		String[] data = stuff.split(";");
		Record r = new Record(data[0],data[1],data[2],data[3],data[4],data[5]);
		list.add(r);
	}

		
	private void save(){
		try {
			FileWriter fileWriter = new FileWriter(fileName, false);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			//Number;Name;Nurse;Doctor;Division;Diagnose;
			String save = "";
			for(int i=0;i<list.size();i++){
				save += list.get(i).toSaveString() + "\n";
			}
			bufferedWriter.write(save);

			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
