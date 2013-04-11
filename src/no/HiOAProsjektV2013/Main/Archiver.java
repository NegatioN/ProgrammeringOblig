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
	
	private Skole skolen = null;

	public void writeToFile(Skole skolen){
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("/src/savedData.data"))){
			
			out.writeObject(skolen);
			
		}catch(NotSerializableException nse){
			// ERROR-MESSAGE ikke serialisert
		}catch(IOException ioe){
			// ERROR-MESSAGE IO
		}
	}
	public Skole readFromFile(){
		try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("/src/savedData.data"))){
			skolen = (Skole) in.readObject();
		}catch(ClassNotFoundException cnfe){
			skolen = new Skole();
		}catch(FileNotFoundException fne){
			skolen = new Skole();
		}catch(IOException ioe){
			skolen = new Skole();
		}
		return skolen;
	}
}
