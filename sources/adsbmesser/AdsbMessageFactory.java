package adsbmesser;

import adsbsenser.*;
import java.math.*;

/**
 * {@inheritdoc}
 **/
public class AdsbMessageFactory implements AdsbMessageFactoryInterface
{
	/**
	 * This method gets an ADSB-Message from an ADSB-Sentence
	 * @param ADSB-Sentence from ADSB-Sentence-Server
	 * @return ADSB-Message
	 * */
	public AdsbMessage fromAdsbSentence(AdsbSentence inputSentence)
	{
		String payload = inputSentence.getPayload();
		String binPayload = BinConverter.hex2bin(payload);
		int type = Integer.parseInt(binPayload.substring(0,5), 2); //first 5 bits
		int subtype = Integer.parseInt(binPayload.substring(5,8), 2); // next 3 bits

		String dfca = inputSentence.getDfca();
		String binDfca = BinConverter.hex2bin(dfca);
		int df = Integer.parseInt(binDfca.substring(0,5), 2); //first 5 bits
		int ca = Integer.parseInt(binDfca.substring(5,8), 2); //next 3 bits

		String icao = inputSentence.getIcao();
		String timestamp = inputSentence.getTimestamp();

		AdsbMessage outMessage = null;

		if(type >= 1 && type <= 4){
			outMessage = new AdsbAircraftIdentificationMessage(type, df, ca, payload, icao, timestamp);
		}

		else if((type == 0) || (type >= 9 && type <= 18) || (type >= 20 && type <= 22)){
			outMessage = new AdsbAirbornePositionMessage(type, df, ca, payload, icao, timestamp);
		}

		else if(type == 19 && (subtype >=1 && subtype <= 4)){
			outMessage = new AdsbAirborneVelocityMessage(type, df, ca, payload, icao, timestamp);
		}

		else {
			outMessage = new AdsbMessage(type, df, ca, payload, icao, timestamp);
		}

		return outMessage;
	}
}
