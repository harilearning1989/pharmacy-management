import {Component, Input, OnInit} from '@angular/core';
import {Stock} from "../../models/stock";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {StockService} from "../../services/stock.service";

@Component({
  selector: 'app-stock-form',
  templateUrl: './stock-form.component.html',
  styleUrls: ['./stock-form.component.scss']
})
export class StockFormComponent implements OnInit {

  ngOnInit(): void {
  }

  @Input() stock: Stock = {id: 1,  medicineName: '', quantity: 0, price: 0, expiryDate: '' };
  @Input() mode: 'Add' | 'Edit' = 'Add';

  constructor(public activeModal: NgbActiveModal, private stockService: StockService) {}

  save() {
    if(this.mode === 'Add'){
      this.stockService.addStock(this.stock).subscribe(() => this.activeModal.close(true));
    } else {
      this.stockService.updateStock(this.stock).subscribe(() => this.activeModal.close(true));
    }
  }

}
