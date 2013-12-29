package auctionsniper;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel {
	private String statusText = Main.STATUS_JOINING;
	private final SniperState STARTING_STATE = new SniperState("", 0, 0);
	private SniperState sniperState = STARTING_STATE;
	
	// override AbstractTableModel methods
	@Override
	public int getRowCount() { return 1; }
	@Override
	//public int getColumnCount() { return 1; }
	public int getColumnCount() { return Column.values().length; }
	@Override
	//public Object getValueAt(int row, int column) { return statusText; }
	public Object getValueAt(int row, int column) { 
    switch (Column.at(column)) {
    	case ITEM_IDENTIFIER: 
    		return sniperState.getItemId();
    	case LAST_PRICE: 
    		return sniperState.getLastPrice();
    	case LAST_BID: 
    		return sniperState.getBidPrice();
    	case SNIPER_STATUS: 
    		return statusText;
      default:
        throw new IllegalArgumentException("No column at " + column);
    }
	}

	public void setStatusText(String newStatusText) {
		this.statusText = newStatusText;
		fireTableRowsUpdated(0, 0);
	}

	public void sniperStatusChanged(SniperState newSniperState, String newStatusText) {
		this.sniperState = newSniperState;
		this.statusText = newStatusText;
		fireTableRowsUpdated(0, 0);
	}
}
