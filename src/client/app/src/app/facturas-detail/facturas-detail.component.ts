import { Component, OnInit } from '@angular/core';

import { ActivatedRoute, Router } from "@angular/router";
import { Location } from "@angular/common";
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
import { Factura } from '../models/factura';

import { PedidoService } from "../services/pedido.service";
import { ClienteService } from "../services/cliente.service";
import { ArticuloService } from "../services/articulo.service";
import { ArticuloPedidoService } from "../services/articulo-pedido.service";
import { FacturaService } from "../services/factura.service";

export interface TotalFacturado{
  "totalFacturado": number;
}

@Component({
  selector: 'app-facturas-detail',
  templateUrl: './facturas-detail.component.html',
  styleUrls: ['./facturas-detail.component.css']
})
export class FacturasDetailComponent implements OnInit {

  view: boolean = false;
  createSuccesful: boolean = false;
  factura: Factura;
  totalFacturado: TotalFacturado = {"totalFacturado": 0};
  domiciliosEntrega: DomicilioEntrega[];
  articulosPedido: ArticuloPedido[] = [];
  fechaEmisionString: string = "";
  fechaPagoString: string = "";
  fechaEmisionDate: NgbDateStruct;
  fechaPagoDate: NgbDateStruct;

  facturaAbonada: Factura;
  
  alertMessage = "";
  showMessage: boolean = false;

  searching: boolean = false;
  searchFailed: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pedidoService: PedidoService,
    private clienteService: ClienteService,
    private articuloService: ArticuloService,
    private articuloPedidoService: ArticuloPedidoService,
    private facturaService: FacturaService,
    private location: Location,
    private calendar: NgbCalendar,
    private modalService: ModalService
  ) {}

  ngOnInit() {
    this.get();
  }

  get(): void {
      const numeroFactura = this.route.snapshot.paramMap.get('id');

      this.facturaService.findByNumeroFactura(numeroFactura).subscribe((dataPackage) => {
        this.factura = <Factura>dataPackage.data;
        this.factura.fechaEmision = new Date(this.factura.fechaEmision);
        if(this.factura.fechaPago != null){
          this.factura.fechaPago = new Date(this.factura.fechaPago);
          this.fechaPagoDate = NgbDate.from({
            day: this.factura.fechaPago.getUTCDate(),
            month: this.factura.fechaPago.getUTCMonth(),
            year: this.factura.fechaPago.getUTCFullYear(),
          });
          this.fechaPagoString = this.fechaPagoDate.year+"-"+
            this.fechaPagoDate.month+"-"+
            this.fechaPagoDate.day;
        }
        else this.fechaPagoString = null;

        this.fechaEmisionDate = NgbDate.from({
          day: this.factura.fechaEmision.getUTCDate(),
          month: this.factura.fechaEmision.getUTCMonth()+1,
          year: this.factura.fechaEmision.getUTCFullYear(),
        });
        this.fechaEmisionString = this.fechaEmisionDate.year+"-"+
          this.fechaEmisionDate.month+"-"+
          this.fechaEmisionDate.day;
      });

      this.facturaService.getTotalFacturado(numeroFactura).subscribe((dataPackage) => {
        this.totalFacturado = <TotalFacturado>(<DataPackage>dataPackage).data;
      });
      this.articuloPedidoService.getByNumeroFactura(numeroFactura).subscribe((dataPackage) => {
        this.articulosPedido = <ArticuloPedido[]>dataPackage.data;
      });
    }

  abonar(factura: Factura): void{
    this.facturaService.abonar(factura).subscribe((dataPackage) => {
      this.facturaAbonada = <Factura>dataPackage.data;
      this.alertMessage = dataPackage.StatusCode == 200 ? "Factura "+this.facturaAbonada.factura+" abonada correctamente" :
        "No se pudo entregar el remito";
      this.showMessage = true;
      this.get();
    });
  }

  goBack(): void {
    this.location.back();
  }

  closeAlert(): void{
    this.showMessage = false;
  }
}
