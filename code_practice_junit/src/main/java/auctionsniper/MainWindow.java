package auctionsniper;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MainWindow extends JFrame {

	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPERS_TABLE_NAME = "snipers table";
	public static final String SNIPER_STATUS_NAME = "status";
	public static final String NEW_ITEM_ID_NAME = "item id text field";
	public static final String JOIN_BUTTON_NAME = "join button";
		
	private final SnipersTableModel snipers;
	
	//MainWindow(SnipersTableModel snipers) { // >>>>>>>> I don't like make this public; have to for testing
	public MainWindow(SnipersTableModel snipers) {
		super(MAIN_WINDOW_NAME);
		this.snipers = snipers;
		setName(MAIN_WINDOW_NAME);
		configGui();
		//fillContentPane(makeSnipersTable());
		fillContentPane(makeSnipersTable(), makeControls());
		pack();
		setVisible(true);
	}
	
 	void configGui() {
 	  setLocation(50, 100);
 	  setMinimumSize(new Dimension(300, 100));
 	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 	}
 	
 	//private void fillContentPane(JTable snipersTable) {
 	private void fillContentPane(JTable snipersTable, JPanel addItemControlPanel) {
 		final Container contentPane = getContentPane();
 		contentPane.setLayout(new BorderLayout());
 		contentPane.add(addItemControlPanel, BorderLayout.NORTH);
 		contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
 	}
 	
 	private JTable makeSnipersTable() {
 		JTable table = new JTable(snipers);
 		table.setPreferredScrollableViewportSize(new Dimension(450, 150));
 		table.setFillsViewportHeight(true);
 		table.setName(SNIPERS_TABLE_NAME);
 		return table;
 	}
 	
 	private JPanel makeControls() {
 		JPanel controls = new JPanel(new FlowLayout());
 		
 		final JTextField itemIdField = new JTextField();
 		itemIdField.setColumns(25);
 		itemIdField.setName(NEW_ITEM_ID_NAME);
 		controls.add(itemIdField);
 		
 		final JButton joinAuctionButton = new JButton("Join Auction");
 		joinAuctionButton.setName(JOIN_BUTTON_NAME);
 		controls.add(joinAuctionButton);
 		
 		return controls;
 	}
 	
 	public void addUserRequestListener(UserRequestListener userRequestListener){}
}