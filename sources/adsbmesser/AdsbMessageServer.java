package adsbmesser;

import java.lang.*;
import java.util.*;
import java.util.concurrent.*;
import adsbsenser.AdsbSenser;
import adsbsenser.AdsbSentence;

/**
 * @author David Mändlen, damait06@hs-esslingen.de
 * @author Antonio Parrotta, anpait00@hs-esslingen.de
 * */
public class AdsbMessageServer extends Observable implements AdsbMessageServerInterface, Runnable, Observer
{
	private Semaphore sem;
	private AdsbSenser mySenser;
	private Thread senserThread;
	private AdsbMessageFactoryInterface myFactory;
	private BlockingQueue<AdsbMessage> myQueue;
	private AdsbMessageDisplay myDisp;

	private AdsbSentence mySentence;

	private boolean runThread = true;

	/**
	 * @param uri please enter flugmon-address here
	 * */
	public AdsbMessageServer(String uri)
	{
		this.sem = new Semaphore(0,true); //create Semaphore, set to blocked

		//setup MessageServer Internals
		this.mySenser = new AdsbSenser(uri,1000);
		this.senserThread = new Thread(this.mySenser);
		this.myFactory = new AdsbMessageFactory();
		this.myQueue = new LinkedBlockingQueue<AdsbMessage>(1000);
		this.myDisp = new AdsbMessageDisplay();

		//start Thread
		this.senserThread.start();
	}

	/**
	 * fetches Sentences from ADSB-Sentence-Server, creates ADSB-Messages and displays them.
	 * */
	@Override
	public void run()
	{
		//declare this class as an Observer to the Sentence Server
		this.mySenser.addObserver(this);
		while(runThread){
			try{
				sem.acquire(); //see if sem is blocked
				if(mySentence != null){
					AdsbMessage myMessage = myFactory.fromAdsbSentence(mySentence); // create message from sentence
					if(myMessage != myQueue.peek()){ //see if this message occurred before
						myQueue.put(myMessage); // add new message to list
						setChanged();
						this.notifyObservers(myQueue.take());
					}
				}
				else{
					throw new Exception("Sentence is null");
				}
			}
			catch(Exception e){System.out.println(e.toString());}

			try{
				Thread.sleep(100); // make sure we don't overuse the processor
			}
			catch(InterruptedException e){System.out.println(e.toString());}
		}
	}

	@Override
	public void update(Observable o, Object arg)
	{
		this.mySentence = (AdsbSentence) arg; //accept sentence from Sentence Server
		sem.release(); //unblock semaphore
	}

	public void myStop()
	{
		runThread = false;
	}

	/**
	 * Returns Last ADSB-Message on queue
	 * @return ADSB-Message from Queue
	 * */
	public AdsbMessage getMessage() throws InterruptedException
	{
		return myQueue.take();
	}
}
