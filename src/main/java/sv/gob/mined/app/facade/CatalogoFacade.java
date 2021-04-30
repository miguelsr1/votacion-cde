/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.facade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sv.gob.mined.app.model.Cargo;

/**
 *
 * @author misanchez
 */
@Stateless
public class CatalogoFacade {

    @PersistenceContext(unitName = "votacionUP")
    private EntityManager em;

    public List<Cargo> findAllCargos() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cargo> q = cb.createQuery(Cargo.class);
        Root<Cargo> c = q.from(Cargo.class);
        q.orderBy(cb.asc(c.get("idCargo")));

        q.select(c);

        TypedQuery<Cargo> query = em.createQuery(q);
        return query.getResultList();
    }

    public List<SelectItem> findCargos(List<Integer> idCargos) {
        List<SelectItem> items = new ArrayList();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Cargo> q = cb.createQuery(Cargo.class);
        Root<Cargo> c = q.from(Cargo.class);
        q.orderBy(cb.asc(c.get("idCargo")));
        q.select(c).where(c.get("idCargo").in(idCargos));

        TypedQuery<Cargo> query = em.createQuery(q);

        query.getResultList().forEach(cargo -> {
            items.add(new SelectItem(cargo.getIdCargo(), cargo.getDescripcionCargo()));
        });
        return items;
    }
}
