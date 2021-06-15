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
public class HabilitarVotacionView implements Serializable {

    private Boolean habilitarVotacion;
    private Boolean habilitarResultados;
    private ProcesoVotacion procesoVotacion;

    @Inject
    private PersistenceFacade persistenceFacade;
    @Inject
    private CatalogoFacade catalogoFacade;

    @PostConstruct
    public void init() {
        procesoVotacion = catalogoFacade.findProcesoByAnhoAndCodigoEntidad("2021", "20627");
        habilitarResultados = (procesoVotacion.getHabilitarResultados() == 1);
        habilitarVotacion = (procesoVotacion.getHabilitarVotacion() == 1);
    }

    public ProcesoVotacion getProcesoVotacion() {
        return procesoVotacion;
    }

    public void setProcesoVotacion(ProcesoVotacion procesoVotacion) {
        this.procesoVotacion = procesoVotacion;
    }

    public Boolean getHabilitarVotacion() {
        return habilitarVotacion;
    }

    public void setHabilitarVotacion(Boolean habilitarVotacion) {
        this.habilitarVotacion = habilitarVotacion;
    }

    public Boolean getHabilitarResultados() {
        return habilitarResultados;
    }

    public void setHabilitarResultados(Boolean habilitarResultados) {
        this.habilitarResultados = habilitarResultados;
    }

    public PersistenceFacade getPersistenceFacade() {
        return persistenceFacade;
    }

    public void setPersistenceFacade(PersistenceFacade persistenceFacade) {
        this.persistenceFacade = persistenceFacade;
    }

    public CatalogoFacade getCatalogoFacade() {
        return catalogoFacade;
    }

    public void setCatalogoFacade(CatalogoFacade catalogoFacade) {
        this.catalogoFacade = catalogoFacade;
    }

    public void guardar() {
        procesoVotacion.setHabilitarResultados((short) (habilitarResultados ? 1 : 0));
        procesoVotacion.setHabilitarVotacion((short) (habilitarVotacion ? 1 : 0));

        persistenceFacade.modificar(procesoVotacion);
    }
}
