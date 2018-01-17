/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cr.ac.ucr.sigebi.domain;

/**
 *
 * @author alvaro.cascante
 */
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "BienDetalleCaracteristicas")
@Table(name = "SIGEBI_OAF.V_SIGB_BIEN_CARACTERISTICA")

public class BienDetalleCaracteristicas implements Serializable {
    
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "MARCA")
    private String marca;
    
    @Column(name = "MODELO")
    private String modelo;
    
    @Column(name = "SERIE")
    private String serie;

    //<editor-fold defaultstate="collapsed" desc="Gets y Sets">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    //</editor-fold>
}
