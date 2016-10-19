Look in /Users/mikerocha/.bash_profile for sqltool aliases

To start hsqldb in memory
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="org.hsqldb.Server" -Dexec.args="-database.0 file:target/data/tutorial"

To create a new Event
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="storeevent"

To create a new Person
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="storperson"

To list all the events
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="listevents"

To list all the persons
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="listpersons"

To add person to event
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="addpersontoevent"
