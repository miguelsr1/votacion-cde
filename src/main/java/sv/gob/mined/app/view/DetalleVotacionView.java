/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.ParametrosFacade;
import sv.gob.mined.app.facade.PersistenceFacade;
import sv.gob.mined.app.model.Candidato;
import sv.gob.mined.app.model.ParametroVotacion;
import sv.gob.mined.app.model.ProcesoVotacion;
import sv.gob.mined.app.model.Votacion;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class DetalleVotacionView implements Serializable {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("Bundle");

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
        List<ParametroVotacion> lstParam = parametrosFacade.
                findParametrosByCodigoEntAndAnho(parametrosSesionView.getCodigoEntidad(), 
                        parametrosSesionView.getAnho().getAnho(),
                        parametrosSesionView.getProcesoVotacion().getIdProcesoVotacion());
        procesoVotacion = parametrosSesionView.getProcesoVotacion();

        switch (parametrosSesionView.getTipoUsuario()) {
            case "A":
            case "D":
                showSecretario = true;
                showConsejalDoc = true;
                break;
            case "E":
                showRepresentante1 = true;
                showRepresentante2 = true;
                break;
            case "P":
                showTesorero = true;
                showConcejal1 = true;
                showConcejal2 = true;
                break;
        }

        for (ParametroVotacion param : lstParam) {
            switch (param.getIdCargo().getIdCargo()) {
                case 1:
                case 2:
                    if (showSecretario) {
                        lstPropietarioSecretario = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 1, procesoVotacion.getIdProcesoVotacion());
                        lstSuplenteSecretario = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 2, procesoVotacion.getIdProcesoVotacion());
                    }
                    showSecretario = !(lstPropietarioSecretario.isEmpty() && lstSuplenteSecretario.isEmpty());
                    break;
                case 3:
                case 4:
                    if (showConsejalDoc) {
                        lstPropietarioConsejalDoc = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 3, procesoVotacion.getIdProcesoVotacion());
                        lstSuplenteConsejalDoc = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 4, procesoVotacion.getIdProcesoVotacion());
                    }
                    showConsejalDoc = !(lstPropietarioConsejalDoc.isEmpty() && lstSuplenteConsejalDoc.isEmpty());
                    break;
                case 5:
                case 6:
                    if (showTesorero) {
                        lstPropietarioTesorero = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 5, procesoVotacion.getIdProcesoVotacion());
                        lstSuplenteTesorero = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 6, procesoVotacion.getIdProcesoVotacion());
                    }
                    showTesorero = !(lstPropietarioTesorero.isEmpty() && lstSuplenteTesorero.isEmpty());
                    break;
                case 7:
                case 8:
                    if (showConcejal1) {
                        lstPropietarioConsejal1 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 7, procesoVotacion.getIdProcesoVotacion());
                        lstSuplenteConsejal1 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 8, procesoVotacion.getIdProcesoVotacion());
                    }
                    showConcejal1 = !(lstPropietarioConsejal1.isEmpty() && lstSuplenteConsejal1.isEmpty());
                    break;
                case 9:
                case 10:
                    if (showConcejal2) {
                        lstPropietarioConsejal2 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 9, procesoVotacion.getIdProcesoVotacion());
                        lstSuplenteConsejal2 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 10, procesoVotacion.getIdProcesoVotacion());
                    }
                    showConcejal2 = !(lstPropietarioConsejal2.isEmpty() && lstSuplenteConsejal2.isEmpty());
                    break;
                case 11:
                case 12:
                    if (showRepresentante1) {
                        lstPropietarioRepresentante1 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 11, procesoVotacion.getIdProcesoVotacion());
                        lstSuplenteRepresentante1 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 12, procesoVotacion.getIdProcesoVotacion());
                    }
                    showRepresentante1 = !(lstPropietarioRepresentante1.isEmpty() && lstSuplenteRepresentante1.isEmpty());
                    break;
                case 13:
                case 14:
                    if (showRepresentante2) {
                        lstPropietarioRepresentante2 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 13, procesoVotacion.getIdProcesoVotacion());
                        lstSuplenteRepresentante2 = catalogoFacade.findCandidatosByAnhoAndCodigoEntidadAndCargo(parametrosSesionView.getAnho().getIdAnho(), parametrosSesionView.getCodigoEntidad(), 14, procesoVotacion.getIdProcesoVotacion());
                    }
                    showRepresentante2 = !(lstPropietarioRepresentante2.isEmpty() && lstSuplenteRepresentante2.isEmpty());
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
            if (!lstPropietarioSecretario.isEmpty() && idPro1 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Secretario Propietario<br/>");
            }
            if (!lstSuplenteSecretario.isEmpty() && idSup1 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Secretario Suplente<br/>");
            }
        }
        if (showConsejalDoc) {
            if (!lstPropietarioConsejalDoc.isEmpty() && idPro2 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Consejal Docente Propietario<br/>");
            }
            if (!lstSuplenteConsejalDoc.isEmpty() && idSup2 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Consejal Docente Suplente<br/>");
            }
        }
        if (showTesorero) {
            if (!lstPropietarioTesorero.isEmpty() && idPro3 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Consejal Docente Propietario<br/>");
            }
            if (!lstSuplenteTesorero.isEmpty() && idSup3 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Consejal Docente Suplente<br/>");
            }
        }
        if (showConcejal1) {
            if (!lstPropietarioConsejal1.isEmpty() && idPro4 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Consejal 1 Padre de Familia Propietario<br/>");
            }
            if (!lstSuplenteConsejal1.isEmpty() && idSup4 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Consejal 1 Padre de Familia Suplente<br/>");
            }
        }
        if (showConcejal2) {
            if (!lstPropietarioConsejal2.isEmpty() && idPro5 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Consejal 2 Padre de Familia Propietario<br/>");
            }
            if (!lstSuplenteConsejal2.isEmpty() && idSup5 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Consejal 2 Padre de Familia Suplente<br/>");
            }
        }
        if (showRepresentante1) {
            if (!lstPropietarioRepresentante1.isEmpty() && idPro6 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Representante 1 Propietario<br/>");
            }
            if (!lstSuplenteRepresentante1.isEmpty() && idSup6 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Representante 1 Suplente<br/>");
            }
        }
        if (showRepresentante2) {
            if (!lstPropietarioRepresentante2.isEmpty() && idPro7 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Representante 2 Propietario<br/>");
            }
            if (!lstSuplenteRepresentante2.isEmpty() && idSup7 == null) {
                validar = false;
                msj = (msj.isEmpty() ? "" : msj).concat("Debe de selecionar un candidato a Representante 2 Suplente<br/>");
            }
        }

        if (!msj.isEmpty()) {
            JsfUtil.mensajeAlerta(msj);
        }

        return validar;
    }

    public StreamedContent getGraphicText(String path) {
        return DefaultStreamedContent.builder()
                .contentType("image/png")
                .stream(() -> {
                    try {
                        String pathImg;
                        byte[] bytes;
                        if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
                            pathImg = RESOURCE_BUNDLE.getString("path_images_win");
                        } else {
                            pathImg = RESOURCE_BUNDLE.getString("path_images_linux");
                        }

                        return new FileInputStream(pathImg + File.separator + path);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .build();
    }
}
