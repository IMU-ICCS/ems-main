package eu.melodic.upperware.dlms.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

public class Test {

	public static void main(String[] args) throws IOException {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			System.out.println("IP Address: " + inetAddress.getHostAddress());
			System.out.println("Host Name: " + inetAddress.getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		URL whatismyip = new URL("http://checkip.amazonaws.com");
		BufferedReader in = new BufferedReader(new InputStreamReader(
		                whatismyip.openStream()));

		String ip = in.readLine(); //you get the IP as a String
		System.out.println(ip);
		
		
	}
	
//	 public static void main(String args[]) throws SocketException {
//	        Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
//	        for (NetworkInterface netint : Collections.list(nets))
//	            displayInterfaceInformation(netint);
//	    }
//
//	    static void displayInterfaceInformation(NetworkInterface netint) throws SocketException {
//	        System.out.printf("Display name: %s\n", netint.getDisplayName());
//	        System.out.printf("Name: %s\n", netint.getName());
//	        Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
//	        for (InetAddress inetAddress : Collections.list(inetAddresses)) {
//	        	System.out.printf("InetAddress: %s\n", inetAddress);
//	        }
//	        System.out.printf("\n");
//	     }
	 
}
