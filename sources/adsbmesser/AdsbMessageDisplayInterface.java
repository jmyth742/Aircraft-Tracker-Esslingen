package adsbmesser;

/**
 * Class displays the contents of an ADSB-Message
 * @Author Jonathan Smyth
 * */
public interface AdsbMessageDisplayInterface
{
	/**
	 * Displays the content of an ADSB-Message
	 * */
	public void display(AdsbMessageInterface msg);
}
