/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

import cr.ac.ucr.sigebi.domain.CampoReporteBien;
import cr.ac.ucr.sigebi.domain.ReporteBien;
import cr.ac.ucr.sigebi.domain.Tipo;
import cr.ac.ucr.sigebi.domain.Usuario;
import java.util.List;

/**
 *
 * @author alvaro.cascante
 */
public class GenerarReporteSobrantesCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long idTipo;
    private Long idEstado;
    private Long idOrden;
    private Long idOrden1;
    private Long idOrden2;
    private Long idOrden3;
    private String identificacion;
    private String descripcion;
    private String marca;
    private String modelo;
    private String serie;
    private String usuario;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public GenerarReporteSobrantesCommand() { 
        super();
        this.idTipo = -1L;
        this.idEstado = -1L;
        this.idOrden = -1L;
        this.idOrden1 = -1L;
        this.idOrden2 = -1L;
        this.idOrden3 = -1L;
    }    
    //</editor-fold>
     
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public Long getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Long idOrden) {
        this.idOrden = idOrden;
    }

    public Long getIdOrden1() {
        return idOrden1;
    }

    public void setIdOrden1(Long idOrden1) {
        this.idOrden1 = idOrden1;
    }

    public Long getIdOrden2() {
        return idOrden2;
    }

    public void setIdOrden2(Long idOrden2) {
        this.idOrden2 = idOrden2;
    }

    public Long getIdOrden3() {
        return idOrden3;
    }

    public void setIdOrden3(Long idOrden3) {
        this.idOrden3 = idOrden3;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }    
    //</editor-fold>
}