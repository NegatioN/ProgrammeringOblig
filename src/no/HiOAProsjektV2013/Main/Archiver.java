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

	public void writeToFile(Skole skolen) {

		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream("src/savedData.data"))) {
			out.writeObject(skolen);
			//skriver statics til fil.
			out.writeInt(skolen.getStudentene().getStudentnummer());
			
			
			out.close();

		} catch (NotSerializableException nse) {

			nse.printStackTrace();
			System.out.println("Not serialized");
		} catch (IOException ioe) {
			System.out.println("IO error saving");
		}
	}

	public Skole readFromFile() {
		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(
				"src/savedData.data"))) {
			skolen = (Skole) in.readObject();
			//leser statics
			int studentnummer = in.readInt();
			skolen.getStudentene().setStudentNrCount(studentnummer);
			in.close();
		} catch (ClassNotFoundException cnfe) {
			System.out.println("Class not found");
			skolen = new Skole();
		} catch (FileNotFoundException fne) {
			System.out.println("File not found");
			skolen = new Skole();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("IO input error");
//			skolen = new Skole();
		}
		return skolen;
	}

	public Skole getSkole() {
		return skolen;
	}
}
