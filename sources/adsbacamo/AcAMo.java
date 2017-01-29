package adsbacamo;

import adsbmesser.*;
import adsbsenser.*;
import java.lang.Runnable;
import java.lang.Thread;

import java.util.Observer;

public class AcAMo /* implements Runnable */
{
	private ActiveAircraftServer myAircraftServer;
	private ActiveAircraft myAircraft;
	private AdsbMessageServer myAdsbMessageServer;

	private Thread myAdsbMessageServerThread;

	public AcAMo(String uri)
	{
		myAircraftServer = new ActiveAircraftServer();
		myAircraft = new ActiveAircraft();
		myAircraftServer.addObserver(myAircraft);

		myAdsbMessageServer = new AdsbMessageServer(uri);
		
		myAdsbMessageServerThread = new Thread(myAdsbMessageServer);

		this.setMesserObserver(myAircraftServer);
		this.myAdsbMessageServerThread.start();
	}
	public void setMesserObserver(Observer observer)
	{
		this.myAdsbMessageServer.addObserver(observer);
	}

	public void setAircraftObserver(Observer observer)
	{
		this.myAircraft.addObserver(observer);
	}

/*	@Override
	public void run()
	{
		while(true)
		{
		}
	}
*/
}
