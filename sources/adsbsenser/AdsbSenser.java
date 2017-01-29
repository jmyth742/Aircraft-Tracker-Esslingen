/**
 * AdsbSenser displays information about airplanes gathered from a specific
 * webserver given to the software via a parameter.
 *
 * @author David Mändlen
 * @version 0.9
 * */

package adsbsenser;

import java.lang.Thread;
import java.lang.Runnable;
import java.util.concurrent.*;
import java.util.*;

public class AdsbSenser extends Observable implements AdsbSenserInterface, Runnable
{
	private boolean runThread = false;
	private String pattern = "(?<timestamp>[0-9]+.[0-9]+)!ADS-B\\*(?<dfca>[0-9A-F]{2})(?<icao>[0-9A-F]{6})(?<payload>[0-9A-F]{14})(?<parity>[0-9A-F]{6})";
	private final int buffersize;

	private AdsbSentenceFactory factory;
	private StreamingWebClient client;
	private BlockingQueue<AdsbSentence> queue;

	/**
	 * @param clientURI this is where you find the webserver
	 * @param buffersize this is how much data you want to read
	 * */
	public AdsbSenser(String clientURI, int buffersize)
	{
		this.buffersize = buffersize;
		factory = new AdsbSentenceFactory();
		client = new StreamingWebClient(clientURI, this.buffersize);
		queue = new LinkedBlockingQueue<AdsbSentence>(1000);
		runThread = true;
	}

	/**
	 * Start Thread
	 * */
	@Override
	public void run()
	{
		while(runThread){
			try{
				queue.put(factory.fromWebdisJson(client.readChunk(pattern)));
				setChanged();
				notifyObservers(queue.take());
			} catch (Exception e){}
		}
	}

	/**
	 * Stop Thread
	 * */
	public void myStop()
	{
		runThread = false;
	}

	/**
	 * @return AdsbSentence-object
	 * */
	public AdsbSentence getSentence()
	{
		AdsbSentence outSentence = null;
		try {
			outSentence = queue.take();
		} catch (Exception e) {}

		return outSentence;
	}
}
