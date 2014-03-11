package auctionsniper.xmpp;

import auctionsniper.Announcer;
import auctionsniper.Auction;
import auctionsniper.AuctionEventListener;
import auctionsniper.Main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPPAuction implements Auction
{
	static Logger logger = LogManager.getLogger(XMPPAuction.class.getName());	
	
	public static final String AUCTION_RESOURCE    = "Auction";
	private       final String ITEM_ID_AS_LOGIN    = "auction-%s";
	private       final String AUCTION_ID_FORMAT   = ITEM_ID_AS_LOGIN + "@%S/" + AUCTION_RESOURCE;

	private       final String JOIN_COMMAND_FORMAT = "SQLVersion: 1.1; Command: JOIN;";
	public static final String BID_COMMAND_FORMAT  = "SQLVersion: 1.1; Command: BID; Price: %d;";

	private Chat chat;
  private final Announcer<AuctionEventListener> auctionEvents = 
          Announcer.to(AuctionEventListener.class);

	public XMPPAuction(XMPPConnection connection, String itemId) {
		
	  // <mlr 140310: begin - add failure detection code>
	  AuctionMessageTranslator translator = translatorFor(connection);
	  
    this.chat = 
    	connection.getChatManager().createChat(
      auctionId(itemId, connection),
      translator
      /*
    	new AuctionMessageTranslator(
    	      connection.getUser(),
    	      auctionEvents.announce()
    	                             )
    	*/
      );
    addAuctionEventListener(chatDisconnectorFor(translator));
	  // <mlr 140310: end - add failure detection code>
	}

	private String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
		                     connection.getServiceName());
	}
	
	// <mlr 140310: begin - add failure detection code>
	private AuctionMessageTranslator translatorFor(XMPPConnection connection) {
		return new AuctionMessageTranslator(connection.getUser(), auctionEvents.announce());
	}
	
	private AuctionEventListener
	chatDisconnectorFor(final AuctionMessageTranslator translator) {
		return new AuctionEventListener() {
			public void auctionFailed() {
				chat.removeMessageListener(translator);
			}
			public void auctionClosed(){}
			public void currentPrice(int p, int i, PriceSource ps){}
		};
	}
	// <mlr 140310: end - add failure detection code>
	// ----- OVERRIDES FOR INTERFACE Auction -----
	@Override
	public void addAuctionEventListener(AuctionEventListener ael) {
		auctionEvents.addListener(ael);
	}
	
	@Override
	public void bid(int amount){
		try {
			chat.sendMessage(String.format(BID_COMMAND_FORMAT, amount));
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void join() {
		try {
			logger.info("in call: join(), sniper join msg to -->{}<--", chat.getParticipant());
			chat.sendMessage(JOIN_COMMAND_FORMAT);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
}