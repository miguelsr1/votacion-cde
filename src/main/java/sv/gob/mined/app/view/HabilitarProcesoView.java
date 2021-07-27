/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.PersistenceFacade;
import sv.gob.mined.app.model.dto.EntidadEducativaProcesoDto;
import sv.gob.mined.app.model.paquetes.Municipio;
import sv.gob.mined.app.view.util.VarSession;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class HabilitarProcesoView implements Serializable {

    private String codigoMunicipio;
    private String nombreCe;
    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private PersistenceFacade persistenceFacade;
    @Inject
    private ParametrosSesionView parametrosSesionView;

    private List<Municipio> lstMunicipio = new ArrayList();
    private List<EntidadEducativaProcesoDto> lstEntidades = new ArrayList();

    @PostConstruct
    public void init() {
        lstEntidades = catalogoFacade.findEntidadesByCodigoDepartamento(VarSession.getVariableSession(VarSession.CODIGO_DEPARTAMENTO).toString());

        lstMunicipio = catalogoFacade.findAllMunicipio()
                .stream()
                .filter(mun -> {
                    return mun.getCodigoDepartamento().compareTo(VarSession.getVariableSession(VarSession.CODIGO_DEPARTAMENTO).toString()) == 0;
                })
                .collect(Collectors.toList());
    }

    public List<EntidadEducativaProcesoDto> getLstEntidades() {
        return lstEntidades;
    }

    public List<Municipio> getLstMunicipio() {
        return lstMunicipio;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getNombreCe() {
        return nombreCe;
    }

    public void setNombreCe(String nombreCe) {
        this.nombreCe = nombreCe;
    }

    public void buscar() {
        lstMunicipio = catalogoFacade.findAllMunicipio()
                .stream()
                .filter(mun -> {
                    return (mun.getCodigoDepartamento().compareTo(VarSession.getVariableSession(VarSession.CODIGO_DEPARTAMENTO).toString()) == 0
                            && mun.getCodigoMunicipio().compareTo(codigoMunicipio) == 0 && mun.getNombreMunicipio().toUpperCase().compareTo(nombreCe) == 0);
                })
                .collect(Collectors.toList());
    }

    public void guardarModificacion(EntidadEducativaProcesoDto ce) {
        persistenceFacade.guardarProcesoVotacion(ce.getCodigoEntidad(), parametrosSesionView.getAnho().getIdAnho(), ce.getHabilitado());
        JsfUtil.mensajeInformacion("Centro educativo actualizado");
    }
}
