package auctionsniper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Item {
	private final String itemId;
	private final int    stopPrice;
	
	public Item(String itemId, int stopPrice) {
		this.itemId    = itemId;
		this.stopPrice = stopPrice;
	}
	
	public String  getItemId()       { return itemId; }
	public int     getStopPrice()    { return stopPrice; }
	public boolean allowsBid(int bid){ return bid <= stopPrice;  }
	//public boolean allowsBid(int bid){ System.out.println("bidding bitches: " + bid + ", " + stopPrice + ", " + (bid <= stopPrice));return bid <= stopPrice;  }

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