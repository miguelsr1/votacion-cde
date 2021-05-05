/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.model.Candidato;

/**
 *
 * @author misanchez
 */
@ManagedBean
@ViewScoped
public class VotacionView implements Serializable {

    @Inject
    private CatalogoFacade catalogoFacade;

    private List<Candidato> lstCandidatos = new ArrayList();

    @PostConstruct
    public void init() {
        lstCandidatos = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(1, "10001", 1);
    }

    public List<Candidato> getLstCandidatos() {
        return lstCandidatos;
    }

    public void setLstCandidatos(List<Candidato> lstCandidatos) {
        this.lstCandidatos = lstCandidatos;
    }

}
