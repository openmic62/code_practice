package auctionsniper;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {
	static Logger logger = LogManager.getLogger(AuctionMessageTranslator.class.getName());	
	
	private AuctionEventListener auctionEventListener;
	
	public AuctionMessageTranslator(AuctionEventListener ael) {
		this.auctionEventListener = ael;
	}
	
	@Override
	public void processMessage(Chat chat, Message message) {
		logger.debug("message received -->" + message.getBody() + "<--");
		
		AuctionEvent auctionEvent = AuctionEvent.find(message.getBody());
		
		String eventType = auctionEvent.getEventType();
		if ("CLOSE".equals(eventType)) {
			auctionEventListener.auctionClosed();
		} else if ("PRICE".equals(eventType)) {
		 	auctionEventListener.currentPrice(
		 		auctionEvent.getCurrentPrice(),
		 		auctionEvent.getIncrement()
		 	);
		}
	}
	
	// See http://stackoverflow.com/questions/70324/java-inner-class-and-static-nested-class
	// See http://docs.oracle.com/javase/tutorial/java/javaOO/whentouse.html ...
	//     ... "When to Use Nested Classes, Local Classes, Anonymous Classes, and Lambda Expressions "
	private static class AuctionEvent {
		
		private static final AuctionEvent auctionEvent = new AuctionEvent();
		
		private HashMap<String, String> auctionEventFields = new HashMap<String, String>();
		
		static AuctionEvent find(String messageBody) {
			auctionEvent.unpackEvents(messageBody);
			return auctionEvent;
		}
		
		private void unpackEvents(String messageBody) {
			final String ON_SEMICOLON_DELIMITER = ";";
			String[] elements = messageBody.split(ON_SEMICOLON_DELIMITER);

			final String ON_COLON_NAME_VALUE_DELIMITER = ":";
			for(String element : elements) {
				String[] pair = element.split(ON_COLON_NAME_VALUE_DELIMITER);
				auctionEventFields.put(pair[0].trim(), pair[1].trim());
			}
		}
		
		String getEventType() {
			return auctionEventFields.get("Event");
		}
		
		int getCurrentPrice() {
			return Integer.parseInt(auctionEventFields.get("CurrentPrice"));
		}
		
		int getIncrement() {
			return Integer.parseInt(auctionEventFields.get("Increment"));
		}
	}
}