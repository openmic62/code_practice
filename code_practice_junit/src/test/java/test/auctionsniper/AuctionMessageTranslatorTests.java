package test.auctionsniper;

import org.jivesoftware.smack.Chat;

import org.junit.Test;

public class AuctionMessageTranslatorTests {
	public static final Chat UNUSED_CHAT = null;
	private final AuctionMessageTranslator translator = new AuctionMessageTranslator();
	
	@Test
	public void notifiesAuctionClosedWhenCloseMessageReceived() {
	}
}