/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.PersistenceFacade;
import sv.gob.mined.app.facade.siges.CatalogoFacadeSiges;
import sv.gob.mined.app.model.Usuario;
import sv.gob.mined.app.model.siges.dto.EstudianteDto;
import sv.gob.mined.app.view.util.CredencialesView;
import sv.gob.mined.app.view.util.VarSession;
import sv.gob.mined.utils.jsf.JsfUtil;

/**
 *
 * @author misanchez
 */
@Named
@ViewScoped
public class LoginView implements Serializable {

    private Boolean showLoginCe = false;
    private Boolean showPadresFam = false;
    private Boolean desactivarDominio = false;
    private Boolean correoValido = false;
    private String correoRemitente;
    private String idDominioCorreo = "1";
    private String dominio;
    private String password;
    private Long nie;
    private String dui;
    private String codigoEntidad;

    @Inject
    private CredencialesView credencialesView;

    @Inject
    private CatalogoFacadeSiges catalogoFacadeSiges;

    @Inject
    private CatalogoFacade catalogoFacade;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getExternalContext().getSessionMap().clear();
    }

    public Boolean getCorreoValido() {
        return correoValido;
    }

    public void setCorreoValido(Boolean correoValido) {
        this.correoValido = correoValido;
    }

    public String getCorreoRemitente() {
        return correoRemitente;
    }

    public void setCorreoRemitente(String correoRemitente) {
        this.correoRemitente = correoRemitente;
    }

    public String getIdDominioCorreo() {
        return idDominioCorreo;
    }

    public void setIdDominioCorreo(String idDominioCorreo) {
        this.idDominioCorreo = idDominioCorreo;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getNie() {
        return nie;
    }

    public void setNie(Long nie) {
        this.nie = nie;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getCodigoEntidad() {
        return codigoEntidad;
    }

    public void setCodigoEntidad(String codigoEntidad) {
        this.codigoEntidad = codigoEntidad;
    }

    public Boolean getShowLoginCe() {
        return showLoginCe;
    }

    public Boolean getMostraOpcionLogin() {
        return (showLoginCe || showPadresFam);
    }

    public void setShowLoginCe(Boolean showLoginCe) {
        this.showLoginCe = showLoginCe;
    }

    public Boolean getShowPadresFam() {
        return showPadresFam;
    }

    public void setShowPadresFam(Boolean showPadresFam) {
        this.showPadresFam = showPadresFam;
    }

    public Boolean getDesactivarDominio() {
        return desactivarDominio;
    }

    public String validarCrendecialesDelCorreo() {
        String url = "";
        Boolean usuarioNuevoEstudiante = false;
        if (correoRemitente != null && password != null) {
            credencialesView.setIdDominioCorreo(idDominioCorreo);
            credencialesView.setCorreoRemitente(correoRemitente);
            credencialesView.setPassword(password);

            Usuario usuario = catalogoFacade.getsUsuarioRegistrado(credencialesView.getRemitente());
            if (usuario == null) {
                EstudianteDto estudianteSiges = catalogoFacadeSiges.validarCredencialesEstudiante(credencialesView.getRemitente());
                crearVariablesPerSiges(estudianteSiges, credencialesView.getRemitente(), VarSession.USUARIO_EST);
                usuarioNuevoEstudiante = true;
            } else {
                VarSession.setVariableSession(VarSession.TIPO_USUARIO, usuario.getTipoUsuario());
            }

            if (usuario != null || usuarioNuevoEstudiante) {
                //credencialesView.validarCredencial();

                /*correoValido = credencialesView.isCorreoValido();
                if (correoValido) {*/
                switch (usuario.getTipoUsuario()) {
                    case "A": //nivel central, departamentales y directores
                        if (usuario.getCodigoEntidad() != null) { //director
                            VarSession.setVariableSession(VarSession.CODIGO_ENTIDAD, catalogoFacade.getCodigoEntidadByCorreoDirector(credencialesView.getRemitente()));
                        } else if (usuario.getCodigoDepartamento() != null) {
                            VarSession.setVariableSession(VarSession.CODIGO_DEPARTAMENTO, usuario.getCodigoDepartamento());

                        }
                        url = "/app/habilitarProceso?faces-redirect=true";
                        break;
                    case "D":
                    case "E":
                    case "P":
                        //recuperar el centro educativo
                        if (!VarSession.isVariableSession(VarSession.CODIGO_ENTIDAD)) {
                            VarSession.setVariableSession(VarSession.CODIGO_ENTIDAD, usuario.getCodigoEntidad());
                            VarSession.setVariableSession(VarSession.ID_USUARIO, usuario.getIdUsuario());
                        }
                        url = "/app/inicio?faces-redirect=true";
                        break;
                    default:
                        url = "";
                }

                /*} else {
                    JsfUtil.mensajeError("Error en el usuario o  clave de acceso.");
                }*/
            } else {
                JsfUtil.mensajeError("Este USUARIO no esta registrado en el sistema.");
            }
        }

        return url;
    }

    public String validarCredencialesResponsable() {
        EstudianteDto padreSiges = catalogoFacadeSiges.validarCredenciales(nie, dui, codigoEntidad);
        if (padreSiges != null) {
            crearVariablesPerSiges(padreSiges, null, VarSession.USUARIO_PAD);

            //registrar primer logeo para le proceso de votación.
            return "app/inicio?faces-redirect=true";
        } else {
            return "";
        }
    }

    private void crearVariablesPerSiges(EstudianteDto perSiges, String correo, String tipoUsuario) {
        VarSession.setVariableSession("nie", nie);
        VarSession.setVariableSession("dui", dui);
        VarSession.setVariableSession("nombres", perSiges.getNombres());
        VarSession.setVariableSession("apellidos", perSiges.getApellidos());
        VarSession.setVariableSession("nombreUsuario", perSiges.getNombres().concat(" ").concat(perSiges.getApellidos()));
        VarSession.setVariableSession("correo", correo);
        VarSession.setVariableSession(VarSession.CODIGO_ENTIDAD, perSiges.getSedCodigo());
        VarSession.setVariableSession(VarSession.TIPO_USUARIO, tipoUsuario);
        VarSession.setVariableSession(VarSession.ID_USUARIO_SIGES, new BigDecimal(perSiges.getIdPerSiges().toString()));
    }

    public void showLoginDocEst() {
        desactivarDominio = false;
        showLoginCe = true;
        showPadresFam = false;
        dominio = null;
    }

    public void cancelarLogin() {
        showLoginCe = false;
        showPadresFam = false;
    }

    public void showLoginPadRes() {
        showLoginCe = false;
        showPadresFam = true;
    }

    public void showLoginAdmin() {
        desactivarDominio = true;
        showLoginCe = true;
        showPadresFam = false;
        dominio = "1";
    }
}
