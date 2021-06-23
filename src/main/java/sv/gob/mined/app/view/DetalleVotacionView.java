/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.ParametrosFacade;
import sv.gob.mined.app.facade.PersistenceFacade;
import sv.gob.mined.app.model.Candidato;
import sv.gob.mined.app.model.ParametroVotacion;
import sv.gob.mined.app.model.ProcesoVotacion;
import sv.gob.mined.app.model.Votacion;
import sv.gob.mined.app.view.util.VarSession;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class DetalleVotacionView implements Serializable {

    private Boolean showSecretario = false;
    private Boolean showConsejalDoc = false;
    private Boolean showTesorero = false;
    private Boolean showConcejal1 = false;
    private Boolean showConcejal2 = false;
    private Boolean showRepresentante1 = false;
    private Boolean showRepresentante2 = false;

    private Integer idCargo;
    private BigDecimal idPro1;
    private BigDecimal idSup1;
    private BigDecimal idPro2;
    private BigDecimal idSup2;
    private BigDecimal idPro3;
    private BigDecimal idSup3;
    private BigDecimal idPro4;
    private BigDecimal idSup4;
    private BigDecimal idPro5;
    private BigDecimal idSup5;
    private BigDecimal idPro6;
    private BigDecimal idSup6;
    private BigDecimal idPro7;
    private BigDecimal idSup7;

    private ProcesoVotacion procesoVotacion;
    private List<Candidato> lstPropietarioSecretario = new ArrayList();
    private List<Candidato> lstSuplenteSecretario = new ArrayList();
    private List<Candidato> lstPropietarioConsejalDoc = new ArrayList();
    private List<Candidato> lstSuplenteConsejalDoc = new ArrayList();

    private List<Candidato> lstPropietarioTesorero = new ArrayList();
    private List<Candidato> lstSuplenteTesorero = new ArrayList();
    private List<Candidato> lstPropietarioConsejal1 = new ArrayList();
    private List<Candidato> lstSuplenteConsejal1 = new ArrayList();
    private List<Candidato> lstPropietarioConsejal2 = new ArrayList();
    private List<Candidato> lstSuplenteConsejal2 = new ArrayList();
    private List<Candidato> lstPropietarioRepresentante1 = new ArrayList();
    private List<Candidato> lstSuplenteRepresentante1 = new ArrayList();
    private List<Candidato> lstPropietarioRepresentante2 = new ArrayList();
    private List<Candidato> lstSuplenteRepresentante2 = new ArrayList();

    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private ParametrosSesionView parametrosSesionView;
    @Inject
    private ParametrosFacade parametrosFacade;
    @Inject
    private PersistenceFacade persistenceFacade;

    @PostConstruct
    public void init() {
        List<ParametroVotacion> lstParam = parametrosFacade.findParametrosByCodigoEntAndAnho(parametrosSesionView.getCodigoEntidad(), parametrosSesionView.getAnho().getAnho());
        procesoVotacion = parametrosSesionView.getProcesoVotacion();

        for (ParametroVotacion param : lstParam) {
            switch (param.getIdCargo().getIdCargo()) {
                case 1:
                    showSecretario = true;
                    lstPropietarioSecretario = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 1, true);
                    lstSuplenteSecretario = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 1, false);
                    break;
                case 2:
                    showConsejalDoc = true;
                    lstPropietarioConsejalDoc = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 2, true);
                    lstSuplenteConsejalDoc = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 2, false);
                    break;
                case 3:
                    showTesorero = true;
                    lstPropietarioTesorero = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 3, true);
                    lstSuplenteTesorero = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 3, false);
                    break;
                case 4:
                    showConcejal1 = true;
                    lstPropietarioConsejal1 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 4, true);
                    lstSuplenteConsejal1 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 4, false);
                    break;
                case 5:
                    showConcejal2 = true;
                    lstPropietarioConsejal2 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 5, true);
                    lstSuplenteConsejal2 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 5, false);
                    break;
                case 6:
                    showRepresentante1 = true;
                    lstPropietarioRepresentante1 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 6, true);
                    lstSuplenteRepresentante1 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 6, false);
                    break;
                case 7:
                    showRepresentante2 = true;
                    lstPropietarioRepresentante2 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 7, true);
                    lstSuplenteRepresentante2 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargoAndNombramiento(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 7, false);
                    break;
            }
        }
    }

    public Boolean getShowSecretario() {
        return showSecretario;
    }

    public Boolean getShowConsejalDoc() {
        return showConsejalDoc;
    }

    public Boolean getShowTesorero() {
        return showTesorero;
    }

    public Boolean getShowConcejal1() {
        return showConcejal1;
    }

    public Boolean getShowConcejal2() {
        return showConcejal2;
    }

    public Boolean getShowRepresentante1() {
        return showRepresentante1;
    }

    public Boolean getShowRepresentante2() {
        return showRepresentante2;
    }

    public BigDecimal getIdPro1() {
        return idPro1;
    }

    public void setIdPro1(BigDecimal idPro1) {
        this.idPro1 = idPro1;
    }

    public BigDecimal getIdSup1() {
        return idSup1;
    }

    public void setIdSup1(BigDecimal idSup1) {
        this.idSup1 = idSup1;
    }

    public BigDecimal getIdPro2() {
        return idPro2;
    }

    public void setIdPro2(BigDecimal idPro2) {
        this.idPro2 = idPro2;
    }

    public BigDecimal getIdSup2() {
        return idSup2;
    }

    public void setIdSup2(BigDecimal idSup2) {
        this.idSup2 = idSup2;
    }

    public BigDecimal getIdPro3() {
        return idPro3;
    }

    public void setIdPro3(BigDecimal idPro3) {
        this.idPro3 = idPro3;
    }

    public BigDecimal getIdSup3() {
        return idSup3;
    }

    public void setIdSup3(BigDecimal idSup3) {
        this.idSup3 = idSup3;
    }

    public BigDecimal getIdPro4() {
        return idPro4;
    }

    public void setIdPro4(BigDecimal idPro4) {
        this.idPro4 = idPro4;
    }

    public BigDecimal getIdSup4() {
        return idSup4;
    }

    public void setIdSup4(BigDecimal idSup4) {
        this.idSup4 = idSup4;
    }

    public BigDecimal getIdPro5() {
        return idPro5;
    }

    public void setIdPro5(BigDecimal idPro5) {
        this.idPro5 = idPro5;
    }

    public BigDecimal getIdSup5() {
        return idSup5;
    }

    public void setIdSup5(BigDecimal idSup5) {
        this.idSup5 = idSup5;
    }

    public BigDecimal getIdPro6() {
        return idPro6;
    }

    public void setIdPro6(BigDecimal idPro6) {
        this.idPro6 = idPro6;
    }

    public BigDecimal getIdSup6() {
        return idSup6;
    }

    public void setIdSup6(BigDecimal idSup6) {
        this.idSup6 = idSup6;
    }

    public BigDecimal getIdPro7() {
        return idPro7;
    }

    public void setIdPro7(BigDecimal idPro7) {
        this.idPro7 = idPro7;
    }

    public BigDecimal getIdSup7() {
        return idSup7;
    }

    public void setIdSup7(BigDecimal idSup7) {
        this.idSup7 = idSup7;
    }

    public List<Candidato> getLstPropietarioSecretario() {
        return lstPropietarioSecretario;
    }

    public List<Candidato> getLstSuplenteSecretario() {
        return lstSuplenteSecretario;
    }

    public List<Candidato> getLstPropietarioConsejalDoc() {
        return lstPropietarioConsejalDoc;
    }

    public List<Candidato> getLstSuplenteConsejalDoc() {
        return lstSuplenteConsejalDoc;
    }

    public List<Candidato> getLstPropietarioTesorero() {
        return lstPropietarioTesorero;
    }

    public List<Candidato> getLstSuplenteTesorero() {
        return lstSuplenteTesorero;
    }

    public List<Candidato> getLstPropietarioConsejal1() {
        return lstPropietarioConsejal1;
    }

    public List<Candidato> getLstSuplenteConsejal1() {
        return lstSuplenteConsejal1;
    }

    public List<Candidato> getLstPropietarioConsejal2() {
        return lstPropietarioConsejal2;
    }

    public List<Candidato> getLstSuplenteConsejal2() {
        return lstSuplenteConsejal2;
    }

    public List<Candidato> getLstPropietarioRepresentante1() {
        return lstPropietarioRepresentante1;
    }

    public List<Candidato> getLstSuplenteRepresentante1() {
        return lstSuplenteRepresentante1;
    }

    public List<Candidato> getLstPropietarioRepresentante2() {
        return lstPropietarioRepresentante2;
    }

    public List<Candidato> getLstSuplenteRepresentante2() {
        return lstSuplenteRepresentante2;
    }

    public Integer getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Integer idCargo) {
        this.idCargo = idCargo;
    }

    public void guardar() {
        if (validarVotoDeCargosDisponibles()) {
            guardarVotacion(idPro1, idSup1, idPro2, idSup2, idPro3, idSup3, idPro4, idSup4, idPro5, idSup5, idPro6, idSup6, idPro7, idSup7);
            persistenceFacade.guardarDetalleVoto(parametrosSesionView.getIdUsuario(), procesoVotacion);

            PrimeFaces.current().executeScript("PF('dlgVoto').show()");
        }
    }

    private void guardarVotacion(BigDecimal... idCandidatos) {
        for (BigDecimal idCandidato : idCandidatos) {
            if (idCandidato != null) {
                Votacion votacion = new Votacion();
                votacion.setFechaInsercion(new Date());
                votacion.setIdCandidato(catalogoFacade.find(Candidato.class, idCandidato));
                votacion.setIdProcesoVotacion(procesoVotacion);

                persistenceFacade.crear(votacion);
            }
        }
    }

    private Boolean validarVotoDeCargosDisponibles() {
        Boolean validar = true;
        String msj = "";
        if (showSecretario) {
            if (idPro1 != null && idSup1 != null) {
            } else {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Secretario<br/>");
            }
        }
        if (showConsejalDoc) {
            if (idPro2 != null && idSup2 != null) {

            } else {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Consejal Docente<br/>");
            }
        }
        if (showTesorero) {
            if (idPro3 != null && idSup3 != null) {
            } else {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Tesorero<br/>");
            }
        }
        if (showConcejal1) {
            if (idPro4 != null && idSup4 != null) {
            } else {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Consejal 1<br/>");
            }
        }
        if (showConcejal2) {
            if (idPro5 != null && idSup5 != null) {
            } else {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Consejal 2<br/>");
            }
        }
        if (showRepresentante1) {
            if (idPro6 != null && idSup6 != null) {
            } else {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Representante 1<br/>");
            }
        }
        if (showRepresentante2) {
            if (idPro7 != null && idSup7 != null) {
            } else {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Representante 2<br/>");
            }
        }

        if (!msj.isEmpty()) {
            JsfUtil.mensajeAlerta(msj);
        }

        return validar;
    }
}
