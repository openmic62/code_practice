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
		///System.out.println("AMT: message received -->" + message.getBody() + "<--");
		logger.debug("message received -->" + message.getBody() + "<--");
		HashMap<String, String> event = unpackEvent(message);
		String type = event.get("Event");
		if ("CLOSE".equals(type)) {
			auctionEventListener.auctionClosed();
		} else if ("PRICE".equals(type)) {
		 	auctionEventListener.currentPrice(
		 		Integer.parseInt(event.get("CurrentPrice")),
		 		Integer.parseInt(event.get("Increment"))
		 	);
		}
	}
	
	private HashMap<String, String> unpackEvent(Message message) {
		String messageBody = message.getBody().toString(); 
		final String ON_SEMICOLON_DELIMITER = ";";
		String[] elements = messageBody.split(ON_SEMICOLON_DELIMITER);
		HashMap<String, String> event = new HashMap<String, String>();
		final String ON_COLON_NAME_VALUE_DELIMITER = ":";
		for(String element : elements) {
			String[] pair = element.split(ON_COLON_NAME_VALUE_DELIMITER);
			event.put(pair[0].trim(), pair[1].trim());
		}
		return event;
	}
}