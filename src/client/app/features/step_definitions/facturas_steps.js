const assert = require('assert');
const { Given, When, Then } = require('cucumber');
const request = require('sync-request');
const jsondiff = require('json-diff');

const serverUrl = "http://server:8080/labprog-server/rest";

let respuestaRemitoEntregado;
let facturasGeneradas;
let facturaSinAbonar;
let respuestaFacturaAbonada;

Given('el remito {int} que no está entregado aún', function (remito) {
   // return 'pending';
   try {
      let res = request('GET', serverUrl + '/remitos/numero/' + remito);
      let body = JSON.parse(res.getBody('utf8'));
      let data = body.data;

      if (body.StatusCode == 200) {
         if(!data.entregado){
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

When('se marca como entregado al remito número {int}', function (remito) {
   // return 'pending';
   try {
      let res = request('PUT', serverUrl + '/remitos/entregar',{
         json: {
            "remito": remito
         }
      });

      let body = JSON.parse(res.getBody('utf8'));
      let data = body.data;

      if (body.StatusCode == 200) {
         respuestaRemitoEntregado = body;
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

Then('se marca como entregado se obtiene {string}', function (resultadoEsperado) {
   // return 'pending';
   let respuestaEsperada = JSON.parse(resultadoEsperado);

   return assert.equal(undefined, jsondiff.diff(respuestaEsperada, respuestaRemitoEntregado));
});

Given('los remitos entregados', function () {
   return true;
});

When('se solicita facturar', function () {
   // return 'pending';
   try {
      let res = request('POST', serverUrl + '/facturas/facturar');

      let body = JSON.parse(res.getBody('utf8'));

      if (body.StatusCode == 200) {
         facturasGeneradas = body.data;
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

Then('se obtiente el siguiente resultado de facturación', function (resultadoEsperado) {
   // return 'pending';
   let facturasEsperadas = JSON.parse(resultadoEsperado);

   return assert.equal(undefined, jsondiff.diff(facturasGeneradas, facturasEsperadas));
});

Given('la factura {int} que no ha sido abonada aún', function (factura) {
   // return 'pending';
   try {
      let res = request('GET', serverUrl + '/facturas/' + factura);
      let body = JSON.parse(res.getBody('utf8'));
      facturaSinAbonar = body;
      return true;
   } 
   catch (error) {
      return assert.fail(error.message);
   }
});

When('la factura número {int} se marca como abonada en la fecha {string}', function (factura, fechaAbono) {
   // return 'pending';
   try {
      let res = request('PUT', serverUrl + '/facturas/abonar',{
         json: {
            "factura": factura,
            "fechaPago": fechaAbono
         }
      });

      let body = JSON.parse(res.getBody('utf8'));
      respuestaFacturaAbonada = body;

      if (body.StatusCode == 200 || body.StatusCode == 500) {
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

Then('se obtiene el siguiente {string} de facturas abonadas', function (resultadoEsperado) {
   // Write code here that turns the phrase above into concrete actions
   // return 'pending';
   let facturaAbonadaEsperada = JSON.parse(resultadoEsperado);

   return assert.equal(undefined, jsondiff.diff(facturaAbonadaEsperada, respuestaFacturaAbonada));
});
