/*
 * ActiveAircraftServer
 *
 * aircraftserver observes adsbMesser (obtains new Messages)
 * aircraftserver cuts Messages into peaces
 * calls constructor of Aircraft with respective parameters
 * notifies observers with new constructed aircraft
 * 
 * author: Parrotta, Antonio
 * contact: anpait00@hs-esslingen.de
 * modified:	2015-01-09
 *	 	2015-01-10
 *
 * new features with wacmop:
 *	builds an ArrayList of AdsbAirbornePositionMessages
 *	puts position messages in it
 *	it compares 2 position messages based on the ICAO
 *	if 2 pos messages corresponds to a specific plane they will be decoded to latlon ...
 *	... and deleted of ArrayList. The CprFormat is changed to 2 for sequel shit
 *	if not, the new pos message is put in the ArrayList ...
 *	..., normal pos messages will be made
*/

package adsbacamo;

import java.util.Observer;
import java.util.Observable;
import java.util.ArrayList;
import java.lang.Runnable;
import adsbmesser.*;

public class ActiveAircraftServer extends Observable implements Observer
{
	private AdsbMessage message;
	private Aircraft aircraft;
	private int type;

	private ArrayList<AdsbAirbornePositionMessage> myPosList;

	protected String timestamp;
	protected String icao;
	protected String aircraftId;
	protected int messageType;
	protected int speed;
	protected int heading;
	protected int altitude;
	protected int cprFormat;
	protected double cprLongitude; /* has to be changed to double */
	protected double cprLatitude;  /* has to be changed to double */

	//private LatLon decoder;
	private CprCoder decoder;
	
	// constructor
	public ActiveAircraftServer()
	{
		System.out.println("Debugging: Constructor of ActiveAircraftServer works");
		this.message = null;
		this.aircraft = null;
		this.timestamp = "";
		this.icao = "";
		this.aircraftId = "";
		this.messageType = 0;
		this.speed = 0;
		this.heading = 0;
		this.altitude = 0;
		this.cprFormat = 0;
		this.cprLongitude = 0.0;
		this.cprLatitude = 0.0;

		myPosList = new ArrayList<AdsbAirbornePositionMessage>();
		//decoder = new LatLon();
		decoder = new CprCoder();
	}

	@Override
	public void update(Observable o, Object arg)
	{
		System.out.println("Debugging: update-method of AAS started");
		this.message = (AdsbMessage) arg;	// argument from adsbMesser
		this.messageType = this.message.getType();
		makeAircraft(this.message);
		notifyObservers(this.aircraft);
	}

	private void makeAircraft(AdsbMessage message)
	{
		this.speed = 0;
		this.heading = 0;
		this.altitude = 0;
		this.cprFormat = 0;
		this.cprLongitude = 0.0;
		this.cprLatitude = 0.0;
		this.aircraftId = "";

		this.timestamp = message.getTimestamp();
		this.icao = message.getIcao();
		this.type = message.getType();	

		int index = -1;
		// identification
		if(type >= 1 && type <= 4){
			AdsbAircraftIdentificationMessage tempMessage = (AdsbAircraftIdentificationMessage) message;
			this.aircraftId = tempMessage.getAircraftId();
		}
		// position
		else if((type == 0) || (type >= 9 && type <= 18) || (type >= 20 && type <= 22))
		{
			AdsbAirbornePositionMessage tempMessage = (AdsbAirbornePositionMessage) message;
			this.altitude = tempMessage.getAltitude();
			this.cprFormat = tempMessage.getCprFormat();
			this.cprLongitude = (double)tempMessage.getCprLongitude();
			this.cprLatitude = (double)tempMessage.getCprLatitude();
			double [] latLon = new double[2];
			
			if(myPosList.isEmpty() == false)		/* if list is empty skip the following */
			{
				int i = 0;
				
				for(AdsbAirbornePositionMessage element : myPosList)
				{
					System.out.println("!!!!!!!!!!!!!!!!tempMessage" + tempMessage.getIcao() + "==" + element.getIcao());
					
					if(	(element.getIcao().compareTo(tempMessage.getIcao())) == 0 && (element.getCprFormat() != tempMessage.getCprFormat()))
					{
						System.out.println("????????????????????????????????????????????????????????????????????????");
						/* decode shit */
						// int [] lat = {element.getCprLatitude(), tempMessage.getCprLatitude()};
						// int [] lon = {element.getCprLongitude(), tempMessage.getCprLongitude()};
						
						
						latLon = decoder.decodeGlobalAirborne(element, tempMessage);
						this.cprLatitude = latLon[0];
						this.cprLongitude = latLon[1];

						this.cprFormat = 2;
						this.myPosList.remove(i);		// delete element from list
					}
					else
					{
						if( ( element.getIcao().compareTo(tempMessage.getIcao()) == 0) && 
							(element.getCprFormat() == tempMessage.getCprFormat())){
								/*latLon = decoder.decodeLocalAirborne(element, tempMessage);
								this.cprLatitude = latLon[0];
								this.cprLongitude = latLon[1];
								
								this.cprFormat = 2; */
								this.myPosList.remove(i);
							}					
					
					
					}
					
				i = i + 1;
				}
				
			}
			
			if(this.cprFormat != 2)
			{	
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				myPosList.add(tempMessage);			// if no pos message corresponds, put in list
			}
			
			
		}
		// velocity
		else if(type == 19)
		{
			AdsbAirborneVelocityMessage tempMessage = (AdsbAirborneVelocityMessage) message;
			if(tempMessage.getSubtype() >=1 && tempMessage.getSubtype() <= 4){
				this.speed = tempMessage.getSpeed();
				this.heading = tempMessage.getHeading();
			}
		}
		// create new Aircraft with parameters
		this.aircraft = new Aircraft(this.timestamp, this.icao, this.speed, this.heading, this.altitude, this.cprFormat, this.cprLongitude, this.cprLatitude, this.aircraftId );
		setChanged(); // returns true
		}
	
}
