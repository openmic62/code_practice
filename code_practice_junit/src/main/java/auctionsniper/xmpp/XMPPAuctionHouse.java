package auctionsniper.xmpp;

import auctionsniper.Auction;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import static org.apache.commons.io.FilenameUtils.getFullPath;

public class XMPPAuctionHouse implements AuctionHouse
{
	//static Logger logger = LogManager.getLogger(XMPPAuctionHouse.class.getName());
	// <mlr 140312: add failure detection code>
	private final String LOGGER_NAME   = XMPPAuctionHouse.class.getName();

  // <mlr 140312: add failure detection code>
	public static final String LOG_FILE_NAME = "auction-sniper.log"; // <mlr 140315: moved from AuctionLogDriver per BV
	public static XMPPConnection connection;
	// <mlr 140312: add failure detection code>
	private final LoggingXMPPFailureReporter failureReporter;
	
	private XMPPAuctionHouse( XMPPConnection connection ) {
		this.connection = connection;
		// <mlr 140312: add failure detection code>
		this.failureReporter = new LoggingXMPPFailureReporter(makeLogger()); 
	}
	
	// <mlr 140312: begin - add failure detection code>
	private Logger makeLogger() {
		Logger logger = Logger.getLogger(LOGGER_NAME);
		logger.setUseParentHandlers(false);
		logger.addHandler(simpleFileHandler());
		return logger;
	}
	
	private FileHandler simpleFileHandler() {
		try {
			FileHandler handler = new FileHandler(LOG_FILE_NAME);
			handler.setFormatter(new SimpleFormatter());
			return handler;
		} catch (IOException ioe) {
			throw new XMPPAuctionException("Could not create logger FileHandler " +
			                               getFullPath(LOG_FILE_NAME));
	  }
	}
	// <mlr 140312: end - add failure detection code>
	
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
		//return new XMPPAuction(connection, itemId);
	  // <mlr 140312: add failure detection code>
		return new XMPPAuction(connection, itemId, failureReporter);
	}
}