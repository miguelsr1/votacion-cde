/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.facade.ParametrosFacade;
import sv.gob.mined.app.model.ParametroVotacion;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class VotacionView implements Serializable {

    private Boolean showCargo = true;
    private Boolean showSecretario;
    private Boolean showConsejalDocente;
    private Boolean showTesorero;
    private Boolean showConsejal1;
    private Boolean showConsejal2;
    private Boolean showRepresentante1;
    private Boolean showRepresentante2;
    private Integer idCargo;

    @Inject
    private ParametrosFacade parametrosFacade;
    @Inject
    private ParametrosSesionView parametrosSesionView;

    @PostConstruct
    public void init() {
        List<ParametroVotacion> lstParam = parametrosFacade.findParametrosByCodigoEntAndAnho(parametrosSesionView.getCodigoEntidad(), parametrosSesionView.getAnho().getAnho());

        for (ParametroVotacion param : lstParam) {
            switch (param.getIdCargo().getIdCargo()) {
                case 1:
                    showSecretario = true;
                    break;
                case 2:
                    showConsejalDocente = true;
                    break;
                case 3:
                    showTesorero = true;
                    break;
                case 4:
                    showConsejal1 = true;
                    break;
                case 5:
                    showConsejal2 = true;
                    break;
                case 6:
                    showRepresentante1 = true;
                    break;
                case 7:
                    showRepresentante2 = true;
                    break;
            }
        }
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public Boolean getShowCargo() {
        return showCargo;
    }

    public void setShowCargo(Boolean showCargo) {
        this.showCargo = showCargo;
    }

    public Boolean getShowSecretario() {
        return showSecretario;
    }

    public Boolean getShowConsejalDocente() {
        return showConsejalDocente;
    }

    public Boolean getShowTesorero() {
        return showTesorero;
    }

    public Boolean getShowConsejal1() {
        return showConsejal1;
    }

    public Boolean getShowConsejal2() {
        return showConsejal2;
    }

    public Boolean getShowRepresentante1() {
        return showRepresentante1;
    }

    public Boolean getShowRepresentante2() {
        return showRepresentante2;
    }

    public String validarVotoRealizado(){
        //if(catalogoFacade.isVotoRealizadoByUsuarioAndParametro(BigDecimal.ZERO, BigInteger.ZERO)){
            return "";
        //}
    }
}
