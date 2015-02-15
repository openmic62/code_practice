package auctionsniper.ui;

import auctionsniper.AuctionSniper;
import auctionsniper.Defect;
import auctionsniper.PortfolioListener;
import auctionsniper.SniperListener;
import auctionsniper.SniperSnapshot;
import auctionsniper.SniperState;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel implements SniperListener, PortfolioListener {
  public static String[] STATUS_TEXT = {"Joining auction",
                                        "Bidding in auction",
                                        "Winning auction",
                                        "Losing auction",
                                        "Lost auction",
                                        "Won auction!!! Bitches!",
                                        "FAILED!!!"};
                                        

  private ArrayList<SniperSnapshot> snapshots = new ArrayList<SniperSnapshot>(); 

	public static String textFor(SniperState state) {
		return STATUS_TEXT[state.ordinal()];
	}
	
	private void addSniperSnapshot(SniperSnapshot sniperSnapshot) {
	  snapshots.add(sniperSnapshot);
	  int rowAddedIndex = snapshots.size()-1;
	  fireTableRowsInserted(rowAddedIndex, rowAddedIndex);
	}
	
	// Implement the PortfolioListener interface
	@Override
	public void sniperAdded(AuctionSniper auctionSniper) {
    addSniperSnapshot(auctionSniper.getSnapShot());
    auctionSniper.addSniperListener(new SwingThreadSniperListener(this));
	}
	
	// Implement the SniperListener interface
	@Override
	public void sniperStateChanged(SniperSnapshot changedSnapshot) {
		int row = rowMatching(changedSnapshot);
		snapshots.set(row, changedSnapshot);
		fireTableRowsUpdated(row, row);
	}
	
	private int rowMatching(SniperSnapshot changedSnapshot) {
		for (int i=0; i<snapshots.size(); i++) {
		 	if (changedSnapshot.isForSameItemAs(snapshots.get(i))) {
		 	 	return i;
		 	}
		}
		throw(new Defect());
	}
	
	// override AbstractTableModel methods
	@Override
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
