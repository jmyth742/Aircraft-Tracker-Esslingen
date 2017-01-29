package adsbmesser;

/**
 * {@inheritdoc}
 * */
public class AdsbAirbornePositionMessage extends AdsbMessage implements AdsbAirbornePositionMessageInterface
{
	private int surveillanceStatus;
	private int nicSupplement;
	private int altitude;
	private int timeFlag;
	private int cprFormat;
	private int cprLongitude;
	private int cprLatitude;
	private String binPl;
	/**
	 * Save aircraft's information
	 * <p>
	 * Constructor takes the parameters and decodes them further.
	 * Type, DFCA, ICAO and Timestamp are used by the superclasses' constructor,
	 * payload is evaluated to create and save data.
	 * </p>
	 *
	 * @param type Messagetype, used to distinguish the Messages
	 * @param df Downlink Format, contains information about data
	 * @param ca CApability
	 * @param payload contains data
	 * @param icao International Civilian Aircraft Organization Airport Code
	 * @param timestamp Time of receipt
	 * */
	public AdsbAirbornePositionMessage(int type, int df, int ca, String payload, String icao, String timestamp)
	{
		super(type, df, ca, payload, icao, timestamp); //Call constructor of parent-class AdsbMessage

		this.binPl = BinConverter.hex2bin(this.payload); //Convert payload to binary String
		/*
		 * decode payload according to doc, see /doc/senser_decoding.pdf
		 */
		this.surveillanceStatus = Integer.parseInt(this.binPl.substring(5,7),2);
		this.nicSupplement = Integer.parseInt(this.binPl.substring(7,8),2);
		this.altitude = Integer.parseInt(this.binPl.substring(8,20),2);
		this.timeFlag = Integer.parseInt(this.binPl.substring(20,21),2);
		this.cprFormat = Integer.parseInt(this.binPl.substring(21,22),2);
		this.cprLatitude = Integer.parseInt(this.binPl.substring(22,39),2);
		this.cprLongitude = Integer.parseInt(this.binPl.substring(39,56),2);
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getSurveillanceStatus()
	{
		return surveillanceStatus;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getNicSupplement()
	{
		return nicSupplement;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getAltitude()
	{
		return altitude;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getTimeFlag()
	{
		return timeFlag;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getCprFormat()
	{
		return cprFormat;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getCprLongitude()
	{
		return cprLongitude;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getCprLatitude()
	{
		return cprLatitude;
	}
}
