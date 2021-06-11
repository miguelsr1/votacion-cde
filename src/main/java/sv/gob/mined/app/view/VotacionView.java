/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class VotacionView implements Serializable {

    private Boolean showCargo = true;
    private Integer idCargo;

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

    public void registrarVoto() {

    }
}
