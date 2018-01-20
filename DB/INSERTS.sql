INSERT INTO SIGB_CLASIFICACION (ID_CLASIFICACION, NOMBRE, ID_SUB_CATEGORIA, ID_ESTADO) SELECT ID_CLASIFICACION, NOMBRE, CODIGO_SUB_CATEGORIA, ID_ESTADO FROM SGB_CLASIFICACION;
INSERT INTO SIGB_SUB_CLASIFICACION (ID_SUB_CLASIFICACION, ID_CLASIFICACION, NOMBRE, ID_ESTADO) SELECT ID_SUB_CLASIFICACION, ID_CLASIFICACION, NOMBRE, ID_ESTADO FROM SGB_SUB_CLASIFICACION;
INSERT INTO SIGB_IDENTIFICACION (ID_IDENTIFICACION, ID_TIPO, NUMERO_IDENTIFICACION, ID_ESTADO, ID_UNIDAD_EJECUTORA) SELECT ID_IDENTIFICACION, ID_TIPO, NUMERO_IDENTIFICACION, ID_ESTADO, NUM_UNIDAD_EJEC FROM SGB_IDENTIFICACION; 
INSERT INTO SIGB_UBICACION (ID_UBICACION, DETALLE, PERTENECE, ID_PERSONA, ID_UNIDAD_EJECUTORA, ID_ESTADO) SELECT ID_UBICACION, DETALLE, PERTENECE, ID_PERSONA, NUM_UNIDAD_EJEC, ID_ESTADO FROM SGB_UBICACION;
INSERT INTO SIGB_BIEN (ID_BIEN, DESCRIPCION, CANTIDAD, ID_SUB_CATEGORIA, ID_SUB_CLASIFICACION, ID_TIPO_BIEN,
ID_ORIGEN, ID_UNIDAD_EJECUTORA, ID_PROVEEDOR, ID_MONEDA, COSTO, FECHA_ADQUISICION, ID_PERSONA, INICIO_GARANTIA,
FIN_GARANTIA, DESCRIPCION_GARANTIA, ID_ESTADO, NUMERO_LOTE, CAPITALIZABLE, ID_UBICACION,
DESCRIPCION_UBICACION, ID_ESTADO_INTERNO, REFERENCIA, ID_IDENTIFICACION) 
SELECT 
ID_BIEN, DESCRIPCION, CANTIDAD, SUBSTR(CODIGO_SUB_CATEGORIA, 5, 3), ID_SUB_CLASIFICACION, TIPO_BIEN, 
ORIGEN, NUM_UNIDAD_EJEC, PROVEEDOR, ID_MONEDA, COSTO, FECHA_ADQUISICION,  ID_PERSONA, INICIO_GARANTIA, 
FIN_GARANTIA, DESCRIPCION_GARANTIA, ID_ESTADO, NUM_LOTE, CAPITALIZABLE, ID_UBICACION, 
DESCRIPCION_UBICACION, ESTADO_INTERNO, REFERENCIA, ID_IDENTIFICACION
FROM SGB_BIEN;
INSERT INTO SIGB_BIEN_CARACTERISTICA (ID_BIEN_CARACTERISTICA, ID_TIPO, ID_BIEN, DETALLE, ID_ESTADO) select ID_DATO_BIEN, ID_TIPO, ID_BIEN, DETALLE, ID_ESTADO FROM SGB_DATO_BIEN;

INSERT INTO SIGB_ACCESORIO SIGB_ACCESORIO (ID_ACCESORIO, ID_BIEN, DETALLE, ID_ESTADO) SELECT ID_ACCESORIO, ID_BIEN, DETALLE, ID_ESTADO FROM SGB_ACCESORIO;