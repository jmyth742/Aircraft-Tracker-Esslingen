package adsbmesser;

/**
 * Class contains information about an aircraft's movement in the sky
 * @Author David Mändlen, damait06@hs-esslingen.de
 * */
public interface AdsbAirborneVelocityMessageInterface
{
	/**
	 * Returns Subtype
	 * @return int Subtype
	 * */
	public int getSubtype();

	/**
	 * Returns IntentChange
	 * @return int IntentChange
	 * */
	public int getIntentChange();

	/**
	 * Returns ReservedA
	 * @return int ReservedA
	 * */
	public int getReservedA();

	/**
	 * Returns NavigationAccuracy
	 * @return int NavigationAccuracy
	 * */
	public int getNavigationAccuracy();

	/**
	 * Returns Speed
	 * @return int Speed
	 * */
	public int getSpeed();

	/**
	 * Returns Heading
	 * @return int Heading
	 * */
	public int getHeading();

	/**
	 * Returns VerticalRatesource
	 * @return int VerticalRateSource
	 * */
	public int getVerticalRateSource();

	/**
	 * Returns VerticalSpeed
	 * @return int VerticalSpeed
	 * */
	public int getVerticalSpeed();
}
