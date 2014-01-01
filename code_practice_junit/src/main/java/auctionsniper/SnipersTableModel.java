package auctionsniper;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel implements SniperListener {
	private final SniperSnapshot STARTING_STATE = new SniperSnapshot("", 0, 0, SniperState.JOINING);
	private SniperSnapshot snapshot = STARTING_STATE;
  public static String[] STATUS_TEXT = {"Joining auction",
                                        "Bidding in auction",
                                        "Winning auction",
                                        "Lost auction",
                                        "Won auction!!! Bitches!"};

	public static String textFor(SniperState state) {
		return STATUS_TEXT[state.ordinal()];
	}
	
	// Implement the SniperListener interface
	@Override
	public void sniperStateChanged(SniperSnapshot newSnapshot) {
		this.snapshot = newSnapshot;
		fireTableRowsUpdated(0, 0);
	}
	
	// override AbstractTableModel methods
	@Override
	public int getRowCount() { return 1; }
	@Override
	public int getColumnCount() { return Column.values().length; }
	@Override
	public Object getValueAt(int row, int column) { 
		return Column.values()[column].valueIn(snapshot);
	}
}
