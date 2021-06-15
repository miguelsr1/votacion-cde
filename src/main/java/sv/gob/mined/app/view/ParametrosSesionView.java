/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;;
import javax.inject.Named;
import sv.gob.mined.app.view.util.VarSession;

@Named
@SessionScoped
public class ParametrosSesionView implements Serializable {  

    @PostConstruct
    public void init() {

    }


    public String getTipoUsuario() {
        return VarSession.getVariableSession(VarSession.TIPO_USUARIO).toString();
    }

    public Boolean getTipoUsuarioVotante() {
        switch (VarSession.TIPO_USUARIO) {
            case "D":
            case "E":
            case "P":
                return true;
            default:
                return false;
        }
    }
}
