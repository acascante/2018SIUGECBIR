/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import cr.ac.ucr.sigebi.utils.Constantes;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author jairo.cisneros
 */
@Entity(name = "RegistroMovimientoAsignacionPlaca")
@Table(name = "SIGEBI_OAF.SIGB_REGISTRO_MOV_ASIG_PLACA")
@DiscriminatorValue("5")
@PrimaryKeyJoinColumn(name = "ID_REGISTRO_MOVIMIENTO", referencedColumnName = "ID_REGISTRO_MOVIMIENTO")
public class RegistroMovimientoAsignacionPlaca extends RegistroMovimiento implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @ManyToOne
    @JoinColumn(name = "ID_ASIGNACION", referencedColumnName = "ID_ASIGNACION")
    private AsignacionPlaca asignacionPlaca;
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public RegistroMovimientoAsignacionPlaca() {
    }

    public RegistroMovimientoAsignacionPlaca(Tipo tipo, String observacion, Integer telefono, Date fecha, Usuario usuario, Estado estado, AsignacionPlaca asignacionPlaca) {
        super(tipo, observacion, telefono, fecha, usuario, estado, Constantes.DISCRIMINATOR_REGISTRO_MOVIMIENTO_CONVENIO);
        this.asignacionPlaca = asignacionPlaca;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="GET's y SET's">
   
    public AsignacionPlaca getAsignacionPlaca() {
        return asignacionPlaca;
    }

    public void setAsignacionPlaca(AsignacionPlaca asignacionPlaca) {
        this.asignacionPlaca = asignacionPlaca;
    }
        
    //</editor-fold>

}