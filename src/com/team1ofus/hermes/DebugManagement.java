package com.team1ofus.hermes;

import java.util.Date;

//Used for testing purposes
public class DebugManagement {
	
	public static void writeLineToLog(SEVERITY_LEVEL severity, String message) {
		Date d = new Date();
		System.out.println("[ " + d + "] " + severity.toString() + ": " + message);
		DebugConsole.addLine("[ " + d + "] " + severity.toString() + ": " + message);
				
	}
	public static void writeNotificationToLog(String message) {
		Date d = new Date();	
		
		if(BootstrapperConstants.WRITE_NOTIFICATIONS) {
			
			
			DebugConsole.addLine("[ " + d + "] " + "NOTIFICATION" + ": " + message);
		
		}
		System.out.println("[ " + d + "] " + "NOTIFICATION" + ": " + message);
	}
}
