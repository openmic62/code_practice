/*
 * Copyright 2013 (C) RoCo, Inc.

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
 xcopy src\main\resources target\classes\components\resources /e /y 
 date /T
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\components\FResource.java
 java -cp %CLASSPATH%;%SC%;%TC% components.FResource
 */
package components;


import java.awt.Container;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
 
public class FResource extends JFrame {
 	private void setGui() {
 		try {
 			setLocation(100, 100);
 			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 			Container cp = getContentPane();
 			ImageIcon img = new ImageIcon(getClass().getResource("resources/clown.jpg"));
 			//ImageIcon img = new ImageIcon(getClass().getResource("resources/zxing_c128_barcode.png"));
 			cp.add(new JLabel(img));
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
 	
 	public static void main(String[] args) {
 		try {
 			SwingUtilities.invokeAndWait(new Runnable() {
 				public void run() {
 					FResource f = new FResource();
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