import javax.swing.SwingUtilities;

public class Main {

	public static final String SNIPER_STATUS_NAME = "status";
	public static final String STATUS_JOINING = "Joining auction";
	public static final String STATUS_LOST = "Lost auction";

	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					MainWindow mw = new MainWindow();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}