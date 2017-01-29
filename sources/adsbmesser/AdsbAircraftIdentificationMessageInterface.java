package adsbmesser;

/**
 * Class contains information about an aircraft's ID
 * @author David Mändlen, damait06@hs-esslingen.de
 * */
public interface AdsbAircraftIdentificationMessageInterface
{
	/**
	 * Returns EmitterCategory
	 * @return EmitterCategory
	 * */
	public int getEmitterCategory();

	/**
	 * Returns AircraftID
	 * @return AircraftID
	 * */
	public String getAircraftId();
}
