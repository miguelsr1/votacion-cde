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
import sv.gob.mined.app.facade.ParametrosFacade;
import sv.gob.mined.app.model.Candidato;
import sv.gob.mined.app.model.ParametroVotacion;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class DetalleVotacionView implements Serializable {

    private Boolean showSecretario = false;
    private Boolean showConsejalDoc = false;
    private Boolean showTesorero = false;
    private Boolean showConcejal1 = false;
    private Boolean showConcejal2 = false;
    private Boolean showRepresentante1 = false;
    private Boolean showRepresentante2 = false;
    
    private Integer idCargo;
    private BigDecimal idCandidatoPro;
    private BigDecimal idCandidatoSup;

    private List<Candidato> lstPropietarioSecretario = new ArrayList();
    private List<Candidato> lstSuplenteSecretario = new ArrayList();
    private List<Candidato> lstPropietarioConsejalDoc = new ArrayList();
    private List<Candidato> lstSuplenteConsejalDoc = new ArrayList();

    private List<Candidato> lstPropietarioTesorero = new ArrayList();
    private List<Candidato> lstSuplenteTesorero = new ArrayList();
    private List<Candidato> lstPropietarioConsejal1 = new ArrayList();
    private List<Candidato> lstSuplenteConsejal1 = new ArrayList();
    private List<Candidato> lstPropietarioConsejal2 = new ArrayList();
    private List<Candidato> lstSuplenteConsejal2 = new ArrayList();
    private List<Candidato> lstPropietarioRepresentante1 = new ArrayList();
    private List<Candidato> lstSuplenteRepresentante1 = new ArrayList();
    private List<Candidato> lstPropietarioRepresentante2 = new ArrayList();
    private List<Candidato> lstSuplenteRepresentante2 = new ArrayList();

    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private ParametrosSesionView parametrosSesionView;
    @Inject
    private ParametrosFacade parametrosFacade;

    @PostConstruct
    public void init() {
        List<ParametroVotacion> lstParam = parametrosFacade.findParametrosByCodigoEntAndAnho(parametrosSesionView.getCodigoEntidad(), parametrosSesionView.getAnho().getAnho());

        for (ParametroVotacion param : lstParam) {
            switch (param.getIdCargo().getIdCargo()) {
                case 1:
                    showSecretario = true;
                    lstPropietarioSecretario = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 1, true);
                    lstSuplenteSecretario = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 1, false);
                    break;
                case 2:
                    showConsejalDoc = true;
                    lstPropietarioConsejalDoc = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 2, true);
                    lstSuplenteConsejalDoc = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 2, false);
                    break;
                case 3:
                    showTesorero = true;
                    lstPropietarioTesorero = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 3, true);
                    lstSuplenteTesorero = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 3, false);
                    break;
                case 4:
                    showConcejal1 = true;
                    lstPropietarioConsejal1 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 4, true);
                    lstSuplenteConsejal1 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 4, false);
                    break;
                case 5:
                    showConcejal2 = true;
                    lstPropietarioConsejal2 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 5, true);
                    lstSuplenteConsejal2 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 5, false);
                    break;
                case 6:
                    showRepresentante1 = true;
                    lstPropietarioRepresentante1 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 6, true);
                    lstSuplenteRepresentante1 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 6, false);
                    break;
                case 7:
                    showRepresentante2 = true;
                    lstPropietarioRepresentante2 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 7, true);
                    lstSuplenteRepresentante2 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 7, false);
                    break;
            }
        }
    }

    public Boolean getShowSecretario() {
        return showSecretario;
    }

    public Boolean getShowConsejalDoc() {
        return showConsejalDoc;
    }

    public Boolean getShowTesorero() {
        return showTesorero;
    }

    public Boolean getShowConcejal1() {
        return showConcejal1;
    }

    public Boolean getShowConcejal2() {
        return showConcejal2;
    }

    public Boolean getShowRepresentante1() {
        return showRepresentante1;
    }

    public Boolean getShowRepresentante2() {
        return showRepresentante2;
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

    public List<Candidato> getLstPropietarioSecretario() {
        return lstPropietarioSecretario;
    }

    public List<Candidato> getLstSuplenteSecretario() {
        return lstSuplenteSecretario;
    }

    public List<Candidato> getLstPropietarioConsejalDoc() {
        return lstPropietarioConsejalDoc;
    }

    public List<Candidato> getLstSuplenteConsejalDoc() {
        return lstSuplenteConsejalDoc;
    }

    public List<Candidato> getLstPropietarioTesorero() {
        return lstPropietarioTesorero;
    }

    public List<Candidato> getLstSuplenteTesorero() {
        return lstSuplenteTesorero;
    }

    public List<Candidato> getLstPropietarioConsejal1() {
        return lstPropietarioConsejal1;
    }

    public List<Candidato> getLstSuplenteConsejal1() {
        return lstSuplenteConsejal1;
    }

    public List<Candidato> getLstPropietarioConsejal2() {
        return lstPropietarioConsejal2;
    }

    public List<Candidato> getLstSuplenteConsejal2() {
        return lstSuplenteConsejal2;
    }

    public List<Candidato> getLstPropietarioRepresentante1() {
        return lstPropietarioRepresentante1;
    }

    public List<Candidato> getLstSuplenteRepresentante1() {
        return lstSuplenteRepresentante1;
    }

    public List<Candidato> getLstPropietarioRepresentante2() {
        return lstPropietarioRepresentante2;
    }

    public List<Candidato> getLstSuplenteRepresentante2() {
        return lstSuplenteRepresentante2;
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
