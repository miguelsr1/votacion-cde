/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PARAMETRO_VOTACION")
@XmlRootElement
public class ParametroVotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PARAMETRO")
    @GeneratedValue(generator = "SEQ_PARAMETRO_VO", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_PARAMETRO_VO", sequenceName = "SEQ_PARAMETRO_VO", allocationSize = 1, initialValue = 1)
    private BigDecimal idParametro;
    @Column(name = "TIPO_NOMBRAMIENTO")
    private String tipoNombramiento;
    @JoinColumn(name = "ID_CARGO", referencedColumnName = "ID_CARGO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cargo idCargo;
    @JoinColumn(name = "ID_PROCESO_VOTACION", referencedColumnName = "ID_PROCESO_VOTACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProcesoVotacion idProcesoVotacion;

    public ParametroVotacion() {
    }

    public ParametroVotacion(BigDecimal idParametro) {
        this.idParametro = idParametro;
    }

    public BigDecimal getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(BigDecimal idParametro) {
        this.idParametro = idParametro;
    }

    public String getTipoNombramiento() {
        return tipoNombramiento;
    }

    public void setTipoNombramiento(String tipoNombramiento) {
        this.tipoNombramiento = tipoNombramiento;
    }

    public Cargo getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Cargo idCargo) {
        this.idCargo = idCargo;
    }

    public ProcesoVotacion getIdProcesoVotacion() {
        return idProcesoVotacion;
    }

    public void setIdProcesoVotacion(ProcesoVotacion idProcesoVotacion) {
        this.idProcesoVotacion = idProcesoVotacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idParametro != null ? idParametro.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParametroVotacion)) {
            return false;
        }
        ParametroVotacion other = (ParametroVotacion) object;
        if ((this.idParametro == null && other.idParametro != null) || (this.idParametro != null && !this.idParametro.equals(other.idParametro))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.app.model.ParametroVotacion[ idParametro=" + idParametro + " ]";
    }
    
}
