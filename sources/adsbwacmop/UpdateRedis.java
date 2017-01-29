package adsbwacmop;

import adsbmesser.*;
import adsbacamo.*;

import java.lang.StringBuilder;
import java.util.*;

import redis.clients.jedis.*;


public class UpdateRedis implements Observer, Runnable
{
	//private variables
	private String KmlString;
	private Aircraft[] Aircraftlist;
	private StringBuilder tmp;
	
	//Jedis variables new redis for compile
	Jedis rediscli;
	
	
	//constructor
	public UpdateRedis()
	{
		//create Aircraftlist
		this.Aircraftlist = null;
		//connect redis client
		//construct jedis connections
		rediscli = new Jedis("localhost");
	}
	
	//method to determine the correct png file depending on direction
	public String getPng (int heading)
	{
		if (heading == 0)
		{
			return ("plane00.png");
		}
		else if (heading >= 1 && heading < 18)
		{
			return ("plane01.png");
		}
		else if (heading >= 18 && heading < 36)
		{
			return ("plane02.png");
		}
		else if (heading >= 36 && heading < 54)
		{
			return ("plane03.png");
		}
		else if (heading >= 54 && heading < 72)
		{
			return ("plane04.png");
		}
		else if (heading >= 72 && heading < 90)
		{
			return ("plane05.png");
		}
		else if (heading == 90)
		{
			return ("plane06.png");
		}
		else if (heading > 90 && heading < 108)
		{
			return ("plane07.png");
		}
		else if (heading >= 108 && heading < 128)
		{
			return ("plane08.png");
		}
		else if (heading >= 128 && heading < 144)
		{
			return ("plane09.png");
		}
		else if (heading >= 144 && heading < 162)
		{
			return ("plane10.png");
		}
		else if (heading >= 162 && heading < 180)
		{
			return ("plane11.png");
		}
		else if (heading == 180)
		{
			return ("plane12.png");
		}
		else if (heading >= 181 && heading < 198)
		{
			return ("plane13.png");
		}
		else if (heading >= 198 && heading < 216)
		{
			return ("plane14.png");
		}
		else if (heading >= 216 && heading < 234)
		{
			return ("plane15.png");
		}
		else if (heading >= 234 && heading < 252)
		{
			return ("plane16.png");
		}
		else if (heading >= 252 && heading < 270)
		{
			return ("plane17.png");
		}
		else if (heading == 270)
		{
			return ("plane18.png");
		}
		else if (heading >= 271 && heading < 288)
		{
			return ("plane19.png");
		}
		else if (heading >= 288 && heading < 306)
		{
			return ("plane20.png");
		}
		else if (heading >= 306 && heading < 324)
		{
			return ("plane21.png");
		}
		else if (heading >= 324 && heading < 342)
		{
			return ("plane22.png");
		}
		else if (heading >= 342 && heading < 359)
		{
			return ("plane23.png");
		}
		else
		{
			return ("");
		}
	}
	
	public String buildKmlString(Aircraft ac)
	{
		tmp = new StringBuilder();
		
		//insert KML text
		//tmp.append("<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document><Style id=\"");
		tmp.append("<Style id=\"");
		
		//insert AircraftId for Style Id
		tmp.append(ac.getAircraftId());
		
		//insert KML text
		tmp.append("\"><IconStyle><scale>0.7</scale><heading>");
		
		//insert Heading
		tmp.append(ac.getHeading() + "</heading>");
		tmp.append("<Icon><href>http://localhost:80/Icons/");
		
		//Abfrage welches Icon abhängig von Heading
		tmp.append(getPng(ac.getHeading()));
		
		
		//insert KML text
		tmp.append("</href></Icon>");
		tmp.append("</IconStyle></Style><Placemark><name>");
		
		//insert AircarftId
		tmp.append(ac.getAircraftId());
		
		//insert KML text
		tmp.append("</name><description>EZY19PE Lon: ");
		
		//insert Longitude
		tmp.append(ac.getCprLongitude());
		
		//insert Latitude
		tmp.append(" Lat: ");
		tmp.append(ac.getCprLatitude());
		
		//insert Altitude
		tmp.append(" Alt: ");
		tmp.append(ac.getAltitude() + "m");
		
		//insert Direction
		tmp.append(" Dir: ");
		tmp.append(ac.getHeading() +"deg");
		
		//insert Velocity
		tmp.append(" Vel: ");
		tmp.append(ac.getSpeed() + "kn");
		
		//insert ClimpRate (not implemented insert 0)
		tmp.append(" Clm: 0ft/min");
		
		//insert KML text, end description open StyleURL
		tmp.append("</description><styleUrl>#");
		
		//insert AricraftId
		tmp.append(ac.getAircraftId());
		
		//insert KML text
		tmp.append("</styleUrl><Point><coordinates>");
		
		//insert coordinates
		tmp.append(ac.getCprLongitude() +", " + ac.getCprLatitude() +", " + ac.getAltitude());
		
		//insert KML text insert end
		tmp.append("</coordinates><altitudeMode>relativeToGround</altitudeMode><extrude>1</extrude></Point></Placemark>");
		
		return tmp.toString();
	}
	
	
	
	
	
	//update method
	public void update(Observable o, Object arg)
	{
		if(arg instanceof Aircraft[])
		{
			this.Aircraftlist = (Aircraft[]) arg;
		}
		else
		{
			this.Aircraftlist = null;
			System.out.println("Arg for Display was invalid");
		}
	}
	
	
	//run mehtod
	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(4000);
			}
			catch (InterruptedException e)
			{
				System.out.println(e.toString());
			}
			
			if(this.Aircraftlist != null)
			{
				//make head of KMLFile
				//KmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document>";
				
				KmlString = "<kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document>";
				
				for(Aircraft ac: this.Aircraftlist)
				{
					//check if aircraft is complete (when CprFormat == 2)
					if(ac.getCprFormat() == 2)
					{
						//build KML-String
						KmlString += this.buildKmlString(ac);
					}
				}
				//make tail of kmlfile
				KmlString = KmlString + "</Document></kml>";
				
				//push key + KML-String to redis
				//System.out.println("Debugging: show KML-String");
				//System.out.println(KmlString + "\n");
						
				//rediscli.publish ("KML", KmlString);
				rediscli.set ("KML", KmlString);
				System.out.println(rediscli.get ("KML"));
			}
		}
	}
	
	
}