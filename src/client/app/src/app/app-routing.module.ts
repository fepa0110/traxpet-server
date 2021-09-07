import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';

import { PedidosComponent } from './pedidos/pedidos.component';
import { PedidosDetailComponent } from "./pedidos-detail/pedidos-detail.component";

import { RemitosComponent } from './remitos/remitos.component';
import { RemitosDetailComponent } from "./remitos-detail/remitos-detail.component";

import { FacturasComponent } from './facturas/facturas.component';
import { FacturasDetailComponent } from "./facturas-detail/facturas-detail.component";

import { ClientesComponent } from './clientes/clientes.component';
import { ClientesDetailComponent } from "./clientes-detail/clientes-detail.component";

import { ArticulosPedidoComponent } from './articulos-pedido/articulos-pedido.component';

import { ArticulosComponent } from "./articulos/articulos.component";
import { ArticulosDetailComponent } from "./articulos-detail/articulos-detail.component";

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'pedidos', component: PedidosComponent },
  { path: 'pedidos/byCliente/:id', component: PedidosComponent },
  { path: 'pedidos/:action/:id', component: PedidosDetailComponent },
  { path: 'remitos', component: RemitosComponent },
  { path: 'remitos/:action/:id', component: RemitosComponent },
  { path: 'remitos/:numeroRemito', component: RemitosDetailComponent },
  { path: 'facturas', component: FacturasComponent },
  { path: 'facturas/byCliente/:id', component: FacturasComponent },
  { path: 'facturas/:id', component: FacturasDetailComponent },
  { path: 'clientes', component: ClientesComponent },
  { path: 'clientes/:action/:id', component: ClientesDetailComponent },
  { path: 'articulosPedido/:id', component: ArticulosPedidoComponent },
  { path: 'articulos', component: ArticulosComponent },
  { path: 'articulos/:action/:id', component: ArticulosDetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
