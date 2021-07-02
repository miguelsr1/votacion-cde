/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FilesUploadEvent;
import org.primefaces.model.file.UploadedFile;
import sv.gob.mined.app.model.Candidato;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.PersistenceFacade;
import sv.gob.mined.app.model.Cargo;
import sv.gob.mined.app.model.ProcesoVotacion;
import sv.gob.mined.utils.jsf.JsfUtil;

@Named
@ViewScoped
public class CandidatoView implements java.io.Serializable {

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("bundle");

    private Integer idCargo;
    private String nombramiento;
    private Candidato candidato = new Candidato();
    private ProcesoVotacion procesoVotacion;

    private List<Candidato> lstCandidato = new ArrayList();
    private List<Cargo> lstCargo = new ArrayList();

    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private PersistenceFacade persistenceFacade;
    @Inject
    private ParametrosSesionView parametrosSesionView;

    @PostConstruct
    public void init() {
        procesoVotacion = parametrosSesionView.getProcesoVotacion();
        cargar();
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
        return lstCargo;
    }

    public List<Candidato> getLstCandidato() {
        return lstCandidato;
    }

    public void nuevo() {
        candidato = new Candidato();
    }

    public void cargar() {
        lstCandidato = catalogoFacade.findCandidatosByAnhoAndCodigoEntidad(1, parametrosSesionView.getCodigoEntidad(), idCargo, nombramiento);
        lstCargo = catalogoFacade.findAllCargos(procesoVotacion.getIdProcesoVotacion());
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

    public void actualizarListado() {
        lstCandidato = catalogoFacade.findCandidatosByAnhoAndCodigoEntidad(1, parametrosSesionView.getCodigoEntidad(), idCargo, nombramiento);
    }

    public void subirFoto(FilesUploadEvent event) {
        //UploadedFile updFile = event.getFile();

        File folderProyecto = new File(JsfUtil.getPathReportes(RESOURCE_BUNDLE, "path_images"));
        if (!folderProyecto.exists()) {
            folderProyecto.mkdir();
        }

        try {
            for (UploadedFile updFile : event.getFiles().getFiles()) {
                Path folder = Paths.get(JsfUtil.getPathReportes(RESOURCE_BUNDLE, "path_folder") + File.separator + updFile.getFileName());
                Path arc;
                if (folder.toFile().exists()) {
                    arc = folder;
                } else {
                    arc = Files.createFile(folder);
                }

                try (InputStream input = updFile.getInputStream()) {
                    Files.copy(input, arc, StandardCopyOption.REPLACE_EXISTING);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(CandidatoView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
