/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.model.dto;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *
 * @author misanchez
 */
@Entity
public class EntidadEducativaProcesoDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String codigoEntidad;
    private String nombre;
    private String inicialesModalidad;
    private Short habilitarVotacion;

    @Transient
    private Boolean habilitado;

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInicialesModalidad() {
        return inicialesModalidad;
    }

    public void setInicialesModalidad(String inicialesModalidad) {
        this.inicialesModalidad = inicialesModalidad;
    }

    public Short getHabilitarVotacion() {
        return habilitarVotacion;
    }

    public void setHabilitarVotacion(Short habilitarVotacion) {
        this.habilitarVotacion = habilitarVotacion;
    }

    public Boolean getHabilitado() {
        return habilitarVotacion.compareTo((short) 1) == 0;
    }

    public void setHabilitado(Boolean habilitado) {
        habilitarVotacion = (short) (habilitado ? 1 : 0);
        this.habilitado = habilitado;
    }

}
