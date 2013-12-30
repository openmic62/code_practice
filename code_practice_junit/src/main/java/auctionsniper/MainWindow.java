package auctionsniper;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainWindow extends JFrame {
	
	public static final String STATUS_JOINING = "Joining auction";
	public static final String STATUS_BIDDING = "Bidding in auction";
	public static final String STATUS_WINNING = "Winning auction";
	public static final String STATUS_LOST = "Lost auction";
	public static final String STATUS_WON = "Won auction!!! Bitches!";

	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPERS_TABLE_NAME = "snipers table";
	public static final String SNIPER_STATUS_NAME = "status";
		
	private final SnipersTableModel snipers = new SnipersTableModel();
	
	MainWindow() {
		super(MAIN_WINDOW_NAME);
		setName(MAIN_WINDOW_NAME);
		configGui();
		fillContentPane(makeSnipersTable());
		pack();
		setVisible(true);
	}
	
 	void configGui() {
 	  setLocation(50, 100);
 	  setMinimumSize(new Dimension(300, 100));
 	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 	}
 	
 	private void fillContentPane(JTable snipersTable) {
 		final Container contentPane = getContentPane();
 		contentPane.setLayout(new BorderLayout());
 		
 		contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
 	}
 	
 	private JTable makeSnipersTable() {
 		JTable table = new JTable(snipers);
 		table.setPreferredScrollableViewportSize(new Dimension(450, 150));
 		table.setFillsViewportHeight(true);
 		table.setName(SNIPERS_TABLE_NAME);
 		return table;
 	}
 	
 	public void showStatus(String statusText) {
 		snipers.setStatusText(statusText);
 	}
 	
 	//public void sniperStatusChanged(SniperSnapshot sniperSnapshot, String statusText) {
 	public void sniperStateChanged(SniperSnapshot sniperSnapshot) {
 		snipers.sniperStateChanged(sniperSnapshot);
 	}
 	
}