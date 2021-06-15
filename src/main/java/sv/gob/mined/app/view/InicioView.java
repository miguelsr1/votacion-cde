/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.model.ProcesoVotacion;

@Named
@ViewScoped
public class InicioView implements Serializable {

    @Inject
    private CatalogoFacade catalogoFacade;
    
    public ProcesoVotacion getProcesoVotacion() {
        return catalogoFacade.findProcesoByAnhoAndCodigoEntidad("2021", "20627");
    }
}
