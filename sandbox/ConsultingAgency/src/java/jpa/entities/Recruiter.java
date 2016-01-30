/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mike
 */
@Entity
@Table(name = "recruiter")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recruiter.findAll", query = "SELECT r FROM Recruiter r"),
    @NamedQuery(name = "Recruiter.findByRecruiterId", query = "SELECT r FROM Recruiter r WHERE r.recruiterId = :recruiterId"),
    @NamedQuery(name = "Recruiter.findByEmail", query = "SELECT r FROM Recruiter r WHERE r.email = :email"),
    @NamedQuery(name = "Recruiter.findByPassword", query = "SELECT r FROM Recruiter r WHERE r.password = :password")})
public class Recruiter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "recruiter_id")
    private Integer recruiterId;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "recruiterId")
    private Collection<Consultant> consultantCollection;
    @JoinColumns({
        @JoinColumn(name = "client_name", referencedColumnName = "client_name"),
        @JoinColumn(name = "client_department_number", referencedColumnName = "client_department_number")})
    @ManyToOne
    private Client client;

    public Recruiter() {
    }

    public Recruiter(Integer recruiterId) {
        this.recruiterId = recruiterId;
    }

    public Recruiter(Integer recruiterId, String email, String password) {
        this.recruiterId = recruiterId;
        this.email = email;
        this.password = password;
    }

    public Integer getRecruiterId() {
        return recruiterId;
    }

    public void setRecruiterId(Integer recruiterId) {
        this.recruiterId = recruiterId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public Collection<Consultant> getConsultantCollection() {
        return consultantCollection;
    }

    public void setConsultantCollection(Collection<Consultant> consultantCollection) {
        this.consultantCollection = consultantCollection;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (recruiterId != null ? recruiterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recruiter)) {
            return false;
        }
        Recruiter other = (Recruiter) object;
        if ((this.recruiterId == null && other.recruiterId != null) || (this.recruiterId != null && !this.recruiterId.equals(other.recruiterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jpa.entities.Recruiter[ recruiterId=" + recruiterId + " ]";
    }
    
}
