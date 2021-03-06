/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.PersistenceFacade;
import sv.gob.mined.app.model.ProcesoVotacion;
import sv.gob.mined.app.model.Anho;
import sv.gob.mined.app.model.Usuario;
import sv.gob.mined.app.model.VwCatalogoEntidadEducativa;
import sv.gob.mined.app.view.util.VarSession;
import sv.gob.mined.utils.jsf.JsfUtil;

@Named
@SessionScoped
public class ParametrosSesionView implements Serializable {

    private Integer idAnho;

    private Boolean showTiempoFinalizado = false;
    private Boolean mostrarCeUsuario = true;
    private BigDecimal idUsuario;
    private String estadoProceso = "";
    private String sector;

    private Anho anho;
    private ProcesoVotacion procesoVotacion;
    private VwCatalogoEntidadEducativa entidadEducativa;
    private List<ProcesoVotacion> lstProcesos = new ArrayList();

    private Date now = new Date();
    private Date limite;

    @Inject
    private CatalogoFacade catalogoFacade;

    @Inject
    private PersistenceFacade persistenceFacade;

    @PostConstruct
    public void init() {
        anho = catalogoFacade.findAnhoActivo();
        idAnho = anho.getIdAnho();
        findProcesoVotacionByAnhoAndCodigoEntidad();
    }

    public void cargarDatosProcesoVotacionCe() {
        anho = catalogoFacade.find(Anho.class, idAnho);

        switch (VarSession.getVariableSession(VarSession.TIPO_USUARIO).toString()) {
            case "A":
                if (VarSession.getVariableSession(VarSession.CODIGO_ENTIDAD) == null) {
                    mostrarCeUsuario = false;
                } else {
                    cargarDatosCe();
                }
                break;
            default:
                cargarDatosCe();
                break;
        }

        calcularTiempoRestante();
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public Boolean getMostrarCeUsuario() {
        return mostrarCeUsuario;
    }

    private void cargarDatosCe() {
        if (procesoVotacion == null) {
            procesoVotacion = catalogoFacade.findProcesoByAnhoAndCodigoEntidad(anho.getAnho(), getCodigoEntidad());
        }
        entidadEducativa = catalogoFacade.findEntidadEducativaByCodigo(getCodigoEntidad());

        if (VarSession.isVariableSession(VarSession.ID_USUARIO_SIGES)) {

            try {
                idUsuario = persistenceFacade.guardarUsuarioPerSiges(
                        ((BigDecimal) VarSession.getVariableSession(VarSession.ID_USUARIO_SIGES)).longValue(),
                        VarSession.getVariableSession("nombres").toString(),
                        VarSession.getVariableSession("apellidos").toString(),
                        VarSession.getVariableSession("dui") == null ? null : VarSession.getVariableSession("dui").toString(),
                        VarSession.isVariableSession("correo") ? VarSession.getVariableSession("correo").toString() : null,
                        procesoVotacion.getIdProcesoVotacion(),
                        VarSession.getVariableSession(VarSession.TIPO_USUARIO).toString(),
                        VarSession.getVariableSession(VarSession.CODIGO_ENTIDAD).toString());
            } catch (Exception e) {
                idUsuario = persistenceFacade.guardarUsuarioPerSiges(
                        ((BigInteger) VarSession.getVariableSession(VarSession.ID_USUARIO_SIGES)).longValue(),
                        VarSession.getVariableSession("nombres").toString(),
                        VarSession.getVariableSession("apellidos").toString(),
                        VarSession.getVariableSession("dui") == null ? null : VarSession.getVariableSession("dui").toString(),
                        VarSession.isVariableSession("correo") ? VarSession.getVariableSession("correo").toString() : null,
                        procesoVotacion.getIdProcesoVotacion(),
                        VarSession.getVariableSession(VarSession.TIPO_USUARIO).toString(),
                        VarSession.getVariableSession(VarSession.CODIGO_ENTIDAD).toString());
            }

            VarSession.setVariableSession(VarSession.ID_USUARIO, idUsuario);
        } else if (VarSession.isVariableSession(VarSession.ID_USUARIO)) {
            idUsuario = (BigDecimal) VarSession.getVariableSession(VarSession.ID_USUARIO);
            persistenceFacade.guardarAsistencia(procesoVotacion.getIdProcesoVotacion(), catalogoFacade.find(Usuario.class, idUsuario));
        }
    }

    public String getEstadoProceso() {
        return estadoProceso;
    }

    public String getTipoUsuario() {
        return VarSession.getVariableSession(VarSession.TIPO_USUARIO).toString();
    }

    public Boolean getTipoUsuarioVotante() {
        switch (VarSession.getVariableSession(VarSession.TIPO_USUARIO).toString()) {
            case "D":
            case "E":
            case "P":
                return true;
            default:
                return false;
        }
    }

    public String getCodigoEntidad() {
        if (VarSession.isVariableSession(VarSession.CODIGO_ENTIDAD)) {
            return VarSession.getVariableSession(VarSession.CODIGO_ENTIDAD).toString();
        } else {
            return "";
        }
    }

    public List<ProcesoVotacion> getLstProcesos() {
        return lstProcesos;
    }

    public Integer getIdAnho() {
        return idAnho;
    }

    public void setIdAnho(Integer idAnho) {
        this.idAnho = idAnho;
    }

    public Anho getAnho() {
        return anho;
    }

    public List<Anho> getLstAnho() {
        return catalogoFacade.findAllAnho();
    }

    public void findProcesoVotacionByAnhoAndCodigoEntidad() {
        anho = catalogoFacade.find(Anho.class, idAnho);
        lstProcesos = catalogoFacade.findProcesoVotacionByAnhoAndCodigoEntidad(idAnho, VarSession.getVariableSession(VarSession.CODIGO_ENTIDAD).toString());

        for (ProcesoVotacion proceso : lstProcesos) {
            if (obtenerTiempoRestante(proceso.getFechaInsercion(), proceso.getHoras()) != 0) {
                procesoVotacion = proceso;
            }
        }
    }

    public Boolean getShowTiempoFinalizado() {
        return showTiempoFinalizado;
    }

    public ProcesoVotacion getProcesoVotacion() {
        return procesoVotacion;
    }

    public void setProcesoVotacion(ProcesoVotacion procesoVotacion) {
        this.procesoVotacion = procesoVotacion;
    }

    public String getNombreUsuario() {
        if (VarSession.isVariableSession("nombreUsuario")) {
            return VarSession.getVariableSession("nombreUsuario").toString();
        } else {
            return "";
        }
    }

    public String getCentroEducativo() {
        if (entidadEducativa == null) {
            return "";
        } else {
            return entidadEducativa.getCodigoEntidad().concat(" - ").concat(entidadEducativa.getNombre());
        }
    }

    public BigDecimal getIdUsuario() {
        return idUsuario;
    }

    public void calcularTiempoRestante() {
        if (procesoVotacion == null) {
            showTiempoFinalizado = false;
        } else if (procesoVotacion.getFechaInsercion() != null && procesoVotacion.getHoras() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(procesoVotacion.getFechaInsercion());
            calendar.add(Calendar.HOUR_OF_DAY, procesoVotacion.getHoras());

            limite = calendar.getTime();

            showTiempoFinalizado = (limite.getTime() >= (now).getTime());

            estadoProceso = showTiempoFinalizado ? "Tiempo restante:" : "PROCESO FINALIZADO";
        } else {
            estadoProceso = "PROCESO NO INICIADO";
            showTiempoFinalizado = false;
        }
    }

    public long getTiempoRestante() {
        long tiempo = 0l;
        if (procesoVotacion.getFechaInsercion() != null && procesoVotacion.getHoras() != null) {
            if (showTiempoFinalizado) {
            } else {
                long diffInMillies = Math.abs(limite.getTime() - (new Date()).getTime());
                tiempo = TimeUnit.MILLISECONDS.toSeconds(diffInMillies);
            }
        }
        return tiempo;
    }

    public String paginaInicio() {
        String url = "";

        switch (VarSession.getVariableSession(VarSession.TIPO_USUARIO).toString()) {
            case "A":
                if (VarSession.isVariableSession(VarSession.CODIGO_ENTIDAD)) {
                    url = "inicio?faces-redirect=true";
                } else if (VarSession.isVariableSession(VarSession.CODIGO_DEPARTAMENTO)) {
                    url = "/app/inicioDepa?faces-redirect=true";
                } else if (VarSession.isVariableSession(VarSession.CODIGO_ENTIDAD) && VarSession.isVariableSession(VarSession.CODIGO_DEPARTAMENTO)) {
                    url = "/app/inicio?faces-redirect=true";
                }
                break;
            case "D":
                url = "inicio?faces-redirect=true";
                break;
            case "E":
                url = "inicio?faces-redirect=true";
                break;
            case "P":
                url = "inicio?faces-redirect=true";
                break;
        }
        return url;
    }

    public String ingrasarAProceso() {
        VarSession.setVariableSession("idProceso", procesoVotacion);
        if (VarSession.isVariableSession(VarSession.CODIGO_DEPARTAMENTO)) {
            return "inicioDepa?faces-redirect=true";
        } else {
            return "inicio?faces-redirect=true";
        }
    }

    public long obtenerTiempoRestante(Date fechaInicio, Integer horas) {
        long tiempo = -1l;
        Boolean tiempoFinalizado = false;

        if (fechaInicio != null && horas != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(fechaInicio);
            calendar.add(Calendar.HOUR_OF_DAY, horas);

            Date limiteTiempo = calendar.getTime();

            tiempoFinalizado = (limiteTiempo.getTime() < (new Date()).getTime());

            if (tiempoFinalizado) {
                tiempo = 0l;
            } else {
                long diffInMillies = Math.abs(limiteTiempo.getTime() - (new Date()).getTime());
                tiempo = TimeUnit.MILLISECONDS.toSeconds(diffInMillies);
            }
        }
        return tiempo;
    }

    public void agregarProceso() {
        if (lstProcesos.isEmpty()) {
            persistenceFacade.guardarProcesoVotacion(VarSession.getVariableSession(VarSession.CODIGO_ENTIDAD).toString(), idAnho, false, sector);
            findProcesoVotacionByAnhoAndCodigoEntidad();
        } else {
            lstProcesos.forEach(proceso -> {
                if (obtenerTiempoRestante(proceso.getFechaInsercion(), proceso.getHoras()) > 0) {
                    JsfUtil.mensajeAlerta("Todavia existe un proceso no finalizado. No se puede crear otro proceso.");
                } else if (persistenceFacade.isProcesoSinIniciar(VarSession.getVariableSession(VarSession.CODIGO_ENTIDAD).toString(), idAnho)) {
                    JsfUtil.mensajeAlerta("Todavia existe un proceso no iniciado. No se puede crear otro proceso.");
                } else {
                    persistenceFacade.guardarProcesoVotacion(VarSession.getVariableSession(VarSession.CODIGO_ENTIDAD).toString(), idAnho, false, sector);
                }
            });
            
            lstProcesos = catalogoFacade.findProcesoVotacionByAnhoAndCodigoEntidad(idAnho, VarSession.getVariableSession(VarSession.CODIGO_ENTIDAD).toString());
        }
    }
}
