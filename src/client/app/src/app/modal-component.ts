import { Component } from "@angular/core";
import { NgbActiveModal } from "@ng-bootstrap/ng-bootstrap";

@Component({
selector: "ngbd-modal-confirm",
template: `
<div class="modal-content">
    <div class="modal-header">
        <h4 class="modal-title" id="modal-title">{{title}}</h4>
        <button type="button" class="btn-close" aria-describedby="modal-title" (click)="modal.dismiss()">
        </button>
    </div>
    <div class="modal-body">
        <p>
            <strong>{{message}}</strong>
        </p>
        <p *ngIf="description">
            {{description}}
        </p>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-outline-primary" (click)="modal.dismiss()">
            Cancelar
        </button>
        <button type="button" class="btn btn-primary" (click)="modal.close()">
            Aceptar
        </button>
    </div>
</div>
`,
})
export class NgbdModalConfirm {
    constructor(public modal: NgbActiveModal) {}
}