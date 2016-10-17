Look in /Users/mikerocha/.bash_profile for sqltool aliases

To start hsqldb in memory
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="org.hsqldb.Server" -Dexec.args="-database.0 file:target/data/tutorial"

To create a new event
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="store"

To list all the events
mikerocha@Mikes-MBP:~/NetBeansProjects/code_practice/sandbox/EventManager
$ mvn exec:java -Dexec.mainClass="com.tesi.java.eventmanager.EventManager" -Dexec.args="list"