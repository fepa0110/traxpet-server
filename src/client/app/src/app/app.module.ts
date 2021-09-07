import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';

import { FormsModule } from '@angular/forms';
import { HttpClientModule } from "@angular/common/http";
import { PedidosComponent } from './pedidos/pedidos.component';
import { PedidosDetailComponent } from './pedidos-detail/pedidos-detail.component';

import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { NgbdModalConfirm } from './modal-component';

import { ClientesComponent } from './clientes/clientes.component';
import { ClientesDetailComponent } from './clientes-detail/clientes-detail.component';
import { RemitosComponent } from './remitos/remitos.component';
import { FacturasComponent } from './facturas/facturas.component';
import { FacturasDetailComponent } from './facturas-detail/facturas-detail.component';
import { RemitosDetailComponent } from './remitos-detail/remitos-detail.component';
import { ArticulosPedidoComponent } from './articulos-pedido/articulos-pedido.component';
import { ArticulosComponent } from './articulos/articulos.component';
import { ArticulosDetailComponent } from './articulos-detail/articulos-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    PedidosComponent,
    PedidosDetailComponent,
    NgbdModalConfirm,
    ClientesComponent,
    ClientesDetailComponent,
    RemitosComponent,
    FacturasComponent,
    FacturasDetailComponent,
    RemitosDetailComponent,
    ArticulosPedidoComponent,
    ArticulosComponent,
    ArticulosDetailComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    NgbModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
  entryComponents: [
    NgbdModalConfirm,
  ]
})
export class AppModule { }
