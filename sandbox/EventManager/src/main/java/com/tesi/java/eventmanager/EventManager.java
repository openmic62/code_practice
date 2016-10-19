/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesi.java.eventmanager;

import com.tesi.java.eventmanager.domain.Event;
import com.tesi.java.eventmanager.domain.Person;
import com.tesi.java.eventmanager.util.HibernateUtil;
import java.util.Date;
import java.util.List;
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
        } else if (args[0].equals("list")) {
            List events = mgr.listEvents();
            for (Object e : events) {
                Event event = (Event) e;
                System.out.println("Event: "
                    + event.getTitle()
                    + " Time: "
                    + event.getDate());
            }
        } else if (args[0].equals("addpersontoevent")) {
            Long eventId = mgr.createAndStoreEvent("Stevie Nicks", new Date());
            Long personId = mgr.createAndStorePerson("Conrad", "Rocha", 18);
            mgr.addPersonToEvent(eventId, personId);
            System.out.println("Added person " + personId + " to event " + eventId);
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
    
    public void addPersonToEvent(Long eventId, Long personId) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Person aPerson = (Person) session.load(Person.class, personId);
        Event anEvent = (Event) session.load(Event.class, eventId);
        aPerson.getEvents().add(anEvent);                
        
        session.getTransaction().commit();
    }
    
}
