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
    private Long idGrupo;
    private Long idOrden;
    private Long idOrden1;
    private Long idOrden2;
    private Long idOrden3;
    private String usuario;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public GenerarReporteRolesUsuariosCommand() { 
        super();
        this.idTipo = -1L;
        this.idGrupo = -1L;
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

    public Long getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(Long idGrupo) {
        this.idGrupo = idGrupo;
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