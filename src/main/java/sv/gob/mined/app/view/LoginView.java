/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import sv.gob.mined.app.facade.CatalogoFacade;
import sv.gob.mined.app.facade.siges.CatalogoFacadeSiges;
import sv.gob.mined.app.model.Usuario;
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

    public String validarCrendecialesDelCorreo() {
        String url = "";
        if (correoRemitente != null && password != null) {
            credencialesView.setIdDominioCorreo(idDominioCorreo);
            credencialesView.setCorreoRemitente(correoRemitente);
            credencialesView.setPassword(password);

            Usuario usuario = catalogoFacade.getsUsuarioRegistrado(credencialesView.getRemitente());

            if (usuario != null) {
                //credencialesView.validarCredencial();

                /*correoValido = credencialesView.isCorreoValido();
                if (correoValido) {*/
                    VarSession.setVariableSession(VarSession.TIPO_USUARIO, usuario.getTipoUsuario());
                    if(usuario.getTipoUsuario().equals("A")){
                        //recuperar el centro educativo
                        VarSession.setVariableSession(VarSession.CODIGO_ENTIDAD, 
                                catalogoFacade.getCodigoEntidadByCorreoDirector(usuario.getCuentaCorreo()));
                    }

                    url = "/app/inicio?faces-redirect=true";

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
        if (catalogoFacadeSiges.validarCredenciales(nie, dui, codigoEntidad)) {
            VarSession.setVariableSession("nie", nie);
            VarSession.setVariableSession("dui", dui);
            VarSession.setVariableSession(VarSession.CODIGO_ENTIDAD, codigoEntidad);
            VarSession.setVariableSession(VarSession.TIPO_USUARIO, VarSession.USUARIO_PAD);

            return "app/inicio?faces-redirect=true";
        } else {
            return "";
        }
    }
}
