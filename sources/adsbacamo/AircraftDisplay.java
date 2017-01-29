package adsbacamo;

import adsbmesser.*;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class AircraftDisplay extends JFrame implements Observer, Runnable
{	
	private JTextArea myTextArea;
	private JTextArea myTextArea2;
	private JTextArea myTextArea3;
	private JTextArea myTextArea4;
	private JTextArea myTextArea5;

	private Aircraft[] Aircraftlist;

	//constructor, needs string for windows name
	public AircraftDisplay(String name)
	{
		// define frame.
		super (name);
		// define on operation CLOSE
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		// set frame layout.
		this.setLayout (new FlowLayout());
		// set frame size.
		this.setSize (1024, 768);
		
		// make tabbedPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
		// set tabbedPaneDimension
		tabbedPane.setPreferredSize (new Dimension (1000, 720));
		
		//make non-editable textAreas for each tab
		myTextArea = new JTextArea();
		myTextArea.setEditable(false);
		myTextArea2 = new JTextArea();
		myTextArea2.setEditable(false);
		myTextArea3 = new JTextArea();
		myTextArea3.setEditable(false);
		myTextArea4 = new JTextArea();
		myTextArea4.setEditable(false);
		myTextArea5 = new JTextArea();
		myTextArea5.setEditable(false);


		//make scrollPanes connected with textAreas for each tab
		JScrollPane myScrollPaneAA = new JScrollPane (myTextArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		JScrollPane myScrollPaneVM = new JScrollPane (myTextArea2, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		JScrollPane myScrollPanePM = new JScrollPane (myTextArea3, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		JScrollPane myScrollPaneIM = new JScrollPane (myTextArea4, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		JScrollPane myScrollPaneOM = new JScrollPane (myTextArea5, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);



		//add scrollpanes to tabbedPane
		tabbedPane.addTab ("Active Aircrafts", myScrollPaneAA);
		tabbedPane.addTab ("Velocity Messages", myScrollPaneVM);
		tabbedPane.addTab ("Position Messages", myScrollPanePM);
		tabbedPane.addTab ("Identification Messages", myScrollPaneIM);
		tabbedPane.addTab ("Other Messages", myScrollPaneOM);
		
		//add tabbedPane to frame
		this.add(tabbedPane);
		//set frame visible
		this.setVisible (true);

		//create Aircraftlist
		this.Aircraftlist = null;

	}

	//function for clear specific textarea on the tabs,
	//parameter int for addressing the textfields
	private void clearTextArea(int a)
	{
		switch (a)
		{
			case 1:
				myTextArea.setText("");
				break;
				
			case 2:
				myTextArea2.setText("");
				break;
			
			case 3:
				myTextArea3.setText("");
				break;
				
			case 4:
				myTextArea4.setText("");
				break;
				
			case 5:
				myTextArea5.setText("");
				break;

			default:
				break;
		}
	}
	
	@Override
	public void update(Observable o, Object arg)
	{
		if(arg instanceof AdsbMessage)
		{
			if (arg instanceof AdsbAirborneVelocityMessage)
			{
				AdsbAirborneVelocityMessage tempmsg = (AdsbAirborneVelocityMessage)arg;
				myTextArea2.append("ICAO: " + tempmsg.getIcao() +"\t\t");
				myTextArea2.append("Horizontal Speed: " + tempmsg.getSpeed() +"\t\t");
				myTextArea2.append("Vertical Speed: " + tempmsg.getVerticalSpeed() +"\t\t");
				myTextArea2.append("Heading: " + tempmsg.getHeading() +"\t\t\n");
				myTextArea2.append("\n--------------------------------------------------------\n");
			}
			else if (arg instanceof AdsbAirbornePositionMessage)
			{
				AdsbAirbornePositionMessage tempmsg = (AdsbAirbornePositionMessage)arg;
				myTextArea3.append("ICAO: " + tempmsg.getIcao() +"\t\t");
				myTextArea3.append("CPR Format: " + tempmsg.getCprFormat() +"\t\t");
				myTextArea3.append("CPR Latitude: " + tempmsg.getCprLatitude() +"\t\t");
				myTextArea3.append("CPR Longitude: " + tempmsg.getCprLongitude() +"\t\t");
				myTextArea3.append("Altitude: " + tempmsg.getAltitude() +"\t\t\n");
				myTextArea3.append("\n--------------------------------------------------------\n");
				
			}
			else if (arg instanceof AdsbAircraftIdentificationMessage)
			{
				AdsbAircraftIdentificationMessage tempmsg = (AdsbAircraftIdentificationMessage)arg;
				myTextArea4.append("ICAO: " + tempmsg.getIcao() +"\t\t");
				myTextArea4.append("AircraftId: " + tempmsg.getAircraftId() +"\t\t\n");
				myTextArea4.append("\n--------------------------------------------------------\n");
			}
			else
			{
				AdsbMessage tempmsg = (AdsbMessage)arg;
				myTextArea5.append("ICAO: " + tempmsg.getIcao() +"\t\t");
				myTextArea5.append("Timestamp: " + tempmsg.getTimestamp() + "\n");
				myTextArea5.append("\n--------------------------------------------------------\n");
			}
		}
	 	else if(arg instanceof Aircraft[])
		{
			this.Aircraftlist = (Aircraft[]) arg;
		}

		else
		{
			this.Aircraftlist = null;
			System.out.println("Arg for Display was invalid");
		}
	}

	@Override
	public void run()
	{
		while(true){
			try{
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {System.out.println(e.toString());}
	
			// checks transmitted object and updates the desired textarea on the tab
			
			System.out.println("Debugging: AircraftDisplay aktualisiert Fenster");
	
			this.clearTextArea(1);
			if(this.Aircraftlist != null){
				for(Aircraft ac: this.Aircraftlist)
				{
					myTextArea.append("ICAO:\t" + ac.getIcao());
					myTextArea.append("\nTimestamp:\t" + ac.getTimestamp());
					myTextArea.append("\nAircraftId:\t" + ac.getAircraftId());
					myTextArea.append("\nLatitude, Longitude, Altitude:\t" + ac.getCprLatitude() + ", " + ac.getCprLongitude() + ", " + ac.getAltitude());
					myTextArea.append("\nHorizontal Speed: " + ac.getSpeed());
					myTextArea.append("\nHeading: " + ac.getHeading());
					myTextArea.append("\n--------------------------------------------------------\n");
				}
			}
		}
	}
}
