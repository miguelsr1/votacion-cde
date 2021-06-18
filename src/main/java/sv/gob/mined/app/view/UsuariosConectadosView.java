/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.gob.mined.app.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import sv.gob.mined.app.model.dto.UsuarioConectadoDto;

@Named
@ApplicationScoped
public class UsuariosConectadosView implements Serializable {

    private List<UsuarioConectadoDto> lstUsuarios = new ArrayList();

    public void agregarUsuario(BigDecimal idUsuario, String nombres, String apellidos, String codigoEntidad) {
        UsuarioConectadoDto usu = new UsuarioConectadoDto();
        usu.setIdUsuario(idUsuario);
        usu.setNombres(nombres);
        usu.setApellidos(apellidos);
        usu.setCodigoEntidad(codigoEntidad);

        lstUsuarios.add(usu);
    }

    private Boolean existUsuario(BigDecimal idUsuario) {
        Optional<UsuarioConectadoDto> lstUsu = lstUsuarios.stream().parallel().
                filter(usu -> usu.getIdUsuario().compareTo(idUsuario) == 0).findAny();
        return lstUsu.isPresent();
    }

    public List<UsuarioConectadoDto> getLstUsuarioConectados(String codigoEntidad) {
        return lstUsuarios.stream().parallel().
                filter(usu -> usu.getCodigoEntidad().compareTo(codigoEntidad) == 0).
                collect(Collectors.toList());
    }
}
