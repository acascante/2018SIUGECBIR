package cr.ac.ucr.framework.seguridad.entidades;

import cr.ac.ucr.framework.seguridad.ObjetoBase;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FetchType;
import javax.persistence.FieldResult;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

/**
 *
 * @author Adrián Zamora Villalta
 */
@Entity
@Table(name = "SEGURIDAD_UNIDAD_USUARIO")
@NamedQueries({
    @NamedQuery(name = "SegUnidadUsuario.findAll", query = "SELECT s FROM SegUnidadUsuario s"),
    @NamedQuery(name = "SegUnidadUsuario.findByIdUnidadEjecutora", query = "SELECT s FROM SegUnidadUsuario s WHERE s.unidadUsuarioLlave.idUnidadEjecutora = :idUnidadEjecutora"),
    @NamedQuery(name = "SegUnidadUsuario.findByIdUsuario", query = "SELECT s FROM SegUnidadUsuario s WHERE s.unidadUsuarioLlave.idUsuario = :idUsuario"),
    @NamedQuery(name = "SegUnidadUsuario.findByIdEmpresa", query = "SELECT s FROM SegUnidadUsuario s WHERE s.unidadUsuarioLlave.idEmpresa = :idEmpresa"),
    @NamedQuery(name = "SegUnidadUsuario.findByIdPerfil", query = "SELECT s FROM SegUnidadUsuario s WHERE s.unidadUsuarioLlave.idPerfil = :idPerfil"),
    @NamedQuery(name = "SegUnidadUsuario.findByUsuarioUnidad", query = "SELECT s FROM SegUnidadUsuario s WHERE s.unidadUsuarioLlave.idEmpresa = :idEmpresa AND s.unidadUsuarioLlave.idUnidadEjecutora = :idUnidadEjecutora AND s.unidadUsuarioLlave.idUsuario = :idUsuario")
})

//Código necesario seguridad
/*@NamedNativeQuery(
            name = "SegUnidadUsuario.findUnidadesUsuario",
            query = "CALL SEGURIDAD.FC_SEG_UNIDADES_X_USUARIO_SRF(:pIdUsuario, :pIdSistema)"
            , resultClass = SegUnidadUsuarioLlave.class),*/
@NamedNativeQueries({
    /*@NamedNativeQuery(
            name = "SegUnidadUsuario.findUnidadesUsuario",
            query = "SELECT SEGURIDAD.FC_SEG_UNIDADES_X_USUARIO_SRF(:pIdUsuario, :pIdSistema) FROM DUAL",
            resultSetMapping = "findUnidadesUsuario"),*/
    @NamedNativeQuery(name = "SegUnidadUsuario.findUnidadesUsuario", resultClass = SegUnidadUsuario.class, 
            query = "{ ? = call SEGURIDAD.FC_SEG_UNIDADES_X_USUARIO_SRF(:pIdUsuario, :pIdSistema) }",
            hints = { @QueryHint(name = "org.hibernate.callable", value = "true")}),
    @NamedNativeQuery(name = "SegUnidadUsuario.findUnidadesUsuario2", resultClass = SegUnidadUsuario.class,
            query = "select distinct uu.* from seguridad_unidad_usuario uu, "
            + "seguridad_perfil p, seguridad_elemento_perfil ep,  "
            + "seguridad_elemento e, seguridad_modulo_elemento em, "
            + "seguridad_modulo m, seguridad_sistema_modulo ms, "
            + "seguridad_sistema s where   uu.id_usuario = ? "
            + "AND uu.ind_excepcion = '0' AND uu.id_perfil = p.id_perfil "
            + "AND ep.id_perfil = p.id_perfil AND ep.id_elemento = e.id_elemento "
            + "AND em.id_elemento = e.id_elemento AND em.id_modulo = m.id_modulo "
            + "AND ms.id_modulo = m.id_modulo AND ms.id_sistema = s.id_sistema "
            + "AND s.id_sistema = ? AND ((e.ind_vigencia = '1') "
            + "OR (e.ind_vigencia = '2' AND trunc(e.fec_vigencia_inicio) >= to_date(?,?) "
            + "AND trunc(e.fec_vigencia_fin) <= to_date(?,?)))")
})

public class SegUnidadUsuario extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    protected SegUnidadUsuarioLlave unidadUsuarioLlave;

    @Basic(optional = false)
    @Column(name = "IND_EXCEPCION")
    private Boolean ind_excepcion;

    @JoinColumn(name = "ID_PERFIL", referencedColumnName = "ID_PERFIL", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private SegPerfil perfil;

    @JoinColumn(name = "ID_USUARIO", referencedColumnName = "ID_USUARIO", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private SegUsuario usuario;

    @JoinColumns({
        @JoinColumn(name = "ID_EMPRESA", referencedColumnName = "ID_EMPRESA", insertable = false, updatable = false),
        @JoinColumn(name = "ID_UNIDAD_EJECUTORA", referencedColumnName = "ID_UNIDAD_EJECUTORA", insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private SegUnidadEjecutora unidad_ejecutora;

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      Constructores     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    public SegUnidadUsuario() {
    }

    public SegUnidadUsuario(SegUnidadUsuarioLlave sisUnidadUsuarioPK) {
        this.unidadUsuarioLlave = sisUnidadUsuarioPK;
    }

    public SegUnidadUsuario(Integer idUnidadEjecutora, String idUsuario, Integer idEmpresa, Integer idPerfil) {
        this.unidadUsuarioLlave = new SegUnidadUsuarioLlave(idUnidadEjecutora, idUsuario, idEmpresa, idPerfil);
    }

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      SET's y GET's     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    public SegPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(SegPerfil perfil) {
        this.perfil = perfil;
    }

    public SegUnidadUsuarioLlave getUnidadUsuarioLlave() {
        return unidadUsuarioLlave;
    }

    public void setUnidadUsuarioLlave(SegUnidadUsuarioLlave unidadUsuarioLlave) {
        this.unidadUsuarioLlave = unidadUsuarioLlave;
    }

    public SegUnidadEjecutora getUnidad_ejecutora() {
        return unidad_ejecutora;
    }

    public void setUnidad_ejecutora(SegUnidadEjecutora unidad_ejecutora) {
        this.unidad_ejecutora = unidad_ejecutora;
    }

    public SegUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(SegUsuario usuario) {
        this.usuario = usuario;
    }

    public Boolean getInd_excepcion() {
        return ind_excepcion;
    }

    public void setInd_excepcion(Boolean ind_excepcion) {
        this.ind_excepcion = ind_excepcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (unidadUsuarioLlave != null ? unidadUsuarioLlave.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegUnidadUsuario)) {
            return false;
        }
        SegUnidadUsuario other = (SegUnidadUsuario) object;
        if ((this.unidadUsuarioLlave == null && other.unidadUsuarioLlave != null) || (this.unidadUsuarioLlave != null && !this.unidadUsuarioLlave.equals(other.unidadUsuarioLlave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.ucr.geco.entidades.SisUnidadUsuario[sisUnidadUsuarioPK=" + unidadUsuarioLlave + "]";
    }

}
