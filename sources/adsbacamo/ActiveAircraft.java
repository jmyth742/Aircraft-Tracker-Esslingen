package adsbacamo;

import adsbmesser.*;
import adsbsenser.*;

import java.lang.Runnable;
import java.util.concurrent.Semaphore;
import java.util.Observer;
import java.util.Observable;


public class ActiveAircraft extends Observable implements Observer
{
	private Aircraft tempac;
	private String[] myIcaoList;
	private Aircraft[] myAircraftList;
	private Semaphore mySem;
	private AircraftCache myAircraftCache;

	//constructor
	public ActiveAircraft()
	{
		mySem = new Semaphore(1, true);
		myAircraftCache = new AircraftCache();
		myAircraftCache.addObserver(this);
	}

	//updateMethode
	@Override
	public void update(Observable o, Object arg)
	{
		if(arg != null){
			System.out.println("Debugging: update-method of AA started");
			this.appendAircraft((Aircraft) arg);
		}

		this.convertCache();
		setChanged();
		notifyObservers(this.myAircraftList);
	}

	//other methods
	private void appendAircraft(Aircraft myAircraft)
	{
		try {
			if(mySem.tryAcquire()) {
				System.out.println("Debugging: appendAircraft in AA has Semaphore");
				//check if Aircraft already exists
				tempac = (Aircraft) myAircraftCache.get(myAircraft.getIcao());
				//if not, construct aircraft and append
				if(tempac == null)
				{
					System.out.println("Debugging: tempac was null");
					myAircraftCache.put(myAircraft.getIcao(), myAircraft, 120000);
					System.out.println("Debugging: tempac was put into cache");
				}

				else
				{
					tempac.setIcao(myAircraft.getIcao());
					//check message type and get specific data and set to existing aircraft
					//if (myAircraft.getAltitude() != 0)
					if (myAircraft.getCprFormat() == 2)
					{
						System.out.println("Debugging: updating ac in AA with posmsg");
						tempac.setAltitude(myAircraft.getAltitude());
						tempac.setCprFormat(myAircraft.getCprFormat());
						tempac.setCprLongitude(myAircraft.getCprLongitude());
						tempac.setCprLatitude(myAircraft.getCprLatitude());
					}

					else if (myAircraft.getSpeed() != 0)
					{
						System.out.println("Debugging: updating ac in AA with velmsg");
						tempac.setSpeed(myAircraft.getSpeed());
						tempac.setHeading(myAircraft.getHeading());
					}

					else if (myAircraft.getAircraftId() != "")
					{
						System.out.println("Debugging: updating ac in AA with idmsg");
						tempac.setAircraftId(myAircraft.getAircraftId());
					}

					myAircraftCache.put(tempac.getIcao(), tempac, 120000);
					System.out.println("Debugging: tempac was put into cache");
				}
				
				mySem.release();
			}
			else {
				throw new Exception("Could not acquire semaphore to appendAircraft");
			}
		}
		catch (Exception e) {System.out.println(e.toString());}
		
	}

	private boolean convertCache()
	{
		try {
			if(mySem.tryAcquire()) {
				System.out.println("Debugging: convertCache in AA has Semaphore");
				this.myIcaoList = myAircraftCache.getAllKeys();
				myAircraftList = new Aircraft[myIcaoList.length];
				int i = 0;
				for(String Icao: myIcaoList){
					this.myAircraftList[i] = (Aircraft) myAircraftCache.get(Icao);
					i++;
				}
				mySem.release();

			}
			else {
				throw new Exception("Could not acquire semaphore to convertCache");
			}
		}
		catch (Exception e) {System.out.println(e.toString());}
		return true;
	}
}
