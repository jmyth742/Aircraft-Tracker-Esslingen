package adsbmesser;

/**
 * @Author David Mändlen, damait06@hs-esslingen.de
 * */
public class AdsbMesserMain
{
	/**
	 * @param String[] args, enter flugmon-address here
	 * */
	public static void main(String[] args)
	{
		AdsbMessageServer myServer = new AdsbMessageServer(args[0]);
		AdsbMessageDisplay myDisplay = new AdsbMessageDisplay();
		myServer.addObserver(myDisplay);
		
		Thread myThread = new Thread(myServer);
		myThread.start();
	}
}
