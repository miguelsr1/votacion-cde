/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.facade;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sv.gob.mined.app.model.Candidato;
import sv.gob.mined.app.model.Cargo;
import sv.gob.mined.app.model.ProcesoVotacion;

/**
 *
 * @author misanchez
 */
@Stateless
public class CatalogoFacade {

    @PersistenceContext(unitName = "votacionPU")
    private EntityManager emVotacion;

    public <T extends Object> T find(Class<T> clase, Object pk) {
        return emVotacion.find(clase, pk);
    }

    public List<Cargo> findAllCargos() {
        CriteriaBuilder cb = emVotacion.getCriteriaBuilder();
        CriteriaQuery<Cargo> q = cb.createQuery(Cargo.class);
        Root<Cargo> c = q.from(Cargo.class);
        q.orderBy(cb.asc(c.get("idCargo")));

        q.select(c);

        TypedQuery<Cargo> query = emVotacion.createQuery(q);
        return query.getResultList();
    }

    public List<Candidato> findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(Integer idAnho, String codigoEntidad, Integer idCargo, Boolean propietario) {
        Query q = emVotacion.createQuery("SELECT c FROM Candidato c WHERE c.idProcesoVotacion.idAnho.idAnho=:pIdAnho and c.idProcesoVotacion.codigoEntidad=:pCodigoEntidad and c.idCargo.idCargo=:pIdCargo and c.tipoNombramiento='" + (propietario ? "P" : "S") + "'", Candidato.class);
        q.setParameter("pIdAnho", idAnho);
        q.setParameter("pCodigoEntidad", codigoEntidad);
        q.setParameter("pIdCargo", idCargo);

        return q.getResultList();
    }

    public List<Candidato> findCandidatosByAnhoAndCodigoEntidad(Integer idAnho, String codigoEntidad) {
        Query q = emVotacion.createQuery("SELECT c FROM Candidato c WHERE c.idProcesoVotacion.idAnho.idAnho=:pIdAnho and c.idProcesoVotacion.codigoEntidad=:pCodigoEntidad ORDER BY c.idCargo.idCargo, c.tipoNombramiento", Candidato.class);
        q.setParameter("pIdAnho", idAnho);
        q.setParameter("pCodigoEntidad", codigoEntidad);

        return q.getResultList();
    }

    public ProcesoVotacion findProceso() {
        return null;
    }

    public ProcesoVotacion findProcesoByAnhoAndCodigoEntidad(String anho, String codigoEntidad) {
        Query q = emVotacion.createQuery("SELECT p FROM ProcesoVotacion p WHERE p.idAnho.anho=:pAnho and p.codigoEntidad=:pCodigoEntidad", ProcesoVotacion.class);
        q.setParameter("pAnho", anho);
        q.setParameter("pCodigoEntidad", codigoEntidad);

        return q.getResultList().isEmpty() ? null : (ProcesoVotacion) q.getResultList().get(0);
    }
}
