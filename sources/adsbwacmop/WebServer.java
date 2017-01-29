package adsbwacmop;
// use this template in order to write a web server with the following functions
// http://localhost:8000/time -> returns the current date/time of the server
// http://localhost:8000/ip   -> returns the ip address of the server
// http://localhost:8000/host -> returns the hostname of the server

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.util.*;
import java.net.*;
import java.io.*;
import java.nio.file.*;

import redis.clients.jedis.*;

public class WebServer
{
	protected HttpServer server;
	private static Jedis rediscli;

	public WebServer ( int port ) throws IOException
	{
		// create a new web server instance listening on http://localhost:port
		server = HttpServer.create ( new InetSocketAddress ("localhost", port), 0 );
		// add servlets (business logic) to the http server (every URI gets its own handler)
		
		server.createContext ( "/active.kml", new KmlHandler () );// uri: http://localhost:port/test
		server.createContext ( "/map", new MapHandler () );
		
		//make redis connection
		rediscli = new Jedis("localhost");
		
		
		// start accepting connections
		server.start ();
	} 
		
	static class KmlHandler implements HttpHandler
	{
		public void handle ( HttpExchange t ) throws IOException
		{
			// do whatever necessary in order to process the request (parsing etc.)
			System.out.println ( "Request received: " + t.toString() );
			// create a response on the request
			String response = createResponse ();
			// send the response header (fixed length)
			t.sendResponseHeaders ( 200, response.length() );
			// create a stream for the response body (variable length)
			OutputStream os = t.getResponseBody ();	   
			// send response
			os.write ( response.getBytes () );
			// close connection
			os.close ();
		}
		private String createResponse ()
		{
			InetSocketAddress addr = null;
			// do whatever necessary in order to create an answer
			//return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><kml xmlns=\"http://www.opengis.net/kml/2.2\"><Document><Style id=\"40612C\"><IconStyle><scale>0.7</scale><heading>137</heading><Icon><href>http://localhost:3333/icons/plane09.png</href></Icon></IconStyle></Style><Placemark><name>40612C</name><description>EZY19PE Lon: 9.645923815275493 Lat: 49.78056335449219 Alt: 11292m Dir: 137deg Vel: 483kn Clm: 0ft/min</description><styleUrl>#40612C</styleUrl><Point><coordinates>9.64592382, 49.78056335, 11292</coordinates><altitudeMode>relativeToGround</altitudeMode><extrude>1</extrude></Point></Placemark></Document></kml>";
		
			String tmp = rediscli.get("KML");
			
			return tmp;
		}
	} // Test Handler Host
	
	class MapHandler implements HttpHandler 
	{
		public void handle ( HttpExchange t ) throws IOException 
		{
			// do whatever necessary in order to process the request (parsing etc.)
			//System.out.println ( "Request received: " + t.toString() );
			// create a response on the request
			String response = new String(Files.readAllBytes(Paths.get("basicMap.html")));
			// send the response header (fixed length)
			t.sendResponseHeaders ( 200, response.length() );
			// create a stream for the response body (variable length)
			OutputStream os = t.getResponseBody ();	   
			// send response
			os.write ( response.getBytes () );
			// close connection
			os.close ();
		}
    }

	// public static void main(String... args) throws Exception
	// {
		// final int p = 3333;
		// WebServer s = new WebServer ( p );
		// System.out.println ( "Accepting connections on port " + p + " ..." );
	// }
}