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
    public static final String SELECT_DEFAULT = "-1";
    public static final String DEFAULT_DATE_PATTERN = "dd/MM/yyyy";
    public static final String PATTERN_EMAIL = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getDefault();
    public static final String PATTERN_NUMERIC = "((-|\\+)?[0-9]+(\\.[0-9]+)?)+";
    public static final Long DEFAULT_ID = -1L;
    public static final String DEFAULT_COMBO_MESSAGE = "Seleccione una Opcion";

    // Estados de un bien
    public static final Integer ESTADO_BIEN_PRE_INGRESO = 1;
    public static final Integer ESTADO_BIEN_PENDIENTE = 2;
    public static final Integer ESTADO_BIEN_PENDIENTE_SINCRONIZAR = 3;
    public static final Integer ESTADO_BIEN_PENDIENTE_ACTIVACION = 4;
    public static final Integer ESTADO_BIEN_ACTIVO = 5;
    public static final Integer ESTADO_BIEN_PENDIENTE_INACTIVACION = 6;
    public static final Integer ESTADO_BIEN_INACTIVO = 7;
    public static final Integer ESTADO_BIEN_TRASLADO = 8;
    public static final Integer ESTADO_BIEN_TRANSITO_POR_EXCLUSION = 9;
    public static final Integer ESTADO_BIEN_EXCLUSION_APROBADA = 10;
    public static final Integer ESTADO_BIEN_EXCLUIDO = 11;
    public static final Integer ESTADO_BIEN_TRANSITO_POR_REPARACION = 12;

    // Estado Interno de un bien
    public static final Integer ESTADO_INTERNO_BIEN_NORMAL = 1;
    public static final Integer ESTADO_INTERNO_BIEN_EXCLUSION = 2;
    public static final Integer ESTADO_INTERNO_BIEN_PRESTAMO = 3;
    public static final Integer ESTADO_INTERNO_BIEN_TRASLADO = 4;
    public static final Integer ESTADO_INTERNO_BIEN_ACTA_CREADA = 5;
    public static final Integer ESTADO_INTERNO_BIEN_ACTA_APROBADA = 6;
    public static final Integer ESTADO_INTERNO_BIEN_INFORME_TECNICO_CREADO = 7;
    public static final Integer ESTADO_INTERNO_BIEN_INFORME_TECNICO_APROBADO = 8;
    public static final Integer ESTADO_INTERNO_BIEN_EXCLUSION_SOLICITADO = 21;
    public static final Integer ESTADO_INTERNO_BIEN_EXCLUSION_RECHAZADO = 22;
    public static final Integer ESTADO_INTERNO_BIEN_EXCLUSION_APROBADO = 23;
    public static final Integer ESTADO_INTERNO_BIEN_PRESTAMO_SOLICITADO = 31;
    public static final Integer ESTADO_INTERNO_BIEN_PRESTAMO_APROBADO = 33;
    public static final Integer ESTADO_INTERNO_BIEN_PRESTAMO_ANULADO = 34;
    public static final Integer ESTADO_INTERNO_BIEN_INFORME_TECNICO = 6;
    public static final Integer ESTADO_INTERNO_BIEN_SOLICITUD_MANTENIMIENTO = 9;
    public static final Integer ESTADO_INTERNO_BIEN_MANTENIMIENTO_APLICADO = 91;
    public static final Integer ESTADO_INTERNO_BIEN_MANTENIMIENTO_RECHAZADO = 92;
    public static final Integer ESTADO_INTERNO_BIEN_MANTENIMIENTO_APROBADO = 93;
    public static final Integer ESTADO_INTERNO_BIEN_MANTENIMIENTO_ANULADO = 94;
    public static final Integer ESTADO_INTERNO_BIEN_MANTENIMIENTO_CORRECCION_SOLICITADA = 96;
    public static final Integer ESTADO_INTERNO_BIEN_MANTENIMIENTO_FINALIZADO = 95;
    
    // Estados de una nota
    public static final int ESTADO_NOTA_CREADA = 1;

    // Estados de una notificacion
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

    // CONVENIOS
    public static final int ESTADO_CONVENIO_ACTIVO = 1;
    public static final int ESTADO_CONVENIO_INACTIVO = 2;

    // Acta
    public static final Integer ESTADO_ACTA_PENDIENTE = 1;
    public static final Integer ESTADO_ACTA_PROCESO = 2;
    public static final Integer ESTADO_ACTA_APLICADA = 3;
    public static final Integer ESTADO_ACTA_RECHAZADA = 4;
    
    
    // Exclusiones
    public static final Integer ESTADO_EXCLUSION_CREADA = 1;
    public static final Integer ESTADO_EXCLUSION_SOLICITADA = 2;
    public static final Integer ESTADO_EXCLUSION_RECHAZADA = 3;
    public static final Integer ESTADO_EXCLUSION_APROBADA = 4;
    
    // Mantenimientos
    public static final Integer ESTADO_MANTENIMIENTO_NUEVO = 1;
    public static final Integer ESTADO_MANTENIMIENTO_APLICADO = 2;      // Aplicar, iniciar proceso    
    public static final Integer ESTADO_MANTENIMIENTO_APROBADO = 3;      // Aprobar inicio de proceso
    public static final Integer ESTADO_MANTENIMIENTO_CORRECCION_SOLICITADA = 4;
    public static final Integer ESTADO_MANTENIMIENTO_RECHAZADO = 5;
    public static final Integer ESTADO_MANTENIMIENTO_FINALIZADO = 6;
    public static final Integer ESTADO_MANTENIMIENTO_ANULADO = 7;
    
    public static final int TIPO_EXCLUSION_RETORNO = 4;
    public static final int TIPO_EXCLUSION_PRESTAMO = 3;
    public static final int TIPO_EXCLUSION_DESECHO = 2 ;
    public static final int TIPO_EXCLUSION_DONACION = 1;

    // PRESTAMOS
    public static final Integer ESTADO_PRESTAMO_CREADO = 1;
    public static final Integer ESTADO_PRESTAMO_SOLICITADO = 2;
    public static final Integer ESTADO_PRESTAMO_APROBADO = 3;
    public static final Integer ESTADO_PRESTAMO_RECHAZADO = 4;
    public static final Integer ESTADO_PRESTAMO_ANULADO = 5;
    public static final Integer ESTADO_PRESTAMO_DEVUELTO = 6;

    // Estados de un informe
    public static final Integer ESTADO_INFORME_TECNICO_NUEVO = 1;
    public static final Integer ESTADO_INFORME_TECNICO_PROCESO = 2;
    public static final Integer ESTADO_INFORME_TECNICO_APROBADO = 3;
    public static final Integer ESTADO_INFORME_TECNICO_RECHAZADO = 4;
    public static final Integer ESTADO_INFORME_TECNICO_ANULADO = 5;
    
    public static final Integer TIPO_INFORME_TECNICO_REUBICAR = 1;
    
    public static final Integer TIPO_INFORME_TECNICO_DESECHAR = 4;
    public static final Integer TIPO_INFORME_TECNICO_REPARAR = 6;
    public static final Integer TIPO_INFORME_TECNICO_DONAR = 2;
    public static final Integer TIPO_INFORME_TECNICO_OTROS = 3;
    public static final Integer TIPO_INFORME_TECNICO_USAR_PIEZAS = 5;

    // Estados de una donacion
    public static final Integer ESTADO_SOLITUD_DONACION_NUEVO = 1;
    public static final Integer ESTADO_SOLITUD_DONACION_PROCESO = 2;
    public static final Integer ESTADO_SOLITUD_DONACION_APROBADA = 3;
    public static final Integer ESTADO_SOLITUD_DONACION_RECHAZADA = 4;
    public static final Integer ESTADO_SOLITUD_DONACION_ANULADA = 5;

    // Estados de una solicitud de salida
    public static final Integer ESTADO_SOLITUD_SALIDA_NUEVO = 1;
    public static final Integer ESTADO_SOLITUD_SALIDA_PROCESO = 2;
    public static final Integer ESTADO_SOLITUD_SALIDA_APROBADA = 3;
    public static final Integer ESTADO_SOLITUD_SALIDA_RECHAZADA = 4;
    public static final Integer ESTADO_SOLITUD_SALIDA_ANULADA = 5;
    public static final Integer ESTADO_SOLITUD_SALIDA_CORRECION = 6;

    // Estados generales
    public static final Integer ESTADO_GENERAL_ACTIVO = 1;
    public static final Integer ESTADO_GENERAL_INACTIVO = 2;
    public static final Integer ESTADO_GENERAL_APROBADO = 3;
    public static final Integer ESTADO_GENERAL_RECHAZADO = 4;
    public static final Integer ESTADO_GENERAL_PENDIENTE = 5;
    public static final Integer ESTADO_GENERAL_ANULADO = 6;
    public static final Integer ESTADO_GENERAL_PROCESO = 7;

    // Estados interfazBien
    public static final Integer ESTADO_INTERFAZ_BIEN_PENDIENTE = 1;
    public static final Integer ESTADO_INTERFAZ_BIEN_ERRONEO = 2;
    public static final Integer ESTADO_INTERFAZ_BIEN_PROCESADO = 3;
    public static final Integer ESTADO_INTERFAZ_BIEN_ANULADO = 4;

    //Estados ASIGNAR_RESPONSABLE
    public static final Integer ESTADO_ASIGNAR_RESPONSABLE_PENDIENTE = 1;
    public static final Integer ESTADO_ASIGNAR_RESPONSABLE_ASIGNADO = 2;    
    
    // Toma Fisica
    public static final Integer ESTADO_TOMA_FISICA_PENDIENTE = 1;
    public static final Integer ESTADO_TOMA_FISICA_CERRADO = 2;

    // Toma Fisica
    public static final Integer ESTADO_ASIGNACION_PLACA_PENDIENTE = 1;
    public static final Integer ESTADO_ASIGNACION_PLACA_ANULADA = 2;
    public static final Integer ESTADO_ASIGNACION_PLACA_FINALIZADA = 3;

    //Dominios
    public static final String DOMINIO_ACTA = "ACTA";
    public static final String DOMINIO_ADJUNTO = "ADJUNTO";
    public static final String DOMINIO_BIEN = "BIEN";
    public static final String DOMINIO_BIEN_INTERNO = "BIEN_INTERNO";
    public static final String DOMINIO_CARACTERISTICA = "CARACTERISTICA";
    public static final String DOMINIO_COLUMNAS_REPORTE_SOBRANTES = "ORDEN_COLUMNA_SOBRANTES";
    public static final String DOMINIO_CONVENIO = "CONVENIO";
    public static final String DOMINIO_DOCUMENTO = "DOCUMENTO";
    public static final String DOMINIO_EXCLUSION = "EXCLUSION";
    public static final String DOMINIO_GENERAL = "GENERAL";
    public static final String DOMINIO_IDENTIFICACION = "IDENTIFICACION";
    public static final String DOMINIO_INFORME_TECNICO = "INFORME_TECNICO";
    public static final String DOMINIO_INTERFAZ_BIEN = "INTERFAZ_BIEN";
    public static final String DOMINIO_NOTA = "NOTA";
    public static final String DOMINIO_NOTIFICACION = "NOTIFICACION";
    public static final String DOMINIO_ORDEN_REPORTE = "ORDEN_REPORTE";
    public static final String DOMINIO_ORIGEN = "ORIGEN";
    public static final String DOMINIO_PRESTAMO = "PRESTAMO";
    public static final String DOMINIO_PRESTAMO_ENTIDAD = "PRESTAMO_ENTIDAD";
    public static final String DOMINIO_PROCESO = "PROCESO";
    public static final String DOMINIO_RECEPCION_PRESTAMO = "RECEPCION_PRESTAMO";
    public static final String DOMINIO_REGISTRO_MOVIMIENTO = "REGISTRO_MOVIMIENTO";
    public static final String DOMINIO_REPORTE = "REPORTE";
    public static final String DOMINIO_SOLICITUD_MANTENIMIENTO = "MANTENIMIENTO";
    public static final String DOMINIO_SOLI_DONACION = "SOL_DONACION";
    public static final String DOMINIO_SOLI_SALIDAS = "SOL_SALIDAS";
    public static final String DOMINIO_TIPOS_MODIFICAR = "TIPO";
    public static final String DOMINIO_TOMA_FISICA = "TOMA_FISICA";
    public static final String DOMINIO_TOMA_FISICA_MOTIVO = "TOMA_FISICA_MOTIVO";
    public static final String DOMINIO_ASIGNAR_RESPONSABLE = "ASIGNAR_RESPONSABLE";
    public static final String DOMINIO_ASIGNACION_PLACA = "ASIGNACION_PLACA";

    //TIPO ADJUNTO
    public static final Integer TIPO_ADJUNTO_BIEN = 1;
    public static final Integer TIPO_ADJUNTO_DOCUMENTO = 2;
    public static final Integer TIPO_ADJUNTO_DONACION = 3;
    public static final Integer TIPO_ADJUNTO_CONVENIO = 4;
    public static final Integer TIPO_ADJUNTO_RECEPCION_PRESTAMO = 5;

    //TIPO Solicitud Salidas
    public static final Integer TIPO_SOLICITUD_SALIDA_NACIONAL = 1;
    public static final Integer TIPO_SOLICITUD_SALIDA_INTERNACIONAL = 2;

    //TIPOS ORIGENES
    public static final Integer TIPO_ORIGEN_EXTERNA = 7;
    
    //TIPOS PROCESO
    public static final Integer TIPO_PROCESO_RESPONSABLE = 7;
    public static final Integer TIPO_PROCESO_RESPONSABLE_RECHAZAR = 8;

    //TIPOS REGISTROS MOVIMIENTOS
    public static final Integer TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_BIEN = 1;
    public static final Integer TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_SOLICITUD = 2;
    public static final Integer TIPO_REGISTRO_MOVIMIENTO_CAMBIO_ESTADO_DOCUMENTO = 3;
    public static final Integer TIPO_REGISTRO_MOVIMIENTO_RECHAZAR_AUTORIZACION = 4;
    public static final Integer TIPO_REGISTRO_MOVIMIENTO_CAMBIO_CONVENIO = 5;
    public static final Integer TIPO_REGISTRO_MOVIMIENTO_RECHAZO_ASIGNACION_PLACA = 6;
    public static final Integer TIPO_REGISTRO_MOVIMIENTO_CAMBIO_RECEPCION_PRESTAMO = 7;

    public static final String TIPO_REPORTE_PDF = "PDF";
    public static final String TIPO_REPORTE_EXCELL = "EXCELL";
    public static final String TIPO_REPORTE_PDF_EXTENSION = ".pdf";
    public static final String TIPO_REPORTE_EXCELL_EXTENSION = ".xlsx";
    
    public static final String TIPO_MOVIMIENTO_SINCRONIZAR_AGREGAR = "AGREGAR";
    public static final String TIPO_MOVIMIENTO_SINCRONIZAR_EXCLUIR = "EXCLUIR";
    public static final String TIPO_MOVIMIENTO_SINCRONIZAR_MODIFICAR = "MODIFICAR";
    public static final String TIPO_MOVIMIENTO_SINCRONIZAR_TRASLADAR = "TRASLADAR";    
    
    //TIPO CARACTERISTICA BIEN
    public static final Integer TIPO_CARACTERISTICA_SERIE = 1;
    public static final Integer TIPO_CARACTERISTICA_MODELO = 2;
    public static final Integer TIPO_CARACTERISTICA_MARCA = 3;

    //TIPO TOMAS FISICAS
    public static final Integer TIPO_TOMA_FISICA_COMPLETO = 3;

    //Identificaciones
    public static final Integer IDENTIFICACION_ESTADO_DISPONIBLE = 1;
    public static final Integer IDENTIFICACION_ESTADO_OCUPADA = 2;
    public static final Integer IDENTIFICACION_ESTADO_RESERVADA_UNIDAD = 3;

    //codigos de documentos
    public static final Integer CODIGO_AUTORIZACION_INFORME_EXCLUSION = 1;
    public static final Integer CODIGO_AUTORIZACION_INFORME_TECNICO = 2;
    public static final Integer CODIGO_AUTORIZACION_ACTA_DONACION = 3;
    public static final Integer CODIGO_AUTORIZACION_ACTA_DESECHO = 4;
    public static final Integer CODIGO_AUTORIZACION_TRASLADO_ENVIAR = 5;
    public static final Integer CODIGO_AUTORIZACION_TRASLADO_RECIBIR = 6;
    public static final Integer CODIGO_AUTORIZACION_DONACION = 7;
    public static final Integer CODIGO_AUTORIZACION_ADMINISTRADOR = 8;
    public static final Integer CODIGO_AUTORIZACION_EXCLUSION_DESECHO = 9;
    public static final Integer CODIGO_AUTORIZACION_EXCLUSION_DONACION = 10;
    public static final Integer CODIGO_AUTORIZACION_PRESTAMO = 11;
    public static final Integer CODIGO_AUTORIZACION_MANTENIMIENTO = 12;

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
    public static final String VISTA_PRESTAMO_LISTADO = "prestamos";
    public static final String VISTA_PRESTAMO_LISTADO_PAGINA = "../prestamos/listarPrestamos.xhtml";
    public static final String VISTA_PRESTAMO_NUEVO = "prestamo_nuevo";
    public static final String VISTA_PRESTAMO_NUEVO_PAGINA = "../prestamos/prestamoDetalle.xhtml";
    public static final String VISTA_SINCRONIZACION_BIEN = "sincronizar";
    public static final String VISTA_SINCRONIZACION_BIEN_LISTADO = "../bienes/listarBienSincronizar.xhtml";
    
    public static final String VISTA_MANTENIMIENTO_LISTADO = "mantenimiento";
    public static final String VISTA_MANTENIMIENTO_LISTADO_PAGINA = "../mantenimientos/listarMantenimientos.xhtml";
    public static final String VISTA_MANTENIMIENTO_NUEVO = "mantenimiento_nueva";
    public static final String VISTA_MANTENIMIENTO_NUEVA_PAGINA = "../mantenimientos/mantenimientoDetalle.xhtml";
    
    public static final String KEY_VISTA_SOLICITUD_DONACION_LISTADO = "donacion";
    public static final String VISTA_SOLICITUD_DONACION_LISTADO = "../inclusiones/listarDonaciones.xhtml";
    public static final String KEY_VISTA_SOLICITUD_DONACION_DETALLE = "donacion_deta";
    public static final String VISTA_SOLICITUD_DONACION_DETALLE = "../inclusiones/detalleSolicitudDonacion.xhtml";

    public static final String KEY_VISTA_INTERFAZ_BIEN_LISTADO = "pre_ingreso";
    public static final String VISTA_INTERFAZ_BIEN_LISTADO = "../interfazBien/listarInterfazBien.xhtml";
    public static final String KEY_VISTA_INTERFAZ_BIEN_DETALLE = "pre_ingreso_deta";
    public static final String VISTA_INTERFAZ_BIEN_DETALLE = "../interfazBien/detalleInterfazBien.xhtml";

    public static final String VISTA_CONVENIO_LISTADO = "convenios";
    public static final String VISTA_CONVENIO_LISTADO_PAGINA = "../convenios/listarConvenios.xhtml";
    public static final String VISTA_CONVENIO_NUEVO = "convenio_nuevo";
    public static final String VISTA_CONVENIO_NUEVO_PAGINA = "../convenios/convenioDetalle.xhtml";

    public static final String VISTA_RECEPCION_PRESTAMO_LISTADO = "rec_prestamo";
    public static final String VISTA_RECEPCION_PRESTAMO_LISTADO_PAGINA = "../prestamos/listarRecepcionPrestamos.xhtml";
    public static final String VISTA_RECEPCION_PRESTAMO_NUEVO = "rec_prestamo_nuevo";
    public static final String VISTA_RECEPCION_PRESTAMO_NUEVO_PAGINA = "../prestamos/recepcionPrestamoDetalle.xhtml";

    public static final String VISTA_INFORME_TECNICO = "informe";
    public static final String VISTA_INFORME_TECNICO_LISTADO = "../exclusiones/listarInformesTecnicos.xhtml";
    public static final String VISTA_INFORME_TECNICO_DET = "informe_detalle";
    public static final String VISTA_INFORME_TECNICO_DETALLE = "../exclusiones/detalleInformeTecnico.xhtml";
    public static final String VISTA_PROCESOS = "proceso";
    public static final String VISTA_PROCESO_DIRECCION = "../gestionProceso/gestionProceso.xhtml";

    public static final String KEY_VISTA_UBICACION = "ubicaciones";
    public static final String VISTA_UBICACION = "../ubicacion/ubicacion.xhtml";

    public static final String KEY_VISTA_SOLICITUD_SALIDA_LISTADO = "salidas";
    public static final String VISTA_SOLICITUD_SALIDA_LISTADO = "../salidas/listarSalidas.xhtml";
    public static final String KEY_VISTA_SOLICITUD_SALIDA_DETALLE = "salidas_deta";
    public static final String VISTA_SOLICITUD_SALIDA_DETALLE = "../salidas/detalleSolicitudSalida.xhtml";

    
    //BIENES
    public static final String VISTA_LISTAR_BIENES = "../bienes/listarBienes.xhtml";
    public static final String KEY_VISTA_LISTAR_BIENES = "reg_manual";
    public static final String VISTA_DETALLE_BIEN = "../bienes/bienDetalle.xhtml";
    public static final String KEY_VISTA_DETALLE_BIEN = "bien_nuevo";
    public static final String KEY_VISTA_DETALLE_BIEN_DONACION = "bien_nuevo_donacion";
    public static final String VISTA_DETALLE_BIEN_DONACION = "../bienes/bienAgregarDonacion.xhtml";
    public static final String KEY_VISTA_DETALLE_BIEN_INTERFAZ = "bien_nuevo_interfaz";
    public static final String VISTA_DETALLE_BIEN_INTERFAZ = "../bienes/bienAgregarInterfaz.xhtml";

    //ACTAS
    public static final String KEY_VISTA_ACTA = "actaDetalle";
    public static final String VISTA_ACTA = "../exclusiones/acta.xhtml";
    public static final String KEY_VISTA_LISTAR_ACTAS = "acta";
    public static final String VISTA_LISTAR_ACTAS = "../exclusiones/actasListar.xhtml";

    //TIPOS
    public static final String KEY_VISTA_TIPOS = "tipos";
    public static final String KEY_VISTA_TIPOS_PAGINA = "../tipos/agregarTipos.xhtml";

    //TRASLADOS
    public static final String KEY_VISTA_TRASLADOS_LISTAR = "traslados";
    public static final String VISTA_TRASLADOS_LISTAR = "../movimientos/trasladosListar.xhtml";
    public static final String KEY_VISTA_TRASLADO_DETALLE = "trasladoDetalle";
    public static final String VISTA_TRASLADO_DETALLE = "../movimientos/trasladoDetalle.xhtml";

    //REPORTES
    public static final String KEY_VISTA_REPORTE_TRASLADO = "rep_traslados";
    public static final String VISTA_REPORTE_TRASLADO = "../reportes/reporteTraslados.xhtml";
    public static final String KEY_VISTA_REPORTE_MOVIMIENTOS = "rep_movimientos";
    public static final String VISTA_REPORTE_MOVIMIENTOS = "../reportes/reporteMovimientos.xhtml";
    public static final String KEY_VISTA_REPORTE_BIENES = "rep_bienes";
    public static final String VISTA_REPORTE_BIENES = "../reportes/reporteBien.xhtml";
    public static final String KEY_REPORTE_INVENT_FALTANTES = "rep_invent_faltantes";
    public static final String VISTA_REPORTE_INVENT_FALTANTES = "../reportes/reporteInvertFaltantes.xhtml";
    public static final String KEY_REPORTE_SOBRANTES = "rep_sobrantes"; 
    public static final String VISTA_REPORTE_SOBRANTES = "../reportes/reporteSobrantes.xhtml";
    
    //PERMISOS TRASLADOS
    public static final String KEY_VISTA_TRASLADO_PERMISOS = "permisosMovimientos";
    public static final String VISTA_TRASLADO_PERMISOS = "../movimientos/permisosMovimientos.xhtml";
    
    //Toma Fisica
    public static final String KEY_VISTA_TOMA_FISICA_DETALLE = "inventario_detalle";
    public static final String VISTA_TOMA_FISICA_DETALLE = "../inventario/detalleInventario.xhtml";
    public static final String VISTA_LISTAR_TOMA_FISICA = "../inventario/listarInventarios.xhtml";
    public static final String KEY_VISTA_LISTAR_TOMA_FISICA = "inventario";

    //Navegación Asignació Responsable Bienes
    public static final String KEY_VISTA_ASIGNA_RESPONSABLE = "asignacion";
    public static final String VISTA_ASIGNA_RESPONSABLE = "../bienes/asignarResponsable.xhtml";
    public static final String KEY_VISTA_MIS_BIENES = "mis_bienes";
    public static final String VISTA_MIS_BIENES = "../bienes/asignarResponsableMisBienes.xhtml";
    
    //Asignacion de placas
    public static final String KEY_VISTA_ASIGNACION_PLACA_DETALLE = "asignacion_placa_detalle";
    public static final String VISTA_ASIGNACIONPLACA_DETALLE = "../asignacionPlaca/detalleAsignacionPlacas.xhtml";
    public static final String VISTA_LISTAR_ASIGNACION_PLACA = "../asignacionPlaca/listarAsignacionPlacas.xhtml";
    public static final String KEY_VISTA_LISTAR_ASIGNACION_PLACA = "sol_placas";
    
    
    //ACCIONES TRASLADOS
    public static final String DOCUMENTO_TRASLADO = "TRASLADO";
    public static final String DOCUMENTO_ENVIAR = "ENVIAR";
    public static final String DOCUMENTO_RECIBIR = "RECIBIR";

    //Acciones gestion proceso
    public static final Integer ACCION_PROCESO_AGREGA_DOCUMENTO = 1;
    public static final Integer ACCION_PROCESO_MODIFICAR_DOCUMENTO = 2;
    public static final Integer ACCION_PROCESO_AGREGA_ROL = 3;
    public static final Integer ACCION_PROCESO_MODIFICAR_ROL = 4;

    //Acciones solicitud donacion
    public static final Integer ACCION_DONACION_BUSCAR_RECEPTOR = 1;
    public static final Integer ACCION_DONACION_ANULAR = 2;
    public static final Integer ACCION_DONACION_ELIMINAR_BIEN = 3;
    public static final Integer ACCION_DONACION_RECHAZAR_AUTORIZACION = 4;

    //Acciones solicitud salidas
    public static final Integer ACCION_SALIDA_BUSCAR_BIEN = 1;
    public static final Integer ACCION_SALIDA_ANULAR = 2;
    public static final Integer ACCION_SALIDA_RECHAZAR = 3;
    public static final Integer ACCION_SALIDA_BUSCAR_PERSONA = 4;
    public static final Integer ACCION_SALIDA_CORREGIR = 5;

    //Sincronizar
    public static final char SINCRONIZAR_ESTADO_ACTIVO = '1';
    public static final char SINCRONIZAR_ESTADO_INACTIVO = '2';
    public static final char SINCRONIZAR_ESTADO_TRANSITO_ACTIVACION = '3';
    public static final char SINCRONIZAR_ESTADO_TRANSITO_INATIVACION = '4';

    // Codigos de rol
    public static final Integer CODIGO_ROL_TECNICO_AUTORIZACION_INFORME_TECNICO = 1;
    public static final Integer CODIGO_ROL_ADMINISTRADOR_AUTORIZACION_ADMINISTRADOR = 2;

    //Discriminator Adjunto
    public static final Integer DISCRIMINATOR_ADJUNTO_BIEN = 1;
    public static final Integer DISCRIMINATOR_ADJUNTO_DOCUMENTO = 2;

    //Discriminator Documento
    public static final Integer DISCRIMINATOR_DOCUMENTO_INFORME_TECNICO = 1;
    public static final Integer DISCRIMINATOR_DOCUMENTO_ACTA = 2;
    public static final Integer DISCRIMINATOR_DOCUMENTO_TRASLADO = 3;

    //Discriminator Documento Detalle
    public static final Integer DISCRIMINATOR_DOCUMENTO_DETALLE_GENERAL = 1;

    //Discriminator Detalle Documento
    public static final Integer DISCRIMINATOR_DOCUMENTO_DETALLE_ACTA = 1;

    //Discriminator Solicitud
    public static final Integer DISCRIMINATOR_SOLICITUD_EXCLUSION = 1;
    public static final Integer DISCRIMINATOR_SOLICITUD_DONACION = 2;
    public static final Integer DISCRIMINATOR_SOLICITUD_TRASLADO = 3;
    public static final Integer DISCRIMINATOR_SOLICITUD_PRESTAMO = 4;
    public static final Integer DISCRIMINATOR_SOLICITUD_SALIDA = 5;

    //Discriminator Detalle Solicitud
    public static final Integer DISCRIMINATOR_DETALE_SOLICITUD = 1;
    public static final Integer DISCRIMINATOR_DETALE_SOLICITUD_DONACION = 2;
    public static final Integer DISCRIMINATOR_DETALE_SOLICITUD_TRASLADO = 3;

    //Discriminator Registro Movimiento
    public static final Integer DISCRIMINATOR_REGISTRO_MOVIMIENTO_BIEN = 1;
    public static final Integer DISCRIMINATOR_REGISTRO_MOVIMIENTO_DOCUMENTO = 2; 
    public static final Integer DISCRIMINATOR_REGISTRO_MOVIMIENTO_SOLICITUD = 3;
    public static final Integer DISCRIMINATOR_REGISTRO_MOVIMIENTO_CONVENIO= 4;

    //COnsultas en listados de bienes
    public static final Integer BIENES_LISTADO_ACTAS = 2;
    

    //Tipos de unidades    
    //Unidades con placas espesificas
    public static final String TIPO_UNIDAD_EJECUTORA_COMPRA = "UEC";
    // Para pruebas public static final String TIPO_UNIDAD_EJECUTORA_COMPRA = "SRC";

    
    //Acciones asignacion de placas
    public static final Integer ACCION_ASIGNACION_PLACA_RECHAZAR = 1;
    public static final Integer ACCION_ASIGNACION_PLACA_ACEPTAR = 2;
    
}
