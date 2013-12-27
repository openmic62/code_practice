package auctionsniper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SniperState {
	
	private final String itemID;
	private final int lastPrce;
	private final int bidPrice;

	public SniperState(String itemID, int lastPrce, int bidPrice) {
		this.itemID = itemID;
		this.lastPrce = lastPrce;
		this.bidPrice = bidPrice;
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