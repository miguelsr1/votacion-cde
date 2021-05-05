/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.facade;

import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.app.model.Cargo;
import sv.gob.mined.app.model.ParametroVotacion;
import sv.gob.mined.app.model.ProcesoVotacion;

/**
 *
 * @author misanchez
 */
@Stateless
public class ParametrosFacade {

    @PersistenceContext(unitName = "votacionUP")
    private EntityManager em;

    public void guardarParametroVotacionCe(String codigoEntidad, BigDecimal idProcesoVotacion, Integer[] lstDocentePro, Integer[] lstDocenteSup, Integer[] lstPadrePro, Integer[] lstPadreSup) {
        create(lstDocentePro, "P", idProcesoVotacion);
        create(lstDocenteSup, "S", idProcesoVotacion);
        create(lstPadrePro, "P", idProcesoVotacion);
        create(lstPadreSup, "S", idProcesoVotacion);
    }

    private void create(Integer[] lst, String tipoNombramiento, BigDecimal idProcesoVotacion) {
        for (Integer idCargo : lst) {
            ParametroVotacion pv = new ParametroVotacion();
            pv.setIdCargo(new Cargo(idCargo));
            pv.setIdProcesoVotacion(em.find(ProcesoVotacion.class, idProcesoVotacion));
            pv.setTipoNombramiento(tipoNombramiento);

            em.persist(pv);
        }
    }

    public List<ParametroVotacion> findParametrosByCodigoEntAndAnho(String codigoEntidad, Integer idAnho) {
        Query q = em.createQuery("SELECT p FROM ParametroVotacion p WHERE p.idProcesoVotacion.codigoEntidad=:pCodigoEntidad and p.idProcesoVotacion.idAnho.idAnho=:pIdAnho", ParametroVotacion.class);
        q.setParameter("pCodigoEntidad", codigoEntidad);
        q.setParameter("pIdAnho", idAnho);

        return q.getResultList();
    }
}
