package cr.ac.ucr.framework.service;

import cr.ac.ucr.framework.seguridad.dao.SeguridadDao;
import cr.ac.ucr.framework.seguridad.entidades.SegElemento;
import cr.ac.ucr.framework.seguridad.entidades.SegPerfil;
import cr.ac.ucr.framework.seguridad.entidades.SegUnidadEjecutora;
import cr.ac.ucr.framework.seguridad.entidades.SegUnidadUsuario;
import cr.ac.ucr.framework.seguridad.entidades.SegUsuario;
import cr.ac.ucr.framework.seguridad.utils.ExcepcionSeguridad;
import cr.ac.ucr.framework.seguridad.utils.UTF8;
import cr.ac.ucr.framework.utils.FWExcepcion;
import cr.ac.ucr.framework.vista.util.Util;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service(value = "seguridadMgr")

public class SeguridadMgr {

    @Resource
    private SeguridadDao seguridadDao;

    public List<SegPerfil> obtenerPerfilesUsuario(String usuario, SegUnidadEjecutora unidad) {
        try {
            return seguridadDao.obtenerPerfilesUsuario(usuario, unidad);
        } catch (ExcepcionSeguridad e) {
            throw new FWExcepcion(e.getError_para_usuario(), e.getError_para_programador());
        }
    }

    public boolean autorizarLoginUCR(String nombre, String clave) {
        try {
            return seguridadDao.autorizarLoginUCR(nombre, clave);
        } catch (ExcepcionSeguridad e) {
            throw new FWExcepcion(UTF8.aUTF8("Acceso imposible por validación LDAP de la UCR."), e.getError_para_programador());
        }
    }

    public boolean autorizarLoginSeguridad(String nombre, String clave_encriptada) {
        try {
            return seguridadDao.autorizarLoginSeguridad(nombre, clave_encriptada);
        } catch (ExcepcionSeguridad e) {
            throw new FWExcepcion(UTF8.aUTF8(
                    "No es posible acceder a la aplicación, no hay conexión"
                    + " con la base de datos."), e.getError_para_programador());
        }
    }

    public SegUsuario buscarUsuarioLowerCase(SegUsuario segUsuario) {
        try {
            return seguridadDao.buscarUsuarioLowerCase(segUsuario);
        } catch (ExcepcionSeguridad e) {
            throw new FWExcepcion(e.getError_para_usuario(), e.getError_para_programador());
        }
    }

    public void actualizarUsuario(SegUsuario usuario_actual) {
        try {
            seguridadDao.actualizarUsuario(usuario_actual);
        } catch (ExcepcionSeguridad e) {
            throw new FWExcepcion(e.getError_para_usuario(), e.getError_para_programador());
        }
    }

    public List<SegUnidadUsuario> obtenerUnidadesUsuarioPorSistema(String usuario_actual) {
        try {
            return seguridadDao.obtenerUnidadesUsuarioPorSistema(usuario_actual);
        } catch (ExcepcionSeguridad e) {
            throw new FWExcepcion(e.getError_para_usuario(), e.getError_para_programador());
        }
    }

    public List<SegElemento> obtenerElemUsuaUnidadEjecSist(String usuario_actual, SegUnidadEjecutora unidad_actual, int[] tipos, int[] tipoModulo) {
        try {
            return seguridadDao.obtenerElementosPorUsuarioUnidadEjecutoraSistema(usuario_actual, unidad_actual, tipos, tipoModulo);
        } catch (ExcepcionSeguridad e) {
            throw new FWExcepcion(e.getError_para_usuario(), e.getError_para_programador());
        }
    }

    public List<SegElemento> obtenerElementosSegunPerfil(String perfil_seleccionado, Object[] tipo_elemento) {
        try {
            return seguridadDao.obtenerElementosSegunPerfil(perfil_seleccionado, tipo_elemento);
        } catch (ExcepcionSeguridad e) {
            throw new FWExcepcion(e.getError_para_usuario(), e.getError_para_programador());
        }
    }

    public String obtenerUnidadEjecutora(String id_unidad_ejecutora, String pTipoUnidad) throws FWExcepcion {
        return seguridadDao.obtenerUnidadEjecutora(id_unidad_ejecutora, pTipoUnidad);
    }//fin obtenerEscuelaUA

    public SegUnidadEjecutora obtenerUnidadEjecByCodReferencia(String pCodReferencia)
            throws FWExcepcion {
        return seguridadDao.obtenerUnidadEjecByCodReferencia(pCodReferencia);
    }//fin obtenerEscuelaUA
    

    
    /**
     * Método que permite obtener la unidad ejecutora padre
     * @author Manuel Sanabria
     * @fecha 05/04/2017
     * @param idUnidadEjecutoraPadre
     * @return 
     */
    public SegUnidadEjecutora obtenerUnidadEjecutoraPadre(Integer idUnidadEjecutoraPadre){
        try{
            return seguridadDao.obtenerUnidadEjecutoraPadre(idUnidadEjecutoraPadre);
        } catch (FWExcepcion e) {
            throw e;
        } catch (Exception ex){
            throw new FWExcepcion("error.login.getRecinto", "Error al obtener el recinto asociado: getRecinto(Integer idUnidadEjecutoraPadre). En la clase" + this.getClass());
        }
    }
}//fin clase