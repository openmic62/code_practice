/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tesi.java.eventmanager.domain;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author mikerocha
 */
public class Person {
    private Long id;
    private int age;
    private String firstname;
    private String lastname;
    
    private Set events = new HashSet();
    private Set emailAddresses = new HashSet();

    public Person() {}

    public Set getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(Set emailaddresses) {
        this.emailAddresses = emailaddresses;
    }

    public Set getEvents() {
        return events;
    }

    public void setEvents(Set events) {
        this.events = events;
    }
    
    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    
}
