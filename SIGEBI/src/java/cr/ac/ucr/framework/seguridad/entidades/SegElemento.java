package cr.ac.ucr.framework.seguridad.entidades;

import cr.ac.ucr.framework.seguridad.ObjetoBase;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author huevo
 */
@Entity(name = "SegElemento")
@Table(name = "SEGURIDAD_ELEMENTO")
@NamedQueries({
    @NamedQuery(name = "SegElemento.findAll", query = "SELECT s FROM SegElemento s"),
    @NamedQuery(name = "SegElemento.findByIdElemento", query = "SELECT s FROM SegElemento s WHERE s.idElemento = :idElemento"),
    @NamedQuery(name = "SegElemento.findElementosHijos", query = "SELECT s FROM SegElemento s WHERE s.idElementoPadre.idElemento = :idElemento"),
    @NamedQuery(name = "SegElemento.findByDscNombre", query = "SELECT s FROM SegElemento s WHERE s.dscNombre = :dscNombre"),
    @NamedQuery(name = "SegElemento.findByNumOrden", query = "SELECT s FROM SegElemento s WHERE s.numOrden = :numOrden"),
    @NamedQuery(name = "SegElemento.findByIndVigencia", query = "SELECT s FROM SegElemento s WHERE s.indVigencia = :indVigencia"),
    @NamedQuery(name = "SegElemento.findByFecVigenciaInicio", query = "SELECT s FROM SegElemento s WHERE s.fecVigenciaInicio = :fecVigenciaInicio"),
    @NamedQuery(name = "SegElemento.findByFecVigenciaFin", query = "SELECT s FROM SegElemento s WHERE s.fecVigenciaFin = :fecVigenciaFin")})
@NamedNativeQueries({
    @NamedNativeQuery(name = "SegElemento.findElementosSistemaUnidad", resultClass = SegElemento.class,
    query = "select distinct e.* from seguridad_unidad_usuario uu, seguridad_perfil p, "
    + "seguridad_elemento_perfil ep,  seguridad_elemento e, seguridad_modulo_elemento em, "
    + "seguridad_modulo m, seguridad_sistema_modulo ms, seguridad_sistema s "
    + " where   uu.id_usuario = ? AND "
    + " uu.id_unidad_ejecutora = ? AND "
    + " uu.id_empresa = ? AND "
    + " uu.ind_excepcion = '0' AND "
    + " uu.id_perfil = p.id_perfil AND "
    + " p.id_sistema_administrador IS NULL AND "
    + " ep.id_perfil = p.id_perfil AND "
    + " ep.id_elemento = e.id_elemento AND "
    + " em.id_elemento = e.id_elemento AND "
    + " em.id_modulo = m.id_modulo AND "
    + " ms.id_modulo = m.id_modulo AND "
    + " ms.id_sistema = s.id_sistema AND "
    + " s.id_sistema = ? AND "
    + " ((e.ind_vigencia = '1') OR (e.ind_vigencia = '2' "
    + "AND trunc(e.fec_vigencia_inicio) >= to_date(?,?) "
    + "AND trunc(e.fec_vigencia_fin) <= to_date(?,?)))"
    + "AND e.id_tipo_elemento in (?,?) AND "
    + "m.id_modulo in (?,?) "),
    @NamedNativeQuery(name = "SegElemento.findElementosPerfil", resultClass = SegElemento.class,
    query = "SELECT e.* FROM seguridad_elemento_perfil p,seguridad_elemento e where p.id_perfil=? "
    + " and e.id_tipo_elemento in (?,?) and p.id_elemento=e.id_elemento and p.ID_ELEMENTO = e.ID_ELEMENTO")})
public class SegElemento extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "ID_ELEMENTO")
    private Integer idElemento;
    @Basic(optional = false)
    @Column(name = "DSC_NOMBRE")
    private String dscNombre;
    @Column(name = "NUM_ORDEN")
    private Integer numOrden;
    @Column(name = "IND_VIGENCIA")
    private Integer indVigencia;
    @Column(name = "FEC_VIGENCIA_INICIO")
    @Temporal(TemporalType.DATE)
    private Date fecVigenciaInicio;
    @Column(name = "FEC_VIGENCIA_FIN")
    @Temporal(TemporalType.DATE)
    private Date fecVigenciaFin;
    @Column(name = "REGLA_NAVEGACION")
    private String regla_navegacion;
    @JoinColumn(name = "ID_ELEMENTO_PADRE", referencedColumnName = "ID_ELEMENTO")
    @ManyToOne(fetch = FetchType.EAGER)
    private SegElemento idElementoPadre;
    public transient static final String SECUENCIA = "SEQ_SEGURIDAD_ELEMENTO";

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      Constructores     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    public SegElemento() {
    }

    public SegElemento(Integer idElemento) {
        this.idElemento = idElemento;
    }

    public SegElemento(Integer idElemento, String dscNombre) {
        this.idElemento = idElemento;
        this.dscNombre = dscNombre;
    }

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      SET's y GET's     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    public Integer getIdElemento() {
        return idElemento;
    }

    public void setIdElemento(Integer idElemento) {
        this.idElemento = idElemento;
    }

    public String getDscNombre() {
        return dscNombre;
    }

    public void setDscNombre(String dscNombre) {
        this.dscNombre = dscNombre;
    }

    public Integer getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(Integer numOrden) {
        this.numOrden = numOrden;
    }

    public Integer getIndVigencia() {
        return indVigencia;
    }

    public void setIndVigencia(Integer indVigencia) {
        this.indVigencia = indVigencia;
    }

    public Date getFecVigenciaInicio() {
        return fecVigenciaInicio;
    }

    public void setFecVigenciaInicio(Date fecVigenciaInicio) {
        this.fecVigenciaInicio = fecVigenciaInicio;
    }

    public Date getFecVigenciaFin() {
        return fecVigenciaFin;
    }

    public void setFecVigenciaFin(Date fecVigenciaFin) {
        this.fecVigenciaFin = fecVigenciaFin;
    }

    public String getRegla_navegacion() {
        return regla_navegacion;
    }

    public void setRegla_navegacion(String regla_navegacion) {
        this.regla_navegacion = regla_navegacion;
    }

    public SegElemento getIdElementoPadre() {
        return idElementoPadre;
    }

    public void setIdElementoPadre(SegElemento idElementoPadre) {
        this.idElementoPadre = idElementoPadre;
    }

    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idElemento != null ? idElemento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegElemento)) {
            return false;
        }
        SegElemento other = (SegElemento) object;
        if ((this.idElemento == null && other.idElemento != null) || (this.idElemento != null && !this.idElemento.equals(other.idElemento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.SegElemento[idElemento=" + idElemento + "]";
    }
}
