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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "ASISTENCIA")
@XmlRootElement
public class Asistencia implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ASISTENCIA") 
    @GeneratedValue(generator = "SEQ_ASISTENCIA", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "SEQ_ASISTENCIA", name = "SEQ_ASISTENCIA", initialValue = 1, allocationSize = 1)
    private BigDecimal idAsistencia;
    @Column(name = "ID_PROCESO_VOTACION")
    private BigDecimal idProcesoVotacion;
    @Column(name = "FECHA_LOGEO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaLogeo;
    @JoinColumn(name = "ID_ASISTENCIA", referencedColumnName = "ID_PROCESO_VOTACION", insertable = false, updatable = false)
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    private ProcesoVotacion procesoVotacion;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario idUsuario;

    public Asistencia() {
    }

    public Asistencia(BigDecimal idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public BigDecimal getIdAsistencia() {
        return idAsistencia;
    }

    public void setIdAsistencia(BigDecimal idAsistencia) {
        this.idAsistencia = idAsistencia;
    }

    public BigDecimal getIdProcesoVotacion() {
        return idProcesoVotacion;
    }

    public void setIdProcesoVotacion(BigDecimal idProcesoVotacion) {
        this.idProcesoVotacion = idProcesoVotacion;
    }

    public Date getFechaLogeo() {
        return fechaLogeo;
    }

    public void setFechaLogeo(Date fechaLogeo) {
        this.fechaLogeo = fechaLogeo;
    }

    public ProcesoVotacion getProcesoVotacion() {
        return procesoVotacion;
    }

    public void setProcesoVotacion(ProcesoVotacion procesoVotacion) {
        this.procesoVotacion = procesoVotacion;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsistencia != null ? idAsistencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Asistencia)) {
            return false;
        }
        Asistencia other = (Asistencia) object;
        if ((this.idAsistencia == null && other.idAsistencia != null) || (this.idAsistencia != null && !this.idAsistencia.equals(other.idAsistencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.app.model.Asistencia[ idAsistencia=" + idAsistencia + " ]";
    }
    
}
