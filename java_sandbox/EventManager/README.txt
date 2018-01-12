Look in /Users/mikerocha/.bash_profile for sqltool aliases

To start hsqldb in memory
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="org.hsqldb.Server" -Dexec.args="-database.0 file:target/data/tutorial"

To stop hsqldb in memory
ps -A | grep java |grep args=
kill -9 <pid>

To create a new Event
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="storeevent"

To create a new Person
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="storeperson"

To list all the events
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="listevents"

To list all the persons
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="listpersons"

To add person to event: version 1
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="addpersontoevent"

To add person to event: version 2, detached
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="addpersontoevent2"

To add and email to a person
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="addemailtoperson"

--------------------------------------------
Do this to get the "1.3.3. Deploying and testing" to work
--------------------------------------------
1. Start the HSQLDB (the dir below is my Netbeans project directory)
cd ~/NetBeansProjects/code_practice/sandbox/EventManager
mvn exec:java -Dexec.mainClass="org.hsqldb.Server" -Dexec.args="-database.0 file:target/data/tutorial"

2. Build the war file
In Netbeans: right click "EventManager" project -> "Clean and build"

3. Start tomcat (if it isn't already running)
cd ~/dev/apache-tomcat-8.5.6/bin
./startup.sh
lsof -i tcp:8080 # use this to confirm it's running

4. Navigate to tomcat manager-gui in a browser and authenticate
In browser: http://localhost:8080/manager/html
Tomcat Web Application Manager appears (after authentication)

5. Deploy the war file via "Tomcat Web Application Manager" (might have to "undeploy" first)
In browser, in section "WAR file to deploy": click "Choose File"
In dialog, navigate to "~/NetBeansProjects/code_practice/sandbox/EventManager/target"
           select "EventManager-1.0-SNAPSHOT.war"
           click "Open"
In browser, in section "WAR file to deploy": click "Deploy"

6. Run the webapp
In browser: http://localhost:8080/EventManager-1.0-SNAPSHOT/eventmanager?action=store&eventTitle=
