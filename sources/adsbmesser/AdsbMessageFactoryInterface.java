package adsbmesser;
import adsbsenser.*;

/**
 * Class creates ADSB-Messages from ADSB-Sentences
 *
 * @Author Jonathan Smyth
 * */
public interface AdsbMessageFactoryInterface
{
	/**
	 * @param AdsbSentence inputSentence
	 * @return AdsbMessage
	 * */
	public AdsbMessage fromAdsbSentence(AdsbSentence inputSentence);
}
