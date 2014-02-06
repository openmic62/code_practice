package auctionsniper.xmpp;

import auctionsniper.Auction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPPAuctionHouse implements AuctionHouse
{
	static Logger logger = LogManager.getLogger(XMPPAuctionHouse.class.getName());	

	public static XMPPConnection connection;
	
	private XMPPAuctionHouse( XMPPConnection connection ) {
		this.connection = connection;
	}
	
	public static XMPPAuctionHouse 
	connect(String hostname, String username, String password)
		throws XMPPException 
	{
		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, XMPPAuction.AUCTION_RESOURCE);

		Connection.DEBUG_ENABLED = false;		
		XMPPAuctionHouse auctionHouse = new XMPPAuctionHouse(connection);
		
		return auctionHouse;
	}

	// ----- OVERRIDES FOR INTERFACE AucionHouse -----
	@Override
	public Auction auctionFor(String itemId) {
		return new XMPPAuction(connection, itemId);
	}
}