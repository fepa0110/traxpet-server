# language: es

Característica: cargar un pedido para cliente domicilio
   módulo de administración y cara de pedidos de productos para delivery

Esquema del escenario: Cargar nuevo pedido a cliente que existe con domicilio ya registrado
   Dado que llama el cliente existente con razón social "<RazonSocial>" con cuit <cuit>
   Y que solicita se envíe el pedido al domicilio existente en la localidad "<Localidad>" calle "<Calle>" y altura <Altura>
   Y que pide los siguientes artículos
      | Articulo | Nombre                | Cantidad  | PrecioUnitario  |
      | 1000     | remache corto         | 10        | 2.3             |
      | 1001     | remache mediano       | 40        | 3.1             |
      | 2000     | tornillo largo        | 125       | 3.4             |
      | 3000     | lija 0.5              | 6         | 0.45            |
      | 3001     | lija 1                | 10        | 0.77            |
      | 4000     | pintura blanca x 4lts | 1         | 390             |
   Cuando guarda el nuevo pedido con fecha "<Fecha>"
   Entonces obtiene la siguiente respuesta <Respuesta>

   Ejemplos:
   | Cliente | RazonSocial   | cuit     | Localidad     | Calle      | Altura| Fecha      | Respuesta |
   | 1000    | Juan Perez    | 10100100 | Trelew        | San Martín | 100   | 2020-05-12 |'{"Status Code": 200,"Status Text": "Se cargó el pedido exitosamente","data":{"pedido":1,"fecha":"2020-05-12","cliente": {"cuit":10100100,"razonSocial":"Juan Perez","nroDoc":"10100100","tipoDoc":"DNI"},"domicilio": {"altura":100,"calle":"San Martín","pisoDpto":"","localidad":{"cp":"9100","nombre":"Trelew"}},"total": 972.40}}'           |
   | 1000    | Juan Perez    | 10100100 | Trelew        | Belgrano   | 100   | 2020-05-12 |'{"Status Code": 200,"Status Text": "Se cargó el pedido exitosamente","data":{"pedido":2,"fecha":"2020-05-12","cliente": {"cuit":10100100,"razonSocial":"Juan Perez","nroDoc":"10100100","tipoDoc":"DNI"},"domicilio": {"altura":100,"calle":"Belgrano","pisoDpto":"","localidad":{"cp":"9100","nombre":"Trelew"}},"total": 972.40}}'             |   
   | 2000    | Raul Iriarte  | 20200200 | Puerto Madryn | Roca       | 200   | 2020-05-12 |'{"Status Code": 200,"Status Text": "Se cargó el pedido exitosamente","data":{"pedido":3,"fecha":"2020-05-12","cliente": {"cuit":20200200,"razonSocial":"Raul Iriarte","nroDoc":"20200200","tipoDoc":"DNI"},"domicilio": {"altura":200,"calle":"Roca","pisoDpto":"","localidad":{"cp":"9120","nombre":"Puerto Madryn"}},"total": 972.40}}'        |
   | 2000    | Raul Iriarte  | 20200200 | Puerto Madryn | Roca       | 200   | 2020-05-12 |'{"Status Code": 200,"Status Text": "Se cargó el pedido exitosamente","data":{"pedido":4,"fecha":"2020-05-12","cliente": {"cuit":20200200,"razonSocial":"Raul Iriarte","nroDoc":"20200200","tipoDoc":"DNI"},"domicilio": {"altura":200,"calle":"Roca","pisoDpto":"","localidad":{"cp":"9120","nombre":"Puerto Madryn"}},"total": 972.40}}'        |   
   | 3000    | Rosana Lirios | 30300300 | Puerto Madryn | Maiz       | 300   | 2020-05-12 |'{"Status Code": 200,"Status Text": "Se cargó el pedido exitosamente","data":{"pedido":5,"fecha":"2020-05-12","cliente": {"cuit":30300300,"razonSocial":"Rosana Lirios","nroDoc":"30300300","tipoDoc":"DNI"},"domicilio": {"altura":300,"calle":"Maiz","pisoDpto":"","localidad":{"cp":"9120","nombre":"Puerto Madryn"}},"total": 972.40}}'       |   
   | 4000    | Marta Ríos    | 40400400 | Rawson        | Maiz       | 400   | 2020-05-12 |'{"Status Code": 200,"Status Text": "Se cargó el pedido exitosamente","data":{"pedido":6,"fecha":"2020-05-12","cliente": {"cuit":40400400,"razonSocial":"Marta Ríos","nroDoc":"40400400","tipoDoc":"DNI"},"domicilio": {"altura":400,"calle":"Maiz","pisoDpto":"","localidad":{"cp":"9103","nombre":"Rawson"}},"total": 972.40}}'                 |   
   | 5000    | José Quintana | 50500500 | Puerto Madryn | Sarmiento  | 500   | 2020-05-12 |'{"Status Code": 200,"Status Text": "Se cargó el pedido exitosamente","data":{"pedido":7,"fecha":"2020-05-12","cliente": {"cuit":50500500,"razonSocial":"José Quintana","nroDoc":"50500500","tipoDoc":"DNI"},"domicilio": {"altura":500,"calle":"Sarmiento","pisoDpto":"","localidad":{"cp":"9120","nombre":"Puerto Madryn"}},"total": 972.40}}'  |    

Esquema del escenario: Cargar nuevo pedido a cliente que no existe
   Dado que llama la el cliente "<Cliente>" que no existe con razón social "<RazonSocial>" con tipo de documento "<TipoDoc>" y número "<Doc>"
   Y que solicita se envíe el pedido al domicilio que no existe con localidad "<Localidad>" calle "<Calle>" y altura <Altura>
   Y que pide los siguientes artículos
      | Articulo| Nombre                | Cantidad  | PrecioUnitario  |
      | 5000    | Machimbre 10mm        | 20        | 355.25          |
      | 6000    | Aceite de lino x 4lts | 1         | 450.10          |

   Cuando guarda el nuevo pedido con fecha "<Fecha>"
   Entonces obtiene la siguiente respuesta <Respuesta>

   Ejemplos:
   | Cliente | RazonSocial    | TipoDoc | Doc       | Localidad     | Calle     | Altura| Fecha      | Respuesta |
   | 6000    | Juan Perez     | DNI     | 60600600  | Puerto Madryn | Av. Gales | 600   | 2020-05-14 |'{"Status Code": 200,"Status Text": "Se cargó el pedido exitosamente","data":{"pedido":8, "fecha":"2020-05-14","cliente": {"cuit":60600600,"razonSocial":"Juan Perez","nroDoc":"60600600","tipoDoc":"DNI"},"domicilio": {"altura":600,"calle":"Av. Gales","pisoDpto":"","localidad":{"cp":"9120","nombre":"Puerto Madryn"}},"total": 7555.1 }}' |
   | 7000    | Dora Barrancos | DNI     | 70700700  | Puerto Madryn | Mitre     | 700   | 2020-05-14 |'{"Status Code": 200,"Status Text": "Se cargó el pedido exitosamente","data":{"pedido":9, "fecha":"2020-05-14","cliente": {"cuit":70700700,"razonSocial":"Dora Barrancos","nroDoc":"70700700","tipoDoc":"DNI"},"domicilio": {"altura":700,"calle":"Mitre","pisoDpto":"","localidad":{"cp":"9120","nombre":"Puerto Madryn"}},"total": 7555.1 }}' |   

Esquema del escenario: Cargar nuevo pedido a cliente que existe con domicilio ya registrado
   Dado que llama el cliente existente con razón social "<RazonSocial>" con cuit <cuit>
   Y que solicita se envíe el pedido al domicilio existente en la localidad "<Localidad>" calle "<Calle>" y altura <Altura>
   Y que pide los siguientes artículos
      | Articulo | Nombre                | Cantidad  | PrecioUnitario  |
      | 1000     | remache corto         | 10        | 2.3             |
      | 1001     | remache mediano       | 40        | 3.1             |
      | 2000     | tornillo largo        | 125       | 3.4             |
      | 3000     | lija 0.5              | 6         | 0.45            |
      | 3001     | lija 1                | 10        | 0.77            |
      | 4000     | pintura blanca x 4lts | 1         | 390             |
   Cuando guarda el nuevo pedido con fecha "<Fecha>"
   Entonces obtiene la siguiente respuesta <Respuesta>

   Ejemplos:
   | Cliente | RazonSocial   | cuit     | Localidad     | Calle      | Altura| Fecha      | Respuesta |
   | 8000    | Laura Agosto  | 80800800 | Puerto Madryn | Bvd. Brown | 800   | 2020-05-15 |'{"Status Code": 200,"Status Text": "Se cargó el pedido exitosamente","data":{"pedido":10,"fecha":"2020-05-15","cliente": {"cuit":80800800,"razonSocial":"Laura Agosto","nroDoc":"80800800","tipoDoc":"DNI"},"domicilio": {"altura":800,"calle":"Bvd. Brown","pisoDpto":"","localidad":{"cp":"9120","nombre":"Puerto Madryn"}},"total": 972.40}}' |   
   | 9000    | Martín Burla  | 90900900 | Puerto Madryn | Chiquichan | 900   | 2020-05-15 |'{"Status Code": 200,"Status Text": "Se cargó el pedido exitosamente","data":{"pedido":11,"fecha":"2020-05-15","cliente": {"cuit":90900900,"razonSocial":"Martín Burla","nroDoc":"90900900","tipoDoc":"DNI"},"domicilio": {"altura":900,"calle":"Chiquichan","pisoDpto":"","localidad":{"cp":"9120","nombre":"Puerto Madryn"}},"total": 972.40}}' |   
   | 10000   | Pedro Villa   | 11001000 | Trelew        | Yrigoyen   | 1000  | 2020-05-15 |'{"Status Code": 200,"Status Text": "Se cargó el pedido exitosamente","data":{"pedido":12,"fecha":"2020-05-15","cliente": {"cuit":11001000,"razonSocial":"Pedro Villa","nroDoc":"11001000","tipoDoc":"DNI"},"domicilio": {"altura":1000,"calle":"Yrigoyen","pisoDpto":"","localidad":{"cp":"9100","nombre":"Trelew"}},"total": 972.40}}'          |   