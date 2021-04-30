/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.facade;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import sv.gob.mined.app.model.Cargo;
import sv.gob.mined.app.model.ParametroVotacion;

/**
 *
 * @author misanchez
 */
@Stateless
public class ParametrosFacade {

    @PersistenceContext(unitName = "votacionUP")
    private EntityManager em;

    public void guardarParametroVotacionCe(String codigoEntidad, BigInteger idProcesoVotacion, Integer[] lstDocentePro, Integer[] lstDocenteSup, Integer[] lstPadrePro, Integer[] lstPadreSup) {
        create(lstDocentePro, "P", idProcesoVotacion);
        create(lstDocenteSup, "S", idProcesoVotacion);
        create(lstPadrePro, "P", idProcesoVotacion);
        create(lstPadreSup, "S", idProcesoVotacion);
    }

    private void create(Integer[] lst, String tipoNombramiento, BigInteger idProcesoVotacion) {
        for (Integer idCargo : lst) {
            ParametroVotacion pv = new ParametroVotacion();
            pv.setIdCargo(new Cargo(idCargo));
            pv.setIdProcesoVotacion(idProcesoVotacion);
            pv.setTipoNombramiento(tipoNombramiento);

            em.persist(pv);
        }
    }

    public List<ParametroVotacion> findParametrosByCodigoEntAndAnho(String codigoEntidad, Integer idAnho) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ParametroVotacion> q = cb.createQuery(ParametroVotacion.class);
        Root<ParametroVotacion> c = q.from(ParametroVotacion.class);
        ParameterExpression<String> pCodigoEntidad = cb.parameter(String.class);
        ParameterExpression<Integer> pAnho = cb.parameter(Integer.class);

        q.select(c).where(cb.equal(c.get("codigoEntidad"), c));

        TypedQuery<ParametroVotacion> query = em.createQuery(q);
        query.setParameter(pCodigoEntidad, codigoEntidad);
        query.setParameter(pAnho, idAnho);
        
        return query.getResultList();
    }
}
