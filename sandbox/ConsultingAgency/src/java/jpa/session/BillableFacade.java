/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpa.session;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jpa.entities.Billable;

/**
 *
 * @author Mike
 */
@Stateless
public class BillableFacade extends AbstractFacade<Billable> {

    @PersistenceContext(unitName = "ConsultingAgencyPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BillableFacade() {
        super(Billable.class);
    }
    
}
