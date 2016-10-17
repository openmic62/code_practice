/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesi.java.eventmanager;

import com.tesi.java.eventmanager.domain.Event;
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
        
        if (args[0].equals("store")) {
            mgr.createAndStoreEvent("My Event", new Date());
        } else if (args[0].equals("list")) {
            List events = mgr.listEvents();
            for (Object e : events) {
                Event event = (Event) e;
                System.out.println("Event: "
                    + event.getTitle()
                    + " Time: "
                    + event.getDate());
            }
        }
        
        HibernateUtil.getSessionFactory().close();
    }
    
    public void createAndStoreEvent(String title, Date theDate) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();     
        session.beginTransaction();
        
        Event theEvent = new Event();
        theEvent.setTitle(title);
        theEvent.setDate(theDate);
        session.save(theEvent);
        
        session.getTransaction().commit();        
    }
    
    public List listEvents() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from Event").list();
        session.getTransaction().commit();
        return result;
    }
    
}
