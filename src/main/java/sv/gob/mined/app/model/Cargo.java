/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "CARGO")
public class Cargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CARGO")
    private Integer idCargo;
    @Column(name = "DESCRIPCION_CARGO")
    private String descripcionCargo;
    @Column(name = "ACTIVO")
    private Short activo;
    @OneToMany(mappedBy = "idCargo", fetch = FetchType.LAZY)
    private List<ParametroVotacion> parametroVotacionList;

    public Cargo() {
    }

    public Cargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getDescripcionCargo() {
        return descripcionCargo;
    }

    public void setDescripcionCargo(String descripcionCargo) {
        this.descripcionCargo = descripcionCargo;
    }

    public Short getActivo() {
        return activo;
    }

    public void setActivo(Short activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<ParametroVotacion> getParametroVotacionList() {
        return parametroVotacionList;
    }

    public void setParametroVotacionList(List<ParametroVotacion> parametroVotacionList) {
        this.parametroVotacionList = parametroVotacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCargo != null ? idCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cargo)) {
            return false;
        }
        Cargo other = (Cargo) object;
        if ((this.idCargo == null && other.idCargo != null) || (this.idCargo != null && !this.idCargo.equals(other.idCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.app.model.Cargo[ idCargo=" + idCargo + " ]";
    }
    
}
