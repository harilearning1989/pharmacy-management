import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { SaleHistoryDetail } from 'src/app/models/sale-history-detail';
import { SaleService } from 'src/app/services/sale.service';

@Component({
  selector: 'app-sale-dialog-details',
  templateUrl: './sale-dialog-details.component.html',
  styleUrls: ['./sale-dialog-details.component.scss']
})
export class SaleDialogDetailsComponent implements OnInit {

  @Input() saleId!: number;
  saleHistoryDetail: any;

  constructor(private saleService: SaleService) { }

  ngOnInit(): void {
  }

  loadSaleDetailsBySaleId() {
    this.saleService.loadSaleDetailsBySaleId(this.saleId).subscribe({
      next: (response: SaleHistoryDetail) => {
        this.saleHistoryDetail = response || [];
      },
      error: () => {
        this.saleHistoryDetail = {};
      }
    });
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['saleId'] && this.saleId) {
      this.loadSaleDetailsBySaleId();
    }
  }

}
