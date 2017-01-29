package adsbmesser;
/**
 * Class contains information about an aircraft's position in the sky
 * @author David Mändlen <damait06@hs-esslingen.de>
 * @version 1.0
 * */
public interface AdsbAirbornePositionMessageInterface
{
	/**
	 * Returns SurveillanceStatus
	 * @return int SurveillanceStatus
	 * */
	public int getSurveillanceStatus();

	/**
	 * Returns NicSupplement
	 * @return int NicSupplement
	 * */
	public int getNicSupplement();

	/**
	 * Returns Altitude
	 * @return int Altitude
	 * */
	public int getAltitude();

	/**
	 * Returns TimeFlag
	 * @return int TimeFlag
	 * */
	public int getTimeFlag();

	/**
	 * Returns CprFormat
	 * @return int CprFormat
	 * */
	public int getCprFormat();

	/**
	 * Returns CprLongitude
	 * @return int CprLongitude
	 * */
	public int getCprLongitude();

	/**
	 * Returns CprLatitude
	 * @return int CprLatitude
	 * */
	public int getCprLatitude();
}
