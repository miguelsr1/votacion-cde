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
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PF;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.ParametrosFacade;
import sv.gob.mined.app.model.Cargo;
import sv.gob.mined.app.model.ParametroVotacion;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class ParametroVotacionView implements Serializable {

    private Boolean deshabilitar = false;

    private Integer[] cargoPropietarioDocente;
    private Integer[] cargoPropietarioPadre;
    private Integer[] cargoPropietarioEstudiante;
    private Integer[] cargoSuplenteDocente;
    private Integer[] cargoSuplentePadre;
    private Integer[] cargoSuplenteEstudiante;

    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private ParametrosFacade parametrosFacade;
    @Inject
    private ParametrosSesionView parametrosSesionView;

    @PostConstruct
    public void init() {
        List<ParametroVotacion> lstParam = parametrosFacade.findParametrosByCodigoEntAndAnho(parametrosSesionView.getCodigoEntidad(), parametrosSesionView.getAnho().getAnho());
        String cpDoc = "";
        String csDoc = "";
        String cpPadre = "";
        String csPadre = "";
        String cpEst = "";
        String csEst = "";

        deshabilitar = (parametrosSesionView.getProcesoVotacion() != null
                && parametrosSesionView.getProcesoVotacion().getHabilitarVotacion() == 1
                && parametrosSesionView.getProcesoVotacion().getHabilitarResultados() == 1);

        for (ParametroVotacion param : lstParam) {
            //deshabilitar = true;
            switch (param.getIdCargo().getIdCargo()) {
                case 1:
                case 2:
                case 3:
                case 4:
                    if (param.getTipoNombramiento().equals("P")) {
                        cpDoc += (cpDoc.isEmpty() ? "" : ",").concat(param.getIdCargo().getIdCargo().toString());
                    } else {
                        csDoc += (csDoc.isEmpty() ? "" : ",").concat(param.getIdCargo().getIdCargo().toString());
                    }
                    break;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                    if (param.getTipoNombramiento().equals("P")) {
                        cpPadre += (cpPadre.isEmpty() ? "" : ",").concat(param.getIdCargo().getIdCargo().toString());
                    } else {
                        csPadre += (csPadre.isEmpty() ? "" : ",").concat(param.getIdCargo().getIdCargo().toString());
                    }
                    break;
                case 11:
                case 12:
                case 13:
                case 14:
                    if (param.getTipoNombramiento().equals("P")) {
                        cpEst += (cpEst.isEmpty() ? "" : ",").concat(param.getIdCargo().getIdCargo().toString());
                    } else {
                        csEst += (csEst.isEmpty() ? "" : ",").concat(param.getIdCargo().getIdCargo().toString());
                    }
                    break;
            }
        }
        if (!cpDoc.isEmpty()) {
            cargoPropietarioDocente = cargarParametros(cpDoc.split(","));
        }
        if (!csDoc.isEmpty()) {
            cargoSuplenteDocente = cargarParametros(csDoc.split(","));
        }
        if (!cpPadre.isEmpty()) {
            cargoPropietarioPadre = cargarParametros(cpPadre.split(","));
        }
        if (!csPadre.isEmpty()) {
            cargoSuplentePadre = cargarParametros(csPadre.split(","));
        }
        if (!cpEst.isEmpty()) {
            cargoPropietarioEstudiante = cargarParametros(cpEst.split(","));
        }
        if (!csEst.isEmpty()) {
            cargoSuplenteEstudiante = cargarParametros(csEst.split(","));
        }
    }

    private Integer[] cargarParametros(String[] valores) {
        Integer[] cargo = new Integer[valores.length];
        for (int i = 0; i < valores.length; i++) {
            cargo[i] = Integer.parseInt(valores[i]);
        }
        return cargo;
    }

    public List<Cargo> getCargos() {
        return catalogoFacade.findAllCargos(parametrosSesionView.getProcesoVotacion().getIdProcesoVotacion());
    }

    public List<SelectItem> getCargo(String idCargos) {
        List<Integer> ids = new ArrayList();
        for (String id : idCargos.split(",")) {
            ids.add(Integer.parseInt(id));
        }
        return catalogoFacade.findCargos(ids);
    }

    public Integer[] getCargoPropietarioDocente() {
        return cargoPropietarioDocente;
    }

    public void setCargoPropietarioDocente(Integer[] cargoPropietarioDocente) {
        this.cargoPropietarioDocente = cargoPropietarioDocente;
    }

    public Integer[] getCargoPropietarioPadre() {
        return cargoPropietarioPadre;
    }

    public void setCargoPropietarioPadre(Integer[] cargoPropietarioPadre) {
        this.cargoPropietarioPadre = cargoPropietarioPadre;
    }

    public Integer[] getCargoSuplenteDocente() {
        return cargoSuplenteDocente;
    }

    public void setCargoSuplenteDocente(Integer[] cargoSuplenteDocente) {
        this.cargoSuplenteDocente = cargoSuplenteDocente;
    }

    public Integer[] getCargoSuplentePadre() {
        return cargoSuplentePadre;
    }

    public void setCargoSuplentePadre(Integer[] cargoSuplentePadre) {
        this.cargoSuplentePadre = cargoSuplentePadre;
    }

    public Integer[] getCargoPropietarioEstudiante() {
        return cargoPropietarioEstudiante;
    }

    public void setCargoPropietarioEstudiante(Integer[] cargoPropietarioEstudiante) {
        this.cargoPropietarioEstudiante = cargoPropietarioEstudiante;
    }

    public Integer[] getCargoSuplenteEstudiante() {
        return cargoSuplenteEstudiante;
    }

    public void setCargoSuplenteEstudiante(Integer[] cargoSuplenteEstudiante) {
        this.cargoSuplenteEstudiante = cargoSuplenteEstudiante;
    }

    public void guardar() {

        if (cargoPropietarioDocente.length > 0 || cargoPropietarioEstudiante.length > 0
                || cargoPropietarioPadre.length > 0 || cargoSuplenteDocente.length > 0
                || cargoSuplenteEstudiante.length > 0 || cargoSuplentePadre.length > 0) {
            parametrosFacade.guardarParametroVotacionCe(parametrosSesionView.getCodigoEntidad(),
                    parametrosSesionView.getProcesoVotacion().getIdProcesoVotacion(),
                    cargoPropietarioDocente, cargoSuplenteDocente,
                    cargoPropietarioPadre, cargoSuplentePadre,
                    cargoPropietarioEstudiante, cargoSuplenteEstudiante);
            PF.current().executeScript("PF('tbvConfig').select(1)");
        } else {
            JsfUtil.mensajeAlerta("Debe de seleccionar al menos un cargo a cubrir en el proceso de votación");
        }
    }

    public Boolean getDeshabilitar() {
        return deshabilitar;
    }

    public void setDeshabilitar(Boolean deshabilitar) {
        this.deshabilitar = deshabilitar;
        parametrosSesionView.init();
    }
}
