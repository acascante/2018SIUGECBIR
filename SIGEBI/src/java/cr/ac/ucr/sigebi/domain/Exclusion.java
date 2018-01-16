/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "Exclusion")
@Table(name = "SIGEBI_OAF.SIGB_EXCLUSION")
@PrimaryKeyJoinColumn(name="ID_SOLICITUD", referencedColumnName = "ID_SOLICITUD")
@DiscriminatorValue("1")
public class Exclusion extends Solicitud {
    
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    @ManyToOne(fetch = FetchType.EAGER)
    private Tipo tipoExclusion;
    
    @Transient
    private List<ExclusionDetalle> detalles;

    //<editor-fold defaultstate="collapsed" desc="Get's y Set's">
    public Tipo getTipoExclusion() {
        return tipoExclusion;
    }

    public void setTipoExclusion(Tipo tipoExclusion) {
        this.tipoExclusion = tipoExclusion;
    }

    public List<ExclusionDetalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ExclusionDetalle> detalles) {
        this.detalles = detalles;
    }
    //</editor-fold> 
}
