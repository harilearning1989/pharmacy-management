import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-confirm-dialog',
  templateUrl: './confirm-dialog.component.html',
  styleUrls: ['./confirm-dialog.component.scss']
})
export class ConfirmDialogComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  @Input() modalId: string = 'confirmModal';
  @Input() title: string = 'Confirm';
  @Input() message: string = '';
  @Input() confirmText: string = 'Confirm';
  @Input() cancelText: string = 'Cancel';

  @Output() onConfirm = new EventEmitter<void>();

  confirm() {
    this.onConfirm.emit();
  }

}
