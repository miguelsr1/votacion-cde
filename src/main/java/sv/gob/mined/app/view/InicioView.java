/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.PersistenceFacade;
import sv.gob.mined.app.model.ProcesoVotacion;

@Named
@ViewScoped
public class InicioView implements Serializable {

    private Boolean usuarioVoto = false;
    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private PersistenceFacade persistenceFacade;
    @Inject
    private ParametrosSesionView parametrosSesionView;

    @PostConstruct
    public void init() {
        usuarioVoto = persistenceFacade.isVotoDeUsuario(parametrosSesionView.getIdUsuario(), parametrosSesionView.getProcesoVotacion().getIdProcesoVotacion());
        parametrosSesionView.calcularTiempoRestante();
    }

    public ProcesoVotacion getProcesoVotacion() {
        return catalogoFacade.findProcesoByAnhoAndCodigoEntidad("2021", parametrosSesionView.getCodigoEntidad());
    }

    public Boolean getUsuarioVoto() {
        return usuarioVoto;
    }

    public void setUsuarioVoto(Boolean usuarioVoto) {
        this.usuarioVoto = usuarioVoto;
    }

    public String detVotacion() {
        if (usuarioVoto) {
            return null;
        } else {
            return "detalleVotacion";
        }
    }
}
