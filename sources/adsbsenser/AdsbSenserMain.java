package adsbsenser;

import java.util.*;

/**
 * Exists solely to provide a running program for the already implemented class
 * AdsbSenser for testing purposes
 *
 * @author David Mändlen, Ulf Schmelzer
 * */
public class AdsbSenserMain
{
	/**
	 * @param url An URL where you find the data you want to scrutinize
	 * @param buffersize The precise amount of bytes you want to read
	 * */
	public static void main (String[] args)
	{
		if(args.length == 2){
			AdsbSenser mySenser = new AdsbSenser(args[0], Integer.valueOf(args[1]));
			Thread myThread = new Thread(mySenser);
			myThread.start();
			
			AdsbSentenceDisplay myDisplay = new AdsbSentenceDisplay();

			while(true){
				try{
					myDisplay.display(mySenser.getSentence());
				} catch (Exception e){
					System.out.println(e);
				}
			}
		}
		else{
			System.out.println("usage: <url> <bufsize>");
		}
	}
}
