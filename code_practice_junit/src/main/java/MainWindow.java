import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class MainWindow extends JFrame {
	
	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	
	MainWindow() {
		super(MAIN_WINDOW_NAME);
		setName(MAIN_WINDOW_NAME);
		this.configGui();
		this.addLabel(Main.SNIPER_STATUS_NAME, Main.STATUS_JOINING);
		this.pack();
		this.setVisible(true);
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
 	
 	void addLabel(String labelName, String labelText) {
 		Container cp = getContentPane();
 		JLabel label = new JLabel(labelText);
 		label.setName(labelName);
 		cp.add(label);
 	}
}