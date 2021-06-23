/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.facade;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sv.gob.mined.app.model.Anho;
import sv.gob.mined.app.model.Candidato;
import sv.gob.mined.app.model.Cargo;
import sv.gob.mined.app.model.DetalleVotaUsuario;
import sv.gob.mined.app.model.Director;
import sv.gob.mined.app.model.ProcesoVotacion;
import sv.gob.mined.app.model.Usuario;
import sv.gob.mined.app.model.dto.VwCatalogoEntidadEducativa;

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

    public ProcesoVotacion findProcesoByAnhoAndCodigoEntidad(String anho, String codigoEntidad) {
        Query q = emVotacion.createQuery("SELECT p FROM ProcesoVotacion p WHERE p.idAnho.anho=:pAnho and p.codigoEntidad=:pCodigoEntidad", ProcesoVotacion.class);
        q.setParameter("pAnho", anho);
        q.setParameter("pCodigoEntidad", codigoEntidad);

        return q.getResultList().isEmpty() ? null : (ProcesoVotacion) q.getResultList().get(0);
    }

    public Usuario getsUsuarioRegistrado(String correo) {
        Query q = emVotacion.createQuery("SELECT u FROM Usuario u WHERE u.cuentaCorreo=:pCorreo", Usuario.class);
        q.setParameter("pCorreo", correo);
        return q.getResultList().isEmpty() ? null : (Usuario) q.getResultList().get(0);
    }

    public VwCatalogoEntidadEducativa findEntidadEducativaByCodigo(String codigoEntidad) {
        Query q = emVotacion.createQuery("SELECT v FROM VwCatalogoEntidadEducativa v WHERE v.codigoEntidad=:pCodEnt", VwCatalogoEntidadEducativa.class);
        q.setParameter("pCodEnt", codigoEntidad);

        return (VwCatalogoEntidadEducativa) q.getSingleResult();
    }

    public List<SelectItem> findCargos(List<Integer> idCargos) {
        List<SelectItem> items = new ArrayList();

        CriteriaBuilder cb = emVotacion.getCriteriaBuilder();
        CriteriaQuery<Cargo> q = cb.createQuery(Cargo.class);
        Root<Cargo> c = q.from(Cargo.class);
        q.orderBy(cb.asc(c.get("idCargo")));
        q.select(c).where(c.get("idCargo").in(idCargos));

        TypedQuery<Cargo> query = emVotacion.createQuery(q);

        query.getResultList().forEach(cargo -> {
            items.add(new SelectItem(cargo.getIdCargo(), cargo.getDescripcionCargo()));
        });
        return items;
    }

    public String getCodigoEntidadByCorreoDirector(String correo) {
        Query q = emVotacion.createQuery("SELECT d FROM Director d WHERE d.correoElectronico=:pCorreo", Director.class);
        q.setParameter("pCorreo", correo);

        if (q.getResultList().isEmpty()) {
            return null;
        } else {
            return ((Director) q.getResultList().get(0)).getCodigoEntidad();
        }
    }

    public Anho findAnhoActivo() {
        Query q = emVotacion.createQuery("SELECT a FROM Anho a WHERE a.activo=1 ", Anho.class);
        return (Anho) q.getResultList().get(0);
    }

    public Boolean isVotoRealizadoByUsuarioAndParametro(BigDecimal idParametro, BigInteger idUsuario) {
        Query q = emVotacion.createQuery("SELECT d FROM DetalleVotaUsuario d WHERE d.idParametro.idParametro=:idParam and d.idUsuario.idUsuario=:idUsu", DetalleVotaUsuario.class);
        q.setParameter("idParam", idParametro);
        q.setParameter("idUsu", idUsuario);

        return !q.getResultList().isEmpty();
    }
}
