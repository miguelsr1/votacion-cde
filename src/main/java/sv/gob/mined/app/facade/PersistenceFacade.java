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
import sv.gob.mined.app.view.util.VarSession;

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

    public BigDecimal guardarUsuarioPadre(Long idPerSiges, String nombres, String apellidos, String dui, BigDecimal idProcesoVotacion) {
        BigDecimal idUsuario;
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.idPerSiges=:pIdPer", Usuario.class);
        q.setParameter("pIdPer", new BigInteger(idPerSiges.toString()));

        if (q.getResultList().isEmpty()) {
            Usuario usuario = new Usuario();
            usuario.setIdPerSiges(new BigInteger(idPerSiges.toString()));
            usuario.setTipoUsuario(VarSession.USUARIO_PAD);
            usuario.setNombres(nombres.replaceAll("  ", " ").trim());
            usuario.setApellidos(apellidos.replaceAll("  ", " ").trim());
            usuario.setDui(dui);
            em.persist(usuario);

            idUsuario = usuario.getIdUsuario();

            Asistencia asistencia = new Asistencia();
            asistencia.setFechaLogeo(new Date());
            asistencia.setIdProcesoVotacion(idProcesoVotacion);
            asistencia.setIdUsuario(usuario);

            em.persist(asistencia);
        } else {
            idUsuario = ((Usuario) q.getResultList().get(0)).getIdUsuario();
        }
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
