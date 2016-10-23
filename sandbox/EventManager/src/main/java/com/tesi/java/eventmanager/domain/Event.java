/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesi.java.eventmanager.domain;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author mikerocha
 */
public class Event {
    private long id;
    
    private String title;
    private Date date;
    
    private Set participants;

    public Event() {}

    public Set getParticipants() {
        return participants;
    }

    public void setParticipants(Set participants) {
        this.participants = participants;
    }
    
    public void addParticipant(Person person) {
        this.getParticipants().add(person);
        person.getEvents().add(this);
    }
    
    public void removeParticipant(Person person) {
        this.getParticipants().remove(person);
        person.getEvents().remove(this);
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }   
}
