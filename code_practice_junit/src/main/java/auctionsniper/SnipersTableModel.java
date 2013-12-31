package auctionsniper;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel {
	//private final SniperSnapshot STARTING_STATE = new SniperSnapshot("", 0, 0);
	private final SniperSnapshot STARTING_STATE = new SniperSnapshot("", 0, 0, SniperState.JOINING);
	//private String[] STATUS_TEXT = {MainWindow.STATUS_JOINING,
	   public String[] STATUS_TEXT = {MainWindow.STATUS_JOINING,
		                                MainWindow.STATUS_BIDDING,
		                                MainWindow.STATUS_WINNING,
		                                MainWindow.STATUS_LOST,
		                                MainWindow.STATUS_WON};

	//private String statusText = MainWindow.STATUS_JOINING;
	private String state = MainWindow.STATUS_JOINING;
	private SniperSnapshot snapshot = STARTING_STATE;
	
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
    		return snapshot.getItemId();
    	case LAST_PRICE: 
    		return snapshot.getLastPrice();
    	case LAST_BID: 
    		return snapshot.getLastBid();
    	case SNIPER_STATE: 
    		//return statusText;
    		//return snapshot.getSniperState();
    		return this.state;
      default:
        throw new IllegalArgumentException("No column at " + column);
    }
	}

	public void setStatusText(String newStatusText) {
		//this.statusText = newStatusText;
		this.state = newStatusText;
		fireTableRowsUpdated(0, 0);
	}

	//public void sniperStatusChanged(SniperSnapshot newSnapshot, String newStatusText) {
	public void sniperStateChanged(SniperSnapshot newSnapshot) {
		this.snapshot = newSnapshot;
		//this.statusText = newStatusText;
		//this.state = newStatusText;
		this.state = STATUS_TEXT[newSnapshot.getSniperState().ordinal()];
		fireTableRowsUpdated(0, 0);
	}
}
