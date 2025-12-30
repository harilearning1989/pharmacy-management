import {Component, OnInit} from '@angular/core';

type DiscountType = 'percent' | 'amount';

interface Patient {
  name: string;
  contact: string;
  address: string;
}

interface Item {
  name: string;
  batchNo: string;
  expiryDate: string;
  quantity: number;
  price: number;
  discount: number;
  discountType: DiscountType;
  tax: number;
  total: number;
}

interface Summary {
  subtotal: number;
  discount: number;
  tax: number;
  grandTotal: number;
}

interface Payment {
  method: string;
  amountPaid: number;
  balanceDue: number;
}

interface Invoice {
  invoiceNo: string;
  invoiceDate: string;
  dueDate: string;
  patient: Patient;
  items: Item[];
  summary: Summary;
  payment: Payment;
  notes: string;
}

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html',
  styleUrls: ['./invoice.component.scss']
})
export class InvoiceComponent implements OnInit {

  constructor() {
  }

  ngOnInit(): void {
  }

  items = [
    {
      name: 'Amoxicillin 500mg',
      batch: 'AMX123',
      expiry: '08/25/2024',
      qty: 2,
      price: 12.00,
      discount: 5,
      tax: 1.14,
      total: 22.74
    },
    {
      name: 'Lisinopril 10mg',
      batch: 'LIS567',
      expiry: '11/10/2023',
      qty: 1,
      price: 15.00,
      discount: 0,
      tax: 1.35,
      total: 16.35
    },
    {
      name: 'Omeprazole 20mg',
      batch: 'OME789',
      expiry: '05/30/2024',
      qty: 3,
      price: 8.00,
      discount: 10,
      tax: 1.44,
      total: 21.04
    },
    {
      name: 'Paracetamol 500mg',
      batch: 'PAR456',
      expiry: '07/12/2023',
      qty: 2,
      price: 5.00,
      discount: 0,
      tax: 0.40,
      total: 10.40
    }
  ];

  // Totals for the right-side calculation box
  totals = {
    subtotal: 70.00,
    discount: 2.00,
    tax: 4.33,
    grandTotal: 72.33
  };

}
