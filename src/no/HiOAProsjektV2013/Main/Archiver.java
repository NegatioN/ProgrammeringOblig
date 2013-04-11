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
<<<<<<< HEAD
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savedData.data"))){
=======
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("src/savedData.data"))){
>>>>>>> Save skal fungere.
			
			out.writeObject(skolen);
			out.close();
			
		}catch(NotSerializableException nse){
<<<<<<< HEAD
			System.out.println("IKKE SERIALISERT");
=======
			nse.printStackTrace();
			System.out.println("Not serialized");
			// ERROR-MESSAGE ikke serialisert
>>>>>>> Save skal fungere.
		}catch(IOException ioe){
			System.out.println("IO error saving");
			// ERROR-MESSAGE IO
		}
	}
<<<<<<< HEAD
	public void readFromFile(){
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("savedData.data"))){
=======
	public Skole readFromFile(){
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/savedData.data"))){
>>>>>>> Save skal fungere.
			skolen = (Skole) in.readObject();
			in.close();
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
