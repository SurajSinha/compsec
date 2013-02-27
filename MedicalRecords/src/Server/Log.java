package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.sun.crypto.provider.DESCipher;

public class Log {
	
	//type;event

	private static String fileName = "eventLog.lg";
	
	public Log(){
		//load();
	}
	
	public void addEvent(String event, String description){
		try {
			FileWriter fileWriter = new FileWriter(fileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			bufferedWriter.append(event +";"+description+"\n");

			bufferedWriter.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String load(){
		StringBuilder sb = new StringBuilder();
		try{
			FileInputStream fstream = new FileInputStream(fileName);
			DataInputStream datastream = new DataInputStream(fstream);
			BufferedReader reader = new BufferedReader(new InputStreamReader(datastream));
	
			String stringLine = reader.readLine();
			while (stringLine != null) {
				sb.append(parseLine(stringLine)+"\n");
				stringLine = reader.readLine();
			}
	
			reader.close();
			datastream.close();
			fstream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "Event\tInfo\n" + sb.toString();
	
	}
	
	private String parseLine(String stuff){
		String[] data = stuff.split(";");
		return data[0] + "\t" + data[1];
	}
	
//	public void save(){}
	
//	public String toString(){
//		//TIMESTAMP\t EVENT\t DESCRIPTION
//		return "";
//	}
}
