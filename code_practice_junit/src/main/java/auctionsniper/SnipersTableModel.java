package auctionsniper;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel implements SniperListener {
	//private final SniperSnapshot STARTING_STATE = new SniperSnapshot("", 0, 0, SniperState.JOINING);
	private final SniperSnapshot STARTING_STATE = new SniperSnapshot("item-54321", 0, 0, SniperState.JOINING);
	//private final SniperSnapshot STARTING_STATE = new SniperSnapshot("item-65432", 0, 0, SniperState.JOINING);
	//private SniperSnapshot snapshot = STARTING_STATE;
  public static String[] STATUS_TEXT = {"Joining auction",
                                        "Bidding in auction",
                                        "Winning auction",
                                        "Lost auction",
                                        "Won auction!!! Bitches!"};
                                        
  private ArrayList<SniperSnapshot> snapshots = new ArrayList<SniperSnapshot>();

	public static String textFor(SniperState state) {
		return STATUS_TEXT[state.ordinal()];
	}
	
	public void addSniper(SniperSnapshot newSnapshot) {
	  snapshots.add(newSnapshot);
	  int rowAddedIndex = snapshots.size()-1;
	  fireTableRowsInserted(rowAddedIndex, rowAddedIndex);
	}
	
	// Implement the SniperListener interface
	@Override
	public void sniperStateChanged(SniperSnapshot changedSnapshot) {
		/*
		//this.snapshot = changedSnapshot;
		snapshots.set(0, changedSnapshot);
		fireTableRowsUpdated(0, 0);
		*/
		int row = findRowForItemIn(changedSnapshot);
		snapshots.set(row, changedSnapshot);
		fireTableRowsUpdated(row, row);
	}
	
	private int findRowForItemIn(SniperSnapshot changedSnapshot) {
		for (int i=0; i<snapshots.size(); i++) {
		 	if (changedSnapshot.isForSameItemAs(snapshots.get(i))) {
		 	 	return i;
		 	}
		}
		throw(new Defect());
	}
	
	// override AbstractTableModel methods
	@Override
	//public int getRowCount() { return 1; }
	public int getRowCount() { return snapshots.size(); }
	@Override
	public int getColumnCount() { return Column.values().length; }
	@Override
	public String getColumnName(int column) { return Column.at(column).name; }
	@Override
	public Object getValueAt(int row, int column) { 
		return Column.values()[column].valueIn(snapshots.get(row));
	}
}
