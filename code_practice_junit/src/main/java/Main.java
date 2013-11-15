import javax.swing.SwingUtilities;

public class Main {
	private MainWindow ui;

	public static final String SNIPER_STATUS_NAME = "status";
	public static final String STATUS_JOINING = "Joining auction";
	public static final String STATUS_LOST = "Lost auction";
	
	public Main() {
		startUserInterface();
	}

	public static void main(String[] args) {
		Main main = new Main();
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
}