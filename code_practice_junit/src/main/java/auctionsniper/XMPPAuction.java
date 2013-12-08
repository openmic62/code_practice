package auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class XMPPAuction implements Auction
{
	private Chat chat;

	XMPPAuction(Chat c) {
		this.chat = c;
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
			chat.sendMessage(new Message(Main.JOIN_COMMAND_FORMAT));
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
}