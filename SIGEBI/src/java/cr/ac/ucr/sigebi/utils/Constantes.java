package cr.ac.ucr.sigebi.utils;

import java.util.TimeZone;

/**
 *
 * @author jairo.cisneros
 */
public final class Constantes {

    static {
        new Constantes();
    }

    public static final String OK = "OK";
    public static final String DEFAULT_DATE_PATTERN = "dd/MM/yyyy";
    public static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    public static final String PATTERN_NUMERIC = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";
   
    // Estados de un bien
    public static final Integer ESTADO_BIEN_PRE_INGRESO= 1;
    public static final Integer ESTADO_BIEN_PENDIENTE = 2;
    public static final Integer ESTADO_BIEN_PENDIENTE_SINCRONIZAR = 3;
    public static final Integer ESTADO_BIEN_PENDIENTE_ACTIVACION = 4;
    public static final Integer ESTADO_BIEN_ACTIVO = 5;
    public static final Integer ESTADO_BIEN_PENDIENTE_INACTIVACION = 6;
    public static final Integer ESTADO_BIEN_INACTIVO = 7;
    
    // Estados de un a notificacion
    public static final int ESTADO_DEFAULT = -1;
    public static final int ESTADO_NOTIFICACION_CREADA = 1;
    public static final int ESTADO_NOTIFICACION_ENVIADA = 2;
    public static final int ESTADO_NOTIFICACION_ENVIO_FALLIDO = 3;
    public static final String ESTADO_NOTIFICACION_CREADA_DESC = "CREADA";
    public static final String ESTADO_NOTIFICACION_ENVIADA_DESC = "ENVIADA";
    public static final String ESTADO_NOTIFICACION_ENVIO_FALLIDO_DESC = "ENVIO FALLIDO";

    // Prioridades de un a notificacion
    public static final Integer PRIORIDAD_NOTIFICACION_URGENTE = 1;
    public static final Integer PRIORIDAD_NOTIFICACION_NORMAL = 2;
    
    // Exclusiones
    public static final int ESTADO_EXCLUSION_CREADA = 1;
    public static final int ESTADO_EXCLUSION_ENVIADA = 2;
    public static final int ESTADO_EXCLUSION_ENVIO_FALLIDO = 3;
    public static final String ESTADO_EXCLUSION_CREADA_DESC = "CREADA";
    public static final String ESTADO_EXCLUSION_ENVIADA_DESC = "EN TRAMITE";
    public static final String ESTADO_EXCLUSION_ENVIO_FALLIDO_DESC = "CERRADA";
    
    // Estados de un informe
    public static final Integer ESTADO_INFORME_TECNICO_NUEVO = 1;
    public static final Integer ESTADO_INFORME_TECNICO_PROCESO = 2;
    public static final Integer ESTADO_INFORME_TECNICO_APROBADO = 3;
    public static final Integer ESTADO_INFORME_TECNICO_RECHAZADO = 4;
    public static final Integer ESTADO_INFORME_TECNICO_ANULADO = 5;

    // Estados generales
    public static final Integer ESTADO_GENERAL_ACTIVO = 1;
    public static final Integer ESTADO_GENERAL_INACTIVO = 2;
    public static final Integer ESTADO_GENERAL_APROBADO = 3;
    public static final Integer ESTADO_GENERAL_RECHAZADO = 4;
    public static final Integer ESTADO_GENERAL_PENDIENTE = 5;
    public static final Integer ESTADO_GENERAL_ANULADO = 6;
    
    //Dominio estados
    public static final String DOMINI0_ESTADO_BIEN = "BIEN";
    public static final String DOMINI0_ESTADO_INFORME_TECNICO = "INFORME_TECNICO";
    public static final String DOMINI0_ESTADO_ACTA = "ACTA";
    public static final String DOMINI0_ESTADO_GENERAL = "GENERAL";
    public static final String DOMINI0_NOTIFICACION = "NOTIFICACION";
    public static final String DOMINI0_EXCLUSION = "EXCLUSION";

    //Dominio tipos
    public static final String DOMINI0_TIPO_IDENTIFICACION = "IDENTIFICACION";
    public static final String DOMINI0_TIPO_INFORME_TECNICO = "INFORME_TECNICO";
    public static final String DOMINI0_TIPO_PROCESO = "PROCESO";
    public static final String DOMINIO_ACTA = "ACTA";
    public static final String DOMINIO_ORIGEN = "ORIGEN";
    public static final String DOMINIO_TIPO = "TIPO";
    public static final String DOMINIO_CARACTERISTICA = "CARACTERISTICA";
    public static final String DOMINI0_TIPO_DOCUMENTO = "DOCUMENTO";
    public static final String DOMINI0_TIPO_ADJUNTO = "ADJUNTO";

    // Tipos Documentos
    public static final Integer TIPO_DOCUMENTO_INFORME_TECNICO = 1;

    // Tipos Adjuntos
    public static final Integer TIPO_ADJUNTO_INFORME_TECNICO = 1;

    //Id documentos
    public static final Long ID_DOCUMENTO_INFORME_TECNICO = 1l;
    //public static final Long ID_DOCUMENTO_ACTA = 2l;
    public static final Long ID_DOCUMENTO_ACTA_DESECHO = 2l;
    public static final Long ID_DOCUMENTO_ACTA_DONACION = 28l;
    
    public static final String SELECT_DEFAULT = "-1";
    
    public static final String ACTA_ID_TIPO_DONACION = "26";
    public static final String ACTA_ID_TIPO_DESECHO = "27";
    
    

    //Vistas de Navegacion"
    public static final String KEY_VISTA_ORIGEN = "keyVistaOrigen";
    public static final String VISTA_NOTIFICACION_LISTADO = "notificacion";
    public static final String VISTA_NOTIFICACION_LISTADO_PAGINA = "../notificaciones/listarNotificaciones.xhtml";
    public static final String VISTA_NOTIFICACION_NUEVA = "notificacion_nueva";
    public static final String VISTA_NOTIFICACION_NUEVA_PAGINA = "../notificaciones/notificacionDetalle.xhtml";
    public static final String VISTA_EXCLUSION_LISTADO = "exclusion";
    public static final String VISTA_EXCLUSION_LISTADO_PAGINA = "../exclusiones/listarExclusiones.xhtml";
    public static final String VISTA_EXCLUSION_NUEVA = "exclusion_nueva";
    public static final String VISTA_EXCLUSION_NUEVA_PAGINA = "../exclusiones/exclusionDetalle.xhtml";
    //BIENES    
    public static final String VISTA_LISTAR_BIENES = "../bienes/listarBienes.xhtml";
    public static final String KEY_VISTA_LISTAR_BIENES = "reg_manual";
    public static final String VISTA_DETALLE_BIEN = "../bienes/bienDetalle.xhtml";
    public static final String KEY_VISTA_DETALLE_BIEN = "bien_nuevo";
    //ACTAS
    public static final String KEY_VISTA_ACTA= "actaDetalle";
    public static final String VISTA_ACTA = "../exclusiones/acta.xhtml";
    public static final String KEY_VISTA_LISTAR_ACTAS= "acta";
    public static final String VISTA_LISTAR_ACTAS = "../exclusiones/actasListar.xhtml";
    
    
    //TRASLADOS
    public static final String KEY_VISTA_TRASLADOS_LISTAR = "traslados";
    public static final String VISTA_TRASLADOS_LISTAR = "../movimientos/trasladosListar.xhtml";
    public static final String KEY_VISTA_TRASLADO_DETALLE = "trasladoDetalle";
    public static final String VISTA_TRASLADO_DETALLE = "../movimientos/trasladoDetalle.xhtml";
    //PERMISOS TRASLADOS
    public static final String KEY_VISTA_TRASLADO_PERMISOS = "permisosMovimientos";
    public static final String VISTA_TRASLADO_PERMISOS= "../movimientos/permisosMovimientos.xhtml";
    //ACCIONES TRASLADOS
    public static final String DOCUMENTO_TRASLADO = "TRASLADO";
    public static final String DOCUMENTO_ENVIAR = "ENVIAR";
    public static final String DOCUMENTO_RECIBIR = "RECIBIR";
    
    
    //Acciones gestion proceso
    public static final Integer ACCION_PROCESO_AGREGA_DOCUMENTO = 1;
    public static final Integer ACCION_PROCESO_MODIFICAR_DOCUMENTO = 2;
    public static final Integer ACCION_PROCESO_AGREGA_ROl = 3;
    public static final Integer ACCION_PROCESO_MODIFICAR_ROL = 4;
    
    
    
    //Sincronizar
    // ESTADOS
    public static final char SINCRONIZAR_ESTADO_ACTIVO = '1';
    public static final char SINCRONIZAR_ESTADO_INACTIVO = '2';
    public static final char SINCRONIZAR_ESTADO_TRANSITO_ACTIVACION = '3';
    public static final char SINCRONIZAR_ESTADO_TRANSITO_INATIVACION = '4';
    
    // Codigos de rol
    public static final String CODIGO_ROL_TECNICO = "1";
    public static final String CODIGO_ROL_DIRECTOR = "2";
    public static final String CODIGO_ROL_RESPONSABLE = "3";
    
}