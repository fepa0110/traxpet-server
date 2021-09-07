# language: es

Característica: Administrar de la Facturación
         proceso que emite facturas de remitos entregados no facturados aún

   Esquema del escenario:
      Dado el remito <remito> que no está entregado aún
      Cuando se marca como entregado al remito número <remito>
      Entonces se marca como entregado se obtiene <resultado>

      Ejemplos:
      | remito | resultado                                                                                     |
      | 1      | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 1}}'   |
      | 2      | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 2}}'   |
      | 4      | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 4}}'   |
      | 5      | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 5}}'   |
      | 6      | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 6}}'   |
      | 10     | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 10}}'  |
      | 11     | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 11}}'  |
      | 12     | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 12}}'  |
      | 14     | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 14}}'  |

   Escenario: 
      Dado los remitos entregados
      Cuando se solicita facturar
      Entonces se obtiente el siguiente resultado de facturación
      """
      {
         "facturas": [
            {  "factura": 1,
               "cliente": 1000,
               "remitos": [1,2],
               "totalFacturado": 1944.80
            },
            {  "factura": 2,
               "cliente": 3000,
               "remitos": [4,12],
               "totalFacturado": 972.40
            },
            {  "factura": 3,
               "cliente": 4000,
               "remitos": [5],
               "totalFacturado": 579.70
            },
            {  "factura": 4,
               "cliente": 5000,
               "remitos": [6,14],
               "totalFacturado": 972.40
            },
            {  "factura": 5,
               "cliente": 9000,
               "remitos": [10],
               "totalFacturado": 556.70
            },
            {  "factura": 6,
               "cliente": 10000,
               "remitos": [11],
               "totalFacturado": 556.70
            }
         ]
      }
      """

   Esquema del escenario:
      Dado el remito <remito> que no está entregado aún
      Cuando se marca como entregado al remito número <remito>
      Entonces se marca como entregado se obtiene <resultado>

      Ejemplos:
      | remito | resultado                                                                                            |
      | 3      | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 3}}' |
      | 7      | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 7}}' |
      | 8      | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 8}}' |
      | 9      | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 9}}' |
      | 13     | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 13}}' |
      | 15     | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 15}}' |
      | 16     | '{"StatusCode": 200,"StatusText": "Remito entregado correctamente","data":{"remito": 16}}' |

   Escenario: 
      Dado los remitos entregados
      Cuando se solicita facturar
      Entonces se obtiente el siguiente resultado de facturación
      """
      {
         "facturas": [
            {  "factura": 7,
               "cliente": 2000,
               "remitos": [3],
               "totalFacturado": 1944.80
            },
            {  "factura": 8,
               "cliente": 4000,
               "remitos": [13],
               "totalFacturado": 392.70
            },
            {  "factura": 9,
               "cliente": 6000,
               "remitos": [7,15],
               "totalFacturado": 7555.10
            },
            {  "factura": 10,
               "cliente": 7000,
               "remitos": [8,16],
               "totalFacturado": 7555.10
            },
            {  "factura": 11,
               "cliente": 8000,
               "remitos": [9],
               "totalFacturado": 556.70
            }
         ]
      }
      """

      Esquema del escenario:
      Dada la factura <factura> que no ha sido abonada aún
      Cuando la factura número <factura> se marca como abonada en la fecha "2020-06-03"
      Entonces se obtiene el siguiente <resultado> de facturas abonadas

      Ejemplos:
      | factura | resultado                                                                                            |
      | 1       | '{"StatusCode": 200,"StatusText": "Factura abonada correctamente","data":{"factura": 1}}' |
      | 2       | '{"StatusCode": 200,"StatusText": "Factura abonada correctamente","data":{"factura": 2}}' |
      | 3       | '{"StatusCode": 200,"StatusText": "Factura abonada correctamente","data":{"factura": 3}}' |
      | 5       | '{"StatusCode": 200,"StatusText": "Factura abonada correctamente","data":{"factura": 5}}' |
      | 8       | '{"StatusCode": 200,"StatusText": "Factura abonada correctamente","data":{"factura": 8}}' |
      | 13      | '{"StatusCode": 500,"StatusText": "la factura 13 no existe","data":{}}' |
