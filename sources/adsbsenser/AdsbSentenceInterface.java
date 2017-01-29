package adsbsenser;
/**
  * Class parses a JSON-formatted String under the premise that it contains a Message from an aircraft.
  @author Herr Yilmaz
  Date: 21.11.2014
*/
public interface AdsbSentenceInterface
{
	public String getTimestamp();
	public String getDfca();
	public String getIcao();
	public String getParity();
}
