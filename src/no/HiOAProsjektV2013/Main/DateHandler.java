package no.HiOAProsjektV2013.Main;

import java.util.Calendar;
import java.util.GregorianCalendar;

/*
 * Klassen har som formål å håndtere alle datoer sendt fra input.
 */
public class DateHandler {

	
	public GregorianCalendar dateFixer(String dateToBeConverted, String timeToBeConverted, String regex) {
		String sHour;
		int hour = 0;
		if (timeToBeConverted != null) {
			sHour = timeToBeConverted.substring(0, 2);
			hour = Integer.parseInt(sHour);
		}
		String sDay, sMonth, sYear;
		int day,month,year;
		sDay = dateToBeConverted.substring(0, 2);
		sMonth = dateToBeConverted.substring(3, 5);
		//sjekker om hvilken regex som ble aktivert for å sende input til klassen.
		if(dateToBeConverted.matches(regex)){
		sYear = dateToBeConverted.substring(6, 10);
		year = Integer.parseInt(sYear);
		}else{
			sYear = dateToBeConverted.substring(6, 8);
			year = Integer.parseInt(sYear);
			if(year > 70){
				year = year + 1900;
			}else{
				year = year + 2000;
			}
		}
		day = Integer.parseInt(sDay);
		month = Integer.parseInt(sMonth) - 1;
		// true hvis month invalid value
		if (month < 0 || month > 11) {
			return null;
		}
		int dayCheck = new GregorianCalendar(year, month, 1, hour, 0, 0)
				.getActualMaximum(Calendar.DAY_OF_MONTH);
		// true hvis day invalid value
		if (day < 1 || day > dayCheck)
			return null;
		
		GregorianCalendar newDate = new GregorianCalendar(year, month, day,
				hour, 0, 0);
		return newDate;
	}
}
