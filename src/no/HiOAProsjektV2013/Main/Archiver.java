package no.HiOAProsjektV2013.Main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import no.HiOAProsjektV2013.DataStructure.Skole;

public class Archiver {
	
	private Skole skolen;

	public void writeToFile(Skole skolen){
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savedData.data"))){
			
			out.writeObject(skolen);
			
		}catch(NotSerializableException nse){
			System.out.println("IKKE SERIALISERT");
		}catch(IOException ioe){
			// ERROR-MESSAGE IO
		}
	}
	public void readFromFile(){
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("savedData.data"))){
			skolen = (Skole) in.readObject();
		}catch(ClassNotFoundException cnfe){
			System.out.println("Class not found");
			skolen = new Skole();
		}catch(FileNotFoundException fne){
			System.out.println("File not found");
			skolen = new Skole();
		}catch(IOException ioe){
			System.out.println("IO input error");
			skolen = new Skole();
		}
	}
	public Skole getSkole(){
		return skolen;
	}
}
