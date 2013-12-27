package auctionsniper;

import javax.swing.table.AbstractTableModel;

public class SnipersTableModel extends AbstractTableModel {
	private String statusText = Main.STATUS_JOINING;

	@Override
	public int getRowCount() { return 1; }
	@Override
	public int getColumnCount() { return 1; }
	@Override
	public Object getValueAt(int row, int column) { return statusText; }

	public void setStatusText(String statusText) {
		this.statusText = statusText;
		fireTableRowsUpdated(0, 0);
	}

	public void sniperStatusChanged(SniperState sniperState, String statusText) {
	}
}
