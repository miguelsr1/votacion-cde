/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
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
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
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

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("Bundle");

    private Integer idCargo;
    private String nombramiento;
    private Candidato candidato = new Candidato();
    private ProcesoVotacion procesoVotacion;
    private UploadedFile file;
    private StreamedContent sContent;
    private byte [] byteImagen;

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
        cargarImagen();
    }

    public void guardar() {
        cargarFoto();
        candidato.setPathImagen(file.getFileName());
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
        
        candidato = new Candidato();
        file = null;
        idCargo = null;
        cargarImagen();
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void cargarFoto() {
        File folderProyecto = new File(JsfUtil.getPathReportes(RESOURCE_BUNDLE, "path_images"));
        if (!folderProyecto.exists()) {
            folderProyecto.mkdir();
        }

        try {
            Path folder = Paths.get(JsfUtil.getPathReportes(RESOURCE_BUNDLE, "path_images") + File.separator + file.getFileName());
            Path arc;
            if (folder.toFile().exists()) {
                arc = folder;
            } else {
                arc = Files.createFile(folder);
            }

            try (InputStream input = new ByteArrayInputStream(byteImagen)) {
                Files.copy(input, arc, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            Logger.getLogger(CandidatoView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarImagen() {
        sContent = DefaultStreamedContent.builder()
                .contentType("image/png")
                .stream(() -> {
                    try {
                        if (candidato.getIdCandidato() == null && file == null) {
                            String path = candidato.getIdCandidato() == null ? "sin_foto.png" : candidato.getPathImagen();
                            String pathImg;

                            if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
                                pathImg = RESOURCE_BUNDLE.getString("path_images_win");
                            } else {
                                pathImg = RESOURCE_BUNDLE.getString("path_images_linux");
                            }

                            return new FileInputStream(pathImg + File.separator + path);
                        } else {
                            byteImagen = file.getContent();
                            
                            return file.getInputStream();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .build();
    }

    public StreamedContent getImagen() {
        return sContent;
    }

    public void handleFileUpload(FileUploadEvent event) {
        file = event.getFile();
        cargarImagen();
    }

}
