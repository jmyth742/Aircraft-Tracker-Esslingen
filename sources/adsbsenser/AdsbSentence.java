package adsbsenser;

import java.util.regex.*;
/**
*{@inheritdoc}
**/
public class AdsbSentence implements AdsbSentenceInterface
{
	private String timestamp;
	private String dfca;
	private String icao;
	private String payload;
	private String parity;

	AdsbSentence (String timestamp, String dfca, String icao, String payload, String parity)
	{
		this.timestamp = timestamp;
		this.dfca = dfca;
		this.icao = icao;
		this.parity = parity;
		this.payload = payload;
	}
	/**
	 * @return timestamp first part of the string contains the time it was sent
	 * */
	public String getTimestamp()
	{
		return timestamp;
	}
	/**
	 *	@return dfca DF+CA 1 Byte, bit [0..4] squitter sentence, [4..7] capability of the transmitter
	 * */
	public String getDfca()
	{
		return dfca;
	}
	/**
	 *	@return icao id of aircraft what sent the message Byte 2,3,4
	 * */
	public String getIcao()
	{
		return icao;
	}
	/**
	 *	@return parity [12..14] checking the correct transmission of the sentence
	 * */
	public String getParity()
	{
		return parity;
	}
	/**
	 *	@return payload Byte [5..11] position of the aircraft
	 * */
	public String getPayload()
	{
		return payload;
	}
}
