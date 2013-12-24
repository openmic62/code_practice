package auctionsniper;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class MainWindow extends JFrame {
	
	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	private final JLabel sniperStatus = createLabel(Main.SNIPER_STATUS_NAME, Main.STATUS_JOINING);
	private final SnipersTableModel snipers = new SnipersTableModel();
	
	MainWindow() {
		super(MAIN_WINDOW_NAME);
		setName(MAIN_WINDOW_NAME);
		configGui();
		//add(sniperStatus);
		add(new JScrollPane(createTable(Main.SNIPER_STATUS_NAME)));
		pack();
		setVisible(true);
	}
	
 	void configGui() {
 		try {
 			setLocation(0, 100);
 			setMinimumSize(new Dimension(350, 150));
 			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
 	
 	private JLabel createLabel(String labelName, String labelText) {
 		Container cp = getContentPane();
 		JLabel label = new JLabel(labelText);
 		label.setName(labelName);
 		label.setBorder(new LineBorder(Color.BLACK));
 		return label;
 	}
 	
 	public void showStatus(String statusText) {
 		///sniperStatus.setText(statusText);
 		snipers.setStatusText(statusText);
 	}
 	
 	private JTable createTable(String tableName) {
 		JTable table = new JTable(snipers);
 		table.setPreferredScrollableViewportSize(new Dimension(350, 150));
 		table.setFillsViewportHeight(true);
 		table.setName(tableName);
 		return table;
 	}
 	
 	private class SnipersTableModel extends AbstractTableModel {
 		private String statusText = Main.STATUS_JOINING;
 		
 		SnipersTableModel() { super(); }
 		
 		@Override
    public int getRowCount() { return 1; }
 		@Override
    public int getColumnCount() { return 1; }
 		@Override
    public Object getValueAt(int row, int column) { return statusText; }
 		@Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {}
    
    public void setStatusText(String statusText) {
    	this.statusText = statusText;
    	fireTableRowsUpdated(0, 0);
    }
  }
}