import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainWindow extends JFrame {
	
	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	private final JLabel sniperStatus = createLabel(Main.SNIPER_STATUS_NAME, Main.STATUS_JOINING);
	
	MainWindow() {
		super(MAIN_WINDOW_NAME);
		setName(MAIN_WINDOW_NAME);
		configGui();
		add(sniperStatus);
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
 		sniperStatus.setText(statusText);
 	}
}