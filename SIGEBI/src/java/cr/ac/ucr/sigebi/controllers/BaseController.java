/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.seguridad.entidades.SegUnidadEjecutora;
import cr.ac.ucr.framework.seguridad.entidades.SegUsuario;
import cr.ac.ucr.sigebi.utils.Constantes;
import cr.ac.ucr.framework.vista.VistaUsuario;
import cr.ac.ucr.framework.vista.util.PaginacionOracle;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.ArrayList;
import javax.faces.model.SelectItem;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 *
 * @author jairo.cisneros
 */
@Controller(value = "controllerBase")
@Scope("session")
public class BaseController extends PaginacionOracle {

    //<editor-fold defaultstate="collapsed" desc="Constantes">
    Integer estadoPendiente;// = Constantes.ESTADO_BIEN_PENDIENTE;
    Integer estadoPendienteSincronizar;// = Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Variables Locales">
    VistaUsuario lVistaUsuario;
    String nombreUnidad;
    String codPersonaReg;
    String usuarioRegistrado;
    String vistaOrigen;
    String vistaActual;
    UnidadEjecutora unidadEjecutora;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's & Set's">
    public String getVistaActual() {
        return vistaActual;
    }

    public void setVistaActual(String vistaActual) {
        this.vistaActual = vistaActual;
    }

    public Integer getEstadoPendiente() {
        return estadoPendiente;
    }

    public Integer getEstadoPendienteSincronizar() {
        return estadoPendienteSincronizar;
    }

    public VistaUsuario getlVistaUsuario() {
        return lVistaUsuario;
    }

    public void setlVistaUsuario(VistaUsuario lVistaUsuario) {
        this.lVistaUsuario = lVistaUsuario;
    }

    public String getVistaOrigen() {
        return vistaOrigen;
    }

    public void setVistaOrigen(String vistaOrigen) {
        this.vistaOrigen = vistaOrigen;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }

    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public String getNombreUnidad() {
        return nombreUnidad;
    }

    public void setNombreUnidad(String nombreUnidad) {
        this.nombreUnidad = nombreUnidad;
    }

    public String getCodPersonaReg() {
        return codPersonaReg;
    }

    public void setCodPersonaReg(String codPersonaReg) {
        this.codPersonaReg = codPersonaReg;
    }

    public String getUsuarioRegistrado() {
        return usuarioRegistrado;
    }

    public void setUsuarioRegistrado(String usuarioRegistrado) {
        this.usuarioRegistrado = usuarioRegistrado;
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public BaseController() {
        
        estadoPendiente = Constantes.ESTADO_BIEN_PENDIENTE; 
        estadoPendienteSincronizar = Constantes.ESTADO_BIEN_PENDIENTE_SINCRONIZAR; 
        incializaBienes();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    //@PostConstruct
    private void incializaBienes() {

        lVistaUsuario = (VistaUsuario) Util.obtenerVista("#{vistaUsuario}");

        //Obtener el id de la unidad ejecutora
        SegUnidadEjecutora unidad = lVistaUsuario.getgUnidadActual();
        unidadEjecutora = lVistaUsuario.getUnidadEjecutoraSIGEBI();

        //Obtener usuario
        SegUsuario usuario = lVistaUsuario.getgUsuarioActual();
        codPersonaReg = usuario.getIdUsuario();
        usuarioRegistrado = usuario.getNombre_completo();

        ArrayList<SelectItem> cantPorPaginas = new ArrayList<SelectItem>();
        cantPorPaginas.add(new SelectItem(5, "5"));
        cantPorPaginas.add(new SelectItem(10, "10"));
        cantPorPaginas.add(new SelectItem(25, "25"));
        cantPorPaginas.add(new SelectItem(50, "50"));
        this.setListaRegistrosPagina(cantPorPaginas);
    }
    //</editor-fold>
}
