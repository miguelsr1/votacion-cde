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
import org.primefaces.PrimeFaces;
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
    private BigDecimal idCandidatoPro;
    private BigDecimal idCandidatoSup;

    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private ParametrosSesionView parametrosSesionView;

    private List<Candidato> lstCandidatosPropietarios = new ArrayList();
    private List<Candidato> lstCandidatosSuplentes = new ArrayList();

    @PostConstruct
    public void init() {
        lstCandidatosPropietarios = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), Integer.parseInt(JsfUtil.getParametroUrl("idCargo")), true);
        lstCandidatosSuplentes = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), Integer.parseInt(JsfUtil.getParametroUrl("idCargo")), false);
    }

    public BigDecimal getIdCandidatoPro() {
        return idCandidatoPro;
    }

    public void setIdCandidatoPro(BigDecimal idCandidatoPro) {
        this.idCandidatoPro = idCandidatoPro;
    }

    public BigDecimal getIdCandidatoSup() {
        return idCandidatoSup;
    }

    public void setIdCandidatoSup(BigDecimal idCandidatoSup) {
        this.idCandidatoSup = idCandidatoSup;
    }

    public List<Candidato> getLstCandidatosPropietarios() {
        return lstCandidatosPropietarios;
    }

    public List<Candidato> getLstCandidatosSuplentes() {
        return lstCandidatosSuplentes;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public void guardar() {
        if (idCandidatoPro != null && idCandidatoSup != null) {

            
            PrimeFaces.current().executeScript("PF('dlgVoto').show()");
        } else {
            JsfUtil.mensajeAlerta("Debe de seleccionar un candidato.");
        }
    }
}
