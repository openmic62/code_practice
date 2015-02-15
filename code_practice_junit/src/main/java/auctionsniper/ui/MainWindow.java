package auctionsniper.ui;

import auctionsniper.Announcer;
import auctionsniper.Item;
import auctionsniper.UserRequestListener;
import auctionsniper.SniperPortfolio;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class MainWindow extends JFrame {
	static Logger logger = LogManager.getLogger(MainWindow.class.getName());
	private static final Marker ANNOUNCER_MARKER = MarkerManager.getMarker("ANNOUNCER");

	public static final String MAIN_WINDOW_NAME               = "Auction Sniper Main";
	public static final String SNIPERS_TABLE_NAME             = "snipers table";
	public static final String SNIPER_STATUS_NAME             = "status";
	public static final String NEW_ITEM_ID_NAME               = "item id text field";
	public static final String NEW_ITEM_ID_LABEL_NAME         = "item id label";
	public static final String NEW_ITEM_ID_LABEL_TEXT         = "Item ID: ";
	public static final String NEW_ITEM_STOP_PRICE_NAME       = "stop price text field";
	public static final String NEW_ITEM_STOP_PRICE_LABEL_NAME = "stop price label";
	public static final String NEW_ITEM_STOP_PRICE_LABEL_TEXT = "Stop Price: ";
	public static final String JOIN_BUTTON_NAME               = "join button";
		
	private final Announcer<UserRequestListener> userRequests =
	              Announcer.to(UserRequestListener.class);
	
	//MainWindow(SnipersTableModel snipers) { // >>>>>>>> I don't like to make this public; have to for testing JMock 2 ... as far as I can see right now
	public MainWindow(SniperPortfolio portfolio) {
		super(MAIN_WINDOW_NAME);
		setName(MAIN_WINDOW_NAME);
		configGui();
		fillContentPane(makeSnipersTable(portfolio), makeControls());
		pack();
		setVisible(true);
	}	
	
 	void configGui() {
 	  setLocation(50, 100);
 	  setMinimumSize(new Dimension(300, 100));
 	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 	}
 	
 	private void fillContentPane(JTable snipersTable, JPanel addItemControlPanel) {
 		final Container contentPane = getContentPane();
 		contentPane.setLayout(new BorderLayout());
 		contentPane.add(addItemControlPanel, BorderLayout.NORTH);
 		contentPane.add(new JScrollPane(snipersTable), BorderLayout.CENTER);
 	}
 	
 	private JTable makeSnipersTable(SniperPortfolio portfolio) {
 		SnipersTableModel model = new SnipersTableModel(); 
 		portfolio.addPortfolioListener(model);
 		JTable table = new JTable(model);
 		table.setPreferredScrollableViewportSize(new Dimension(450, 150));
 		table.setFillsViewportHeight(true);
 		table.setName(SNIPERS_TABLE_NAME);
 		return table;
 	}
 	
 	private JPanel makeControls() {
 		JPanel controls = new JPanel(new FlowLayout());
 		
 		final JLabel itemIdLabel = new JLabel();
 		itemIdLabel.setName(NEW_ITEM_ID_LABEL_NAME);
 		itemIdLabel.setText(NEW_ITEM_ID_LABEL_TEXT);
 		controls.add(itemIdLabel);
 		
 		final JTextField itemIdField = new JTextField();
 		itemIdField.setColumns(25);
 		itemIdField.setName(NEW_ITEM_ID_NAME);
 		controls.add(itemIdField);
 		
 		final JLabel stopPriceLabel = new JLabel();
 		stopPriceLabel.setName(NEW_ITEM_STOP_PRICE_LABEL_NAME);
 		stopPriceLabel.setText(NEW_ITEM_STOP_PRICE_LABEL_TEXT);
 		controls.add(stopPriceLabel);
 		
 		final JFormattedTextField stopPriceField = new JFormattedTextField();
 		stopPriceField.setColumns(10);
 		stopPriceField.setName(NEW_ITEM_STOP_PRICE_NAME);
 		controls.add(stopPriceField);
 		
 		// <mlr 140117: begin - some log info to help gain understanding of the reflection stuff used in Announcer.java>
 		logger.info(ANNOUNCER_MARKER, getClassNameInfo(UserRequestListener.class));
 		logger.info(ANNOUNCER_MARKER, getClassNameInfo(userRequests));
 		// <mlr 140117: end - some log info to help gain understanding of the reflection stuff used in Announcer.java>
 		
 		final JButton joinAuctionButton = new JButton("Join Auction");
 		joinAuctionButton.setName(JOIN_BUTTON_NAME);
 		joinAuctionButton.addActionListener( new ActionListener() {
 			public void actionPerformed(ActionEvent ae) {
 				userRequests.announce().joinAuction(new Item(itemIdField.getText(), Integer.valueOf(stopPriceField.getText())));
 			}
 		});
 			
 		controls.add(joinAuctionButton);
 		
 		return controls;
 	}
  private String getClassNameInfo(Object obj) {
    return String.format("The class of object-->%s<-- is -->%s<--", obj, obj.getClass().getName());
  }
 	
 	public void addUserRequestListener(UserRequestListener userRequestListener){
 		userRequests.addListener(userRequestListener);
 	}
}