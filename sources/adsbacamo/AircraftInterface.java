package adsbacamo;

public interface AircraftInterface

{	//getFunctions
	public String getTimestamp();
	public String getIcao();
	public int getSpeed();
	public int getHeading();
	public int getAltitude();
	public int getCprFormat();
	public double getCprLongitude();
	public double getCprLatitude();
	public String getAircraftId();

	//setFunctions
	public void setTimestamp(String timestamp);
	public void setIcao(String icao);
	public void setSpeed(int speed);
	public void setHeading(int heading);
	public void setAltitude(int altitude);
	public void setCprFormat(int cprFormat);
	public void setCprLongitude(double cprLongitude);
	public void setCprLatitude(double cprLatitude); 
	public void setAircraftId(String aircraftId);
}
