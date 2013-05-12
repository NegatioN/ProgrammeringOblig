package no.HiOAProsjektV2013.Main;

import java.util.Calendar;
import java.util.GregorianCalendar;

import no.HiOAProsjektV2013.Interface.TestWindow;

/*
 * Klassen har som formål å håndtere alle datoer sendt fra input.
 */
public class DateHandler {

	
	public GregorianCalendar dateFixer(String dateToBeConverted, String timeToBeConverted) {
		//hvis ikke input matcher dd/mm/YYYY eller dd/mm/YY
		
		if(!dateToBeConverted.matches(TestWindow.dateRegex)){
			return null;
		}
		String sHour;
		int hour = 0;
		//hvis tid blir sendt med
		if (timeToBeConverted != null) {
			sHour = timeToBeConverted.substring(0, 2);
			hour = Integer.parseInt(sHour);
		}
		int day,month,year;
		
		String[] daymonthyear = dateToBeConverted.split("\\W");

		if(daymonthyear[2].matches("\\d{2}")){
			year = Integer.parseInt(daymonthyear[2]);
			if(year > 70){
				year = year + 1900;
			}
			//antar det er 2069-
			else{
				year = year + 2000;
			}
		}else{
			year = Integer.parseInt(daymonthyear[2]);
		}
		day = Integer.parseInt(daymonthyear[0]);
		month = Integer.parseInt(daymonthyear[1]);
			
		
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
