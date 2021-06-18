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
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.ParametrosFacade;
import sv.gob.mined.app.model.Cargo;
import sv.gob.mined.app.model.ParametroVotacion;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class ParametroVotacionView implements Serializable {

    private Boolean deshabilitar = false;

    private Integer[] cargoPropietarioDocente;
    private Integer[] cargoPropietarioPadre;
    private Integer[] cargoSuplenteDocente;
    private Integer[] cargoSuplentePadre;

    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private ParametrosFacade parametrosFacade;
    @Inject
    private ParametrosSesionView parametrosSesionView;

    @PostConstruct
    public void init() {
        List<ParametroVotacion> lstParam = parametrosFacade.findParametrosByCodigoEntAndAnho(parametrosSesionView.getCodigoEntidad(), parametrosSesionView.getAnho().getAnho());
        String cpd = "";
        String csd = "";
        String cpp = "";
        String csp = "";

        for (ParametroVotacion param : lstParam) {
            deshabilitar = true;
            switch (param.getIdCargo().getIdCargo()) {
                case 1:
                case 2:
                    if (param.getTipoNombramiento().equals("P")) {
                        cpd += (cpd.isEmpty() ? "" : ",").concat(param.getIdCargo().getIdCargo().toString());
                    } else {
                        csd += (csd.isEmpty() ? "" : ",").concat(param.getIdCargo().getIdCargo().toString());
                    }
                    break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    if (param.getTipoNombramiento().equals("P")) {
                        cpp += (cpp.isEmpty() ? "" : ",").concat(param.getIdCargo().getIdCargo().toString());
                    } else {
                        csp += (csp.isEmpty() ? "" : ",").concat(param.getIdCargo().getIdCargo().toString());
                    }
                    break;
            }
        }
        if (!cpd.isEmpty()) {
            cargoPropietarioDocente = cargarParametros(cpd.split(","));
        }
        if (!csd.isEmpty()) {
            cargoSuplenteDocente = cargarParametros(csd.split(","));
        }
        if (!cpp.isEmpty()) {
            cargoPropietarioPadre = cargarParametros(cpp.split(","));
        }
        if (!csp.isEmpty()) {
            cargoSuplentePadre = cargarParametros(csp.split(","));
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
        return catalogoFacade.findAllCargos();
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

    public void guardar() {
        parametrosFacade.guardarParametroVotacionCe(parametrosSesionView.getCodigoEntidad(),
                parametrosSesionView.getProcesoVotacion().getIdProcesoVotacion(),
                cargoPropietarioDocente, cargoSuplenteDocente, cargoPropietarioPadre, cargoSuplentePadre);
    }

    public Boolean getDeshabilitar() {
        return deshabilitar;
    }
}
