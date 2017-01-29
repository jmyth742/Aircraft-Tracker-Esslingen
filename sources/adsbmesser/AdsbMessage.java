package adsbmesser;

/**
 * {@inheritdoc}
 * */
public class AdsbMessage implements AdsbMessageInterface
{
	protected int type;
	protected int df;
	protected int ca;
	protected String payload;
	protected String icao;
	protected String timestamp;

	/**
	 * Constructor of ADSB-Message
	 * @param type Message-Type, bits [0...4] of the payload
	 * @param df DownlinkFormat, bits [0...4] of DFCA
	 * @param ca CApability, bits [5...7] of DFCA
	 * @param payload full payload
	 * @param icao full ICAO
	 * @param timestamp timestamp of ADSB-Sentence
	 * */
	public AdsbMessage(int type, int df, int ca, String payload, String icao, String timestamp)
	{
		this.type = type;
		this.df = df;
		this.ca = ca;
		this.payload = payload;
		this.icao = icao;
		this.timestamp = timestamp;
	}

	/**
	 * {@inheritdoc}
	 * */
	public String getTimestamp()
	{
		return timestamp;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getType()
	{
		return type;
	}

	/**
	 * {@inheritdoc}
	 * */
	public String getIcao()
	{
		return icao;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getDownlinkFormat()
	{
		return df;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getCapability()
	{
		return ca;
	}
}
