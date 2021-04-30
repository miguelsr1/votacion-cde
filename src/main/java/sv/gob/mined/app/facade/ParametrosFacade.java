/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.facade;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
}
