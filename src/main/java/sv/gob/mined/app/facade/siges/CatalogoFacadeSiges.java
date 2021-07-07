/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.facade.siges;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sv.gob.mined.app.model.siges.dto.EstudianteDto;

/**
 *
 * @author misanchez
 */
@Stateless
public class CatalogoFacadeSiges {

    @PersistenceContext(unitName = "sigesPU")
    private EntityManager emSiges;

    public EstudianteDto validarCredenciales(Long nie, String dui, String codigoEntidad) {
        Query q = emSiges.createNamedQuery("Votacion.EstudianteDto", EstudianteDto.class);
        q.setParameter(1, nie);
        q.setParameter(2, codigoEntidad);
        q.setParameter(3, dui);

        return q.getResultList().isEmpty() ? null : (EstudianteDto) q.getResultList().get(0);
    }
    
    public EstudianteDto validarCredencialesEstudiante(String correoEstudiante) {
        Query q = emSiges.createNamedQuery("Votacion.EstudiantePerDto", EstudianteDto.class);
        q.setParameter(1, correoEstudiante);

        return q.getResultList().isEmpty() ? null : (EstudianteDto) q.getResultList().get(0);
    }
}
