package adsbmesser;

/**
 * Class displays the contents of an ADSB-Message
 * @Author David Mändlen, damait06@hs-esslingen.de
 * */
public interface AdsbMessageDisplayInterface
{
	/**
	 * Displays the content of an ADSB-Message
	 * */
	public void display(AdsbMessageInterface msg);
}
