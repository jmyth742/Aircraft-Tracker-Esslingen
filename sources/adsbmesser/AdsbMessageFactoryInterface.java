package adsbmesser;
import adsbsenser.*;

/**
 * Class creates ADSB-Messages from ADSB-Sentences
 *
 * @Author David Mändlen, damait06@hs-esslingen.de
 * */
public interface AdsbMessageFactoryInterface
{
	/**
	 * @param AdsbSentence inputSentence
	 * @return AdsbMessage
	 * */
	public AdsbMessage fromAdsbSentence(AdsbSentence inputSentence);
}
