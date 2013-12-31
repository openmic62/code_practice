package auctionsniper;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel {
	private final SniperSnapshot STARTING_STATE = new SniperSnapshot("", 0, 0, SniperState.JOINING);
	//private String[] STATUS_TEXT = {MainWindow.STATUS_JOINING,
	/* <mlr 131231: begin - GOOS, p. 166a moves these to SnipersTableModel>
	   public String[] STATUS_TEXT = {MainWindow.STATUS_JOINING,
		                                MainWindow.STATUS_BIDDING,
		                                MainWindow.STATUS_WINNING,
		                                MainWindow.STATUS_LOST,
		                                MainWindow.STATUS_WON};
	   <mlr 131231: end - GOOS, p. 166a moves these to SnipersTableModel> */
	//private String state = MainWindow.STATUS_JOINING;
	private SniperSnapshot snapshot = STARTING_STATE;
  public static String[] STATUS_TEXT = {"Joining auction",
                                        "Bidding in auction",
                                        "Winning auction",
                                        "Lost auction",
                                        "Won auction!!! Bitches!"};

	public static String textFor(SniperState state) {
		return STATUS_TEXT[state.ordinal()];
	}
	
	public void sniperStateChanged(SniperSnapshot newSnapshot) {
		this.snapshot = newSnapshot;
		//this.state = STATUS_TEXT[newSnapshot.getSniperState().ordinal()];
		fireTableRowsUpdated(0, 0);
	}
	
	// override AbstractTableModel methods
	@Override
	public int getRowCount() { return 1; }
	@Override
	public int getColumnCount() { return Column.values().length; }
	@Override
	public Object getValueAt(int row, int column) { 
    switch (Column.at(column)) {
    	case ITEM_IDENTIFIER: 
    		return snapshot.getItemId();
    	case LAST_PRICE: 
    		return snapshot.getLastPrice();
    	case LAST_BID: 
    		return snapshot.getLastBid();
    	case SNIPER_STATE: 
    		//return this.state;
    		return textFor(snapshot.getState());
      default:
        throw new IllegalArgumentException("No column at " + column);
    }
	}
}
