/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.model.Candidato;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class DetalleVotacionView implements Serializable {

    private Integer idCargo;

    private BigDecimal idCandidato;

    @Inject
    private CatalogoFacade catalogoFacade;

    private List<Candidato> lstCandidatosPropietarios = new ArrayList();
    private List<Candidato> lstCandidatosSuplentes = new ArrayList();

    @PostConstruct
    public void init() {
        lstCandidatosPropietarios = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(1, "10001", Integer.parseInt(JsfUtil.getParametroUrl("idCargo")), true);
        lstCandidatosSuplentes = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(1, "10001", Integer.parseInt(JsfUtil.getParametroUrl("idCargo")), false);
    }

    public List<Candidato> getLstCandidatosPropietarios() {
        return lstCandidatosPropietarios;
    }

    public List<Candidato> getLstCandidatosSuplentes() {
        return lstCandidatosSuplentes;
    }

    public BigDecimal getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(BigDecimal idCandidato) {
        this.idCandidato = idCandidato;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }
}
