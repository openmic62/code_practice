package auctionsniper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class XMPPAuction implements Auction
{
	static Logger logger = LogManager.getLogger(XMPPAuction.class.getName());	
	
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_ID_FORMAT = ITEM_ID_AS_LOGIN + "@%S/" + AUCTION_RESOURCE;

	private Chat chat;
	//private final String itemId;
  //private final Announcer<AuctionEventListener> auctionEventsXX = 
  private final Announcer<AuctionEventListener> auctionEvents = 
          Announcer.to(AuctionEventListener.class);

  @Override
  public Chat getChat() { return chat; }

	//XMPPAuction(Chat c) {
	///XMPPAuction(XMPPConnection connection, Chat c) {
	////XMPPAuction(XMPPConnection connection, String itemId, Chat c) {
	XMPPAuction(XMPPConnection connection, String itemId) {
		//this.itemId = itemId;
		
    this.chat = 
    	connection.getChatManager().createChat(
      auctionId(itemId, connection),
      //null
    	new AuctionMessageTranslator(
    	      connection.getUser(),
    	      //auctionEventsXX.announce()
    	      auctionEvents.announce()
    	                            )
      );
    
    /*
    chat.addMessageListener(
    	new AuctionMessageTranslator(
    	      connection.getUser(),
    	      auctionEventsXX.announce()
    	                            )
                           );
    */
		
	}

	private String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
		                     connection.getServiceName());
	}
	
	@Override
	public void addAuctionEventListener(AuctionEventListener ael) {
		//auctionEventsXX.addListener(ael);
		auctionEvents.addListener(ael);
	}
	
	@Override
	public void bid(int amount){
		try {
			chat.sendMessage(String.format(Main.BID_COMMAND_FORMAT, amount));
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void join() {
		try {
			logger.debug("chat.getParticipant() -->" + chat.getParticipant() + "<--");
			chat.sendMessage(Main.JOIN_COMMAND_FORMAT);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
}