import {Component, OnInit} from '@angular/core';
import {Stock} from "../../models/stock";
import {StockFormComponent} from "../stock-form/stock-form.component";
import {StockService} from "../../services/stock.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'app-stock',
  templateUrl: './stock.component.html',
  styleUrls: ['./stock.component.scss']
})
export class StockComponent implements OnInit {

  stocks: Stock[] = [];

  constructor(private stockService: StockService, private modalService: NgbModal) {
  }

  ngOnInit(): void {
    this.loadStocks();
  }

  loadStocks() {
    this.stockService.getStocks().subscribe(data => this.stocks = data);
  }

  addStock() {
    const modalRef = this.modalService.open(StockFormComponent);
    modalRef.componentInstance.mode = 'Add';
    modalRef.result.then((result: any) => {
      if (result) this.loadStocks();
    });
  }

  editStock(stock: Stock) {
    const modalRef = this.modalService.open(StockFormComponent);
    modalRef.componentInstance.mode = 'Edit';
    modalRef.componentInstance.stock = {...stock};
    modalRef.result.then((result: any) => {
      if (result) this.loadStocks();
    });
  }

  deleteStock(id: number) {
    if (confirm('Are you sure you want to delete this stock?')) {
      this.stockService.deleteStock(id).subscribe(() => this.loadStocks());
    }
  }

}
