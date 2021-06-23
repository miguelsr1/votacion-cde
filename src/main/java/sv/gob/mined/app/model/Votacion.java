/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "VOTACION")
public class Votacion implements Serializable {

    @JoinColumn(name = "ID_PROCESO_VOTACION", referencedColumnName = "ID_PROCESO_VOTACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProcesoVotacion idProcesoVotacion;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_VOTACION")
    @GeneratedValue(generator = "SEQ_VOTACION", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "SEQ_VOTACION", name = "SEQ_VOTACION", initialValue = 1, allocationSize = 1)
    private BigDecimal idVotacion;
    @Basic(optional = false)
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @JoinColumn(name = "ID_CANDIDATO", referencedColumnName = "ID_CANDIDATO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Candidato idCandidato;

    public Votacion() {
    }

    public Votacion(BigDecimal idVotacion) {
        this.idVotacion = idVotacion;
    }

    public Votacion(BigDecimal idVotacion, Date fechaInsercion) {
        this.idVotacion = idVotacion;
        this.fechaInsercion = fechaInsercion;
    }

    public BigDecimal getIdVotacion() {
        return idVotacion;
    }

    public void setIdVotacion(BigDecimal idVotacion) {
        this.idVotacion = idVotacion;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public Candidato getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Candidato idCandidato) {
        this.idCandidato = idCandidato;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVotacion != null ? idVotacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Votacion)) {
            return false;
        }
        Votacion other = (Votacion) object;
        if ((this.idVotacion == null && other.idVotacion != null) || (this.idVotacion != null && !this.idVotacion.equals(other.idVotacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.app.model.Votacion[ idVotacion=" + idVotacion + " ]";
    }

    public ProcesoVotacion getIdProcesoVotacion() {
        return idProcesoVotacion;
    }

    public void setIdProcesoVotacion(ProcesoVotacion idProcesoVotacion) {
        this.idProcesoVotacion = idProcesoVotacion;
    }
    
}
