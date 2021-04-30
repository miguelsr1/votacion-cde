/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PROCESO_VOTACION")
public class ProcesoVotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PROCESO_VOTACION")
    private BigDecimal idProcesoVotacion;
    @Column(name = "CODIGO_ENTIDAD")
    private String codigoEntidad;
    @OneToMany(mappedBy = "idProcesoVotacion", fetch = FetchType.LAZY)
    private List<Candidato> candidatoList;
    @JoinColumn(name = "ID_ANHO", referencedColumnName = "ID_ANHO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Anho idAnho;

    public ProcesoVotacion() {
    }

    public ProcesoVotacion(BigDecimal idProcesoVotacion) {
        this.idProcesoVotacion = idProcesoVotacion;
    }

    public BigDecimal getIdProcesoVotacion() {
        return idProcesoVotacion;
    }

    public void setIdProcesoVotacion(BigDecimal idProcesoVotacion) {
        this.idProcesoVotacion = idProcesoVotacion;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    @XmlTransient
    public List<Candidato> getCandidatoList() {
        return candidatoList;
    }

    public void setCandidatoList(List<Candidato> candidatoList) {
        this.candidatoList = candidatoList;
    }

    public Anho getIdAnho() {
        return idAnho;
    }

    public void setIdAnho(Anho idAnho) {
        this.idAnho = idAnho;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProcesoVotacion != null ? idProcesoVotacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProcesoVotacion)) {
            return false;
        }
        ProcesoVotacion other = (ProcesoVotacion) object;
        if ((this.idProcesoVotacion == null && other.idProcesoVotacion != null) || (this.idProcesoVotacion != null && !this.idProcesoVotacion.equals(other.idProcesoVotacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.app.model.ProcesoVotacion[ idProcesoVotacion=" + idProcesoVotacion + " ]";
    }

}
