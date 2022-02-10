/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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
import sv.gob.mined.app.model.Director;
import sv.gob.mined.app.model.ProcesoVotacion;
import sv.gob.mined.app.model.Usuario;
import sv.gob.mined.app.model.Asistencia;
import sv.gob.mined.app.model.VwCatalogoEntidadEducativa;
import sv.gob.mined.app.model.dto.DistribucionVotacionDto;
import sv.gob.mined.app.model.dto.EntidadEducativaProcesoDto;
import sv.gob.mined.app.model.paquetes.Municipio;

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

    public List<Cargo> findAllCargos(BigDecimal idProcesoVotacion) {
        /*CriteriaBuilder cb = emVotacion.getCriteriaBuilder();
        CriteriaQuery<Cargo> q = cb.createQuery(Cargo.class);
        Root<Cargo> c = q.from(Cargo.class);
        q.orderBy(cb.asc(c.get("idCargo")));

        q.select(c);

        TypedQuery<Cargo> query = emVotacion.createQuery(q);*/
        Query query = emVotacion.createQuery("SELECT distinct p.idCargo FROM ParametroVotacion p WHERE p.idProcesoVotacion.idProcesoVotacion=:pIdProceso ORDER BY p.idCargo.idCargo", Cargo.class);
        query.setParameter("pIdProceso", idProcesoVotacion);
        return query.getResultList();
    }

    public List<Candidato> findCandidatosByAnhoAndCodigoEntidadAndCargo(Integer idAnho, String codigoEntidad, Integer idCargo, BigDecimal idProcesoVotacion) {
        Query q = emVotacion.createQuery("SELECT c FROM Candidato c WHERE c.idProcesoVotacion.idAnho.idAnho=:pIdAnho and c.idProcesoVotacion.codigoEntidad=:pCodigoEntidad and c.idCargo.idCargo=:pIdCargo and c.idProcesoVotacion.idProcesoVotacion=:pIdPro", Candidato.class);
        q.setParameter("pIdAnho", idAnho);
        q.setParameter("pCodigoEntidad", codigoEntidad);
        q.setParameter("pIdCargo", idCargo);
        q.setParameter("pIdPro", idProcesoVotacion);

        return q.getResultList();
    }

    public List<Candidato> findCandidatosByAnhoAndCodigoEntidad(BigDecimal idProcesoVotacion, String codigoEntidad, Integer idCargo, String nombramiento) {
        String partialWhere = "";
        if (idCargo != null) {
            partialWhere = "and c.idCargo.idCargo=:pIdCargo ";
        }
        if (nombramiento != null) {
            partialWhere += "and c.tipoNombramiento=:pNombramiento ";
        }
        Query q = emVotacion.createQuery("SELECT c FROM Candidato c WHERE c.idProcesoVotacion.idProcesoVotacion=:pIdProcesoV and c.idProcesoVotacion.codigoEntidad=:pCodigoEntidad " + partialWhere + " ORDER BY c.idCargo.idCargo, c.tipoNombramiento", Candidato.class);
        q.setParameter("pIdProcesoV", idProcesoVotacion);
        q.setParameter("pCodigoEntidad", codigoEntidad);
        if (idCargo != null) {
            q.setParameter("pIdCargo", idCargo);
        }
        if (nombramiento != null) {
            q.setParameter("pNombramiento", nombramiento);
        }

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

    public List<Anho> findAllAnho() {
        Query q = emVotacion.createQuery("SELECT a FROM Anho a ORDER BY a.idAnho", Anho.class);
        return q.getResultList();
    }

    public List<Usuario> findUsuariosAsistentes(BigDecimal idProcesoVotacion) {
        Query q = emVotacion.createQuery("SELECT a.idUsuario FROM Asistencia a WHERE a.idProcesoVotacion.idProcesoVotacion=:pIdProcesoVota ORDER BY a.fechaLogeo", Asistencia.class);
        q.setParameter("pIdProcesoVota", idProcesoVotacion);
        return q.getResultList();
    }

    public List<DistribucionVotacionDto> getDistribucionVotos(BigDecimal idProcesoVotacion) {
        Query q = emVotacion.createNamedQuery("Votacion.DistribucionVotacionDto", DistribucionVotacionDto.class);
        q.setParameter(1, idProcesoVotacion);
        return q.getResultList();
    }

    public List<DistribucionVotacionDto> getDistribucionVotosFinal(BigDecimal idProcesoVotacion) {
        Query q = emVotacion.createNamedQuery("Votacion.DistribucionVotacionFinalDto", DistribucionVotacionDto.class);
        q.setParameter(1, idProcesoVotacion);
        return q.getResultList();
    }

    public List<DistribucionVotacionDto> getDistribucionVotosFinalPorcentaje(BigDecimal idProcesoVotacion, Integer idCargo, String tipoNombramiento) {
        Query q = emVotacion.createNamedQuery("Votacion.DistribucionVotacionFinalPorcentajeDto", DistribucionVotacionDto.class);
        q.setParameter(1, idProcesoVotacion);
        q.setParameter(2, idCargo);
        q.setParameter(3, tipoNombramiento);
        return q.getResultList();
    }

    public List<EntidadEducativaProcesoDto> findEntidadesByCodigoDepartamento(String codigoDepartamento) {
        Query q = emVotacion.createNamedQuery("Votacion.EntidadEducativaProcesoDto", EntidadEducativaProcesoDto.class);
        q.setParameter(1, codigoDepartamento);

        return q.getResultList();
    }

    public List<Municipio> findAllMunicipio() {
        Query q = emVotacion.createQuery("SELECT m FROM Municipio m ORDER BY m.idMunicipio", Municipio.class);
        return q.getResultList();
    }

    public List<ProcesoVotacion> findProcesoVotacionByAnhoAndCodigoEntidad(Integer idAnho, String codigoEntidad) {
        Query q = emVotacion.createQuery("SELECT p FROM ProcesoVotacion p WHERE p.idAnho.idAnho=:pIdAnho AND p.codigoEntidad=:pCodigoEntidad", ProcesoVotacion.class);
        q.setParameter("pIdAnho", idAnho);
        q.setParameter("pCodigoEntidad", codigoEntidad);
        return q.getResultList();
    }

    public Long getCantidadAsistentes(BigDecimal idProcesoVotacion, String... tipoUsuarios) {
        Query q = emVotacion.createQuery("select count(distinct a.idAsistencia) from Asistencia a where a.idUsuario.tipoUsuario in :pTiposUsuario and a.idProcesoVotacion.idProcesoVotacion=:pIdProcesoVotacion");
        q.setParameter("pTiposUsuario", Arrays.asList(tipoUsuarios));
        q.setParameter("pIdProcesoVotacion", idProcesoVotacion);

        return q.getResultList().isEmpty() ? 0l : (Long) q.getSingleResult();
    }

    public Long getCantidadVotos(BigDecimal idProcesoVotacion, String... tipoUsuarios) {
        Query q = emVotacion.createQuery("select count(distinct d.idDetalleVoto) from DetalleVotaUsuario d where d.idUsuario.tipoUsuario in :pTiposUsuario and d.idProcesoVotacion.idProcesoVotacion = :pIdProcesoVotacion");
        q.setParameter("pTiposUsuario", Arrays.asList(tipoUsuarios));
        q.setParameter("pIdProcesoVotacion", idProcesoVotacion);

        return q.getResultList().isEmpty() ? 0l : (Long) q.getSingleResult();
    }
}
