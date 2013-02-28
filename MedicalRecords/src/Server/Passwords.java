package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map.Entry;

public class Passwords {

	private final String fileName = "pw.db";
	
	private HashMap<String,String> map;
	
	public Passwords() {
		map=new HashMap<String, String>();		
		load();
	}
	
	public static String hash(String str)
	{
		byte[] data=(str+"lalalagoodmorningstarshine").getBytes();
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");
			 md.update(data);
			 byte[] b=md.digest();
			 return new java.math.BigInteger(1, b).toString(16);			 
			 
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;	
	
	}
	
	public boolean hasPassword(String personnummer)
	{
		return map.containsKey(personnummer);
	}
	
	public boolean validateUser(String personnummer,String passwordhash)
	{
		String hash=map.get(personnummer);
		return (passwordhash.equals(hash));
	}
	public void add(String personnummer,String passwordhash)
	{		
		map.put(personnummer, passwordhash);
		save();
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
		map.put(data[0],data[1]);
	}

		
	private void save(){
		try {
			FileWriter fileWriter = new FileWriter(fileName, false);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			//Number;Name;Nurse;Doctor;Division;Diagnose;
			String save = "";
			for(Entry<String, String> e: map.entrySet())
			{
				save += e.getKey()+";"+e.getValue() + "\n";
			}
			bufferedWriter.write(save);

			bufferedWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
