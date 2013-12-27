package auctionsniper;

import java.util.EventListener;

public interface SniperListener extends EventListener {
	///public void sniperBidding();
	public void sniperBidding(SniperState sniperState);
	public void sniperLost();
	public void sniperWinning();
	public void sniperWon();
}