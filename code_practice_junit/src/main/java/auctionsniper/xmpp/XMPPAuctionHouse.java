package auctionsniper.xmpp;

import auctionsniper.Auction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPPAuctionHouse implements AuctionHouse
{
	static Logger logger = LogManager.getLogger(XMPPAuctionHouse.class.getName());	

	//public static final String AUCTION_RESOURCE = "Auction"; // <mlr 140201: duplicated in Main, XMPPAuction>
	public static XMPPConnection connection;
	
	private XMPPAuctionHouse( XMPPConnection connection ) {
		this.connection = connection;
	}
	
	//public static XMPPConnection 
	public static XMPPAuctionHouse 
	connect(String hostname, String username, String password)
	//connection(String hostname, String username, String password)
		throws XMPPException 
	{
		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, XMPPAuction.AUCTION_RESOURCE);
		
		XMPPAuctionHouse auctionHouse = new XMPPAuctionHouse(connection);
		
		return auctionHouse;
		//return connection;
	}

	// ----- OVERRIDES FOR INTERFACE AucionHouse -----
	@Override
	public Auction auctionFor(String itemId) {
		//return null;
		return new XMPPAuction(connection, itemId);
	}
}