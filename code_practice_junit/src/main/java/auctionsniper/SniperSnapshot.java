package auctionsniper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SniperSnapshot {
	
	private final String itemId;
	private final int lastPrice;
	private final int bidPrice;

	public SniperSnapshot(String itemId, int lastPrice, int bidPrice) {
		this.itemId = itemId;
		this.lastPrice = lastPrice;
		this.bidPrice = bidPrice;
	}
	
	public String getItemId() { return this.itemId; }
	public int getLastPrice() { return this.lastPrice; }
	public int getBidPrice() { return this.bidPrice; }
	
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