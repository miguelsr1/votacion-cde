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

/**
 *
 * @author misanchez
 */
@Named
@SessionScoped
public class CredencialesView implements Serializable {

    private String correoRemitente;
    private String idDominioCorreo = "2";
    private String dominio;
    private String password;

    private String remitente;
    private String remitenteOficial;
    private String port;
    private String server;
    private Session mailSession;
    private Session mailSessionRemitente;
    private Transport transport;

    private boolean correoValido;

    public void setDominio(String dominio) {
        this.dominio = dominio;
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
                remitente = correoRemitente.concat("@").concat("mined.gob.sv");
                port = "587";
                server = "svr2k13mail01.mined.gob.sv";
                //mailSession = eMailFacade.getMailSessionMined(mailSession, dominio, password, remitente);
            } else {
                remitente = correoRemitente.concat("@").concat("admin.mined.edu.sv");
                port = "587";
                server = "smtp.office365.com";
                //mailSession = eMailFacade.getMailSessionOffice(mailSession, remitente, password);
            }

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
        if(mailSessionRemitente == null){
            remitenteOficial = "cooperacion@admin.mined.edu.sv";
            //mailSessionRemitente = eMailFacade.getMailSessionOffice(mailSessionRemitente, "cooperacion@admin.mined.edu.sv", "mined2021.*");
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
