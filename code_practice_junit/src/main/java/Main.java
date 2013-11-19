import javax.swing.SwingUtilities;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class Main {
	private MainWindow ui;

	public static final String SNIPER_STATUS_NAME = "status";
	public static final String STATUS_JOINING = "Joining auction";
	public static final String STATUS_LOST = "Lost auction";
	
	private int port = 5222;
	private String hostname;
	private ChatManager chatManager;
	private ConnectionConfiguration config;
	private XMPPConnection connection;

	public Main(String[] args) throws XMPPException, Exception {
		String username = args[0];
		String password = args[1];
		hostname = java.net.InetAddress.getLocalHost().getHostName();
		startUserInterface();
		config = createConnectionConfiguration(hostname, port);
		connection = createConnection(config);
		connection.connect();
		performLogin(username, password);
		chatManager = connection.getChatManager();
		sendMessage("JOIN", "auction-item-54321@" + hostname, new SniperMessageListener());
	}

	public static void main(String[] args) throws XMPPException, Exception {
		Main main = new Main(args);
	}

	private void startUserInterface() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					ui = new MainWindow();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private ConnectionConfiguration createConnectionConfiguration(String server, int port) {
		config = new ConnectionConfiguration(server, port);
		config.setSASLAuthenticationEnabled(false);
		config.setSecurityMode(SecurityMode.disabled);
		return config;
	}
	
	private XMPPConnection createConnection(ConnectionConfiguration config) throws XMPPException {
		return new XMPPConnection(config);
	}
	
	private void performLogin(String username, String password) throws XMPPException {
		if (connection!=null && connection.isConnected()) {
			connection.login(username, password);
		}
	}

	private void sendMessage(String message, String buddyJID, MessageListener messageListener) throws XMPPException {
		Chat chat = chatManager.createChat(buddyJID, messageListener);
		chat.sendMessage(message);
	}
	
	class SniperMessageListener implements MessageListener {
		@Override
		public void processMessage(Chat chat, Message message) {
			ui.setSniperStatusText(STATUS_LOST);
		}
	}
}