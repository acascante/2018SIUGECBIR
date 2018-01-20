/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.controllers;

import cr.ac.ucr.framework.vista.VistaUsuario;
import cr.ac.ucr.framework.vista.util.PaginacionOracle;
import cr.ac.ucr.framework.vista.util.Util;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.entities.EstadoEntity;
import cr.ac.ucr.sigebi.models.TipoModel;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;
import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

/**
 *
 * @author oscar_acuna
 */
@Controller(value = "tipoController")
@Scope("session")
public class TipoController extends PaginacionOracle {

    @Resource
    private TipoModel tipoModel;

    VistaUsuario lVistaUsuario;
    
    private Integer id;
    private String nombre;
    private String dominio;
    private EstadoEntity estado;
    
    private Tipo tipoSeleccionado;
    
    private List<Tipo> tipos =  new ArrayList<Tipo>();

    @PostConstruct
    public final void inicializar() {
        lVistaUsuario = (VistaUsuario) Util.obtenerVista("#{vistaUsuario}");
        
        //TODO verificar dominio para este tipo
        Tipo tipoEntity = tipoModel.buscarPorDominioTipo("Tipo", 1);
        
        this.setId(tipoEntity.getIdTipo());
        this.setNombre(tipoEntity.getNombre());
        this.setDominio(tipoEntity.getDominio());
        
        tipos = tipoModel.listarPorDominio("TIPO");
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public EstadoEntity getEstado() {
        return estado;
    }

    public void setEstado(EstadoEntity estado) {
        this.estado = estado;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public List<Tipo> getTipos(){
        return this.tipos;
    }
    
    public void abrirTipo(ActionEvent pEvent){
    if (!pEvent.getPhaseId().equals(PhaseId.INVOKE_APPLICATION)) {
            pEvent.setPhaseId(PhaseId.INVOKE_APPLICATION);
            pEvent.queue();
            return;
        }
        this.tipoSeleccionado = (Tipo) pEvent.getComponent().getAttributes().get("tipoSeleccionado");
        this.setId(tipoSeleccionado.getIdTipo());
        this.setNombre(tipoSeleccionado.getNombre());
        this.setDominio(tipoSeleccionado.getDominio());
        Util.navegar("tipo_detalle");
    }
}
