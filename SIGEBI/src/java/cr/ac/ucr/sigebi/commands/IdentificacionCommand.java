/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.Estado;
import cr.ac.ucr.sigebi.domain.Identificacion;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.UnidadEjecutora;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.SelectItem;

/**
 *
 * @author alvaro.cascante
 */
public class IdentificacionCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private List<SelectItem> itemsTipo;
    private Identificacion ultimoRegistro;
    private Estado estadoNuevo;
    private List<Identificacion> identificacionesExistentes;
    
    private Long id;
    private Long idTipo;
    private String prefijo;
    private Long rangoInicial;
    private Long rangoFinal;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public IdentificacionCommand() { 
        super();
        this.itemsTipo = new ArrayList<SelectItem>();
        this.identificacionesExistentes = new ArrayList<Identificacion>();
    }
    //</editor-fold>
 
    //<editor-fold defaultstate="collapsed" desc="Metodos">
    public List<Identificacion> getIdentificaciones(Tipo tipo, UnidadEjecutora unidadEjecutora) { 
        List<Identificacion> identificaciones = new ArrayList<Identificacion>();
        for (Long i = rangoInicial; i <= rangoFinal; i++) {
            Identificacion identificacion = new Identificacion();
            identificacion.setEstado(this.estadoNuevo);
            identificacion.setIdentificacion(prefijo + i);
            identificacion.setMarcado(false);
            identificacion.setSeleccionado(false);
            identificacion.setTipo(tipo);
            identificacion.setUnidadEjecutora(unidadEjecutora);
            identificaciones.add(identificacion);
        }        
        return identificaciones;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public List<SelectItem> getItemsTipo() {
        return itemsTipo;
    }

    public void setItemsTipo(List<SelectItem> itemsTipo) {
        this.itemsTipo = itemsTipo;
    }

    public Identificacion getUltimoRegistro() {
        return ultimoRegistro;
    }

    public void setUltimoRegistro(Identificacion ultimoRegistro) {
        this.ultimoRegistro = ultimoRegistro;
    }

    public Estado getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(Estado estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public List<Identificacion> getIdentificacionesExistentes() {
        return identificacionesExistentes;
    }

    public void setIdentificacionesExistentes(List<Identificacion> identificacionesExistentes) {
        this.identificacionesExistentes = identificacionesExistentes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public Long getRangoInicial() {
        return rangoInicial;
    }

    public void setRangoInicial(Long rangoInicial) {
        this.rangoInicial = rangoInicial;
    }

    public Long getRangoFinal() {
        return rangoFinal;
    }

    public void setRangoFinal(Long rangoFinal) {
        this.rangoFinal = rangoFinal;
    }
    //</editor-fold>
}