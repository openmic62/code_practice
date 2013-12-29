package auctionsniper;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel {
	private String statusText = Main.STATUS_JOINING;
	private final SniperSnapshot STARTING_STATE = new SniperSnapshot("", 0, 0);
	private SniperSnapshot sniperSnapshot = STARTING_STATE;
	
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
    		return sniperSnapshot.getItemId();
    	case LAST_PRICE: 
    		return sniperSnapshot.getLastPrice();
    	case LAST_BID: 
    		return sniperSnapshot.getBidPrice();
    	case SNIPER_STATE: 
    		return statusText;
      default:
        throw new IllegalArgumentException("No column at " + column);
    }
	}

	public void setStatusText(String newStatusText) {
		this.statusText = newStatusText;
		fireTableRowsUpdated(0, 0);
	}

	public void sniperStatusChanged(SniperSnapshot newSniperSnapshot, String newStatusText) {
		this.sniperSnapshot = newSniperSnapshot;
		this.statusText = newStatusText;
		fireTableRowsUpdated(0, 0);
	}
}
