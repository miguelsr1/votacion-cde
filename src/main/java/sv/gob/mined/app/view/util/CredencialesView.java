/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view.util;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import sv.gob.mined.app.facade.EMailFacade;

/**
 *
 * @author misanchez
 */
@Named
@SessionScoped
public class CredencialesView implements Serializable {

    private String correoRemitente;
    private String idDominioCorreo = "2";
    private String password;

    private String remitente;
    private String remitenteOficial;
    private String port;
    private String server;
    private Session mailSession;
    private Session mailSessionRemitente;
    private Transport transport;

    private boolean correoValido;

    @Inject
    private EMailFacade eMailFacade;

    public String getIdDominioCorreo() {
        return idDominioCorreo;
    }

    public void setIdDominioCorreo(String idDominioCorreo) {
        this.idDominioCorreo = idDominioCorreo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCorreoRemitente(String correoRemitente) {
        this.correoRemitente = correoRemitente;
    }

    public Session getMailSession() {
        return mailSession;
    }

    public Transport getTransport() {
        return transport;
    }

    public void validarCredencial() {
        try {
            if (idDominioCorreo.equals("1")) {
                remitente = correoRemitente.concat("@").concat("docentes.mined.edu.sv");
                port = "587";
                server = "smtp.office365.com";
            } else {
                remitente = correoRemitente.concat("@").concat("clases.edu.sv");
                port = "587";
                server = "smtp.gmail.com";
            }
            mailSession = eMailFacade.getMailSession(idDominioCorreo, mailSession, remitente, password);

            transport = mailSession.getTransport("smtp");
            transport.connect(server, Integer.parseInt(port), remitente, password);

            correoValido = true;

            transport.close();
        } catch (NoSuchProviderException ex) {
            correoValido = false;
        } catch (MessagingException ex) {
            correoValido = false;
        }
    }

    public boolean isCorreoValido() {
        return correoValido;
    }

    public String getRemitente() {
        return remitente;
    }

    public Session getMailSessionRemitente() {
        if (mailSessionRemitente == null) {
            remitenteOficial = "cooperacion@admin.mined.edu.sv";
        }

        return mailSessionRemitente;
    }

    public String getRemitenteOficial() {
        return remitenteOficial;
    }

    public void setRemitenteOficial(String remitenteOficial) {
        this.remitenteOficial = remitenteOficial;
    }

}
