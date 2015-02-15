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
 javac -cp %CLASSPATH%;%SC% -d %SC% %SD%\components\FConcurrent.java
 java -cp %CLASSPATH%;%SC%;%TC% components.FConcurrent
 */
package components;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Random;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

/**
 * Class <code>FConcurrent</code> embodies Mike Rocha's work through
 * the learning curve for Java GUI applications. By creating this working
 * example, he gained skill in the following areas:<br>
 *
 * <ul>
 *   <li>creating a standalone app. with a GUI</li>
 *   <li>extending a class (<code>Frame</code>)</li>
 *   <li>implementing an interface (<code>ActionListener</code>)</li>
 *   <li>overriding a low-level event handler method (<code>processWindowEvent</code>)
 *       in order to get the GUI to exit gracefully onClick in the title bar "X"</li>
 *   <li>chaining constructors (from general to specific)</li>
 *   <li>creating a nested (inner) class (<code>ButtonWorker</code>), showing use
 *       of a composition association</li>
 *   <li>proper use of the Event Dispatch Thread (EDT)</li>
 *   <li>proper use of worker threads to maintain GUI responsiveness</li>
 *   <li>using private helper methods</li>
 *   <li>establishing an instance of a source file that follows suggested
 *       ordering conventions, e.g. comment header, then package, then imports, etc.</li>
 * </ul>
 */
public class FConcurrent extends Frame implements ActionListener {
	/* Class implementation comment:
	 * I started with a goal to use only AWT components. However, I needed to gain
	 * confidence with mutlithreading in a GUI context. So I opted to use <code>SwingWorker</code>
	 * and <code>JProgressBar</code> to facilitate that learning.
	 */
	
	//----------------------------------------------------------------------------
	// public interface section
	//----------------------------------------------------------------------------
	/* public interface section comment */
	
	//---- Constant declarations
	
	//---- Class variable declarations
	
	//---- Instance variable declarations
	
	//---- Constructors
	/**
	 * This explicit, no-arg, contructor creates an instance of <code>FConcurrent</code>
	 * with a default <code>Frame</code> title of "FConcurrent".
	 */
 	public FConcurrent() {
 		this("FConcurrent");
 	}
 	
	/**
	 * This explicit, one-arg, contructor creates an instance of <code>FConcurrent</code>
	 * with a <code>Frame</code> title of <code>frameTitle.
	 * @param frameTitle the title you want to appear in the <code>Frame</code>'s title bar.
	 */
 	public FConcurrent(String frameTitle) {
 		super(frameTitle);
 		
 		// set up label and progressbar constraints for the GridBagLayout
 		progressBarGBC = new GridBagConstraints();
 		progressBarGBC.gridwidth = GridBagConstraints.REMAINDER;
 		progressLabelGBC = new GridBagConstraints();
 		progressLabelGBC.weightx = 1.0;
 		
 		// create the task status area in the GUI center region
 		progressPanel = new Panel(new GridBagLayout());
 		progressPanel.setBackground(Color.green);
 		progressScroller = new ScrollPane(ScrollPane.SCROLLBARS_AS_NEEDED);
 		progressScroller.add(this.progressPanel);
 		add(progressScroller, BorderLayout.CENTER);
 		
 		// create the buttons that invoke the tasks	
 		add(makeButton("North"), BorderLayout.NORTH);
 		add(makeButton("South"), BorderLayout.SOUTH);
 		add(makeButton("East"), BorderLayout.EAST);
 		add(makeButton("West"), BorderLayout.WEST);
 	}
	
	//---- Methods
	
	//---- Nested interfaces
	
	//---- Nested classes
	
	//----------------------------------------------------------------------------
	// package interface section
	//----------------------------------------------------------------------------
	/* package interface section comment */
	
	//---- Constant declarations
	
	//---- Class variable declarations
	
	//---- Instance variable declarations
	GridBagConstraints progressBarGBC;
	GridBagConstraints progressLabelGBC;
	ScrollPane progressScroller;
	Panel progressPanel;
	
	//---- Constructors
	
	//---- Methods
	/**
	* The entry point for this class that provides a starting point for AWT applications.<p>
	* Notes:<br>
	* <ul>
	* <li>Sets a default location, minimum size, and tile for the frame.</li>
	* <li>Handles only the <code>WindowEvent.WINDOW_CLOSING</code> event so that the user 
	*     can close the window using the "X" at the right of the title bar. This capability
	*     provided by overriding method <code>processWinodwEvents()</code> rather than with 
	*     an event listenter or adapter subclass.</li>
	* <li>Uses <code>SwingUtilities.invokeAndWait()</code> to spawn the AWT event dispatch thread.</li>
	* </ul>
	* @param args the standard array of <code>String</code> objects for method <code>main</code>
	*/
 	public static void main(String[] args) {
 		try {
 			SwingUtilities.invokeAndWait(new Runnable() {
 				public void run() {
 					FConcurrent f = new FConcurrent("AWT Concurrent Application");
 					f.setGui();
 					f.pack();
 					f.setVisible(true);
 				}
 			});
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}

	/**
	 * Implements the <code>ActionListener</code> interface.
	 */
 	public void actionPerformed(ActionEvent ae) {
 		Button source = (Button)ae.getSource();
 		
 		String sourceLabel = source.getLabel();
 		Label pl = new Label(sourceLabel + " task: ");
 		progressPanel.add(pl, progressLabelGBC);
 		
 		JProgressBar pb = new JProgressBar();
 		pb.setBorderPainted(true);
 		pb.setStringPainted(true);
 		pb.setValue(0);
 		progressPanel.add(pb, progressBarGBC);
 		
 		ButtonWorker bw = new ButtonWorker(source, pl, pb);
 		
 		source.setLabel("Working..." + bw.getIterations());
 		source.setBackground(Color.red);
 		source.setEnabled(false);
 		pack();
 		
 		bw.execute();
 	}
	
	//---- Nested interfaces
	
	//---- Nested classes
	
	//----------------------------------------------------------------------------
	// protected interface section
	//----------------------------------------------------------------------------
	/* protected interface section comment */
	
	//---- Constant declarations
	
	//---- Class variable declarations
	
	//---- Instance variable declarations
	
	//---- Constructors
	
	//---- Methods
	/**
	 * overrides the <code>java.awt.Window.processEvent</code> method.
	 */
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
		 	this.dispose();
		 	System.exit(0);
		}
		super.processWindowEvent(e);
	}
	
	//---- Nested interfaces
	
	//---- Nested classes
	
	//----------------------------------------------------------------------------
	// private interface section
	//----------------------------------------------------------------------------
	/* private interface section comment */
	
	//---- Constant declarations
	
	//---- Class variable declarations
	
	//---- Instance variable declarations
	
	//---- Constructors
	
	//---- Methods
	/**
	 * This helper method consolidates general GUI setup tasks in one place.
	 */
 	private void setGui() {
 		try {
 			setLocation(100, 100);
 			setMinimumSize(new Dimension(350, 150));
 			enableEvents(AWTEvent.WINDOW_EVENT_MASK);
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 	}
 	
 	/**
 	 * Creates a new button, sets its action command, and adds a listener.
 	 * @param label a label that goes on the button
 	 * @return a reference to the button created
 	 */
 	private Button makeButton(String label) {
 		Button b = new Button(label);
 		b.setActionCommand(label.toLowerCase());
 		b.addActionListener(this);
 		return b;
 	}

	//---- Nested interfaces
	
	//---- Nested classes
 	
	private class ButtonWorker extends SwingWorker<Boolean, Integer> {
		/**
		 * A reference to the JProgressBar that will show work progress
		 */
		private JProgressBar buttonJPB;
		
		/**
		 * A reference to the Label for this progressbar
		 */
		private Label buttonJPBLabel;
		
		/**
		 * A reference to the button for which we will do work
		 */
		private Button buttonSource;
		
		/**
		 * Holds the original background color of our button. We keep it here so
		 * that we can restore it after we're done working.
		 */
		private Color buttonOriginalBG;
		
		/**
		 * Holds the original label of our button. We keep it here so
		 * that we can restore it after we're done working.
		 */
		private String buttonOriginalLabel;

		/**
		 * A random number generator for simulating differnt length tasks.
		 */
		private Random random;

		/**
		 * The number of iteration in this task.
		 */
		private final int iterations;

		/**
		 * Exlpicit, no-arg constructor
		 */
		ButtonWorker(Button b, Label l, JProgressBar pb) {
			super();
			this.buttonSource = b;
			this.buttonOriginalBG = b.getBackground();
			this.buttonOriginalLabel = b.getLabel();
			this.buttonJPBLabel = l;
			this.buttonJPB = pb;
			
			// set up a random nature for this task
			this.random = new Random();
			this.iterations = random.nextInt(128);
			this.buttonJPB.setMinimum(0);
			this.buttonJPB.setMaximum(iterations);
		}
		
		int getIterations() {
			return this.iterations;
		}
		
		/**
		 * Overrides the abstract declaration in <code>SwingWorker</code> super class.
		 */
		protected Boolean doInBackground() {
			for (int i=1; i<iterations; i++) {
			 	publish(i);
			 	try {
			 		///System.out.println("doInBackground():-->" + Thread.currentThread().toString() + "<--");
			 		Thread.sleep(random.nextInt(600));
			 	} catch (InterruptedException ie) {
			 		System.out.println("doInBackground():ie.toString()-->" + ie.toString() + "<--");
			 	}
			}
			if (!isCancelled()) {
			 	return true;
			} else {
				return false;
			}
		} // end doInBackground()
		
		/**
		 * Overrides process() in <code>SwingWorker</code> and implements our button update functionality.
		 */
		protected void process(List<Integer> chunks) {
			int progressValue = chunks.get(chunks.size() - 1);
			///String bLabel = buttonSource.getLabel();
			///buttonSource.setLabel(bLabel.substring(0,bLabel.length()-1) + progressValue);
			buttonJPB.setValue(progressValue);
			repaint();
		}
		
		/**
		 * Overrides done() in <code>SwingWorker</code> and implements our button functionality.
		 */
		protected void done() {
			progressPanel.remove(buttonJPB);
			progressPanel.remove(buttonJPBLabel);
			progressPanel.repaint();
			buttonSource.setBackground(buttonOriginalBG);
			buttonSource.setLabel(buttonOriginalLabel);
			buttonSource.setEnabled(true);
			pack();
		}
	} // end private class ButtonWorker
}