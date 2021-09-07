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
import { Remito } from '../models/remito';

import { PedidoService } from "../services/pedido.service";
import { ClienteService } from "../services/cliente.service";
import { RemitoService } from "../services/remito.service";
import { ArticuloPedidoService } from "../services/articulo-pedido.service";

@Component({
  selector: 'app-remitos-detail',
  templateUrl: './remitos-detail.component.html',
  styleUrls: ['./remitos-detail.component.css']
})
export class RemitosDetailComponent implements OnInit {
  alertMessage = "";
  showMessage: boolean = false;

  view: boolean = false;

  remito: Remito;
  domiciliosEntrega: DomicilioEntrega[];
  articulosPedido: ArticuloPedido[] = [];
  remitoEntregado: Remito = null;

  pedidoParaEnviar: PedidoParaEnviar;
  fechaArmadoDate: NgbDateStruct;
  fechaArmadoString: string = "";

  searching: boolean = false;
  searchFailed: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private pedidoService: PedidoService,
    private clienteService: ClienteService,
    private remitoService: RemitoService,
    private articuloPedidoService: ArticuloPedidoService,
    private location: Location,
    private calendar: NgbCalendar,
    private modalService: ModalService
  ) {}

  ngOnInit() {
    this.get();
  }

  goBack(): void {
    this.location.back();
  }

  closeAlert(): void{
    this.showMessage = false;
  }

  get(): void {
    const numeroRemito = this.route.snapshot.paramMap.get('numeroRemito');
    this.remitoService.getByNumeroRemito(numeroRemito).subscribe((dataPackage) => {
      this.remito = <Remito>dataPackage.data;
      this.remito.fechaArmado = new Date(this.remito.fechaArmado);
      this.fechaArmadoDate = NgbDate.from({
        day: this.remito.fechaArmado.getDate()+1,
        month: this.remito.fechaArmado.getMonth()+1,
        year: this.remito.fechaArmado.getUTCFullYear(),
      });
      this.fechaArmadoString = this.fechaArmadoDate.year+"-"+
          this.fechaArmadoDate.month+"-"+
          this.fechaArmadoDate.day;
    });
    this.articuloPedidoService.getByNumeroRemito(numeroRemito).subscribe((dataPackage) => {
      this.articulosPedido = <ArticuloPedido[]>dataPackage.data;
    });
  }

    entregar(remito: Remito): void{
    this.remitoService.entregar(remito).subscribe((dataPackage) => {
      this.remitoEntregado = <Remito>dataPackage.data;
      this.alertMessage = dataPackage.StatusCode == 200 ? "Remito "+this.remitoEntregado.remito+" entregado correctamente" :
        "No se pudo entregar el remito";
      this.showMessage = true;
      this.get();
    });
  }
}
