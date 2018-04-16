/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;

/**
 *
 * @author jairo.cisneros
 */
public class ReporteSobrantes implements Serializable {
    
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private SubClasificacion subClasificacion;
    private ViewResumenBien resumenBien;
    private UnidadEjecutora unidadEjecutora;
    private Estado estado;
    private Identificacion identificacion;
    private String funcionario;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteSobrantes() {}

    public ReporteSobrantes(Bien bien) {
        this.subClasificacion = bien.getSubClasificacion();
        this.resumenBien = bien.getResumenBien();
        this.unidadEjecutora = bien.getUnidadEjecutora();
        this.estado = bien.getEstado();
        this.identificacion = bien.getIdentificacion();
        this.funcionario = "Pendiente Definir"; 
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
    public SubClasificacion getSubClasificacion() {
        return subClasificacion;
    }
    public String getClasificacion() {
        return subClasificacion.getClasificacion().getNombre();
    }

    public void setSubClasificacion(SubClasificacion subClasificacion) {
        this.subClasificacion = subClasificacion;
    }

    public ViewResumenBien getResumenBien() {
        return resumenBien;
    }
    public String getMarca() {
        return resumenBien.getMarca();
    }
    public String getModelo() {
        return resumenBien.getModelo();
    }
    public String getSerie() {
        return resumenBien.getSerie();
    }
    public void setResumenBien(ViewResumenBien resumenBien) {
        this.resumenBien = resumenBien;
    }

    public UnidadEjecutora getUnidadEjecutora() {
        return unidadEjecutora;
    }
    public String getNombreUnidadEjecutora() {
        return unidadEjecutora.getDescripcion();
    }
    public void setUnidadEjecutora(UnidadEjecutora unidadEjecutora) {
        this.unidadEjecutora = unidadEjecutora;
    }

    public Estado getEstado() {
        return estado;
    }
    public String getNombreEstado() {
        return estado.getNombre();
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Identificacion getIdentificacion() {
        return identificacion;
    }
    public String getDescripcionIdentificacion() {
        return identificacion.getIdentificacion();
    }

    public void setIdentificacion(Identificacion identificacion) {
        this.identificacion = identificacion;
    }
    
    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }
    //</editor-fold>
}
