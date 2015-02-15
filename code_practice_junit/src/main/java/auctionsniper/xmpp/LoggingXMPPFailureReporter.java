package auctionsniper.xmpp;

import java.util.logging.Logger;

public class LoggingXMPPFailureReporter implements XMPPFailureReporter {
	private final Logger logger;
	
	public LoggingXMPPFailureReporter(Logger l) {
		this.logger = l;
	}
	
	// Implement the XMPPFailureReporter interface
	@Override
	public void cannotTranslateMessage(String auctionId, String failedMessage, Exception e) {
		String message = String.format("<%s> Could not translate message \"%s\" because \"%s\"", 
		                                auctionId,
		                                failedMessage,
		                                e.toString()
		                               );
		logger.severe(message);
	}
}