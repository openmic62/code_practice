package auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {
	
	private AuctionEventListener auctionEventListener;
	
	public AuctionMessageTranslator(AuctionEventListener ael) {
		this.auctionEventListener = ael;
	}
	
	@Override
	public void processMessage(Chat chat, Message message) {
		System.out.println("Main: message received -->" + message.getBody() + "<--");
		auctionEventListener.auctionClosed();
	}
}