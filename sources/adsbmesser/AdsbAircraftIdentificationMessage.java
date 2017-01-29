package adsbmesser;

public class AdsbAircraftIdentificationMessage extends AdsbMessage implements AdsbAircraftIdentificationMessageInterface
{
	private int emitterCategory;
	private String aircraftId;
	private String binPl;

	/**
	 * @param int type
	 * @param int df
	 * @param int ca
	 * @param String payload
	 * @param String icao
	 * @param String timestamp
	 * */
	public AdsbAircraftIdentificationMessage(int type, int df, int ca, String payload, String icao, String timestamp)
	{
		super(type, df, ca, payload, icao, timestamp); //call constructor of super-class AdsbMessage

		this.binPl = BinConverter.hex2bin(this.payload); //convert payload to binary String

		/**
		 * parse payload according to documentation, see /doc/senser_decoding.pdf
		 * */

		this.emitterCategory = Integer.parseInt(this.binPl.substring(5,7),2);
		this.aircraftId = AISUtil.aisLetter(this.binPl.substring(8,55));
	}

	public int getEmitterCategory()
	{
		return emitterCategory;
	}

	public String getAircraftId()
	{
		return aircraftId;
	}
}
