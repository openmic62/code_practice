package auctionsniper.tests.acceptance;

import auctionsniper.Main;
import auctionsniper.xmpp.XMPPAuction;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class FakeAuctionServer {
	static Logger logger = LogManager.getLogger(FakeAuctionServer.class.getName());	
		
	public static final String ITEM_ID_AS_LOGIN = "auction-%s";
	public static final String AUCTION_RESOURCE = "Auction";
	public static final String XMPP_HOSTNAME = "localhost";
	private static final String AUCTION_PASSWORD = "auction";
	
	public static final String BUYER_ID = "sniper@roco-3";

	private final String itemID;
	private final XMPPConnection connection;
	private Chat currentChat;
	
	private final SingleMessageListener messageListener = new SingleMessageListener();
	
	public FakeAuctionServer(String item) {
		this.itemID = item;
		this.connection = new XMPPConnection(
			new ConnectionConfiguration(XMPP_HOSTNAME,5222,AUCTION_RESOURCE));
		SmackConfiguration.setLocalSocks5ProxyEnabled(false);
	}
	
	public void startSellingItem() throws XMPPException{
		connection.connect();
		connection.login(String.format(ITEM_ID_AS_LOGIN, itemID),
		                 AUCTION_PASSWORD, AUCTION_RESOURCE);
		logger.info("in call: startSellingItem(), XMPP connected as -->{}<--", connection.getUser());
		connection.getChatManager().addChatListener(
			new ChatManagerListener() {
				@Override
				public void chatCreated(Chat chat, boolean createdLocally) {
					currentChat = chat;
					chat.addMessageListener(messageListener);
				}
			});
	}
	
	public String getItemID() {
		return this.itemID;
	}
	
	public void hasReceivedJoinRequestFromSniper(String sniperId) 
		throws InterruptedException 
	{
		messageListener.receivesAMessage(is(anything()));
	}

	public void hasReceivedBid(int bid, String sniperId) 
		throws InterruptedException 
	{
		receivesAMessageMatching(sniperId, equalTo(String.format(XMPPAuction.BID_COMMAND_FORMAT, bid)));
	}
	
	// helper method
	private void receivesAMessageMatching(String sniperId, org.hamcrest.Matcher<? super String> messageMatcher)
		throws InterruptedException 
	{
		messageListener.receivesAMessage(messageMatcher);
		assertThat(currentChat.getParticipant(), equalTo(sniperId));
	}
	
	public void reportPrice(int price, int increment, String bidder) 
	  throws XMPPException 
	{
		currentChat.sendMessage(
			String.format(Main.REPORT_PRICE_COMMAND_FORMAT, price, increment, bidder));
	}
	
	// <mlr 140310: begin - add failure detection code>
	public void sendInvalidMessageContaining(String brokenMessage) 
	  throws XMPPException 
	{
		currentChat.sendMessage(brokenMessage);
	}
	// <mlr 140310: end - add failure detection code>
	
	public void announceClosed() throws XMPPException {
		currentChat.sendMessage(Main.CLOSE_COMMAND_FORMAT);
	}
	
	public void stop() {
		connection.disconnect();
	}
	
	/* implement the MessageListener interface */
	public class SingleMessageListener implements MessageListener {
		private final ArrayBlockingQueue<Message> messages =
			        new ArrayBlockingQueue<Message>(1);
		
		@Override
    public void processMessage(Chat currentChat, Message message) {
    	messages.add(message);
    }

    @SuppressWarnings("unchecked")
    public void receivesAMessage(org.hamcrest.Matcher<? super String> messageMatcher) 
    	throws InterruptedException 
    {
    	final Message message = messages.poll(5, TimeUnit.SECONDS);
    	logger.info("in call: receivesAMessage(...)\n\t\t{}\n\t\tmsg from -->{}<--\n\t\tmsg rx'd -->{}<--",
    	            connection.getUser(), currentChat.getParticipant(), message.getBody());
    	assertThat("Message", message, is(notNullValue()));
    	//assertThat("Message", message, is(nullValue()));
    	assertThat(message, hasProperty("body", messageMatcher));
    }
  }






  
  
  
  
  /* <mlr 131113: begin - this is my stuff, not the book's> */
  // <mlr 131213: keep this to support the JUnitParams tests
  public String getSniperCommandFromMessage(String message) {
		// example message-->SQLVersion: 1.1; Command: JOIN;<--
		
		final String ON_SEMICOLON_DELIMITER = ";";
		String[] fields = message.split(ON_SEMICOLON_DELIMITER);

		/* REGEX means: the text "Command: ", start remembering, followed by
		                one of "JOIN" or "BID", stop remembering
		 */
		final String REGEX = "Command: (JOIN|BID)";
		Pattern pattern =
		Pattern.compile(REGEX);

		String returnValue = null;
		for(String field : fields) {
			Matcher matcher =
			pattern.matcher(field);
			
			if (field.contains("Command:")) {
			 	matcher.find();
			 	returnValue = matcher.group(1);
			}
			
		}
		return returnValue;
		///return "Just a plain Jane string, dear.";
	}
  /* <mlr 131113: end - this is my stuff, not the book's> */
}