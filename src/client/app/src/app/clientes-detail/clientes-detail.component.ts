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

import { Cliente } from "../models/cliente";
import { DomicilioEntrega } from "../models/domicilioEntrega";

import { ClienteService } from "../services/cliente.service";
import { LocalidadService } from "../services/localidad.service";
import { DataPackage } from '../data-package';

@Component({
  selector: 'app-clientes-detail',
  templateUrl: './clientes-detail.component.html',
  styleUrls: ['./clientes-detail.component.css']
})

export class ClientesDetailComponent implements OnInit {
  alertMessage = "";
  showMessage: boolean = false;

  view: boolean = false;
  edit: boolean = false;

  cliente: Cliente;

  searching: boolean = false;
  searchFailed: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private clienteService: ClienteService,
    private localidadService: LocalidadService,
    private location: Location,
    private modalService: ModalService
  ) {}

  ngOnInit() {
    this.get();
  }

  closeAlert(): void{
    this.showMessage = false;
  }

  get(): void {
    const action = this.route.snapshot.paramMap.get('action');
    if(action === "new") {
      this.view = false;
      this.cliente = {
        id: null,
        cliente: null,
        cuit: null,
        tipoDoc: "DNI",
        nroDoc: null,
        razonSocial: null,
        domiciliosEntrega: []
      };
    }
    else {
      if(action === "view"){
        this.view = true;
      }
      else if(action === "edit") this.edit = true;

      const numeroCliente = this.route.snapshot.paramMap.get('id');
      this.clienteService.getByNumeroCliente(numeroCliente).subscribe((dataPackage) => {
        this.cliente = <Cliente>dataPackage.data;
        this.cliente.tipoDoc = "DNI"
      });
    } 
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.clienteService.save(this.cliente).subscribe((dataPackage) => {
      this.cliente = <Cliente>dataPackage.data;
      this.view = true;
      this.alertMessage = dataPackage.StatusText;
      this.showMessage = true;
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

  searchLocalidad = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      tap(() => (this.searching = true)),
      switchMap(term => term.length < 1 ? [] :
        this.localidadService
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

  resultFormatLocalidad(value: any) {
    return value.cp +" - "+ value.nombre;
  }

  inputFormatLocalidad(value: any) {
    return value.cp +" - "+ value.nombre;
  }

  addDomicilioEntrega(): void {
    this.cliente.domiciliosEntrega.push({ id: null, calle: null, altura: null, pisoDpto: null, localidad: null});
  }

  removeDomicilioEntrega(domicilioEntrega: DomicilioEntrega): void {
    this.modalService
      .confirm(
        "Eliminar domicilio de entrega",
        "¿Está seguro de borrar este domicilio?",
        "El cambio no se confirmará hasta que no guarde el cliente."
      )
      .then(
        (_) => {
          let domiliciosEntrega = this.cliente.domiciliosEntrega;
          domiliciosEntrega.splice(domiliciosEntrega.indexOf(domicilioEntrega), 1);
        },
        (_) => {}
      );
  } 
}