package no.HiOAProsjektV2013.Main;

//Joakim Rishaug - s188080 - Dataingeniør - 1AA
//Siste versjon: 14.05.13
import java.util.Calendar;
import java.util.GregorianCalendar;

import no.HiOAProsjektV2013.Interface.Vindu;

/*
 * Klassen har som formål å håndtere alle datoer sendt fra input.
 */
public class DateHandler {

	private final int DAY = 0, MONTH = 1, YEAR = 2;
	
	public GregorianCalendar dateFixer(String dateToBeConverted, String timeToBeConverted) {
		//hvis ikke input matcher dd/mm/YYYY eller dd/mm/YY
		
		if(!dateToBeConverted.matches(Vindu.dateRegex)){
			return null;
		}
		int hour = 0;
		//hvis tid blir sendt med
		if (timeToBeConverted != null) {
			//ikke implementert
		}
		int day,month,year;
		
		String[] daymonthyear = dateToBeConverted.split("\\W");

		if(daymonthyear[YEAR].matches("\\d{2}")){
			year = Integer.parseInt(daymonthyear[YEAR]);
			if(year > 70){
				year = year + 1900;
			}
			//antar det er 2069-
			else{
				year = year + 2000;
			}
		}else{
			year = Integer.parseInt(daymonthyear[YEAR]);
		}
		day = Integer.parseInt(daymonthyear[DAY]);
		month = Integer.parseInt(daymonthyear[MONTH]);
		
		// true hvis month invalid value
		if (month < 0 || month > 12) {
			return null;
		}
		int dayCheck = new GregorianCalendar(year, month, 1, hour, 0, 0)
				.getActualMaximum(Calendar.DAY_OF_MONTH);
		// true hvis day invalid value
		if (day < 1 || day > dayCheck){
			return null;
		}
		
		GregorianCalendar newDate = new GregorianCalendar(year, month, day,
				hour, 0, 0);
		return newDate;
	}
}
