import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from "@angular/router";
import { Location, formatCurrency } from "@angular/common";
import { ModalService } from "../modal.service";

import {
  catchError,
  debounceTime,
  distinctUntilChanged,
  tap,
  switchMap,
  map,
} from "rxjs/operators";

import { Observable, of } from "rxjs";

import {
  NgbDateStruct,
  NgbCalendar,
  NgbDate,
} from "@ng-bootstrap/ng-bootstrap";

import { Pedido } from "../models/pedido";
import { Cliente } from "../models/cliente";
import { DomicilioEntrega } from "../models/domicilioEntrega";
import { DataPackage } from "../data-package";
import { PedidoParaEnviar } from '../models/pedidoParaEnviar';
import { ArticuloPedido } from '../models/articuloPedido';
import { Articulo } from '../models/articulo';

import { PedidoService } from "../services/pedido.service";
import { ClienteService } from "../services/cliente.service";
import { ArticuloService } from "../services/articulo.service";
import { ArticuloPedidoService } from "../services/articulo-pedido.service";

export interface PrecioTotalPedido{
  "precioTotal": number;
}

@Component({
  selector: 'app-pedidos-detail',
  templateUrl: './pedidos-detail.component.html',
  styleUrls: ['./pedidos-detail.component.css']
})
export class PedidosDetailComponent implements OnInit {
  newBycliente: boolean = false;
  view: boolean = false;
  
  
  pedido: Pedido;
  precioTotal: PrecioTotalPedido = {"precioTotal": 0};
  domiciliosEntrega: DomicilioEntrega[];
  articulosPedido: ArticuloPedido[] = [];
  pedidoParaEnviar: PedidoParaEnviar;
  fechaDate: NgbDateStruct;

  searching: boolean = false;
  searchFailed: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pedidoService: PedidoService,
    private clienteService: ClienteService,
    private articuloService: ArticuloService,
    private articuloPedidoService: ArticuloPedidoService,
    private location: Location,
    private calendar: NgbCalendar,
    private modalService: ModalService
  ) {}

  ngOnInit() {
    this.get();
  }

  get(): void {
    const action = this.route.snapshot.paramMap.get('action');
    if(action === "new") {
      const numeroCliente = this.route.snapshot.paramMap.get('id');
      
      this.view = false;
      this.pedido = {
        id: null,
        pedido: null,
        fecha: new Date(),
        observaciones: null,
        cliente: {id: null, cliente: null, cuit: null, tipoDoc: null, nroDoc: null, razonSocial:null, domiciliosEntrega: []},
        domicilioEntrega: null
      };
      
      if(numeroCliente != "0"){
        this.clienteService.getByNumeroCliente(numeroCliente).subscribe((dataPackage) => {
          this.pedido.cliente = <Cliente>dataPackage.data;
          this.newBycliente = true;
        }); 
      }
      
      this.fechaDate = this.calendar.getToday();
    }
    else {
      if(action === "view"){
        this.view = true;
      }
      const numeroPedido = this.route.snapshot.paramMap.get('id');
      this.pedidoService.get(numeroPedido).subscribe((dataPackage) => {
        this.pedido = <Pedido>dataPackage.data;
        this.pedido.fecha = new Date(this.pedido.fecha);
        this.fechaDate = NgbDate.from({
          day: this.pedido.fecha.getUTCDate(),
          month: this.pedido.fecha.getUTCMonth(),
          year: this.pedido.fecha.getUTCFullYear(),
        });
      });
      this.pedidoService.getPrecioTotal(numeroPedido).subscribe((dataPackage) => {
        this.precioTotal = <PrecioTotalPedido>(<DataPackage>dataPackage).data;
      });
      this.articuloPedidoService.getByNumeroPedido(numeroPedido).subscribe((dataPackage) => {
        this.articulosPedido = <ArticuloPedido[]>dataPackage.data;
      });
    } 
  }

/*   formatPrecio(precio: number): string{
    return formatCurrency(precio, "$", "ARS", "ARS", ".2-2");
  } */

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.pedidoParaEnviar = {
      cliente: this.pedido.cliente,
      domicilioEntrega: this.pedido.domicilioEntrega,
      observaciones: this.pedido.observaciones,
      fecha: new Date(
        this.fechaDate.year,
        this.fechaDate.month - 1,
        this.fechaDate.day
      ),
      articulosPedido: this.articulosPedido
    };

    this.pedidoService.save(this.pedidoParaEnviar).subscribe((dataPackage) => {
      if(this.newBycliente){
        const numeroCliente = this.route.snapshot.paramMap.get('id');
        this.router
          .navigateByUrl("/", {
            skipLocationChange: true,
          })
          .then(() =>
            this.router.navigate(["/pedidos/byCliente/"+numeroCliente])
          );
      }
      else{
        this.router
          .navigateByUrl("/", {
            skipLocationChange: true,
          })
          .then(() =>
            this.router.navigate(["/pedidos"])
          );
      }
    });
  }

  searchCliente = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap(term => term.length < 1 ? [] :
        this.clienteService
          .search(term)
          .pipe(map((response) => response.data))
          .pipe(
            tap(() => {
              this.searchFailed = false;
            }),
            catchError(() => {
              this.searchFailed = true;
              return of([]);
            })
          )
      ),
      tap(() => {
        this.searching = false;
      })
    );

  searchArticulo = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap(term => term.length < 1 ? [] :
        this.articuloService
          .search(term)
          .pipe(map((response) => response.data))
          .pipe(
            tap(() => {
              this.searchFailed = false;
            }),
            catchError(() => {
              this.searchFailed = true;
              return of([]);
            })
          )
      ),
      tap(() => {
        this.searching = false;
      })
  );

  resultFormatArticulo(value: any) {
    return value.id + " - " + value.nombre + " - $" + value.precioUnitario;
  }

  inputFormatArticulo(value: any) {
    if (value) return value.id + " - " + value.nombre + " - $" + value.precioUnitario;
    return null;
  }

  getDomiciliosEntrega(): DomicilioEntrega[] {
    return this.pedido.cliente ? this.pedido.cliente.domiciliosEntrega : [];
  }

  resultFormatCliente(value: any) {
    return value.cliente ? value.cliente + " - " + value.cuit + " - " + value.razonSocial : "";
  }

  inputFormatCliente(value: any) {
    if (value.cliente) return value.cliente + " - " + value.cuit + " - " + value.razonSocial;
    return "";
  }

  resultFormatDomicilioEntrega(value: any) {
    return value.calle +" "+ value.altura + " - " + value.localidad.nombre;
  }

  inputFormatDomicilioEntrega(value: any) {
    if (value) return value.calle +" "+ value.altura + " - " + value.localidad.nombre;
    return null;
  }

  addArticulo(): void {
    this.articulosPedido.push({ id: null, articulo: null, cantidad: 0, precio: null});
  }

  updatePrecioArticuloPedido(articuloPedido: ArticuloPedido) : number{
    articuloPedido.precio = articuloPedido.articulo.precioUnitario * articuloPedido.cantidad;
    return articuloPedido.precio;
  }

  removeArticulo(articuloPedido: ArticuloPedido): void {
    this.modalService
      .confirm(
        "Eliminar articulo",
        "¿Está seguro de borrar este articulo?",
        "El cambio no se confirmará hasta que no guarde el pedido."
      )
      .then(
        (_) => {
          let articulosPedido = this.articulosPedido;
          articulosPedido.splice(articulosPedido.indexOf(articuloPedido), 1);
        },
        (_) => {}
      );
  } 
}
