

import adsbmesser.*;
import adsbwacmop.*;
import adsbacamo.*;
import adsbsenser.*;
import adsbacamo.ActiveAircraftServer;
import adsbacamo.ActiveAircraft;
import adsbacamo.Aircraft;
import adsbacamo.AircraftInterface;

import redis.clients.jedis.*;

import java.lang.Runnable;
import java.lang.Thread;

public class WacmopMain
{
	static private AircraftDisplay myDisplay;
	static private UpdateRedis myUpdate;
	static private Thread myDisplayThread;
	static private Thread myUpdateThread;
	static private WebServer myWebServer;
	private AcAMo myAcAMo;

	public WacmopMain()
	{
		myDisplay = new AircraftDisplay("Flugmonitor");
		myAcAMo = new AcAMo("http://flugmon-it.hs-esslingen.de/subscribe/ads.sentence");
		myUpdate = new UpdateRedis();
		try
		{
			myWebServer = new WebServer (3333);
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
		}
		

		myAcAMo.setMesserObserver(myDisplay);
		myAcAMo.setAircraftObserver(myDisplay);
		myAcAMo.setAircraftObserver(myUpdate);

		myDisplayThread = new Thread(myDisplay);
		myUpdateThread = new Thread(myUpdate);
		
		
	}

	public static void main(String[] args)
	{
		WacmopMain myMain = new WacmopMain();
		myDisplayThread.start();
		myUpdateThread.start();
	}
}
