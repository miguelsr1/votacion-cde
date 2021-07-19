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

        Asistencia asistencia = new Asistencia();
        asistencia.setFechaLogeo(new Date());
        asistencia.setIdProcesoVotacion(em.find(ProcesoVotacion.class, idProcesoVotacion));
        asistencia.setIdUsuario(usuario);

        em.persist(asistencia);

        return idUsuario;
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
}
