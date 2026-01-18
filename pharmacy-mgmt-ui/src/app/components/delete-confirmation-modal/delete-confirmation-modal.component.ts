import { Component, Input, OnInit } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-delete-confirmation-modal',
  templateUrl: './delete-confirmation-modal.component.html',
  styleUrls: ['./delete-confirmation-modal.component.scss']
})
export class DeleteConfirmationModalComponent implements OnInit {

  @Input() title = 'Confirm Delete';
  @Input() message = 'Are you sure you want to delete this record?';

  constructor(public activeModal: NgbActiveModal) {}

  ngOnInit(): void {
  }

}
