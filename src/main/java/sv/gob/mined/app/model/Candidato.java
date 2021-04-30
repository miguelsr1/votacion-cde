/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "CANDIDATO")
public class Candidato implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_CANDIDATO")
    private BigDecimal idCandidato;
    @Column(name = "ID_PERSONA")
    private BigInteger idPersona;
    @Column(name = "PATH_IMAGEN")
    private String pathImagen;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @Column(name = "USUARIO_INSERCION")
    private Integer usuarioInsercion;
    @Column(name = "ID_CARGO")
    private Integer idCargo;
    @Column(name = "TIPO_NOMBRAMIENTO")
    private String tipoNombramiento;
    @JoinColumn(name = "ID_PROCESO_VOTACION", referencedColumnName = "ID_PROCESO_VOTACION")
    @ManyToOne(fetch = FetchType.LAZY)
    private ProcesoVotacion idProcesoVotacion;

    public Candidato() {
    }

    public Candidato(BigDecimal idCandidato) {
        this.idCandidato = idCandidato;
    }

    public BigDecimal getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(BigDecimal idCandidato) {
        this.idCandidato = idCandidato;
    }

    public BigInteger getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(BigInteger idPersona) {
        this.idPersona = idPersona;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public Integer getUsuarioInsercion() {
        return usuarioInsercion;
    }

    public void setUsuarioInsercion(Integer usuarioInsercion) {
        this.usuarioInsercion = usuarioInsercion;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getTipoNombramiento() {
        return tipoNombramiento;
    }

    public void setTipoNombramiento(String tipoNombramiento) {
        this.tipoNombramiento = tipoNombramiento;
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
        hash += (idCandidato != null ? idCandidato.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Candidato)) {
            return false;
        }
        Candidato other = (Candidato) object;
        if ((this.idCandidato == null && other.idCandidato != null) || (this.idCandidato != null && !this.idCandidato.equals(other.idCandidato))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.app.model.Candidato[ idCandidato=" + idCandidato + " ]";
    }

}
