package adsbsenser;

/**
 * This class is to print adsb messages in the following format to stdout 
 * Time:		  Weekday, DD.MM.YYYY, hrs:min:sec.usec
 * Dfca:		  8D
 * Originator:	4692CA
 * Payload:	   584720707A0996
 * Parity:		49890A
 * 
 * Author: Ulf Schmelzer ulscit01@hs-esslingen.de
 * Date: 14.10.2014
 */

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.Scanner;

public class AdsbSentenceDisplay{
	
	/**
	 * Outputs the Adsbsentence in the following the format:
	 * Time:	Weekday, DD.MM.YYYY, hrs:min:sec.usec
	 * Dfca:	8D
	 * Originator:	4692CA
	 * Payload:	584720707A0996
	 * Parity:	49890A
	 *
	 * @param AdsbSentence object
	 */
	public void display(AdsbSentence sentence){
		if(sentence != null)
		{
			System.out.println("Time:\t\t" + formatAdsbTimestamp(sentence.getTimestamp()));
			System.out.println("DFCA:\t\t" + sentence.getDfca());
			System.out.println("Originator:\t" + sentence.getIcao());
			System.out.println("Payload:\t" + sentence.getPayload()); //needs to be fixed in AdsbSentence.java
			System.out.println("Parity:\t\t" + sentence.getParity());
		}
		else
		{
			throw new IllegalArgumentException("sentence is empty");
		}
	}
	/**
	 * Converts the String Timestamp to an correct formated Timestamp in Format
	 * Weekday, DD.MM.YYYY, hrs:min:sec.usec
	 *
	 * @param str Timestamp String (format: sec.milisec, 123123123.1123)
	 * @return time in the format: Weekday, DD.MM.YYYY, hrs:min:sec.usec
	 */
	private String formatAdsbTimestamp(String str)
	{
		Scanner scn = new Scanner(str).useDelimiter("\\.");
		if(scn.hasNext())
		{
			//
			long timeInMillis = (long)scn.nextInt()*1000l + (long)scn.nextInt()/10000;
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(timeInMillis);
			DateFormat myDateFormatter = new SimpleDateFormat("EEEE, dd.MM.YYYY, HH:mm:ss.SSS");
			return myDateFormatter.format(cal.getTime()); 
		}
		else
		{
			throw new IllegalArgumentException("String in wrong format use sec.ms");
		}
	}
}
