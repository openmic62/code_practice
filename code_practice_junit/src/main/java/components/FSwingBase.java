/**
* <mlr 131222: begin - minimal code for a Swing GUI done with good form>
* When I first started, I found a minimal example online and I mimiced it.
* That example exists in F.java (and FSwing.java).
*
* While studying GOOS, p. 149b, I decided to go through the Oracle tutorials
* for JTable because the book uses JTable in its example. I noticed all
* of the Swing demos followed the same basic structure. Their use of
* "invokeLater()" rather than "invokeAndWait()" eliminated the noisey try/catch
* I needed. It also consolidated all the setup in one method. I liked it.
* So I decided to adopt it for myself.
* http://docs.oracle.com/javase/tutorial/uiswing/components/table.html
*
* http://stackoverflow.com/questions/13212431/jpanel-vs-jframe-in-java
* http://stackoverflow.com/questions/2432839/what-is-the-relation-between-contentpane-and-jpanel
* <mlr 131222: end - minimal code for a Swing GUI done with good form>

 set CLASSPATH=lib;lib\Smack.jar;lib\Smackx.jar;lib\Smackx-debug.jar;lib\junit-4.11.jar;lib\hamcrest-all-1.3.jar
 set WL=lib\windowlicker-core-DEV.jar;lib\windowlicker-swing-DEV.jar
 set CLASSPATH=%WL%;%CLASSPATH%
 set JM=lib\jmock-2.6.0.jar;lib\jmock-junit4-2.6.0.jar
 set CLASSPATH=%JM%;%CLASSPATH%
 set JML=lib\jmock-legacy-2.6.0.jar;lib\cglib-nodep-2.2.3.jar;lib\objenesis-1.0.jar
 set CLASSPATH=%JML%;%CLASSPATH%
 set L4J2=lib\log4j-api-2.0-rc1.jar;lib\log4j-core-2.0-rc1.jar
 set CLASSPATH=%L4J2%;%CLASSPATH%
 set ACL3=lib\commons-lang3-3.1.jar
 set CLASSPATH=%ACL3%;%CLASSPATH%
 set ACIO=lib\commons-io-2.4.jar
 set CLASSPATH=%ACIO%;%CLASSPATH%
 set SIH=src\test\scripts\SysinternalsSuite_131101
 set SC=target\classes
 set TC=target\test-classes
 set SD=src\main\java
 set TD=src\test\java
 
 cd student\code_practice_junit
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\components\FSwingBase.java
 java -cp %CLASSPATH%;%SC%;%TC% components.FSwingBase
 */
package components;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
 
public class FSwingBase extends JPanel {
	
  /**
  * Create the GUI and show it.  For thread safety,
  * this method should be invoked from the
  * event-dispatching thread.
  */
 	private static void createAndShowGUI() {
    //Create and set up the window.
    JFrame frame = new JFrame("FSwingBase");
 	  frame.setLocation(0, 100);
 	  frame.setMinimumSize(new java.awt.Dimension(270, 100));
 	  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //Create and set up the content pane.
    ///JComponent newContentPane = new TableDialogEditDemo();
    JComponent newContentPane = new JPanel();
    newContentPane.setOpaque(true); //content panes must be opaque
    frame.setContentPane(newContentPane);

    //Display the window.
    frame.pack();
    frame.setVisible(true);
 	  frame.setVisible(true);
 	}
 	
 	public static void main(String[] args) {
 	  SwingUtilities.invokeLater(new Runnable() {
 	  	public void run() {
 	  		createAndShowGUI();
 	  	}
 	  });
 	} 	
}