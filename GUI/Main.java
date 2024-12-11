package GUI;
import SQLtest.DBconnection;



public class Main {

	public static void main(String[] args) {
		
		DBconnection.establishConnection();
		Windows instance = new Windows();
	    	instance.myWindows();

	}

}
