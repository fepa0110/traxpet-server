SELECT mas.MASCOTA_ID, val.VALOR_ID, val.NOMBRE, car.NOMBRE 
FROM MASCOTA AS mas JOIN MASCOTA_VALOR AS masval ON (mas.MASCOTA_ID=masval.MASCOTAS_MASCOTA_ID) 
JOIN VALOR AS val ON (masval.VALORES_VALOR_ID=val.VALOR_ID) 
JOIN CARACTERISTICA AS car ON (val.CARACTERISTICA_ID=car.CARACTERISTICA_ID)
WHERE mas.ESPECIE_ID = 1