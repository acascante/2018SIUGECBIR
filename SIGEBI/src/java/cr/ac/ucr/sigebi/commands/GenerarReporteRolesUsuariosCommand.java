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
public class GenerarReporteRolesUsuariosCommand {
        
    //<editor-fold defaultstate="collapsed" desc="Atributos">
    private Long idTipo;
    private Integer idGrupo;
    private Integer idOrden;
    private Integer idOrden1;
    private Integer idOrden2;
    private Integer idOrden3;
    private String usuario;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public GenerarReporteRolesUsuariosCommand() { 
        super();
        this.idTipo = -1L;
        this.idGrupo = -1;
        this.idOrden = -1;
        this.idOrden1 = -1;
        this.idOrden2 = -1;
        this.idOrden3 = -1;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Long getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(Long idTipo) {
        this.idTipo = idTipo;
    }

    public Integer getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Integer idGrupo) {
        this.idGrupo = idGrupo;
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    //</editor-fold>
}