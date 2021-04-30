/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.ParametrosFacade;
import sv.gob.mined.app.model.Cargo;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class ParametroVotacionView implements Serializable {

    private Integer[] cargoPropietarioDocente;
    private Integer[] cargoPropietarioPadre;
    private Integer[] cargoSuplenteDocente;
    private Integer[] cargoSuplentePadre;

    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private ParametrosFacade parametrosFacade;

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
    
    public void guardar(){
        parametrosFacade.guardarParametroVotacionCe("10001", BigInteger.ONE, cargoPropietarioDocente, cargoSuplenteDocente, cargoPropietarioPadre, cargoSuplentePadre);        
    }
}
