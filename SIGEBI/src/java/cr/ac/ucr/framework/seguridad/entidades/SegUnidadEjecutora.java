package cr.ac.ucr.framework.seguridad.entidades;

import cr.ac.ucr.framework.seguridad.ObjetoBase;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;

/**
 *
 * @author Adam M. Gamboa González
 */
@Entity
@Table(name = "SEGURIDAD_UNIDAD_EJECUTORA")
@NamedQueries({
    @NamedQuery(name = "SegUnidadEjecutora.findUnidadEjecutoraPadre", query = "SELECT s FROM SegUnidadEjecutora s WHERE s.unidadEjecutoraLlave.idUnidadEjecutora = :idUnidadEjecutoraPadre"),
    @NamedQuery(name = "SegUnidadEjecutora.findAll", query = "SELECT s FROM SegUnidadEjecutora s"),
    @NamedQuery(name = "SegUnidadEjecutora.encontrarTodas", query = "SELECT s FROM SegUnidadEjecutora s WHERE s.idTipoUnidad IS NOT NULL"),
    @NamedQuery(name = "SegUnidadEjecutora.findByIdUnidadEjecutora", query = "SELECT s FROM SegUnidadEjecutora s WHERE s.unidadEjecutoraLlave.idUnidadEjecutora = :idUnidadEjecutora and s.unidadEjecutoraLlave.idEmpresa = :idEmpresa"),
    @NamedQuery(name = "SegUnidadEjecutora.findByDscUnidadEjecutora", query = "SELECT s FROM SegUnidadEjecutora s WHERE s.dscUnidadEjecutora = :dscUnidadEjecutora"),
    @NamedQuery(name = "SegUnidadEjecutora.findByIdEmpresa", query = "SELECT s FROM SegUnidadEjecutora s WHERE s.unidadEjecutoraLlave.idEmpresa = :idEmpresa"),
    @NamedQuery(name = "SegUnidadEjecutora.findByNumUnidadEjecSiaf", query = "SELECT s FROM SegUnidadEjecutora s WHERE s.codigoReferencia = :numUnidadEjecSiaf"),
    @NamedQuery(name = "SegUnidadEjecutora.findByIndHabilitado", query = "SELECT s FROM SegUnidadEjecutora s WHERE s.indHabilitado = :indHabilitado")})
@SqlResultSetMappings({
    @SqlResultSetMapping(name = "id_escuela", columns =
    @ColumnResult(name = "ID_ESC")),
    @SqlResultSetMapping(name = "cantidad", columns =
    @ColumnResult(name = "CANTIDAD")),
    @SqlResultSetMapping(name = "unidadesPropuesta",
    columns = {
        @ColumnResult(name = "ID_UNIDAD_EJECUTORA"),
        @ColumnResult(name = "DSC_UNIDAD_EJECUTORA"),
        @ColumnResult(name = "COD_REFERENCIA")}),
    @SqlResultSetMapping(name = "unidadInfo",
    columns = {
        @ColumnResult(name = "DSC_UNIDAD_EJECUTORA"),
        @ColumnResult(name = "DSC_TIPO_UNIDAD")})})
@NamedNativeQueries({
    @NamedNativeQuery(name = "SegUnidadEjecutora.esSedeRegional",
    query = "SELECT COUNT(1) CANTIDAD FROM SEGURIDAD_UNIDAD_EJECUTORA "
    + "WHERE COD_REFERENCIA = :pEscuela "
    + "AND COD_REFERENCIA LIKE '6%' "
    + "AND ID_TIPO_UNIDAD IN("
    + "SELECT VALOR_PARAMETRO FROM SGC_PARAMETRO "
    + "WHERE ID_PARAMETRO = 'SEGURIDAD_TIPO_UNIDAD-COORDINACION_DOCENCIA_S_R')",
    resultSetMapping = "cantidad"),
    @NamedNativeQuery(name = "SegUnidadEjecutora.findEscuelas",
    query = "SELECT * "
    + "FROM SEGURIDAD_UNIDAD_EJECUTORA "
    + "WHERE ID_TIPO_UNIDAD IS NOT NULL AND "
    + "ID_TIPO_UNIDAD = 'UA'",
    resultClass = SegUnidadEjecutora.class),
    @NamedNativeQuery(name = "SegUnidadEjecutora.findEscuelaByIdUnidadEjecutora", query = "SELECT COD_REFERENCIA  AS ID_ESC"
    + " FROM SEGURIDAD_UNIDAD_EJECUTORA"
    + " WHERE ID_UNIDAD_EJECUTORA = :pUnidadEjecutora"
    + " AND ID_TIPO_UNIDAD = :pTipoUnidad",
    resultSetMapping = "id_escuela"),
//    @NamedNativeQuery(name="SegUnidadEjecutora.buscarUnidadesParaPropuesta", 
//        query="SELECT * FROM ( SELECT a.*, ROWNUM RNUM FROM ( "
//        + "SELECT ID_UNIDAD_EJECUTORA, DSC_UNIDAD_EJECUTORA, COD_REFERENCIA "
//        + "FROM SEGURIDAD_UNIDAD_EJECUTORA "
//        + "WHERE ID_TIPO_UNIDAD IS NOT NULL AND "
//        + "ID_TIPO_UNIDAD = 'ESC' AND "
//        + "TO_CHAR(ID_UNIDAD_EJECUTORA) LIKE '%'||:pIdUnidad||'%'AND "
//        + "UPPER(DSC_UNIDAD_EJECUTORA) LIKE '%'||UPPER(:pFiltroDescripcion)||'%' "        
//        + "AND COD_REFERENCIA NOT IN ("
//        + "SELECT ESCUELA FROM SGC_PROPUESTA_UNIDAD_ACADEMICA "
//        + "WHERE NUMERO_PROPUESTA = :pNumeroPropuesta "
//        + "AND VERSION_PROPUESTA = :pVersionPropuesta) "
//        + "AND COD_REFERENCIA IN (SELECT ESCUELA "
//        + "FROM ESCUELA WHERE SUBSTR(ESCUELA,0,1) "
//        + "IN ('1','2','3','4','5','9')) "
//        + "AND COD_REFERENCIA LIKE '%'||:pFiltroEscuela||'%'"
//        + ") a WHERE ROWNUM <= :pUltimoRegistro ) WHERE RNUM >= :pPrimerRegistro",
//        resultSetMapping="unidadesPropuesta"
//     ),
    @NamedNativeQuery(name = "SegUnidadEjecutora.cantidadUnidadesParaPropuesta",
    query = "SELECT COUNT(1) AS CANTIDAD "
    + "FROM SEGURIDAD_UNIDAD_EJECUTORA "
    + "WHERE ID_TIPO_UNIDAD IS NOT NULL AND "
    + "ID_TIPO_UNIDAD = 'UA' AND "
    + "TO_CHAR(ID_UNIDAD_EJECUTORA) LIKE '%'||:pIdUnidad||'%'AND "
    + "UPPER(DSC_UNIDAD_EJECUTORA) LIKE '%'||UPPER(:pFiltroDescripcion)||'%' "
    + "AND COD_REFERENCIA NOT IN ("
    + "SELECT ESCUELA FROM SGC_PROPUESTA_UNIDAD_ACADEMICA "
    + "WHERE NUMERO_PROPUESTA = :pNumeroPropuesta "
    + "AND VERSION_PROPUESTA = :pVersionPropuesta) "
    + "AND COD_REFERENCIA IN (SELECT ESCUELA "
    + "FROM ESCUELA WHERE SUBSTR(ESCUELA,0,1) "
    + "IN ('1','2','3','4','5','9')) "
    + "AND COD_REFERENCIA LIKE '%'||:pFiltroEscuela||'%'",
    resultSetMapping = "cantidad"),
    @NamedNativeQuery(name = "SegUnidadEjecutora.nombreTipoUnidad",
    query = "SELECT UE.DSC_UNIDAD_EJECUTORA, TP.DSC_TIPO_UNIDAD "
    + "FROM SEGURIDAD_UNIDAD_EJECUTORA UE, SEGURIDAD_TIPO_UNIDAD TP "
    + "WHERE UE.ID_TIPO_UNIDAD  IN "
    + "(SELECT VALOR_PARAMETRO FROM SGC_PARAMETRO "
    + "WHERE ID_PARAMETRO IN ('SEGURIDAD_TIPO_UNIDAD-ESCUELA','SEGURIDAD_TIPO_UNIDAD-FACULTAD',"
    + "'SEGURIDAD_TIPO_UNIDAD-DIRECCION_SEDE_REGIONAL','SEGURIDAD_TIPO_UNIDAD-COORDINACION_DOCENCIA_S_R')) "
    + "AND UE.ID_TIPO_UNIDAD = TP.ID_TIPO_UNIDAD  "
    + "AND UE.ID_UNIDAD_EJECUTORA = :pIdUnidadEjecutora",
    resultSetMapping = "unidadInfo"),
    @NamedNativeQuery(name = "SegUnidadEjecutora.nombreTipoUnidadPadre",
    query = "SELECT UE.DSC_UNIDAD_EJECUTORA, TP.DSC_TIPO_UNIDAD "
    + "FROM SEGURIDAD_UNIDAD_EJECUTORA UE, SEGURIDAD_TIPO_UNIDAD TP "
    + "WHERE UE.ID_TIPO_UNIDAD  IN "
    + "(SELECT VALOR_PARAMETRO FROM SGC_PARAMETRO "
    + "WHERE ID_PARAMETRO IN ('SEGURIDAD_TIPO_UNIDAD-ESCUELA','SEGURIDAD_TIPO_UNIDAD-FACULTAD',"
    + "'SEGURIDAD_TIPO_UNIDAD-DIRECCION_SEDE_REGIONAL','SEGURIDAD_TIPO_UNIDAD-COORDINACION_DOCENCIA_S_R')) "
    + "AND UE.ID_TIPO_UNIDAD = TP.ID_TIPO_UNIDAD "
    + "AND UE.ID_UNIDAD_EJECUTORA IN("
    + "SELECT UE1.ID_UNIDAD_EJECUTORA_PADRE FROM SEGURIDAD_UNIDAD_EJECUTORA UE1 "
    + "WHERE UE1.COD_REFERENCIA =:pEscuela AND UE1.ID_TIPO_UNIDAD  IN "
    + "(SELECT VALOR_PARAMETRO FROM SGC_PARAMETRO "
    + "WHERE ID_PARAMETRO IN ('SEGURIDAD_TIPO_UNIDAD-ESCUELA','SEGURIDAD_TIPO_UNIDAD-COORDINACION_DOCENCIA_S_R'))) ",
    resultSetMapping = "unidadInfo"),
    @NamedNativeQuery(name = "SegUnidadEjecutora.nombreTipoEscuela",
    query = "SELECT UE.DSC_UNIDAD_EJECUTORA, TP.DSC_TIPO_UNIDAD "
    + "FROM SEGURIDAD_UNIDAD_EJECUTORA UE, SEGURIDAD_TIPO_UNIDAD TP "
    + "WHERE UE.ID_TIPO_UNIDAD  IN "
    + "(SELECT VALOR_PARAMETRO FROM SGC_PARAMETRO "
    + "WHERE ID_PARAMETRO IN ('SEGURIDAD_TIPO_UNIDAD-ESCUELA','SEGURIDAD_TIPO_UNIDAD-COORDINACION_DOCENCIA_S_R')) "
    + "AND UE.ID_TIPO_UNIDAD = TP.ID_TIPO_UNIDAD "
    + "AND UE.COD_REFERENCIA =:pEscuela",
    resultSetMapping = "unidadInfo"),
    @NamedNativeQuery(name = "SegUnidadEjecutora.unidadesParaPropAdmin",
    query = "SELECT * FROM ( SELECT a.*, ROWNUM RNUM FROM ( "
    + "SELECT UE.* "
    + "FROM SEGURIDAD_UNIDAD_EJECUTORA UE "
    + "WHERE UE.ID_TIPO_UNIDAD  IN "
    + "(SELECT VALOR_PARAMETRO FROM SGC_PARAMETRO "
    + "WHERE ID_PARAMETRO IN ('SEGURIDAD_TIPO_UNIDAD-ESCUELA','SEGURIDAD_TIPO_UNIDAD-COORDINACION_DOCENCIA_S_R')) AND "
    + "UPPER(UE.DSC_UNIDAD_EJECUTORA) LIKE '%'||UPPER(:pFiltroNombre)||'%' "
    + ") a WHERE ROWNUM <= :pUltimoRegistro ) WHERE RNUM >= :pPrimerRegistro",
    resultClass = SegUnidadEjecutora.class),
    @NamedNativeQuery(name = "SegUnidadEjecutora.cantidadUnidadesParaPropAdmin",
    query = "SELECT COUNT(1) AS CANTIDAD "
    + "FROM SEGURIDAD_UNIDAD_EJECUTORA UE "
    + "WHERE UE.ID_TIPO_UNIDAD  IN "
    + "(SELECT VALOR_PARAMETRO FROM SGC_PARAMETRO "
    + "WHERE ID_PARAMETRO IN ('SEGURIDAD_TIPO_UNIDAD-ESCUELA','SEGURIDAD_TIPO_UNIDAD-COORDINACION_DOCENCIA_S_R')) AND "
    + "UPPER(UE.DSC_UNIDAD_EJECUTORA) LIKE '%'||UPPER(:pFiltroNombre)||'%' ",
    resultSetMapping = "cantidad"),
    @NamedNativeQuery(name = "SegUnidadEjecutora.unidadesAsociadasAProp",
    query = "SELECT * FROM ( SELECT a.*, ROWNUM RNUM FROM ( "
    + "SELECT UE.* "
    + "FROM SEGURIDAD_UNIDAD_EJECUTORA UE "
    + "WHERE UE.ID_TIPO_UNIDAD  IN "
    + "(SELECT VALOR_PARAMETRO FROM SGC_PARAMETRO "
    + "WHERE ID_PARAMETRO IN ('SEGURIDAD_TIPO_UNIDAD-ESCUELA','SEGURIDAD_TIPO_UNIDAD-COORDINACION_DOCENCIA_S_R')) AND "
    + "UE.COD_REFERENCIA  IN "
    + "(SELECT PUA.ESCUELA FROM SGC_PROPUESTA_UNIDAD_ACADEMICA PUA WHERE "
    + "PUA.NUMERO_PROPUESTA = :pNumeroPropuesta AND PUA.VERSION_PROPUESTA = :pVersionPropuesta) AND "
    + "UPPER(UE.DSC_UNIDAD_EJECUTORA) LIKE '%'||UPPER(:pFiltroNombre)||'%' "
    + ") a WHERE ROWNUM <= :pUltimoRegistro ) WHERE RNUM >= :pPrimerRegistro",
    resultClass = SegUnidadEjecutora.class),
    @NamedNativeQuery(name = "SegUnidadEjecutora.cantidadUnidadesAsociadasAProp",
    query = "SELECT COUNT(1) AS CANTIDAD "
    + "FROM SEGURIDAD_UNIDAD_EJECUTORA UE "
    + "WHERE UE.ID_TIPO_UNIDAD  IN "
    + "(SELECT VALOR_PARAMETRO FROM SGC_PARAMETRO "
    + "WHERE ID_PARAMETRO IN ('SEGURIDAD_TIPO_UNIDAD-ESCUELA','SEGURIDAD_TIPO_UNIDAD-COORDINACION_DOCENCIA_S_R')) AND "
    + "UE.COD_REFERENCIA IN "
    + "(SELECT PUA.ESCUELA FROM SGC_PROPUESTA_UNIDAD_ACADEMICA PUA WHERE "
    + "PUA.NUMERO_PROPUESTA = :pNumeroPropuesta AND PUA.VERSION_PROPUESTA = :pVersionPropuesta) AND "
    + "UPPER(UE.DSC_UNIDAD_EJECUTORA) LIKE '%'||UPPER(:pFiltroNombre)||'%' ",
    resultSetMapping = "cantidad")})
public class SegUnidadEjecutora extends ObjetoBase implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SegUnidadEjecutoraLlave unidadEjecutoraLlave;
    @Basic(optional = false)
    @Column(name = "DSC_UNIDAD_EJECUTORA")
    private String dscUnidadEjecutora;
    @Column(name = "COD_REFERENCIA")//Nuevo nombre COD_REFERENCIA
    private String codigoReferencia;
    //
    @Column(name = "IND_HABILITADO")
    private String indHabilitado;
    @Column(name = "ID_TIPO_UNIDAD")
    private String idTipoUnidad;
    @Column(name = "ID_UNIDAD_EJECUTORA_PADRE")
    private Integer id_unidad_ejecutora_padre;
    public transient static final String SECUENCIA = "SEQ_SEGURIDAD_UNIDAD_EJECUTORA";

    /**
     * @return the unidadEjecutoraLlave
     */
    public SegUnidadEjecutoraLlave getUnidadEjecutoraLlave() {
        return unidadEjecutoraLlave;
    }

    /**
     * @param unidadEjecutoraLlave the unidadEjecutoraLlave to set
     */
    public void setUnidadEjecutoraLlave(SegUnidadEjecutoraLlave unidadEjecutoraLlave) {
        this.unidadEjecutoraLlave = unidadEjecutoraLlave;
    }

    public enum tipos_unidades {

        PRESUPUESTARIAS("PR");
        public String tipo;

        tipos_unidades(String tipo) {
            this.tipo = tipo;
        }
    }

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      Contructores      <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    public SegUnidadEjecutora() {
    }

    public SegUnidadEjecutora(Integer numEmpresa, String codReferencia, String dscUnidadEjecutora) {
        this.unidadEjecutoraLlave = new SegUnidadEjecutoraLlave(numEmpresa);
        this.codigoReferencia = codReferencia;
        this.dscUnidadEjecutora = dscUnidadEjecutora;

    }

    public SegUnidadEjecutora(SegUnidadEjecutoraLlave sisUnidadEjecutoraPK) {
        this.unidadEjecutoraLlave = sisUnidadEjecutoraPK;
    }

    public SegUnidadEjecutora(SegUnidadEjecutoraLlave sisUnidadEjecutoraPK, String dscUnidadEjecutora) {
        this.unidadEjecutoraLlave = sisUnidadEjecutoraPK;
        this.dscUnidadEjecutora = dscUnidadEjecutora;
    }

    public SegUnidadEjecutora(Integer idUnidadEjecutora, Integer idEmpresa) {
        this.unidadEjecutoraLlave = new SegUnidadEjecutoraLlave(idUnidadEjecutora, idEmpresa);
    }

    public SegUnidadEjecutora(Integer idUnidadEjecutora, Integer idEmpresa, String dscUnidadEjecutora, String numUnidadEjecSiaf, String indHabilitado, String idTipoUnidad) {
        this.unidadEjecutoraLlave = new SegUnidadEjecutoraLlave(idUnidadEjecutora, idEmpresa);
        this.dscUnidadEjecutora = dscUnidadEjecutora;
        this.codigoReferencia = numUnidadEjecSiaf;
        this.indHabilitado = indHabilitado;
        this.idTipoUnidad = idTipoUnidad;
    }

    /*<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>>      SET's y GET's     <<>><<>><<>><<>><<>><<>>
     *<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>*/
    public SegUnidadEjecutoraLlave getSisUnidadEjecutoraPK() {
        return getUnidadEjecutoraLlave();
    }

    public void setSisUnidadEjecutoraPK(SegUnidadEjecutoraLlave sisUnidadEjecutoraPK) {
        this.setUnidadEjecutoraLlave(sisUnidadEjecutoraPK);
    }

    public String getDscUnidadEjecutora() {
        return dscUnidadEjecutora;
    }

    public void setDscUnidadEjecutora(String dscUnidadEjecutora) {
        this.dscUnidadEjecutora = dscUnidadEjecutora;
    }

    public String getCodigoReferencia() {
        return codigoReferencia;
    }

    public void setCodigoReferencia(String pCodReferencia) {
        this.codigoReferencia = pCodReferencia;
    }

    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    public List<SegUnidadUsuario> getUnidadUsuario() {
//       try{
//           sisUnidadUsuarioSet.isEmpty(); //Solo utilizo la variable
//           return sisUnidadUsuarioSet;
//       } catch(LazyInitializationException lie){
//           Set<SegUnidadUsuario> temp = new ControlUnidadEjecutora(null, 0).unidadesUsuario(this);
//           sisUnidadUsuarioSet = temp;
//           return sisUnidadUsuarioSet;
//       }
        return new ArrayList<SegUnidadUsuario>();
    }

    /**
     * @return the indHabilitado
     */
    public String getIndHabilitado() {
        return indHabilitado;
    }

    /**
     * @param indHabilitado the indHabilitado to set
     */
    public void setIndHabilitado(String indHabilitado) {
        this.indHabilitado = indHabilitado;
    }

    /**
     * @return the idTipoUnidad
     */
    public String getIdTipoUnidad() {
        return idTipoUnidad;
    }

    /**
     * @param idTipoUnidad the idTipoUnidad to set
     */
    public void setIdTipoUnidad(String idTipoUnidad) {
        this.idTipoUnidad = idTipoUnidad;
    }

    public Integer getId_unidad_ejecutora_padre() {
        return id_unidad_ejecutora_padre;
    }

    public void setId_unidad_ejecutora_padre(Integer id_unidad_ejecutora_padre) {
        this.id_unidad_ejecutora_padre = id_unidad_ejecutora_padre;
    }

    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    //<<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>><<>>
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getUnidadEjecutoraLlave() != null ? getUnidadEjecutoraLlave().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SegUnidadEjecutora)) {
            return false;
        }
        SegUnidadEjecutora other = (SegUnidadEjecutora) object;
        if ((this.getUnidadEjecutoraLlave() == null && other.getUnidadEjecutoraLlave() != null) || (this.getUnidadEjecutoraLlave() != null && !this.unidadEjecutoraLlave.equals(other.unidadEjecutoraLlave))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        if (this.getUnidadEjecutoraLlave() != null) {
            return this.getUnidadEjecutoraLlave().getIdUnidadEjecutora() + "%" + this.getUnidadEjecutoraLlave().getIdEmpresa();
        } else {
            return "";
        }
    }
}
