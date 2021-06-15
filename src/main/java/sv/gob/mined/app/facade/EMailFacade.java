/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.facade;

import java.util.Properties;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;

/**
 *
 * @author misanchez
 */
@Stateless
@LocalBean
public class EMailFacade {

    public Session getMailSession(String idDominio, Session mailSession, String remitente, String password) {
        switch (idDominio) {
            case "1":
                return getMailSessionOffice(mailSession, idDominio, idDominio);
            case "2":
                return getMailSessionGoogle(mailSession, idDominio, idDominio);
            default:
                return null;
        }
    }

    private Session getMailSessionOffice(Session mailSession, String remitente, String password) {
        if (mailSession == null) {
            Properties configEmail = new Properties();

            configEmail.put("mail.smtp.auth", "true");
            configEmail.put("mail.smtp.starttls.enable", "true");

            configEmail.put("mail.smtp.host", "smtp.office365.com");
            configEmail.put("mail.smtp.port", "587");

            configEmail.put("mail.user", remitente);
            configEmail.put("mail.user.pass", password);
            configEmail.put("mail.from", remitente);

            mailSession = Session.getInstance(configEmail, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });
        }

        return mailSession;
    }

    private Session getMailSessionGoogle(Session mailSession, String remitente, String password) {
        if (mailSession == null) {
            Properties configEmail = new Properties();

            configEmail.put("mail.smtp.auth", "true");
            configEmail.put("mail.smtp.starttls.enable", "true");

            configEmail.put("mail.smtp.host", "smtp.gmail.com");
            configEmail.put("mail.smtp.port", "587");

            configEmail.put("mail.user", remitente);
            configEmail.put("mail.user.pass", password);
            configEmail.put("mail.from", remitente);

            mailSession = Session.getInstance(configEmail, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });
        }

        return mailSession;
    }

    public Session getMailSessionMined(Session mailSession, String dominio, String password, String remitente) {
        if (mailSession == null) {
            Properties configEmail = new Properties();

            configEmail.put("mail.smtp.auth", "true");
            configEmail.put("mail.smtp.starttls.enable", "false");

            configEmail.put("mail.smtp.host", "svr2k13mail01.mined.gob.sv");
            configEmail.put("mail.smtp.port", "2525");

            configEmail.put("mail.user", "MINED\\" + dominio);
            configEmail.put("mail.user.pass", password);
            configEmail.put("mail.from", remitente);

            mailSession = Session.getInstance(configEmail, new Authenticator() {

                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("MINED\\" + dominio, password);
                }
            });
        }

        return mailSession;
    }
}
