package auctionsniper.xmpp;

import static auctionsniper.AuctionEventListener.PriceSource.FromOtherBidder;
import static auctionsniper.AuctionEventListener.PriceSource.FromSniper;
import auctionsniper.AuctionEventListener;
import auctionsniper.AuctionEventListener.PriceSource;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {
	static Logger logger = LogManager.getLogger(AuctionMessageTranslator.class.getName());	
	private static final Marker UNIT_TEST_MARKER = MarkerManager.getMarker("UNIT_TEST");
	
	private AuctionEventListener auctionEventListener;
	private String sniperXmppID;
	private XMPPFailureReporter failureReporter;
	
	//public AuctionMessageTranslator(String sniperXmppID, AuctionEventListener ael) {
	public AuctionMessageTranslator(String sniperXmppID, AuctionEventListener ael, XMPPFailureReporter fr) {
		this.auctionEventListener = ael;
		this.sniperXmppID = sniperXmppID;
		this.failureReporter = fr;
	}
	
	@Override
	public void processMessage(Chat chat, Message message) {
		logger.info("in call: processMessage(...)\n\t\tmsg from -->{}<--\n\t\tmsg rx'd -->{}<--", 
		            chat != null ? chat.getParticipant(): "unit_test",
		            message.getBody());
		            
    // <mlr 140312: add failure detection code>
	  String messageBody = message.getBody();
	  try {
	  	translate(message.getBody());
	  } catch (Exception parseException) {
      // <mlr 140312: add failure detection code>
	  	failureReporter.cannotTranslateMessage(sniperXmppID, messageBody, parseException);
	  	auctionEventListener.auctionFailed();
	  }
	  
  // <mlr 140310: begin - add failure detection code>
  /*
		AuctionEvent auctionEvent = AuctionEvent.find(message.getBody());
		
		String eventType = auctionEvent.eventType();
		if ("CLOSE".equals(eventType)) {
			auctionEventListener.auctionClosed();
		} else if ("PRICE".equals(eventType)) {
		 	auctionEventListener.currentPrice(
		 		auctionEvent.currentPrice(),
		 		auctionEvent.increment(),
		 		auctionEvent.isFrom(sniperXmppID)
		 	);
		}
	*/
  // <mlr 140310: end - add failure detection code>
	}
  // <mlr 140310: begin - add failure detection code>
  private void translate(String messageBody) {
		AuctionEvent auctionEvent = AuctionEvent.find(messageBody);
		
		String eventType = auctionEvent.eventType();
		if ("CLOSE".equals(eventType)) {
			auctionEventListener.auctionClosed();
		} else if ("PRICE".equals(eventType)) {
		 	auctionEventListener.currentPrice(
		 		auctionEvent.currentPrice(),
		 		auctionEvent.increment(),
		 		auctionEvent.isFrom(sniperXmppID)
		 	);
		}
  }
  // <mlr 140310: end - add failure detection code>	

	private static class AuctionEvent {
		
		private Map<String, String> auctionEventFields = new HashMap<String, String>();
		
		PriceSource isFrom(String iD) {
			return bidder().contains(iD) ? FromSniper : FromOtherBidder;
		}
		String eventType() {
			return get("Event");
		}
		int currentPrice() {
			return getInt("CurrentPrice");
		}
		int increment() {
			return getInt("Increment");
		}
		
		private String bidder() { return get("Bidder"); }
		private int getInt(String fieldName) { return Integer.parseInt(get(fieldName)); }
	  // <mlr 140310: begin - add failure detection code>
		///private String get(String fieldName) { return auctionEventFields.get(fieldName); }
		private String get(String fieldName) {
			String value = auctionEventFields.get(fieldName);
			if ( null == value ) {
				throw new MissingValueException(fieldName);
			}
			return value;
		}
	  // <mlr 140310: end - add failure detection code>
		
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