package adsbsenser;

import java.util.regex.*;

public class AdsbSentenceFactory implements AdsbSentenceFactoryInterface
{
	public AdsbSentence fromWebdisJson (String Json)
	{
		String timestamp, dfca, icao, payload, parity;

		Pattern pat = Pattern.compile("(?<timestamp>[0-9]+.[0-9]+)!ADS-B\\*(?<dfca>[0-9A-F]{2})(?<icao>[0-9A-F]{6})(?<payload>[0-9A-F]{14})(?<parity>[0-9A-F]{6})");
		Matcher m = pat.matcher(Json);
		if(m.find()){
			timestamp = m.group("timestamp");
			dfca = m.group("dfca");
			icao = m.group("icao");
			payload = m.group("payload");
			parity = m.group("parity");
		}
		else {
			throw new IllegalArgumentException("JSON invalid");
		}

		AdsbSentence newSentence = new AdsbSentence(timestamp, dfca, icao, payload, parity);
		return newSentence;
	}
}
