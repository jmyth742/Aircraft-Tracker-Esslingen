package adsbacamo;


public class Aircraft implements AircraftInterface
{
	//parameters
	private String timestamp;
	private String icao;
	private int speed;
	private int heading;
	private int altitude;
	private int cprFormat;
	private double cprLongitude;
	private double cprLatitude;
	private String aircraftId;

	// constructor
	public Aircraft(String timestamp, String icao, int speed, int heading, int altitude, int cprFormat, double cprLongitude, double cprLatitude, String aircraftId)
	{
		System.out.println("Debugging: Constructor of Aircraft works");
		this.timestamp = timestamp;
		this.icao = icao;
		this.speed = speed;
		this.heading = heading;
		this.altitude = altitude;
		this.cprFormat = cprFormat;
		this.cprLongitude = cprLongitude;
		this.cprLatitude = cprLatitude;
		this.aircraftId = aircraftId;
	}

	// GET-methods
	public String getTimestamp(){
		return this.timestamp;
	}

	public String getIcao(){
		return this.icao;
	}

	public int getSpeed(){
		return this.speed;
	}

	public int getHeading(){
		return this.heading;
	}

	public int getCprFormat(){
		return this.cprFormat;
	}

	public double getCprLongitude(){
		return this.cprLongitude;
	}

	public double getCprLatitude(){
		return this.cprLatitude;
	}

	public String getAircraftId(){
		return this.aircraftId;
	}

	public int getAltitude(){
		return this.altitude;
	}

	// SET METHODS
	public void setTimestamp(String timestamp){
		this.timestamp = timestamp;
	}

	public void setIcao(String icao){
		this.icao = icao;
	}

	public void setSpeed(int speed){
		this.speed = speed;
	}

	public void setHeading(int heading){
		this.heading = heading;
	}

	public void setAltitude(int altitude){
		this.altitude = altitude;
	}

	public void setCprFormat(int cprFormat){
		this.cprFormat = cprFormat;
	}

	public void setCprLongitude(double cprLongitude){
		this.cprLongitude = cprLongitude;
	}

	public void setCprLatitude(double cprLatitude){
		this.cprLatitude = cprLatitude;
	}

	public void setAircraftId(String aircraftId){
		this.aircraftId = aircraftId;
	}	
}
