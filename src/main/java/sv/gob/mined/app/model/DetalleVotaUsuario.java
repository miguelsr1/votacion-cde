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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@Table(name = "DETALLE_VOTA_USUARIO")
@XmlRootElement
public class DetalleVotaUsuario implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "ID_DETALLE_VOTO")
    @GeneratedValue(generator = "SEQ_DET_VOTO", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_DET_VOTO", sequenceName = "SEQ_DET_VOTO", allocationSize = 1, initialValue = 1)
    private BigDecimal idDetalleVoto;
    @Column(name = "FECHA_INSERCION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaInsercion;
    @JoinColumn(name = "ID_PARAMETRO", referencedColumnName = "ID_PARAMETRO")
    @ManyToOne(fetch = FetchType.LAZY)
    private ParametroVotacion idParametro;
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario idUsuario;

    public DetalleVotaUsuario() {
    }

    public DetalleVotaUsuario(BigDecimal idDetalleVoto) {
        this.idDetalleVoto = idDetalleVoto;
    }

    public BigDecimal getIdDetalleVoto() {
        return idDetalleVoto;
    }

    public void setIdDetalleVoto(BigDecimal idDetalleVoto) {
        this.idDetalleVoto = idDetalleVoto;
    }

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }

    public ParametroVotacion getIdParametro() {
        return idParametro;
    }

    public void setIdParametro(ParametroVotacion idParametro) {
        this.idParametro = idParametro;
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
        hash += (idDetalleVoto != null ? idDetalleVoto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DetalleVotaUsuario)) {
            return false;
        }
        DetalleVotaUsuario other = (DetalleVotaUsuario) object;
        if ((this.idDetalleVoto == null && other.idDetalleVoto != null) || (this.idDetalleVoto != null && !this.idDetalleVoto.equals(other.idDetalleVoto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.app.model.DetalleVotaUsuario[ idDetalleVoto=" + idDetalleVoto + " ]";
    }

}
