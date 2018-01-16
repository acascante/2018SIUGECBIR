package cr.ac.ucr.framework.seguridad.entidades;

import cr.ac.ucr.framework.seguridad.ObjetoBase;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;


/**
 *
 * @author Adrian Zamora Villalta
 */
@Entity
@org.hibernate.annotations.Entity(dynamicUpdate = true)
@Table(name = "SEGURIDAD_USUARIO")
@SqlResultSetMappings({
@SqlResultSetMapping(name="totalUsuarios",
columns=@ColumnResult(name="TOT_USUARIOS"))
})//SEGURIDAD_PERFIL-APLICADORSAE
@NamedQueries({
    @NamedQuery(name = "SegUsuario.findAll", query = "SELECT s FROM SegUsuario s"),
    @NamedQuery(name = "SegUsuario.findByIdUsuario", query = "SELECT s FROM SegUsuario s WHERE s.idUsuario = :idUsuario"),
    @NamedQuery(name = "SegUsuario.findByIdUsuarioLower", query="SELECT s FROM SegUsuario s WHERE lower(s.idUsuario) =:idUsuario"),
    @NamedQuery(name = "SegUsuario.findByCodClave", query = "SELECT s FROM SegUsuario s WHERE s.codClave = :codClave"),
    @NamedQuery(name = "SegUsuario.findByIdUsuarioAndCodClave", query = "SELECT s FROM SegUsuario s WHERE lower(s.idUsuario) = :idUsuario AND s.codClave = :codClave")}
)
@NamedNativeQueries({
@NamedNativeQuery(name="SegUnidadUsuario.usuariosJefes",resultClass=SegUsuario.class,
        query="SELECT DISTINCT U.* FROM SEGURIDAD_USUARIO U, SEGURIDAD_UNIDAD_USUARIO UU "
        + "WHERE U.ID_USUARIO = UU.ID_USUARIO AND ID_PERFIL IN (SELECT TO_NUMBER(VALOR_PARAMETRO) "
        + "FROM SGC_PARAMETRO WHERE ID_PARAMETRO "
        + "IN ('SEGURIDAD_PERFIL-JEFATURA_DIEA','SEGURIDAD_PERFIL-EVALUADOR'))"),
@NamedNativeQuery(name="SegUnidadUsuario.usuariosEvaluadores",resultClass=SegUsuario.class,
        query="SELECT * FROM ( SELECT a.*, ROWNUM RNUM FROM ("
        + "SELECT DISTINCT U.* FROM SEGURIDAD_USUARIO U, SEGURIDAD_UNIDAD_USUARIO UU "
        + "WHERE U.ID_USUARIO = UU.ID_USUARIO AND ID_PERFIL IN (SELECT TO_NUMBER(VALOR_PARAMETRO) "
        + "FROM SGC_PARAMETRO WHERE ID_PARAMETRO "
        + "IN ('SEGURIDAD_PERFIL-JEFATURA_DIEA','SEGURIDAD_PERFIL-EVALUADOR'))"
        + "AND UPPER(U.ID_USUARIO) LIKE '%'|| UPPER(:pFiltroCodUsuario)||'%' "
        + "AND UPPER(U.DSC_NOMBRE_COMPLETO) LIKE '%'|| UPPER(:pFiltroNombreUsuario)||'%' "
        + ") a WHERE ROWNUM <= :pUltimoRegistro ) WHERE RNUM >= :pPrimerRegistro"),
@NamedNativeQuery(name="SegUnidadUsuario.usuariosAplicadores",resultClass=SegUsuario.class,
        query="SELECT * FROM ( SELECT a.*, ROWNUM RNUM FROM ("
        + "SELECT DISTINCT U.* FROM SEGURIDAD_USUARIO U, SEGURIDAD_UNIDAD_USUARIO UU "
        + "WHERE U.ID_USUARIO = UU.ID_USUARIO AND ID_PERFIL IN (SELECT TO_NUMBER(VALOR_PARAMETRO) "
        + "FROM SGC_PARAMETRO WHERE ID_PARAMETRO = 'SEGURIDAD_PERFIL-APLICADORSAE')"
        + "AND UPPER(U.ID_USUARIO) LIKE '%'|| UPPER(:pFiltroCodUsuario)||'%' "
        + "AND UPPER(U.DSC_NOMBRE_COMPLETO) LIKE '%'|| UPPER(:pFiltroNombreUsuario)||'%' "
        + ") a WHERE ROWNUM <= :pUltimoRegistro ) WHERE RNUM >= :pPrimerRegistro"),
@NamedNativeQuery(name="SegUnidadUsuario.cantidadUsuariosEvaluadores",resultSetMapping="totalUsuarios",
        query="SELECT COUNT(1) TOT_USUARIOS FROM("
        + "SELECT DISTINCT U.* FROM SEGURIDAD_USUARIO U, SEGURIDAD_UNIDAD_USUARIO UU "
        + "WHERE U.ID_USUARIO = UU.ID_USUARIO AND ID_PERFIL IN (SELECT TO_NUMBER(VALOR_PARAMETRO) "
        + "FROM SGC_PARAMETRO WHERE ID_PARAMETRO "
        + "IN ('SEGURIDAD_PERFIL-JEFATURA_DIEA','SEGURIDAD_PERFIL-EVALUADOR'))"
        + "AND UPPER(U.ID_USUARIO) LIKE '%'|| UPPER(:pFiltroCodUsuario)||'%' "
        + "AND UPPER(U.DSC_NOMBRE_COMPLETO) LIKE '%'|| UPPER(:pFiltroNombreUsuario)||'%') "),
@NamedNativeQuery(name="SegUnidadUsuario.cantidadUsuariosAplicadores",resultSetMapping="totalUsuarios",
        query="SELECT COUNT(1) TOT_USUARIOS FROM("
        + "SELECT DISTINCT U.* FROM SEGURIDAD_USUARIO U, SEGURIDAD_UNIDAD_USUARIO UU "
        + "WHERE U.ID_USUARIO = UU.ID_USUARIO AND ID_PERFIL IN (SELECT TO_NUMBER(VALOR_PARAMETRO) "
        + "FROM SGC_PARAMETRO WHERE ID_PARAMETRO = 'SEGURIDAD_PERFIL-APLICADORSAE')"
        + "AND UPPER(U.ID_USUARIO) LIKE '%'|| UPPER(:pFiltroCodUsuario)||'%' "
        + "AND UPPER(U.DSC_NOMBRE_COMPLETO) LIKE '%'|| UPPER(:pFiltroNombreUsuario)||'%') ")
})        

public class SegUsuario extends ObjetoBase implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Basic(optional = false)
    @Column(name = "ID_USUARIO")
    private String idUsuario;

    @Column(name = "DSC_NOMBRE_COMPLETO")
    private String nombre_completo;

    @Column(name = "COD_CLAVE")
    private String codClave;

    @Column(name = "NUM_TELEFONO1")
    private String telefono1;

    @Column(name = "NUM_TELEFONO2")
    private String telefono2;

    @Column(name = "NUM_EXTENSION1")
    private String extension1;

    @Column(name = "NUM_EXTENSION2")
    private String extension2;

    @Column(name = "DSC_EMAIL")
    private String correo;

    private transient boolean usuarioSeleccionado;
    

   /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>>      Constructores     <<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/

    public SegUsuario() {
    }

    public SegUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public SegUsuario(String idUsuario, String contrasena) {
        this.idUsuario = idUsuario;
        this.codClave = contrasena;
    }

    public SegUsuario(String idUsuario, String nombre_completo, String contrasena) {
        this.idUsuario = idUsuario;
        this.codClave = contrasena;
        this.nombre_completo = nombre_completo;
    }


    
   /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>>      SET's y GET's     <<>><<>><<>><<>><<>><<>>
    *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getCodClave() {
        return codClave;
    }

    public void setCodClave(String codClave) {
        this.codClave = codClave;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getExtension1() {
        return extension1;
    }

    public void setExtension1(String extension1) {
        this.extension1 = extension1;
    }

    public String getExtension2() {
        return extension2;
    }

    public void setExtension2(String extension2) {
        this.extension2 = extension2;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }
    
    public boolean isUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }
   
    public void setUsuarioSeleccionado(boolean usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    

    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegUsuario)) {
            return false;
        }
        SegUsuario other = (SegUsuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idUsuario;
    }

    

}
