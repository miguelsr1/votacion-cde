/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.model.Candidato;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.PersistenceFacade;
import sv.gob.mined.app.model.Cargo;
import sv.gob.mined.app.model.ProcesoVotacion;
import sv.gob.mined.utils.jsf.JsfUtil;

@Named
@ViewScoped
public class CandidatoView implements java.io.Serializable {

    private Integer idCargo;
    private String nombramiento;
    private Candidato candidato = new Candidato();
    private ProcesoVotacion procesoVotacion;

    private List<Candidato> lstCandidato = new ArrayList();

    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private PersistenceFacade persistenceFacade;
    @Inject
    private ParametrosSesionView parametrosSesionView;

    @PostConstruct
    public void init() {
        lstCandidato = catalogoFacade.findCandidatosByAnhoAndCodigoEntidad(1, parametrosSesionView.getCodigoEntidad());
        procesoVotacion = parametrosSesionView.getProcesoVotacion();
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

    public String getNombramiento() {
        return nombramiento;
    }

    public void setNombramiento(String nombramiento) {
        this.nombramiento = nombramiento;
    }

    public List<Cargo> getLstCargo() {
        return catalogoFacade.findAllCargos();
    }

    public List<Candidato> getLstCandidato() {
        return lstCandidato;
    }

    public void nuevo() {
        candidato = new Candidato();
    }

    public void guardar() {
        candidato.setIdCargo(catalogoFacade.find(Cargo.class, idCargo));
        candidato.setTipoNombramiento(nombramiento);
        
        if (candidato.getIdCandidato() == null) {
            candidato.setIdProcesoVotacion(procesoVotacion);
            
            persistenceFacade.crear(candidato);
            
            lstCandidato.add(candidato);
            JsfUtil.mensajeInsert();
        } else {
            candidato = persistenceFacade.modificar(candidato);
            JsfUtil.mensajeUpdate();
        }
    }

    public void cancelarEdicion() {
        candidato = null;
        idCargo = null;
        nombramiento = null;
    }

    public void editar() {
        idCargo = candidato.getIdCargo().getIdCargo();
        nombramiento = candidato.getTipoNombramiento();
    }
   
}
