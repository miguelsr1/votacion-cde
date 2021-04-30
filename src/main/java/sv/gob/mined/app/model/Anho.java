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
@Table(name = "ANHO")
public class Anho implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ANHO")
    private Integer idAnho;
    @Column(name = "ANHO")
    private String anho;
    @OneToMany(mappedBy = "idAnho", fetch = FetchType.LAZY)
    private List<ProcesoVotacion> procesoVotacionList;

    public Anho() {
    }

    public Anho(Integer idAnho) {
        this.idAnho = idAnho;
    }

    public Integer getIdAnho() {
        return idAnho;
    }

    public void setIdAnho(Integer idAnho) {
        this.idAnho = idAnho;
    }

    public String getAnho() {
        return anho;
    }

    public void setAnho(String anho) {
        this.anho = anho;
    }

    @XmlTransient
    public List<ProcesoVotacion> getProcesoVotacionList() {
        return procesoVotacionList;
    }

    public void setProcesoVotacionList(List<ProcesoVotacion> procesoVotacionList) {
        this.procesoVotacionList = procesoVotacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAnho != null ? idAnho.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Anho)) {
            return false;
        }
        Anho other = (Anho) object;
        if ((this.idAnho == null && other.idAnho != null) || (this.idAnho != null && !this.idAnho.equals(other.idAnho))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sv.gob.mined.app.model.Anho[ idAnho=" + idAnho + " ]";
    }
    
}
