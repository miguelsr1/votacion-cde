/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.model.Cargo;
import sv.gob.mined.app.model.ProcesoVotacion;
import sv.gob.mined.app.model.dto.DistribucionVotacionDto;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class ResultadoVotacionView implements Serializable {

    private Integer idCargo;
    private String nombramiento;
    private ProcesoVotacion procesoVotacion;

    private List<DistribucionVotacionDto> lstDistribucionFinalPorcentaje = new ArrayList();

    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private ParametrosSesionView parametrosSesionView;

    @PostConstruct
    public void init() {
        procesoVotacion = parametrosSesionView.getProcesoVotacion();
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombramiento() {
        return nombramiento;
    }

    public void setNombramiento(String nombramiento) {
        this.nombramiento = nombramiento;
    }

    public List<DistribucionVotacionDto> lstDistribucionFinalPorcentaje(Integer idCargo) {
        lstDistribucionFinalPorcentaje.clear();
        if (idCargo != null) {
            lstDistribucionFinalPorcentaje = catalogoFacade.getDistribucionVotosFinalPorcentaje(procesoVotacion.getIdProcesoVotacion(), idCargo, nombramiento);
        }

        return lstDistribucionFinalPorcentaje;
    }

    public List<Cargo> getLstCargo() {
        return catalogoFacade.findAllCargos(procesoVotacion.getIdProcesoVotacion());
    }

    public void limpiar() {
        lstDistribucionFinalPorcentaje.clear();
        nombramiento = null;
    }

    public void filtrar() {
        lstDistribucionFinalPorcentaje.clear();
        if (idCargo != null) {
            lstDistribucionFinalPorcentaje = catalogoFacade.getDistribucionVotosFinalPorcentaje(procesoVotacion.getIdProcesoVotacion(), idCargo, nombramiento);
        } else {
            JsfUtil.mensajeAlerta("Por favor seleccionar valores válidos");
        }
    }
}
