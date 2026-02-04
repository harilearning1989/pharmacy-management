import { Component, Input, OnInit, SimpleChanges } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { SaleHistoryDetail } from 'src/app/models/sale-history-detail';
import { SaleMedicine } from 'src/app/models/sale-medicine';
import { SaleService } from 'src/app/services/sale.service';

@Component({
  selector: 'app-view-sold-medicine-by-customer',
  templateUrl: './view-sold-medicine-by-customer.component.html',
  styleUrls: ['./view-sold-medicine-by-customer.component.scss']
})
export class ViewSoldMedicineByCustomerComponent implements OnInit {

  @Input() saleId!: number;
  saleHistoryDetail: any;
  searchText: string = '';
  sortDirection: 'asc' | 'desc' = 'asc';
  sortColumn: string = '';
  alertMessage: string = '';
  alertType: string = '';
  selectedSaleMedicineIds: number[] = [];

  constructor(private saleService: SaleService,
    public activeModal: NgbActiveModal
  ) { }

  ngOnInit(): void {
    this.loadSaleDetailsBySaleId();
  }

  cancel(): void {
    this.activeModal.dismiss();
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

  deleteMedicine(saleMedicine: SaleMedicine): void {
    console.log('saleMedicine::', saleMedicine);
  }

  returnMedicines() {
    if (this.selectedSaleMedicineIds.length === 0) {
      alert('Please select at least one medicine to return');
      return;
    }

    this.saleService
      .returnSaleMedicines(this.saleId, this.selectedSaleMedicineIds)
      .subscribe({
        next: (response: SaleHistoryDetail) => {
          this.saleHistoryDetail = response || [];
          if (this.saleHistoryDetail.id == null) {
            this.activeModal.close('REFRESH');
          }
        },
        error: () => {
          this.saleHistoryDetail = {};
        }
      });
  }

  onCheckboxChange(event: any, id: number) {
    if (event.target.checked) {
      this.selectedSaleMedicineIds.push(id);
    } else {
      this.selectedSaleMedicineIds =
        this.selectedSaleMedicineIds.filter(x => x !== id);
    }
  }

}
