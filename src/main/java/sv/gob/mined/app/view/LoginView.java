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
import sv.gob.mined.app.view.util.CredencialesView;
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
    private String idDominioCorreo = "2";
    private String dominio;
    private String password;

    @Inject
    private CredencialesView credencialesView;

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

    public String validarCrendecialesDelCorreo() {
        String url = "";
        if (correoRemitente != null && password != null) {

            credencialesView.setDominio(idDominioCorreo);
            credencialesView.setCorreoRemitente(correoRemitente);
            credencialesView.setPassword(password);
            credencialesView.validarCredencial();

            //correoValido = credencialesView.isCorreoValido();

            if (correoValido) {

                url = "/app/inicio?faces-redirect=true";

            } else {
                JsfUtil.mensajeError("Error en el usuario o  clave de acceso.");
            }
        }

        return url;
    }
}
