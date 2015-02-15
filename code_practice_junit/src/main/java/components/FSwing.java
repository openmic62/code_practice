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
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\components\FSwing.java
 java -cp %CLASSPATH%;%SC%;%TC% components.FSwing
 */
package components;

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