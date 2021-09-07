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

import { Articulo } from "../models/articulo";

import { ArticuloService } from "../services/articulo.service";
import { LocalidadService } from "../services/localidad.service";

@Component({
  selector: 'app-articulos-detail',
  templateUrl: './articulos-detail.component.html',
  styleUrls: ['./articulos-detail.component.css']
})

export class ArticulosDetailComponent implements OnInit {
  alertMessage = "";
  showMessage: boolean = false;
  view: boolean = false;
  edit: boolean = false;

  articulo: Articulo;

  searching: boolean = false;
  searchFailed: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private articuloService: ArticuloService,
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

  goBack(): void {
    this.location.back();
  }

  get(): void {
    const action = this.route.snapshot.paramMap.get('action');
    if(action === "new") {
      this.view = false;
      this.articulo = {
        id: null,
        nombre: null,
        descripcion: null,
        precioUnitario: null,
        stock: null
      };
    }
    else {
      if(action === "view"){
        this.view = true;
      }
      else if(action === "edit") this.edit = true;

      const idArticulo = this.route.snapshot.paramMap.get('id');
      this.articuloService.getById(idArticulo).subscribe((dataPackage) => {
        this.articulo = <Articulo>dataPackage.data;
      });
    } 
  }

  save(): void {
    this.articuloService.save(this.articulo).subscribe((dataPackage) => {
      this.articulo = <Articulo>dataPackage.data;
      this.view = true;
      this.alertMessage = dataPackage.StatusText;
      this.showMessage = true;
    });
  }
}