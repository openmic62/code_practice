package auctionsniper;

//import auctionsniper.Announcer;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainWindow extends JFrame {
	static Logger logger = LogManager.getLogger(MainWindow.class.getName());	

	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	public static final String SNIPERS_TABLE_NAME = "snipers table";
	public static final String SNIPER_STATUS_NAME = "status";
	public static final String NEW_ITEM_ID_NAME = "item id text field";
	public static final String JOIN_BUTTON_NAME = "join button";
		
	private final SnipersTableModel snipers;
	private final Announcer<UserRequestListener> userRequests =
	              Announcer.to(UserRequestListener.class);
	
	//MainWindow(SnipersTableModel snipers) { // >>>>>>>> I don't like to make this public; have to for testing JMock 2 ... as far as I can see right now
	public MainWindow(SnipersTableModel snipers) {
		super(MAIN_WINDOW_NAME);
		this.snipers = snipers;
		setName(MAIN_WINDOW_NAME);
		configGui();
		fillContentPane(makeSnipersTable(), makeControls());
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
 		
 		// <mlr 140117: begin - some log info to help gain understanding of the reflection stuff used in Announcer.java>
 		logger.info(getClassNameInfo(UserRequestListener.class));
 		logger.info(getClassNameInfo(userRequests));
 		// <mlr 140117: end - some log info to help gain understanding of the reflection stuff used in Announcer.java>
 		
 		final JButton joinAuctionButton = new JButton("Join Auction");
 		joinAuctionButton.setName(JOIN_BUTTON_NAME);
 		joinAuctionButton.addActionListener( new ActionListener() {
 			public void actionPerformed(ActionEvent ae) {
 				userRequests.announce().joinAuction(itemIdField.getText());
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