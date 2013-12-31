package auctionsniper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SniperSnapshot {
	static Logger logger = LogManager.getLogger(SniperSnapshot.class.getName());	
	
	private final String      itemId;
	private final int         lastPrice;
	private final int         lastBid;
	private final SniperState sniperState;

	
	//public SniperSnapshot(String itemId, int lastPrice, int lastBid) {
	public SniperSnapshot(String itemId, int lastPrice, int lastBid, SniperState state) {
		this.itemId      = itemId;
		this.lastPrice   = lastPrice;
		this.lastBid     = lastBid;
		this.sniperState = state;
	}
	
	public String getItemId() { return this.itemId; }
	public int getLastPrice() { return this.lastPrice; }
	public int getLastBid() { return this.lastBid; }
	public SniperState getSniperState() { return this.sniperState; }
	
	public static SniperSnapshot joining(String itemId) {
		return new SniperSnapshot(itemId, 0, 0, SniperState.JOINING);
	}

	public SniperSnapshot bidding(int newLastPrice, int newLastBid) {
		return new SniperSnapshot(this.itemId, newLastPrice, newLastBid, SniperState.BIDDING);
	}
	
	public SniperSnapshot winning(int newLastPrice) {
		return new SniperSnapshot(this.itemId, newLastPrice, newLastPrice, SniperState.WINNING);
	}
	
	public SniperSnapshot closed() {
		return sniperState.name().contains("WINNING") ? won() : lost();
	}
			
	private SniperSnapshot won() {
		return aSnapshot(SniperState.WON);
	}
	
	private SniperSnapshot lost() {
		return aSnapshot(SniperState.LOST);
	}
		
	private SniperSnapshot aSnapshot(SniperState state) {
		return new SniperSnapshot(this.itemId, this.lastPrice, this.lastBid, state);
	}
	
	@Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }
  
  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}