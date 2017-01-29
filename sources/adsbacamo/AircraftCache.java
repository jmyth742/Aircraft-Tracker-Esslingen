/**
 * Aircraftcache is to store aircrafts
 * Aircrafts will be deleted after time of inactivity
 * If you put an aircraft to the cache you can pass the time in ms
 *
 * @author Jonny Smyth
 * @version 0.10
 */
package adsbacamo;

import java.util.HashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Observable;

public class AircraftCache extends Observable
{

	private HashMap<String,Object> acCache;
	private HashMap<String,Timer> timerCache;

	/** 
	 * constructor für Aircraftcache
	 */
	public AircraftCache()
	{
		System.out.println("Debugging: Contructor of Aircraftcache");
		acCache = new HashMap<String,Object>();
		timerCache = new HashMap<String,Timer>();

	}

	/** 
	* adds key und value to the cache, 
	* every file in cache is deleted after timeout given in ms
	* timeout 0 is infinit
	*
	* @param String key to referenz, Object value any kind of object, long timeout in ms
	* @return no
	*/
	public void put (String key, Object value, long timeout)
	{
		System.out.println("Debugging: put-method of AircraftCache works");
		//Prüfen ob schon ein Timer angelegt wurde, wenn ja löschen
		if(acCache.get(key) != null){
			System.out.println("Debugging: already ac in existence in Cache");
			timerCache.get(key).cancel();
		}

		acCache.put(key,value);

		if (timeout > 0){
			System.out.println("Debugging: setting new timer in Cache");
			//Timer Object speichern um es später löschen zu können
			Timer myTimer = new Timer(key);
			myTimer.schedule( new DeleteOldAircraft(key),timeout);
			timerCache.put(key,myTimer);
		}
	}

	/** 
	 * Returns referenced Object from cache
	 *
	 * @param String key of referenz
	 * @return Object of the stored referenz
	 */
	public Object get (String key)
	{
		System.out.println("Debugging: getting ac from Cache" + key);
		return acCache.get(key);
	}

	/** 
	 * Returns a list of all stored keys
	 *
	 * @param no
	 * @return String [] of keys
	 */
	public String[] getAllKeys()
	{
		System.out.println("Debugging: getAllKeys-method of AircraftCache works");
		Set<String> allKeys = acCache.keySet();
		return allKeys.toArray(new String[0]);
	}

	/** 
	 * removes object from cache
	 *
	 * @param String key to remove
	 * @return no
	 */
	public void remove (String key)
	{
		System.out.println("removed: " + key);
		acCache.remove(key);
		timerCache.get(key).cancel();
	}

	/** 
	 * DeleteOldAircraft is an internal class used to set up timers to remove 
	 * timedout aircrafts
	 */
	class DeleteOldAircraft extends TimerTask
	{
		private String key;

		public DeleteOldAircraft ( String key )
		{
			this.key = key;
		}

		@Override
		public void run ()
		{
			System.out.println("Debugging: DeleteOldAircraft-class of AircraftCache works");
			if( this.key != null && this.key.length () > 0 ) {
				System.out.println("timedout: " + acCache.get(key));
				remove (this.key);
				setChanged();
				notifyObservers();
			}
		}
	}
} 

