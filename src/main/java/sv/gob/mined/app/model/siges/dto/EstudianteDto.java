/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.model.siges.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author misanchez
 */
@Entity
@XmlRootElement
@SqlResultSetMapping(name = "defaultEstudianteDto",
        entities = @EntityResult(entityClass = EstudianteDto.class))
public class EstudianteDto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String sedCodigo;
    private Long perNie;
    private String perDui;
    private String nombres;
    private String apellidos;
    private Long idPerSiges;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date perFechaNacimiento;

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Long getIdPerSiges() {
        return idPerSiges;
    }

    public void setIdPerSiges(Long idPerSiges) {
        this.idPerSiges = idPerSiges;
    }

    public String getSedCodigo() {
        return sedCodigo;
    }

    public void setSedCodigo(String sedCodigo) {
        this.sedCodigo = sedCodigo;
    }

    public Long getPerNie() {
        return perNie;
    }

    public void setPerNie(Long perNie) {
        this.perNie = perNie;
    }

    public String getPerDui() {
        return perDui;
    }

    public void setPerDui(String perDui) {
        this.perDui = perDui;
    }

    public Date getPerFechaNacimiento() {
        return perFechaNacimiento;
    }

    public void setPerFechaNacimiento(Date perFechaNacimiento) {
        this.perFechaNacimiento = perFechaNacimiento;
    }

}
