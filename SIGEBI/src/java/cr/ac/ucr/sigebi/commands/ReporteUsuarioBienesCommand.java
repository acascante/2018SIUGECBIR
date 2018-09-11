/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.commands;

/**
 *
 * @author alvaro.cascante
 */
public class ReporteUsuarioBienesCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long idTipo;
    private Integer idOrden;
    private Integer idOrden1;
    private Integer idOrden2;
    private Integer idOrden3;
    private String idUsuario;
    private String identificacion;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteUsuarioBienesCommand() { 
        super();
        this.idTipo = -1L;
        this.idOrden = -1;
        this.idOrden1 = -1;
        this.idOrden2 = -1;
        this.idOrden3 = -1;
        this.idUsuario = "";
        this.identificacion = "";
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public Integer getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public Integer getIdOrden1() {
        return idOrden1;
    }

    public void setIdOrden1(Integer idOrden1) {
        this.idOrden1 = idOrden1;
    }

    public Integer getIdOrden2() {
        return idOrden2;
    }

    public void setIdOrden2(Integer idOrden2) {
        this.idOrden2 = idOrden2;
    }

    public Integer getIdOrden3() {
        return idOrden3;
    }

    public void setIdOrden3(Integer idOrden3) {
        this.idOrden3 = idOrden3;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    //</editor-fold>
}