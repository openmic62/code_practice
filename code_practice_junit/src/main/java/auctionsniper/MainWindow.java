package auctionsniper;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainWindow extends JFrame {
	
	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
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
 	  setMinimumSize(new Dimension(250, 100));
 	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 	}
 	
 	private void fillContentPane(JTable snipersTable) {
 		final Container contentPane = getContentPane();
 		contentPane.setLayout(new BorderLayout());
 		
 		contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
 	}
 	
 	private JTable makeSnipersTable() {
 		JTable table = new JTable(snipers);
 		table.setPreferredScrollableViewportSize(new Dimension(250, 150));
 		table.setFillsViewportHeight(true);
 		table.setName(Main.SNIPERS_TABLE_NAME);
 		return table;
 	}
 	
 	public void showStatus(String statusText) {
 		snipers.setStatusText(statusText);
 	}
 	
 	public void sniperStatusChanged(SniperState sniperState, String statusText) {
 		snipers.sniperStatusChanged(sniperState, statusText);
 	}
 	
}