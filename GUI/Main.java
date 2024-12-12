package GUI;
import SQLtest.DBconnection;



public class Main {

	public static void main(String[] args) {	
		//we define a connection to the database using the SQLtest package provided and we declare an
		//object of the windows class in order to run the application
		DBconnection.establishConnection();
		Windows instance = new Windows();
	    	instance.myWindows();

	}

}




