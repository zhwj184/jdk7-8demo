package demo.jdk8;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;

public class NewDateTimeAPI {

	public static void main(String[] args) {
		Clock clock = Clock.systemUTC(); // return the current time based on
											// your system clock and set to UTC.

		Clock clock1 = Clock.systemDefaultZone(); // return time based on system
													// clock zone

		long time = clock.millis(); // time in milliseconds from January 1st,
									// 1970
		
		ZoneId zone = ZoneId.systemDefault(); //get the ZoneId of the system 
		Clock clock2 = Clock.system(zone); //set the Clock to given zone 

		ZoneId zone2 = ZoneId.of("Europe/Berlin"); //get the ZoneId from timezone name 
		
		//To deal with "human" date and time, you'll use LocalDate, LocalTime and LocalDateTime.
		LocalDate date = LocalDate.now(); 

	}
}
