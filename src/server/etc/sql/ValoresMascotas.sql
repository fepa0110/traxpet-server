DROP TABLE MASCOTA_VALOR;
DROP TABLE MASCOTASENTRENADAS;
DROP TABLE MODELO;
DROP TABLE USUARIO;
DROP TABLE ROLUSUARIO;
DROP TABLE UBICACION;
DROP TABLE PUBLICACION;
DROP TABLE IMAGENMASCOTA;
DROP TABLE MASCOTA;
DROP TABLE VALOR;
DROP TABLE CARACTERISTICA;
DROP TABLE ESPECIE;

SELECT mas.MASCOTA_ID, val.VALOR_ID, val.NOMBRE, car.NOMBRE 
FROM MASCOTA AS mas JOIN MASCOTA_VALOR AS masval ON (mas.MASCOTA_ID=masval.MASCOTAS_MASCOTA_ID) 
JOIN VALOR AS val ON (masval.VALORES_VALOR_ID=val.VALOR_ID) 
JOIN CARACTERISTICA AS car ON (val.CARACTERISTICA_ID=car.CARACTERISTICA_ID)
WHERE mas.ESPECIE_ID = 2

SELECT COUNT(mas.MASCOTA_ID) FROM MASCOTA AS mas WHERE mas.ESPECIE_ID = 2

SELECT mv.MASCOTAS_MASCOTA_ID, val.VALOR_ID, val.CARACTERISTICA_ID FROM MASCOTA_VALOR AS mv JOIN VALOR AS val ON (mv.VALORES_VALOR_ID=val.VALOR_ID)
JOIN CARACTERISTICA AS car ON (val.CARACTERISTICA_ID = car.CARACTERISTICA_ID)
WHERE mv.MASCOTAS_MASCOTA_ID = 13

SELECT mv.MASCOTAS_MASCOTA_ID, val.VALOR_ID, val.CARACTERISTICA_ID FROM MASCOTA_VALOR AS mv 
JOIN VALOR AS val ON (mv.VALORES_VALOR_ID=val.VALOR_ID)
JOIN CARACTERISTICA AS car ON (val.CARACTERISTICA_ID = car.CARACTERISTICA_ID)
WHERE val.ESPECIE_ID = 1

DELETE FROM MASCOTA_VALOR AS mv WHERE mv.VALORES_VALOR_ID BETWEEN 79 AND 82

SELECT mas.MASCOTA_ID, val.VALOR_ID, val.NOMBRE, car.NOMBRE 
FROM MASCOTA AS mas JOIN MASCOTA_VALOR AS masval ON (mas.MASCOTA_ID=masval.MASCOTAS_MASCOTA_ID)
JOIN VALOR AS val ON (masval.VALORES_VALOR_ID=val.VALOR_ID)
JOIN CARACTERISTICA AS car ON (val.CARACTERISTICA_ID=car.CARACTERISTICA_ID)
WHERE mas.ESPECIE_ID = 1

SELECT COUNT(model.modelo_id) FROM MODELO AS model

INSERT INTO MODELO(MODELO_ID,FILENAME,ESPECIE_ID,ACTIVO)
VALUES
(3,'m_perros.cbm',1,1);

SELECT *
FROM PUBLICACION AS publicacion JOIN MASCOTA AS mascota 
ON (PUBLICACION.MASCOTA_ID = MASCOTA.MASCOTA_ID)
WHERE publicacion.ESTADO = 'ACTIVA' AND MASCOTA.ESPECIE_ID = 2

UPDATE MODELO
SET ACTIVO = 0
WHERE MODELO_ID = 1;
