/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.model.Candidato;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.PersistenceFacade;
import sv.gob.mined.app.model.Cargo;
import sv.gob.mined.utils.jsf.JsfUtil;

@Named
@ViewScoped
public class CandidatoView implements java.io.Serializable {

    private Integer idCargo;
    private Candidato candidato = new Candidato();

    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private PersistenceFacade persistenceFacade;

    @javax.annotation.PostConstruct
    public void init() {
    }

    public Candidato getCandidato() {
        return candidato;
    }

    public void setCandidato(Candidato candidato) {
        this.candidato = candidato;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public List<Cargo> getLstCargo() {
        return catalogoFacade.findAllCargos();
    }

    public void nuevo() {
        candidato = new Candidato();
    }

    public void guardar() {
        candidato.setIdCargo(new Cargo(idCargo));
        if (candidato.getIdCandidato() == null) {
            persistenceFacade.crear(candidato);
            JsfUtil.mensajeInsert();
        } else {
            candidato = persistenceFacade.modificar(candidato);
            JsfUtil.mensajeUpdate();
        }
    }
}
