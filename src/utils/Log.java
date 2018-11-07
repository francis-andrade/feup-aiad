package utils;

public final class Log {
	
	public static void log(String message) {
		System.out.println(Double.toString(Utils.currentTime())+": "+message);
	}
	
	public static void handleMessage(String agent, Object msg, boolean received) {
		System.out.println("");
		if(received == true) {
			System.out.println(Double.toString(Utils.currentTime())+": "+agent + " received a message: ");
		}
		else {
			System.out.println(Double.toString(Utils.currentTime())+": "+agent + " sent a message: ");
			
		}
		
		System.out.println(msg.toString());
	} 
}
