/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.PersistenceFacade;
import sv.gob.mined.app.model.Usuario;
import sv.gob.mined.app.model.ProcesoVotacion;
import sv.gob.mined.app.view.util.VarSession;

@Named
@ViewScoped
public class HabilitarVotacionView implements Serializable {

    private Boolean habilitarVotacion;
    private Boolean habilitarResultados;
    private Boolean tiempoFinalizado = false;
    private ProcesoVotacion procesoVotacion;
    private List<Usuario> lstUsuarios = new ArrayList();

    @Inject
    private PersistenceFacade persistenceFacade;
    @Inject
    private CatalogoFacade catalogoFacade;
    @Inject
    private ParametroVotacionView parametroVotacionView;

    @PostConstruct
    public void init() {
        procesoVotacion = catalogoFacade.findProcesoByAnhoAndCodigoEntidad("2021", VarSession.getVariableSession(VarSession.CODIGO_ENTIDAD).toString());
        habilitarResultados = (procesoVotacion.getHabilitarResultados() == 1);
        habilitarVotacion = (procesoVotacion.getHabilitarVotacion() == 1);
        lstUsuarios = catalogoFacade.findUsuariosAsistentes(procesoVotacion.getIdProcesoVotacion());
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

    public List<Usuario> getLstUsuarios() {
        return lstUsuarios;
    }

    public void setLstUsuarios(List<Usuario> lstUsuarios) {
        this.lstUsuarios = lstUsuarios;
    }

    public Boolean getTiempoFinalizado() {
        return tiempoFinalizado;
    }

    public void setTiempoFinalizado(Boolean tiempoFinalizado) {
        this.tiempoFinalizado = tiempoFinalizado;
    }

    public void guardar() {
        procesoVotacion.setHabilitarResultados((short) (habilitarResultados ? 1 : 0));
        procesoVotacion.setHabilitarVotacion((short) (habilitarVotacion ? 1 : 0));
        if (procesoVotacion.getFechaInsercion() != null) {
            procesoVotacion.setFechaInsercion(new Date());
        }

        persistenceFacade.modificar(procesoVotacion);
        
        parametroVotacionView.setDeshabilitar(true);
    }

    public long getTiempoRestante() {
        long tiempo = 0l;
        if (procesoVotacion.getFechaInsercion() != null && procesoVotacion.getHoras() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(procesoVotacion.getFechaInsercion());
            calendar.add(Calendar.HOUR_OF_DAY, procesoVotacion.getHoras());

            Date limite = calendar.getTime();

            tiempoFinalizado = (limite.getTime() < (new Date()).getTime());

            if (tiempoFinalizado) {
            } else {
                long diffInMillies = Math.abs(limite.getTime() - (new Date()).getTime());
                tiempo = TimeUnit.MILLISECONDS.toSeconds(diffInMillies);
}
        }
        return tiempo;
    }
}