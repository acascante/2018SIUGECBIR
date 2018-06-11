package cr.ac.ucr.sigebi.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author alvaro.cascante
 */
@Entity(name = "ReporteBien")
@Table(name = "SIGEBI_OAF.SIGB_REPORTE_BIEN")
@SequenceGenerator(name="sqReporteBien", sequenceName = "SIGEBI_OAF.SGB_SQ_REPORTE_BIEN", initialValue=1, allocationSize=1)
public class ReporteBien implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Atributos">
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sqReporteBien")
    @Column(name = "ID_REPORTE_BIEN")
    private Long id;
    
    @Column(name = "NOMBRE")
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "ID_TIPO", referencedColumnName = "ID_TIPO")
    private Tipo tipoReporte;
    
    @Column(name = "TAMANO_FUENTE")
    private Integer tamanoFuente;
    
    @Column(name = "DESCRIPCION")
    private String descripcion;
    
    @Transient
    private List<CampoReporteBien> camposReporte;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public ReporteBien() {}
    
    public ReporteBien(Long id, String nombre, Usuario usuario, List<CampoReporteBien> camposReporte, Tipo tipoReporte, Integer tamanoFuente, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.camposReporte = camposReporte;
        this.tipoReporte = tipoReporte;
        this.tamanoFuente = tamanoFuente;
        this.descripcion = descripcion;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="GET's Set't">
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Tipo getTipoReporte() {
        return tipoReporte;
    }

    public void setTipoReporte(Tipo tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getTamanoFuente() {
        return tamanoFuente;
    }

    public void setTamanoFuente(Integer tamanoFuente) {
        this.tamanoFuente = tamanoFuente;
    }
    
    public List<CampoReporteBien> getCamposReporte() {
        if (this.camposReporte == null)
            this.camposReporte = new ArrayList<CampoReporteBien>();
        return camposReporte;
    }

    public void setCamposReporte(List<CampoReporteBien> camposReporte) {
        this.camposReporte = camposReporte;
    }
    //</editor-fold>
}