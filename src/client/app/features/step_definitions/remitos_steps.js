const assert = require('assert');
const { Given, When, Then } = require('cucumber');
const request = require('sync-request');
const jsondiff = require('json-diff');
const deleteKey = require('key-del');

const serverUrl = "http://server:8080/labprog-server/rest";

let articulo;
let respuestaArticuloNewStock;

Given('que existen los siguientes artículos pedidos de pedidos anteriores a la fecha {string} sin remito aún', 
   function (fechaPedido, articulosPedidosDT) {
      try {
         let res = request('GET', serverUrl + '/articulosPedido/beforeFecha?fechaPedido=' + fechaPedido);
         let body = JSON.parse(res.getBody('utf8'));
         let data = body.data;

         if (body.StatusCode == 200) {
            if(data.length == articulosPedidosDT.rows().length){
               return true;
            }
         }
         else {
            return assert.fail(body.StatusText);
         }
      } 
      catch (error) {
         return assert.fail(error.message);
      }
   }
);

let remitosGenerados;
When('solicito generar remitos para pedidos con fecha {string}', function (fechaPedido) {
   // return "pending";
   try {
      let res = request('POST', serverUrl + '/remitos/generarRemitos?fechaProceso='+fechaPedido,{
      });
      
      remitosGenerados = JSON.parse(res.getBody('utf8'));

      
      if (remitosGenerados.StatusCode == 200) {
         return true;
      }
      else {
         return assert.fail(pedidoGenerado.StatusText);
      }
   }
   catch (error) {
      return assert.fail(error.message);
   }
});

Then('se obtiene el siguiente resultado:', function (resultadoEsperado) {
   // return 'pending';
   let remitosEsperados = JSON.parse(resultadoEsperado);   

   let keysToDelete = ['domicilioEntrega.id',
                        'articulos.id',
                        'articulos.remito',
                        'articulo.descripcion',
                        'domicilioEntrega.cliente',
                        'observaciones',
                        'fecha',
                        'pedido.id',
                        'pedido.cliente',
                        'pedido.domicilioEntrega',
                        'pedido.remito',
                        'stock',
                        'factura'
                     ]

   remitosGenerados.data.forEach(remito => {
      remito.articulos.forEach(articuloPedido => {
         delete articuloPedido['id'];
         delete articuloPedido['remito'];
      })
   });

   remitosGenerados = deleteKey(remitosGenerados, keysToDelete);

   return assert.equal(undefined, jsondiff.diff(remitosEsperados, remitosGenerados.data));
});

Given('el artículo {int}', function (numeroArticulo) {
   // return 'pending';
   try {
      let res = request('GET', serverUrl + '/articulos/id/' + numeroArticulo);
      let body = JSON.parse(res.getBody('utf8'));
      let data = body.data;

      if (body.StatusCode == 200) {
         if(data.id == numeroArticulo){
            articulo = data;
            return true;
         }
      }
      else {
         return assert.fail(body.StatusText);
      }
   } 
   catch (error) {
      return assert.fail(error.message);
   }
});

When('actualizo el stock en {int}', function (nuevoStock) {
   // return 'pending';
   try {
      let articuloNewStock = {
         "id": articulo.id,
         "stock": nuevoStock
      }

      let res = request('PUT', serverUrl + '/articulos/newStock',{
            json: articuloNewStock
      });

      let body = JSON.parse(res.getBody('utf8'));

      if (body.StatusCode == 200) {
         respuestaArticuloNewStock = body;
         return true;
      }
      else {
         return assert.fail(body.StatusText);
      }
   } 
   catch (error) {
      return assert.fail(error.message);
   }
});

Then('se obtiene el siguiente resultado {string}', function (resultadoEsperado) {
   // return 'pending';
   let articuloEsperado = JSON.parse(resultadoEsperado);

   return assert.equal(undefined, jsondiff.diff(articuloEsperado, respuestaArticuloNewStock));
});
