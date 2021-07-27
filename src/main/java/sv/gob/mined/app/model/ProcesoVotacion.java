/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "PROCESO_VOTACION")
@XmlRootElement
public class ProcesoVotacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_PROCESO_VOTACION")
    @GeneratedValue(generator = "SEQ_PROCESO_VOTACION", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_PROCESO_VOTACION", sequenceName = "SEQ_PROCESO_VOTACION", initialValue = 1, allocationSize = 1)
    private BigDecimal idProcesoVotacion;
    @Column(name = "CODIGO_ENTIDAD")
    private String codigoEntidad;
    @Column(name = "HABILITAR_VOTACION")
    private Short habilitarVotacion;
    @Column(name = "HABILITAR_RESULTADOS")
    private Short habilitarResultados;
    @Column(name = "HORAS")
    private Integer horas;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @OneToMany(mappedBy = "idProcesoVotacion", fetch = FetchType.LAZY)
    private List<Votacion> votacionList;
    @OneToMany(mappedBy = "idProcesoVotacion", fetch = FetchType.LAZY)
    private List<Asistencia> asistenciaList;
    @OneToMany(mappedBy = "idProcesoVotacion", fetch = FetchType.LAZY)
    private List<Candidato> candidatoList;
    @JoinColumn(name = "ID_ANHO", referencedColumnName = "ID_ANHO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Anho idAnho;
    @OneToMany(mappedBy = "idProcesoVotacion", fetch = FetchType.LAZY)
    private List<ParametroVotacion> parametroVotacionList;
    @OneToMany(mappedBy = "idProcesoVotacion", fetch = FetchType.LAZY)
    private List<DetalleVotaUsuario> detalleVotaUsuarioList;

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

    public Short getHabilitarVotacion() {
        return habilitarVotacion;
    }

    public void setHabilitarVotacion(Short habilitarVotacion) {
        this.habilitarVotacion = habilitarVotacion;
    }

    public Short getHabilitarResultados() {
        return habilitarResultados;
    }

    public void setHabilitarResultados(Short habilitarResultados) {
        this.habilitarResultados = habilitarResultados;
    }

    public Integer getHoras() {
        return horas;
    }

    public void setHoras(Integer horas) {
        this.horas = horas;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    @XmlTransient
    public List<Votacion> getVotacionList() {
        return votacionList;
    }

    public void setVotacionList(List<Votacion> votacionList) {
        this.votacionList = votacionList;
    }

    @XmlTransient
    public List<Asistencia> getAsistenciaList() {
        return asistenciaList;
    }

    public void setAsistenciaList(List<Asistencia> asistenciaList) {
        this.asistenciaList = asistenciaList;
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

    @XmlTransient
    public List<ParametroVotacion> getParametroVotacionList() {
        return parametroVotacionList;
    }

    public void setParametroVotacionList(List<ParametroVotacion> parametroVotacionList) {
        this.parametroVotacionList = parametroVotacionList;
    }

    @XmlTransient
    public List<DetalleVotaUsuario> getDetalleVotaUsuarioList() {
        return detalleVotaUsuarioList;
    }

    public void setDetalleVotaUsuarioList(List<DetalleVotaUsuario> detalleVotaUsuarioList) {
        this.detalleVotaUsuarioList = detalleVotaUsuarioList;
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
