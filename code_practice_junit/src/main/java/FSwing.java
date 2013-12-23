import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
 
public class FSwing extends JFrame {
 	private void setGui() {
 		try {
 			setLocation(0, 100);
 			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 			//Container cp = getContentPane();
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
 	
 	public static void main(String[] args) {
 		try {
 			SwingUtilities.invokeAndWait(new Runnable() {
 				public void run() {
 					FSwing f = new FSwing();
 					f.setGui();
 					f.pack();
 					f.setVisible(true);
 				}
 			});
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
}