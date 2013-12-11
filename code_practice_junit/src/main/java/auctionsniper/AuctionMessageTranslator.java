package auctionsniper;

import java.util.HashMap;
import java.util.Map;

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
		
		String eventType = auctionEvent.eventType();
		if ("CLOSE".equals(eventType)) {
			auctionEventListener.auctionClosed();
		} else if ("PRICE".equals(eventType)) {
		 	auctionEventListener.currentPrice(
		 		auctionEvent.currentPrice(),
		 		auctionEvent.increment()
		 	);
		}
	}
	
	// See http://stackoverflow.com/questions/70324/java-inner-class-and-static-nested-class
	// See http://docs.oracle.com/javase/tutorial/java/javaOO/whentouse.html ...
	//     ... "When to Use Nested Classes, Local Classes, Anonymous Classes, and Lambda Expressions "
	private static class AuctionEvent {
		
		private Map<String, String> auctionEventFields = new HashMap<String, String>();
		
		String eventType() {
			return get("Event");
		}
		int currentPrice() {
			return getInt("CurrentPrice");
		}
		int increment() {
			return getInt("Increment");
		}
		
		private int getInt(String fieldName) { return Integer.parseInt(get(fieldName)); }
		private String get(String fieldName) { return auctionEventFields.get(fieldName); }
		
		static AuctionEvent find(String messageBody) {
			AuctionEvent auctionEvent = new AuctionEvent();
			auctionEvent.parseFields(messageBody);
			return auctionEvent;
		}
		
		private void parseFields(String messageBody) {
			
			String[] fields = fields(messageBody);

			for(String field : fields) {
				addField(field);
			}
		}
		
		private void addField(String fieldValuePair) {
			String[] pair = fieldValuePair.split(":");
			auctionEventFields.put(pair[0].trim(), pair[1].trim());
		}			
		
		private static String[] fields(String messageBody) {
			String[] fields = messageBody.split(";");
			return fields;
		}
	}
}