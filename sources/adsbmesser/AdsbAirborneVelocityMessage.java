package adsbmesser;

import java.lang.*;

/**
 * {@inheritdoc}
 * */
public class AdsbAirborneVelocityMessage extends AdsbMessage implements AdsbAirborneVelocityMessageInterface
{
	private int subType;
	private int intentChange;
	private int reservedA;
	private int navigationAccuracy;
	private int speed;
	private int heading;
	private int verticalRateSource;
	private int verticalRateSign;
	private int verticalSpeed;
	private String binPl;

	/**
	 * @param int type
	 * @param int df
	 * @param int ca
	 * @param String payload
	 * @param String icao
	 * @param String timestamp
	 * */
	public AdsbAirborneVelocityMessage(int type, int df, int ca, String payload, String icao, String timestamp)
	{
		super(type, df, ca, payload, icao, timestamp); // call constructor of super-class AdsbMessage

		this.binPl = BinConverter.hex2bin(this.payload); // convert payload to binary

		/**
		 * parse payload according to documentation, see /doc/senser_decoding
		 * */
		this.subType = Integer.parseInt(this.binPl.substring(5,8),2);
		this.intentChange = Integer.parseInt(this.binPl.substring(8,9),2);
		this.reservedA = Integer.parseInt(this.binPl.substring(9,10),2);
		this.navigationAccuracy = Integer.parseInt(this.binPl.substring(10,13),2);
		this.verticalRateSource = Integer.parseInt(this.binPl.substring(35,36),2);
		this.verticalRateSign = Integer.parseInt(this.binPl.substring(36,37),2);
		this.verticalSpeed = Integer.parseInt(this.binPl.substring(37,46),2);

		if(this.verticalRateSign == 1){
			this.verticalSpeed = (0 - this.verticalSpeed);
		}

		this.calcSpeedHeading();
	}

	private void calcSpeedHeading()
	{
		try{
			if(this.subType == 1 || this.subType == 2){
				int eastWest = Integer.parseInt(this.binPl.substring(14,24),2);
				int northSouth = Integer.parseInt(this.binPl.substring(25,35),2);

				try{
					if(eastWest == 0 || northSouth == 0){
						throw new Exception("Invalid velocity");
					}
					else{
						eastWest--;
						northSouth--;
						this.speed = (int)Math.sqrt((eastWest*eastWest)+(northSouth*northSouth));
					}
				}
				catch(Exception e){}

				double beta = (Math.atan((double)eastWest / (double)northSouth));
				beta = ((beta * 90) / (Math.PI/2));

				StringBuffer dirSB = new StringBuffer();
				dirSB.append(this.binPl.substring(13,14));
				dirSB.append(this.binPl.substring(24,25));

				int dir = Integer.parseInt(dirSB.toString(),2);

				if(dir == 0){
					this.heading = (0 + (int)beta);
				}
				else if(dir == 1){
					this.heading = (90 + (int)beta);
				}
				else if(dir == 2){
					this.heading = (180 + (int)beta);
				}
				else if(dir == 4){
					this.heading = (270 + (int)beta);
				}
			}
			else if(this.subType == 3 || this.subType == 4){
				this.heading = Integer.parseInt(this.binPl.substring(14,24),2);
				this.speed = Integer.parseInt(this.binPl.substring(25,35),2);
			}
			else{
				throw new Exception("Invalid Subtype");
			}
		}
		catch(Exception e){}
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getSubtype()
	{
		return this.subType;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getIntentChange()
	{
		return this.intentChange;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getReservedA()
	{
		return this.reservedA;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getNavigationAccuracy()
	{
		return this.navigationAccuracy;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getSpeed()
	{
		return this.speed;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getHeading()
	{
		return this.heading;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getVerticalRateSource()
	{
		return this.verticalRateSource;
	}

	/**
	 * {@inheritdoc}
	 * */
	public int getVerticalSpeed()
	{
		return this.verticalSpeed;
	}
}
