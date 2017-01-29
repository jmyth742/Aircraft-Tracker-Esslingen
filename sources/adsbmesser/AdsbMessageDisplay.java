package adsbmesser;

import java.lang.StringBuffer;
import java.util.*;

/**
 * {@inheritdoc}
 * */
public class AdsbMessageDisplay implements AdsbMessageDisplayInterface, Observer
{
	/**
	 * Displays AdsbMessage on console
	 * @param AdsbMessage msg
	 * */
	public void display(AdsbMessageInterface msg)
	{
		StringBuffer sb = new StringBuffer();

		if(msg instanceof AdsbAirbornePositionMessage){
			AdsbAirbornePositionMessage airPosMsg = (AdsbAirbornePositionMessage) msg;
			sb.append(airPosMsg.getIcao() + "\t Airborne Position Message\n\r");
			sb.append("\t\tType:\t" + airPosMsg.getType()+"\n\r");
			sb.append("\t\tAlti:\t" + airPosMsg.getAltitude() + "\n\r");
			sb.append("\t\tLatlon:\t" + airPosMsg.getCprLatitude() + " : " + airPosMsg.getCprLongitude() + "\n\r");
			if(airPosMsg.getCprFormat() == 0){
				sb.append("\t\tFormat:\t" + "even\n\r");
			}
			else{
				sb.append("\t\tFormat:\t" + "odd\n\r");
			}
		}

		else if(msg instanceof AdsbAirborneVelocityMessage){
			AdsbAirborneVelocityMessage airVelMsg = (AdsbAirborneVelocityMessage) msg;
			sb.append(airVelMsg.getIcao() + "\t Airborne Velocity Message\n\r");
			sb.append("\t\tSpeed:\t" + airVelMsg.getSpeed() + "\n\r");
			sb.append("\t\tHeadng:\t" + airVelMsg.getHeading() + "\n\r");
			sb.append("\t\tVertic:\t" + airVelMsg.getVerticalSpeed() + "\n\r");
		}

		else if(msg instanceof AdsbAircraftIdentificationMessage){
			AdsbAircraftIdentificationMessage airIdMsg = (AdsbAircraftIdentificationMessage) msg;
			sb.append(airIdMsg.getIcao() + "\t Aircraft Identification and Category Message\n\r");
			sb.append("\t\tIdent:\t" + airIdMsg.getAircraftId() + "\n\r");
			sb.append("\t\tCateg:\t" + airIdMsg.getEmitterCategory() + "\n\r");
		}

		else{
			sb.append(msg.getIcao() + "\t Other Message\n\r");
			sb.append("\t\tType:\t" + msg.getType() + "\n\r");
		}
		System.out.println(sb.toString());
	}

	@Override
	public void update(Observable o, Object arg)
	{
		this.display((AdsbMessage) arg); //accept sentence from Sentence Server
		//sem.release(); //unblock semaphore
	}
}
