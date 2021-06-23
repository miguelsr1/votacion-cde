/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.PersistenceFacade;
import sv.gob.mined.app.model.ProcesoVotacion;
import sv.gob.mined.app.model.Anho;
import sv.gob.mined.app.model.dto.VwCatalogoEntidadEducativa;
import sv.gob.mined.app.view.util.VarSession;

@Named
@SessionScoped
public class ParametrosSesionView implements Serializable {

    private BigDecimal idUsuario;
    private Anho anho;
    private ProcesoVotacion procesoVotacion;
    private VwCatalogoEntidadEducativa entidadEducativa;

    @Inject
    private CatalogoFacade catalogoFacade;

    @Inject
    private PersistenceFacade persistenceFacade;

    @PostConstruct
    public void init() {
        anho = catalogoFacade.findAnhoActivo();
        procesoVotacion = catalogoFacade.findProcesoByAnhoAndCodigoEntidad("2021", getCodigoEntidad());
        entidadEducativa = catalogoFacade.findEntidadEducativaByCodigo(getCodigoEntidad());

        idUsuario = persistenceFacade.guardarUsuarioPadre(
                ((BigDecimal) VarSession.getVariableSession(VarSession.ID_USUARIO_SIGES)).longValue(),
                VarSession.getVariableSession("nombres").toString(),
                VarSession.getVariableSession("apellidos").toString(),
                VarSession.getVariableSession("dui").toString(),
                procesoVotacion.getIdProcesoVotacion());
    }

    public String getTipoUsuario() {
        return VarSession.getVariableSession(VarSession.TIPO_USUARIO).toString();
    }

    public Boolean getTipoUsuarioVotante() {
        switch (VarSession.TIPO_USUARIO) {
            case "D":
            case "E":
            case "P":
                return true;
            default:
                return false;
        }
    }

    public String getCodigoEntidad() {
        return VarSession.getVariableSession(VarSession.CODIGO_ENTIDAD).toString();
    }

    public Anho getAnho() {
        return anho;
    }

    public ProcesoVotacion getProcesoVotacion() {
        return procesoVotacion;
    }

    public String getNombreUsuario() {
        if (VarSession.isVariableSession("nombreUsuario")) {
            return VarSession.getVariableSession("nombreUsuario").toString();
        } else {
            return "";
        }
    }

    public String getCentroEducativo() {
        return entidadEducativa.getCodigoEntidad().concat(" - ").concat(entidadEducativa.getNombre());
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

}
