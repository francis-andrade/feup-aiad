package utils;

public final class Log {
	
	public static void handleMessage(String agent, Object msg, boolean received) {
		if(received == true) {
			System.out.println(Double.toString(Utils.currentTime())+": "+agent + "received a messsage: ");
			System.out.println(msg);
		}
		else {
			System.out.println(Double.toString(Utils.currentTime())+": "+agent + "sent a messsage: ");
			System.out.println(msg);
		}
	} 
}
