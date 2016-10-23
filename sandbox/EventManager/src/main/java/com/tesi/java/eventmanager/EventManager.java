/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesi.java.eventmanager;

import com.tesi.java.eventmanager.domain.Event;
import com.tesi.java.eventmanager.domain.Person;
import com.tesi.java.eventmanager.util.HibernateUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author mikerocha
 */
public class EventManager {

    public static void main(String[] args) {

        EventManager mgr = new EventManager();

        if (args[0].equals("storeevent")) {
            mgr.createAndStoreEvent("My Event", new Date());
        } else if (args[0].equals("storeperson")) {
            mgr.createAndStorePerson("Mike", "Rocha", 54);
        } else if (args[0].equals("listevents")) {
            List events = mgr.listEvents();
            for (Object e : events) {
                Event event = (Event) e;
                System.out.println("Event: "
                        + event.getTitle()
                        + ", Time: "
                        + event.getDate());
            }
        } else if (args[0].equals("listpersons")) {
            List persons = mgr.listPersons();
            for (Object p : persons) {
                Person person = (Person) p;
                System.out.println("First: "
                        + person.getFirstname()
                        + ", Last: "
                        + person.getLastname()
                        + ", Age: "
                        + person.getAge());
            }
        } else if (args[0].equals("addpersontoevent")) {
            Long eventId = mgr.createAndStoreEvent("Stevie Nicks", new Date());
            Long personId = mgr.createAndStorePerson("Conrad", "Rocha", 18);
            mgr.addPersonToEvent(eventId, personId);
            System.out.println("Added person " + personId + " to event " + eventId);
        } else if (args[0].equals("addpersontoevent2")) {
            String string = "July 4, 2016";
            DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            Date date = new Date();
            try {date = format.parse(string);} catch (Exception e) {}

            Long eventId = mgr.createAndStoreEvent("Steely Dan", date);
            Long personId = mgr.createAndStorePerson("Katherine", "Zeta-Jones", 29);
            mgr.addPersonToEvent(eventId, personId);
            System.out.println("Added person " + personId + " to event " + eventId);
        } else if (args[0].equals("addemailtoperson")) {
            Long personId = mgr.createAndStorePerson("Sean", "Connery", 67);
            String emailaddress = "openmic62@gmail.com";
            mgr.addEmailToPerson(personId, emailaddress);
            System.out.println("Added email " + emailaddress + " to person " + personId);
        }

        HibernateUtil.getSessionFactory().close();
    }

    public Long createAndStoreEvent(String title, Date theDate) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);
        session.save(theEvent);

        session.getTransaction().commit();

        return theEvent.getId();
    }

    public Long createAndStorePerson(String firstname, String lastname, int age) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Person aPerson = new Person();
        aPerson.setAge(age);
        aPerson.setLastname(lastname);
        aPerson.setFirstname(firstname);
        session.save(aPerson);

        session.getTransaction().commit();

        return aPerson.getId();
    }

    public List listEvents() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from Event").list();
        session.getTransaction().commit();
        return result;
    }

    public List listPersons() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from Person").list();
        session.getTransaction().commit();
        return result;
    }

    public void addPersonToEvent(Long eventId, Long personId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Person aPerson = (Person) session.load(Person.class, personId);
        Event anEvent = (Event) session.load(Event.class, eventId);
        aPerson.addToEvent(anEvent);

        session.getTransaction().commit();
    }

    public void addPersonToEvent2(Long eventId, Long personId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        Person aPerson = (Person) session
                .createQuery("select p from Person p left join fetch p.events where p.id = :pid")
                .setParameter("pid", personId)
                .uniqueResult(); // Eager fetch the collection so we can use it detached
        Event anEvent = (Event) session.load(Event.class, eventId);

        session.getTransaction().commit();

        // End first session of work
        aPerson.addToEvent(anEvent); // aPerson (and its collection) is detached

        // Begin second unit of work
        Session session2 = HibernateUtil.getSessionFactory().getCurrentSession();
        session2.beginTransaction();
        session2.update(aPerson); // Reattachment of aPerson

        session.getTransaction().commit();
    }
    
    public void addEmailToPerson(Long personId, String emailaddress) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Person aPerson = (Person) session.load(Person.class, personId);
        // adding to the emailAddress collection might trigger a lazy load of the collection
        aPerson.getEmailAddresses().add(emailaddress);
        
        session.getTransaction().commit();
    }

}
