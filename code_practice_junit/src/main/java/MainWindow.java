import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;

public class MainWindow extends JFrame {
	
	public static final String MAIN_WINDOW_NAME = "Auction Sniper Main";
	
	MainWindow() {
		super(MAIN_WINDOW_NAME);
	}
	
 	void setGui() {
 		try {
 			setLocation(0, 100);
 			setMinimumSize(new Dimension(350, 150));
 			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 			Container cp = getContentPane();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
}