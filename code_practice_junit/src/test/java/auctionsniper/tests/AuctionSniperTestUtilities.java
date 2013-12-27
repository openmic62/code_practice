package auctionsniper.tests;

import java.net.UnknownHostException;

public class AuctionSniperTestUtilities {
	// <mlr 131225: ITEM_ID - added per GOOS, p. 155a>
	public static final String ITEM_ID = "item-54321";
	
	// <mlr 131205: begin - I added this to get around testing on Openfire running on home ("roco-3") and work ("vi-1057") laptops>
	public static String myGetHostName() {
		String hostName = null;
		try {
			hostName = java.net.InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException uhe) {
			 uhe.printStackTrace();
		}
		return hostName;
	}
	// <mlr 131205: begin - I added this to get around testing on Openfire running on home ("roco-3") and work ("vi-1057") laptops>
}