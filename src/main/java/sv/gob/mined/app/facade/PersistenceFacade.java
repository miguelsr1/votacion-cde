/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author misanchez
 */
@Stateless
public class PersistenceFacade {

    @PersistenceContext(unitName = "votacionUP")
    private EntityManager em;

    public void crear(Object obj) {
        em.persist(obj);
    }

    public <T extends Object> T modificar(T entity) {
        return em.merge(entity);
    }

}
