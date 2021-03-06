/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.facade;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.app.model.Anho;
import sv.gob.mined.app.model.Asistencia;
import sv.gob.mined.app.model.DetalleVotaUsuario;
import sv.gob.mined.app.model.ProcesoVotacion;
import sv.gob.mined.app.model.Usuario;

/**
 *
 * @author misanchez
 */
@Stateless
public class PersistenceFacade {

    @PersistenceContext(unitName = "votacionPU")
    private EntityManager em;

    public void crear(Object obj) {
        em.persist(obj);
    }

    public <T extends Object> T modificar(T entity) {
        return em.merge(entity);
    }

    public BigDecimal guardarUsuarioPerSiges(Long idPerSiges, String nombres, String apellidos, String dui, String correo, BigDecimal idProcesoVotacion, String tipoUsuario, String codigoEntidad) {
        BigDecimal idUsuario;
        Usuario usuario;
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.idPerSiges=:pIdPer", Usuario.class);
        q.setParameter("pIdPer", new BigInteger(idPerSiges.toString()));

        if (q.getResultList().isEmpty()) {
            usuario = new Usuario();
            usuario.setIdPerSiges(new BigInteger(idPerSiges.toString()));
            usuario.setTipoUsuario(tipoUsuario);
            usuario.setNombres(nombres.replaceAll("  ", " ").trim());
            usuario.setApellidos(apellidos.replaceAll("  ", " ").trim());
            usuario.setDui(dui);
            usuario.setCuentaCorreo(correo);
            usuario.setCodigoEntidad(codigoEntidad);

            em.persist(usuario);

            idUsuario = usuario.getIdUsuario();
        } else {
            usuario = ((Usuario) q.getResultList().get(0));
            idUsuario = usuario.getIdUsuario();
        }

        guardarAsistencia(idProcesoVotacion, usuario);

        return idUsuario;
    }

    public void guardarAsistencia(BigDecimal idProcesoVotacion, Usuario usuario) {
        Query q = em.createQuery("SELECT a FROM Asistencia a WHERE a.idProcesoVotacion.idProcesoVotacion=:pIdProceso and a.idUsuario=:pIdUsuario", Asistencia.class);
        q.setParameter("pIdProceso", idProcesoVotacion);
        q.setParameter("pIdUsuario", usuario);

        if (q.getResultList().isEmpty()) {
            Asistencia asistencia = new Asistencia();
            asistencia.setFechaLogeo(new Date());
            asistencia.setIdProcesoVotacion(em.find(ProcesoVotacion.class, idProcesoVotacion));
            asistencia.setIdUsuario(usuario);

            em.persist(asistencia);
        }
    }

    public void guardarDetalleVoto(BigDecimal idUsuario, ProcesoVotacion procesoVotacion) {
        DetalleVotaUsuario detalleVotaUsuario = new DetalleVotaUsuario();
        detalleVotaUsuario.setIdProcesoVotacion(procesoVotacion);
        detalleVotaUsuario.setIdUsuario(em.find(Usuario.class, idUsuario));
        detalleVotaUsuario.setFechaInsercion(new Date());

        em.persist(detalleVotaUsuario);
    }

    public Boolean isVotoDeUsuario(BigDecimal idUsuario, BigDecimal idProcesoVotacion) {
        Query q = em.createQuery("SELECT d FROM DetalleVotaUsuario d WHERE d.idProcesoVotacion.idProcesoVotacion=:pIdProcesoVotacion and d.idUsuario.idUsuario=:pIdUsuario", DetalleVotaUsuario.class);
        q.setParameter("pIdProcesoVotacion", idProcesoVotacion);
        q.setParameter("pIdUsuario", idUsuario);
        return !q.getResultList().isEmpty();
    }

    public void guardarProcesoVotacion(String codigoEntidad, Integer idAnho, boolean value, String sector) {
        Query q = em.createQuery("SELECT p FROM ProcesoVotacion p WHERE p.codigoEntidad=:pCodEnt and p.idAnho.idAnho=:pIdAnho and p.horas is null", ProcesoVotacion.class);
        q.setParameter("pCodEnt", codigoEntidad);
        q.setParameter("pIdAnho", idAnho);
        if (q.getResultList().isEmpty()) {
            ProcesoVotacion procesoVotacion = new ProcesoVotacion();
            procesoVotacion.setCodigoEntidad(codigoEntidad);
            procesoVotacion.setSector(sector);
            procesoVotacion.setFechaInsercion(new Date());
            procesoVotacion.setHabilitarResultados((short) (value ? 1 : 0));
            procesoVotacion.setHabilitarVotacion((short) (value ? 1 : 0));
            procesoVotacion.setIdAnho(em.find(Anho.class, idAnho));
            procesoVotacion.setEstado("N");

            em.persist(procesoVotacion);
        } else {
            ProcesoVotacion procesoVotacion = (ProcesoVotacion) q.getResultList().get(0);
            procesoVotacion.setHabilitarResultados((short) (value ? 1 : 0));
            procesoVotacion.setHabilitarVotacion((short) (value ? 1 : 0));
            procesoVotacion.setEstado(value ? "E" : "N");
            em.merge(procesoVotacion);
        }
    }

    public boolean isProcesoSinIniciar(String codigoEntidad, Integer idAnho) {
        Query q = em.createQuery("SELECT p FROM ProcesoVotacion p WHERE p.codigoEntidad=:pCodEnt and p.idAnho.idAnho=:pIdAnho and p.horas is null", ProcesoVotacion.class);
        q.setParameter("pCodEnt", codigoEntidad);
        q.setParameter("pIdAnho", idAnho);
        return !q.getResultList().isEmpty();
    }
}
