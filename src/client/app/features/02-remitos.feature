# language: es

Característica: generar remitos
   módulo que genera remitos busando pedidos pendients de genereción para una fecha

Escenario: generar remitos con fecha "2020-05-13" quedando algunos artículos pendientes por falta de stock
   Dado que existen los siguientes artículos pedidos de pedidos anteriores a la fecha "2020-05-13" sin remito aún
   | Pedido | FechaPedido | Cliente | Domicilio | Articulo | Cantidad | Precio | Stock | 
   | 1      | 2020-05-12  | 1000    | 1001      | 1000     | 10       | 2.3    | 65    |
   | 1      | 2020-05-12  | 1000    | 1001      | 1001     | 40       | 3.1    | 750   |
   | 1      | 2020-05-12  | 1000    | 1001      | 2000     | 125      | 3.4    | 1522  |
   | 1      | 2020-05-12  | 1000    | 1001      | 3000     | 6        | 0.45   | 35    |
   | 1      | 2020-05-12  | 1000    | 1001      | 3001     | 10       | 0.77   | 100   |
   | 1      | 2020-05-12  | 1000    | 1001      | 4000     | 1        | 390    | 4     |
   | 2      | 2020-05-12  | 1000    | 1002      | 1000     | 10       | 2.3    | 65    |
   | 2      | 2020-05-12  | 1000    | 1002      | 1001     | 40       | 3.1    | 750   |
   | 2      | 2020-05-12  | 1000    | 1002      | 2000     | 125      | 3.4    | 1522  |
   | 2      | 2020-05-12  | 1000    | 1002      | 3000     | 6        | 0.45   | 35    |
   | 2      | 2020-05-12  | 1000    | 1002      | 3001     | 10       | 0.77   | 100   |
   | 2      | 2020-05-12  | 1000    | 1002      | 4000     | 1        | 390    | 4     |
   | 3      | 2020-05-12  | 2000    | 2001      | 1000     | 10       | 2.3    | 65    |
   | 3      | 2020-05-12  | 2000    | 2001      | 1001     | 40       | 3.1    | 750   |
   | 3      | 2020-05-12  | 2000    | 2001      | 2000     | 125      | 3.4    | 1522  |
   | 3      | 2020-05-12  | 2000    | 2001      | 3000     | 6        | 0.45   | 35    |
   | 3      | 2020-05-12  | 2000    | 2001      | 3001     | 10       | 0.77   | 100   |
   | 3      | 2020-05-12  | 2000    | 2001      | 4000     | 1        | 390    | 4     |
   | 4      | 2020-05-12  | 2000    | 2001      | 1000     | 10       | 2.3    | 65    |
   | 4      | 2020-05-12  | 2000    | 2001      | 1001     | 40       | 3.1    | 750   |
   | 4      | 2020-05-12  | 2000    | 2001      | 2000     | 125      | 3.4    | 1522  |
   | 4      | 2020-05-12  | 2000    | 2001      | 3000     | 6        | 0.45   | 35    |
   | 4      | 2020-05-12  | 2000    | 2001      | 3001     | 10       | 0.77   | 100   |
   | 4      | 2020-05-12  | 2000    | 2001      | 4000     | 1        | 390    | 4     |
   | 5      | 2020-05-12  | 3000    | 3001      | 1000     | 10       | 2.3    | 65    |
   | 5      | 2020-05-12  | 3000    | 3001      | 1001     | 40       | 3.1    | 750   |
   | 5      | 2020-05-12  | 3000    | 3001      | 2000     | 125      | 3.4    | 1522  |
   | 5      | 2020-05-12  | 3000    | 3001      | 3000     | 6        | 0.45   | 35    |
   | 5      | 2020-05-12  | 3000    | 3001      | 3001     | 10       | 0.77   | 100   |
   | 5      | 2020-05-12  | 3000    | 3001      | 4000     | 1        | 390    | 4     |
   | 6      | 2020-05-12  | 4000    | 4001      | 1000     | 10       | 2.3    | 65    |
   | 6      | 2020-05-12  | 4000    | 4001      | 1001     | 40       | 3.1    | 750   |
   | 6      | 2020-05-12  | 4000    | 4001      | 2000     | 125      | 3.4    | 1522  |
   | 6      | 2020-05-12  | 4000    | 4001      | 3000     | 6        | 0.45   | 35    |
   | 6      | 2020-05-12  | 4000    | 4001      | 3001     | 10       | 0.77   | 100   |
   | 6      | 2020-05-12  | 4000    | 4001      | 4000     | 1        | 390    | 4     |
   | 7      | 2020-05-12  | 5000    | 5002      | 1000     | 10       | 2.3    | 65    |
   | 7      | 2020-05-12  | 5000    | 5002      | 1001     | 40       | 3.1    | 750   |
   | 7      | 2020-05-12  | 5000    | 5002      | 2000     | 125      | 3.4    | 1522  |
   | 7      | 2020-05-12  | 5000    | 5002      | 3000     | 6        | 0.45   | 35    |
   | 7      | 2020-05-12  | 5000    | 5002      | 3001     | 10       | 0.77   | 100   |
   | 7      | 2020-05-12  | 5000    | 5002      | 4000     | 1        | 390    | 4     |

   Cuando solicito generar remitos para pedidos con fecha "2020-05-13"
   Entonces se obtiene el siguiente resultado:
   """
   [
      {  "remito":1,
         "fechaArmado":"2020-05-13",
         "entregado": false,
         "domicilioEntrega":{"altura": 100,"calle": "San Martín","pisoDpto": "","localidad":{"id": 2000,"cp": "9100","nombre": "Trelew"}},
         "cliente":{"cliente": 1000},
         "articulos":[
            {"articulo":{"id": 1000,"nombre": "remache corto","precioUnitario": 2.3},"cantidad":10,"precio":2.3,"pedido":{"pedido":1}},
            {"articulo":{"id": 1001,"nombre": "remache mediano","precioUnitario": 3.1},"cantidad":40,"precio":3.1,"pedido":{"pedido":1}},
            {"articulo":{"id": 2000,"nombre": "tornillo largo","precioUnitario": 3.4},"cantidad":125,"precio":3.4,"pedido":{"pedido":1}},
            {"articulo":{"id": 3000,"nombre": "lija 0.5","precioUnitario": 0.45},"cantidad":6,"precio":0.45,"pedido":{"pedido":1}},
            {"articulo":{"id": 3001,"nombre": "lija 1","precioUnitario": 0.77},"cantidad":10,"precio":0.77,"pedido":{"pedido":1}},
            {"articulo":{"id": 4000,"nombre": "pintura blanca x 4lts","precioUnitario": 390.0},"cantidad":1,"precio":390,"pedido":{"pedido":1}}
         ]
      },
      {  "remito":2,
         "fechaArmado":"2020-05-13",
         "entregado": false,
         "domicilioEntrega":{"altura": 100,"calle": "Belgrano","pisoDpto": "","localidad":{"id": 2000,"cp": "9100","nombre": "Trelew"}},
         "cliente":{"cliente": 1000},
         "articulos":[
            {"articulo":{"id": 1000,"nombre": "remache corto","precioUnitario": 2.3},"cantidad":10,"precio":2.3,"pedido":{"pedido":2}},
            {"articulo":{"id": 1001,"nombre": "remache mediano","precioUnitario": 3.1},"cantidad":40,"precio":3.1,"pedido":{"pedido":2}},
            {"articulo":{"id": 2000,"nombre": "tornillo largo","precioUnitario": 3.4},"cantidad":125,"precio":3.4,"pedido":{"pedido":2}},
            {"articulo":{"id": 3000,"nombre": "lija 0.5","precioUnitario": 0.45},"cantidad":6,"precio":0.45,"pedido":{"pedido":2}},
            {"articulo":{"id": 3001,"nombre": "lija 1","precioUnitario": 0.77},"cantidad":10,"precio":0.77,"pedido":{"pedido":2}},
            {"articulo":{"id": 4000,"nombre": "pintura blanca x 4lts","precioUnitario": 390.0},"cantidad":1,"precio":390,"pedido":{"pedido":2}}
         ]
      },
      {  "remito":3,
         "fechaArmado":"2020-05-13",
         "entregado": false,
         "domicilioEntrega":{"altura": 200,"calle": "Roca","pisoDpto": "","localidad":{"id": 1000,"cp": "9120","nombre": "Puerto Madryn"}},
         "cliente":{"cliente": 2000},
         "articulos":[
            {"articulo":{"id": 1000,"nombre": "remache corto","precioUnitario": 2.3},"cantidad":10,"precio":2.3,"pedido":{"pedido":3}},
            {"articulo":{"id": 1001,"nombre": "remache mediano","precioUnitario": 3.1},"cantidad":40,"precio":3.1,"pedido":{"pedido":3}},
            {"articulo":{"id": 2000,"nombre": "tornillo largo","precioUnitario": 3.4},"cantidad":125,"precio":3.4,"pedido":{"pedido":3}},
            {"articulo":{"id": 3000,"nombre": "lija 0.5","precioUnitario": 0.45},"cantidad":6,"precio":0.45,"pedido":{"pedido":3}},
            {"articulo":{"id": 3001,"nombre": "lija 1","precioUnitario": 0.77},"cantidad":10,"precio":0.77,"pedido":{"pedido":3}},
            {"articulo":{"id": 4000,"nombre": "pintura blanca x 4lts","precioUnitario": 390.0},"cantidad":1,"precio":390,"pedido":{"pedido":3}},
            {"articulo":{"id": 1000,"nombre": "remache corto","precioUnitario": 2.3},"cantidad":10,"precio":2.3,"pedido":{"pedido":4}},
            {"articulo":{"id": 1001,"nombre": "remache mediano","precioUnitario": 3.1},"cantidad":40,"precio":3.1,"pedido":{"pedido":4}},
            {"articulo":{"id": 2000,"nombre": "tornillo largo","precioUnitario": 3.4},"cantidad":125,"precio":3.4,"pedido":{"pedido":4}},
            {"articulo":{"id": 3000,"nombre": "lija 0.5","precioUnitario": 0.45},"cantidad":6,"precio":0.45,"pedido":{"pedido":4}},
            {"articulo":{"id": 3001,"nombre": "lija 1","precioUnitario": 0.77},"cantidad":10,"precio":0.77,"pedido":{"pedido":4}},
            {"articulo":{"id": 4000,"nombre": "pintura blanca x 4lts","precioUnitario": 390.0},"cantidad":1,"precio":390,"pedido":{"pedido":4}}
         ]
      },
      {  "remito":4,
         "fechaArmado":"2020-05-13",
         "entregado": false,
         "domicilioEntrega":{"altura": 300,"calle": "Maiz","pisoDpto": "","localidad":{"id": 1000,"cp": "9120","nombre": "Puerto Madryn"}},
         "cliente":{"cliente": 3000},
         "articulos":[
            {"articulo":{"id": 1000,"nombre": "remache corto","precioUnitario": 2.3},"cantidad":10,"precio":2.3,"pedido":{"pedido":5}},
            {"articulo":{"id": 1001,"nombre": "remache mediano","precioUnitario": 3.1},"cantidad":40,"precio":3.1,"pedido":{"pedido":5}},
            {"articulo":{"id": 2000,"nombre": "tornillo largo","precioUnitario": 3.4},"cantidad":125,"precio":3.4,"pedido":{"pedido":5}},
            {"articulo":{"id": 3000,"nombre": "lija 0.5","precioUnitario": 0.45},"cantidad":6,"precio":0.45,"pedido":{"pedido":5}},
            {"articulo":{"id": 3001,"nombre": "lija 1","precioUnitario": 0.77},"cantidad":10,"precio":0.77,"pedido":{"pedido":5}}
         ]
      },
      {  "remito":5,
         "fechaArmado":"2020-05-13",
         "entregado": false,
         "domicilioEntrega":{"altura": 400,"calle": "Maiz","pisoDpto": "","localidad":{"id": 3000,"cp": "9103","nombre": "Rawson"}},
         "cliente":{"cliente": 4000},
         "articulos":[
            {"articulo":{"id": 1000,"nombre": "remache corto","precioUnitario": 2.3},"cantidad":10,"precio":2.3,"pedido":{"pedido":6}},
            {"articulo":{"id": 1001,"nombre": "remache mediano","precioUnitario": 3.1},"cantidad":40,"precio":3.1,"pedido":{"pedido":6}},
            {"articulo":{"id": 2000,"nombre": "tornillo largo","precioUnitario": 3.4},"cantidad":125,"precio":3.4,"pedido":{"pedido":6}},
            {"articulo":{"id": 3001,"nombre": "lija 1","precioUnitario": 0.77},"cantidad":10,"precio":0.77,"pedido":{"pedido":6}}
         ]
      },
      {  "remito":6,
         "fechaArmado":"2020-05-13",
         "entregado": false,
         "domicilioEntrega":{"altura": 500,"calle": "Sarmiento","pisoDpto": "","localidad":{"id": 1000,"cp": "9120","nombre": "Puerto Madryn"}},
         "cliente":{"cliente": 5000},
         "articulos":[
            {"articulo":{"id": 1001,"nombre": "remache mediano","precioUnitario": 3.1},"cantidad":40,"precio":3.1,"pedido":{"pedido":7}},
            {"articulo":{"id": 2000,"nombre": "tornillo largo","precioUnitario": 3.4},"cantidad":125,"precio":3.4,"pedido":{"pedido":7}},
            {"articulo":{"id": 3001,"nombre": "lija 1","precioUnitario": 0.77},"cantidad":10,"precio":0.77,"pedido":{"pedido":7}}
         ]
      }
   ]
   """

Escenario: generar remitos con fecha "2020-05-16" quedando algunos artículos pendientes por falta de stock
   Dado que existen los siguientes artículos pedidos de pedidos anteriores a la fecha "2020-05-16" sin remito aún
   | Pedido | FechaPedido | Cliente | Domicilio | Articulo | Cantidad | Precio | Stock | 
   | 8      | 2020-05-14  | 6000    | 6001      | 5000     | 20       | 355.25 | 15    |
   | 8      | 2020-05-14  | 6000    | 6001      | 6000     | 1        | 450.10 | 7     |
   | 9      | 2020-05-14  | 7000    | 7001      | 5000     | 20       | 355.25 | 15    |
   | 9      | 2020-05-14  | 7000    | 7001      | 6000     | 1        | 450.10 | 7     |
   | 10     | 2020-05-15  | 8000    | 8001      | 1000     | 10       | 2.3    | 65    |
   | 10     | 2020-05-15  | 8000    | 8001      | 1001     | 40       | 3.1    | 750   |
   | 10     | 2020-05-15  | 8000    | 8001      | 2000     | 125      | 3.4    | 1522  |
   | 10     | 2020-05-15  | 8000    | 8001      | 3000     | 6        | 0.45   | 35    |
   | 10     | 2020-05-15  | 8000    | 8001      | 3001     | 10       | 0.77   | 100   |
   | 10     | 2020-05-15  | 8000    | 8001      | 4000     | 1        | 390    | 4     |
   | 11     | 2020-05-15  | 9000    | 9002      | 1000     | 10       | 2.3    | 65    |
   | 11     | 2020-05-15  | 9000    | 9002      | 1001     | 40       | 3.1    | 750   |
   | 11     | 2020-05-15  | 9000    | 9002      | 2000     | 125      | 3.4    | 1522  |
   | 11     | 2020-05-15  | 9000    | 9002      | 3000     | 6        | 0.45   | 35    |
   | 11     | 2020-05-15  | 9000    | 9002      | 3001     | 10       | 0.77   | 100   |
   | 11     | 2020-05-15  | 9000    | 9002      | 4000     | 1        | 390    | 4     |
   | 12     | 2020-05-15  | 10000   | 10001     | 1000     | 10       | 2.3    | 65    |
   | 12     | 2020-05-15  | 10000   | 10001     | 1001     | 40       | 3.1    | 750   |
   | 12     | 2020-05-15  | 10000   | 10001     | 2000     | 125      | 3.4    | 1522  |
   | 12     | 2020-05-15  | 10000   | 10001     | 3000     | 6        | 0.45   | 35    |
   | 12     | 2020-05-15  | 10000   | 10001     | 3001     | 10       | 0.77   | 100   |
   | 12     | 2020-05-15  | 10000   | 10001     | 4000     | 1        | 390    | 4     |

   Cuando solicito generar remitos para pedidos con fecha "2020-05-16"
   Entonces se obtiene el siguiente resultado:
   """
   [
      {  "remito":7,
         "fechaArmado":"2020-05-16",
         "entregado": false,
         "domicilioEntrega":{"altura": 600,"calle": "Av. Gales","pisoDpto": "","localidad":{"id": 1000,"cp": "9120","nombre": "Puerto Madryn"}},
         "cliente":{"cliente": 6000},
         "articulos":[
            {"articulo":{"id": 6000,"nombre": "Aceite de lino x 4lts","precioUnitario": 450.1},"cantidad":1,"precio":450.10,"pedido":{"pedido":8}} 
         ]
      },
      {  "remito":8,
         "fechaArmado":"2020-05-16",
         "entregado": false,
         "domicilioEntrega":{"altura": 700,"calle": "Mitre","pisoDpto": "","localidad":{"id": 1000,"cp": "9120","nombre": "Puerto Madryn"}},
         "cliente":{"cliente": 7000},
         "articulos":[
            {"articulo":{"id": 6000,"nombre": "Aceite de lino x 4lts","precioUnitario": 450.1},"cantidad":1,"precio":450.10,"pedido":{"pedido":9}}
         ]
      },
      {  "remito":9,
         "fechaArmado":"2020-05-16",
         "entregado": false,
         "domicilioEntrega":{"altura": 800,"calle": "Bvd. Brown","pisoDpto": "","localidad":{"id": 1000,"cp": "9120","nombre": "Puerto Madryn"}},
         "cliente":{"cliente": 8000},
         "articulos":[
            {"articulo":{"id": 1001,"nombre": "remache mediano","precioUnitario": 3.1},"cantidad":40,"precio":3.1,"pedido":{"pedido":10}},
            {"articulo":{"id": 2000,"nombre": "tornillo largo","precioUnitario": 3.4},"cantidad":125,"precio":3.4,"pedido":{"pedido":10}},
            {"articulo":{"id": 3001,"nombre": "lija 1","precioUnitario": 0.77},"cantidad":10,"precio":0.77,"pedido":{"pedido":10}}
         ]
      },
      {  "remito":10,
         "fechaArmado":"2020-05-16",
         "entregado": false,
         "domicilioEntrega":{"altura": 900,"calle": "Chiquichan","pisoDpto": "","localidad":{"id": 1000,"cp": "9120","nombre": "Puerto Madryn"}},
         "cliente":{"cliente": 9000},
         "articulos":[
            {"articulo":{"id": 1001,"nombre": "remache mediano","precioUnitario": 3.1},"cantidad":40,"precio":3.1,"pedido":{"pedido":11}},
            {"articulo":{"id": 2000,"nombre": "tornillo largo","precioUnitario": 3.4},"cantidad":125,"precio":3.4,"pedido":{"pedido":11}},
            {"articulo":{"id": 3001,"nombre": "lija 1","precioUnitario": 0.77},"cantidad":10,"precio":0.77,"pedido":{"pedido":11}}
         ]
      },
      {  "remito":11,
         "fechaArmado":"2020-05-16",
         "entregado": false,
         "domicilioEntrega":{"altura": 1000,"calle": "Yrigoyen","pisoDpto": "","localidad":{"id": 2000,"cp": "9100","nombre": "Trelew"}},
         "cliente":{"cliente": 10000},
         "articulos":[
            {"articulo":{"id": 1001,"nombre": "remache mediano","precioUnitario": 3.1},"cantidad":40,"precio":3.1,"pedido":{"pedido":12}},
            {"articulo":{"id": 2000,"nombre": "tornillo largo","precioUnitario": 3.4},"cantidad":125,"precio":3.4,"pedido":{"pedido":12}},
            {"articulo":{"id": 3001,"nombre": "lija 1","precioUnitario": 0.77},"cantidad":10,"precio":0.77,"pedido":{"pedido":12}}
         ]
      }
   ]
   """   

Esquema del escenario: Actualiza stock faltante
   Dado el artículo <Articulo>
   Cuando actualizo el stock en <Cantidad>
   Entonces se obtiene el siguiente resultado <Resultado>

   Ejemplos:
   | Articulo | Cantidad | Resultado |
   | 1000     | 35       | '{"StatusCode": 200,"StatusText": "Stock actualizado correctamente","data":{"articulo": 1000,"stock": 40}}' |
   | 3000     | 13       | '{"StatusCode": 200,"StatusText": "Stock actualizado correctamente","data":{"articulo": 3000,"stock": 18}}' |
   | 4000     | 50       | '{"StatusCode": 200,"StatusText": "Stock actualizado correctamente","data":{"articulo": 4000,"stock": 50}}' |
   | 5000     | 50       | '{"StatusCode": 200,"StatusText": "Stock actualizado correctamente","data":{"articulo": 5000,"stock": 65}}' |

Escenario: generar remitos con fecha "2020-05-15" luego de stock renovado
   Dado que existen los siguientes artículos pedidos de pedidos anteriores a la fecha "2020-05-15" sin remito aún
   | Pedido | FechaPedido | Cliente | Domicilio | Articulo | Cantidad | Precio | Stock | 
   | 5      | 2020-05-12  | 3000    | 3001      | 4000     | 1        | 390    | 40    |
   | 6      | 2020-05-12  | 4000    | 4001      | 3000     | 6        | 0.45   | 17    |
   | 6      | 2020-05-12  | 4000    | 4001      | 4000     | 1        | 390    | 50    |
   | 7      | 2020-05-12  | 5000    | 5002      | 1000     | 10       | 2.3    | 40    |
   | 7      | 2020-05-12  | 5000    | 5002      | 3000     | 6        | 0.45   | 17    |
   | 7      | 2020-05-12  | 5000    | 5002      | 4000     | 1        | 390    | 50    |
   | 8      | 2020-05-14  | 6000    | 6001      | 5000     | 20       | 355.25 | 65    |
   | 9      | 2020-05-14  | 7000    | 7001      | 5000     | 20       | 355.25 | 65    |

   Cuando solicito generar remitos para pedidos con fecha "2020-05-14"
   Entonces se obtiene el siguiente resultado:
   """
   [
      {  "remito":12,
         "fechaArmado":"2020-05-14",
         "entregado": false,
         "domicilioEntrega":{"altura": 300,"calle": "Maiz","pisoDpto": "","localidad":{"id": 1000,"cp": "9120","nombre": "Puerto Madryn"}},
         "cliente":{"cliente": 3000},
         "articulos":[
            {"articulo":{"id": 4000,"nombre": "pintura blanca x 4lts","precioUnitario": 390.0},"cantidad":1,"precio":390,"pedido":{"pedido":5}}
         ]
      },
      {  "remito":13,
         "fechaArmado":"2020-05-14",
         "entregado": false,
         "domicilioEntrega":{"altura": 400,"calle": "Maiz","pisoDpto": "","localidad":{"id": 3000,"cp": "9103","nombre": "Rawson"}},
         "cliente":{"cliente": 4000},
         "articulos":[
            {"articulo":{"id": 3000,"nombre": "lija 0.5","precioUnitario": 0.45},"cantidad":6,"precio":0.45,"pedido":{"pedido":6}},
            {"articulo":{"id": 4000,"nombre": "pintura blanca x 4lts","precioUnitario": 390.0},"cantidad":1,"precio":390,"pedido":{"pedido":6}}
         ]
      },
      {  "remito":14,
         "fechaArmado":"2020-05-14",
         "entregado": false,
         "domicilioEntrega":{"altura": 500,"calle": "Sarmiento","pisoDpto": "","localidad":{"id": 1000,"cp": "9120","nombre": "Puerto Madryn"}},
         "cliente":{"cliente": 5000},
         "articulos":[
            {"articulo":{"id": 1000,"nombre": "remache corto","precioUnitario": 2.3},"cantidad":10,"precio":2.3,"pedido":{"pedido":7}},
            {"articulo":{"id": 3000,"nombre": "lija 0.5","precioUnitario": 0.45},"cantidad":6,"precio":0.45,"pedido":{"pedido":7}},
            {"articulo":{"id": 4000,"nombre": "pintura blanca x 4lts","precioUnitario": 390.0},"cantidad":1,"precio":390,"pedido":{"pedido":7}}
         ]
      },
      {  "remito":15,
         "fechaArmado":"2020-05-14",
         "entregado": false,
         "domicilioEntrega":{"altura": 600,"calle": "Av. Gales","pisoDpto": "","localidad":{"id": 1000,"cp": "9120","nombre": "Puerto Madryn"}},
         "cliente":{"cliente": 6000},
         "articulos":[
            {"articulo":{"id": 5000,"nombre": "Machimbre 10mm","precioUnitario": 355.25},"cantidad":20,"precio":355.25,"pedido":{"pedido":8}}
         ]
      },
      {  "remito":16,
         "fechaArmado":"2020-05-14",
         "entregado": false,
         "domicilioEntrega":{"altura": 700,"calle": "Mitre","pisoDpto": "","localidad":{"id": 1000,"cp": "9120","nombre": "Puerto Madryn"}},
         "cliente":{"cliente": 7000},
         "articulos":[
            {"articulo":{"id": 5000,"nombre": "Machimbre 10mm","precioUnitario": 355.25},"cantidad":20,"precio":355.25,"pedido":{"pedido":9}}
         ]
      }
   ]
   """   
