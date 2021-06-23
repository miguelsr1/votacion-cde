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
import sv.gob.mined.app.model.ParametroVotacion;
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

    public void guardarUsuarioPadre(Long idPerSiges, String nombres, String apellidos, String dui, BigDecimal idProcesoVotacion) {
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

            Asistencia asistencia = new Asistencia();
            asistencia.setFechaLogeo(new Date());
            asistencia.setIdProcesoVotacion(idProcesoVotacion);
            asistencia.setIdUsuario(usuario);
            
            em.persist(asistencia);
        }
    }

    public void guardarDetalleVoto(BigDecimal idParametro, BigInteger idUsuario) {
        DetalleVotaUsuario detalleVotaUsuario = new DetalleVotaUsuario();
        detalleVotaUsuario.setIdParametro(em.find(ParametroVotacion.class, idParametro));
        detalleVotaUsuario.setIdUsuario(em.find(Usuario.class, idUsuario));
        detalleVotaUsuario.setFechaInsercion(new Date());

        em.persist(detalleVotaUsuario);
    }

}
