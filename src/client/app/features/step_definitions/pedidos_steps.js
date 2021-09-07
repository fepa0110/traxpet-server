const assert = require('assert');
const { Given, When, Then } = require('cucumber');
const request = require('sync-request');
const jsondiff = require('json-diff');

const serverUrl = "http://server:8080/labprog-server/rest";

let cliente;
Given('que llama el cliente existente con razón social {string} con cuit {int}',
   function(razonSocial, cuit) {
      try {
            let res = request('GET', serverUrl + '/clientes/cuit/' + cuit);
            let body = JSON.parse(res.getBody('utf8'));

            if (body.StatusCode == 200) {
               assert.equal(razonSocial, body.data.razonSocial);
               assert.equal(cuit, body.data.cuit);
               //va generando el cliente
               cliente = body.data;
               cliente.domiciliosEntrega = [];
               return true;
            } else {
               cliente = {};
               return assert.fail(body.StatusText);
            }
      } 
      catch (error) {
            cliente = {};
            return assert.fail(error.message);
      }
   }
);

let domicilioPedido;
Given('que solicita se envíe el pedido al domicilio existente en la localidad {string} calle {string} y altura {int}', 
      function(localidad, calle, altura) {
         try {
               let res = request('GET', serverUrl + '/clientes/' + cliente.id + '/domicilios');

               let body = JSON.parse(res.getBody('utf8'));

               let encontrado = false;

               if (body.StatusCode == 200) {
                  // busca el domicilio en la lista
                  body.data.forEach(domicilio => {
                     if (domicilio.localidad.nombre == localidad &&
                           domicilio.calle == calle &&
                           domicilio.altura == altura 
                           && !encontrado){
                              domicilioPedido = domicilio;
                              delete domicilioPedido.cliente;        
                              encontrado = true;
                     }
                  });
                  
                  if(encontrado) return encontrado;
                  else return assert.fail("El domicilio no existe");
               } 
               else {
                  return assert.fail(body.StatusText);
               }
         } catch (error) {
               return assert.fail(error.message);
         }
});

let articulosPedido = [];
Given('que pide los siguientes artículos', function(articulosPedidosDT) {
   try {
      articulosPedido = [];
      articulosPedidosDT.rows().forEach(articulo => {
            let res = request('GET', serverUrl + '/articulos/id/' + articulo[0]);
            let body = JSON.parse(res.getBody('utf8'));
            let data = body.data;
            
            if (body.StatusCode == 200) {
               if(articulo[1] == data.nombre &&
                  articulo[3] == data.precioUnitario){
                     articulosPedido.push({
                        "articulo": data,
                        "cantidad": articulo[2],
                        "precio": data.precioUnitario
                     });
                     return true;
                  }
               else return assert.fail("No existe el articulo " + (articulo[1]));
            }
            else {
               return assert.fail(body.StatusText);
            }
         }
      );    
   } 
   catch (error) {
      return assert.fail(error.message);
   }
});

let pedidoGenerado;
When('guarda el nuevo pedido con fecha {string}', function(fechaPedido) {
   try {
      let aPedido = {
         "cliente": cliente,
         "domicilioEntrega": domicilioPedido,
         "observaciones": "",
         "fecha": fechaPedido,
         "articulosPedido": articulosPedido
      }

      // console.log(JSON.stringify(aPedido));

      let res = request('POST', serverUrl + '/pedidos',{
            json: aPedido
      });

      pedidoGenerado = JSON.parse(res.getBody('utf8'));
      
      if (pedidoGenerado.StatusCode == 200) {
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

Then('obtiene la siguiente respuesta {string}', function(resultadoEsperado) {
   let pedidoEsperado = JSON.parse(resultadoEsperado);
   delete pedidoEsperado.data.pedido;

   delete pedidoGenerado.data.id;
   delete pedidoGenerado.data.observaciones;
   delete pedidoGenerado.data.cliente.id;
   pedidoGenerado.data.cliente.nroDoc = pedidoGenerado.data.cliente.cuit.toString();
   pedidoGenerado.data.cliente.tipoDoc = "DNI"
   
   delete pedidoGenerado.data.domicilio.cliente;
   delete pedidoGenerado.data.cliente.cliente;
   delete pedidoGenerado.data.domicilio.id;
   delete pedidoGenerado.data.domicilio.localidad.id;
   delete pedidoGenerado.data.cliente.domiciliosEntrega;

   return assert.equal(undefined, jsondiff.diff(pedidoGenerado.data, pedidoEsperado.data));
});

Given('que llama la el cliente {string} que no existe con razón social {string} con tipo de documento {string} y número {string}', 
   function(nroCliente,razonSocial, tipoDoc, nroDoc) {
      try {
         let res = request('GET', serverUrl + '/clientes/tipoDoc/'+tipoDoc+'/doc/' + nroDoc);
         let body = JSON.parse(res.getBody('utf8'));

         //Si el cliente NO existe
         if (body.StatusCode == 501) {
            let aCliente = {
               "cliente": nroCliente,
               "cuit": nroDoc,
               "tipoDoc": tipoDoc,
               "nroDoc": nroDoc,
               "razonSocial": razonSocial,
               "domiciliosEntrega": []
            }

            res = request('POST', serverUrl + '/clientes',{
                  json: aCliente
            });

            body = JSON.parse(res.getBody('utf8'));
            cliente = body.data;

            if (body.StatusCode == 200) {
               return true;
            }
         }
         else {
            return assert.fail(body.StatusText);
         }
      } 
      catch (error) {
            cliente = {};
            return assert.fail(error.message);
      }
   }
);

Given('que solicita se envíe el pedido al domicilio que no existe con localidad {string} calle {string} y altura {int}', 
   function(localidad, calle, altura) {
      try {
         let res = request('GET', serverUrl + '/clientes/' + cliente.id + '/domicilios');

         let body = JSON.parse(res.getBody('utf8'));

         let domicilioEncontrado = false;

         if (body.StatusCode == 200) {
            // busca el domicilio en la lista
            body.data.forEach(domicilio => {
               if (domicilio.localidad.nombre == localidad &&
                  domicilio.calle == calle &&
                  domicilio.altura == altura 
                  && !domicilioEncontrado){
                        domicilioPedido = domicilio;
                        delete domicilioPedido.cliente;        
                        domicilioEncontrado = true;
               }
            });
            
            if(domicilioEncontrado) {
               return assert.fail("El domicilio ya existe");
            }
            else {
               let aDomicilio = {
                  "calle": calle,
                  "altura": altura,
                  "localidad": {
                     "nombre":localidad
                  },
                  "pisoDpto": ""
               }

               res = request('POST', serverUrl + '/clientes/addDomicilio/'+cliente.id,{
                     json: aDomicilio
               });

               body = JSON.parse(res.getBody('utf8'));

               domicilioPedido = body.data;
               if (body.StatusCode == 200) {
                  return true;
               }
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