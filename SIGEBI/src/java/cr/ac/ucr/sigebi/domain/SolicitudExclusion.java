/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "SolicitudExclusion")
@Table(name = "SIGEBI_OAF.SIGB_SOLITUD_EXCLUSION")
@PrimaryKeyJoinColumn(name = "ID_SOLICITUD", referencedColumnName = "ID_SOLICITUD")
@DiscriminatorValue("1")
public class SolicitudExclusion extends Solicitud {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipo tipoExclusion;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Tipo getTipoExclusion() {
        return tipoExclusion;
    }

    public void setTipoExclusion(Tipo tipoExclusion) {
        this.tipoExclusion = tipoExclusion;
    }
    //</editor-fold> 
}
