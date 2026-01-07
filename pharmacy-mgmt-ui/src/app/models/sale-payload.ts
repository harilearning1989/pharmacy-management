export interface SalePayload {
  customerId: number;
  items: {
    medicineId: number;
    batchNumber: string;
    qty: number;
    price: number;
    total: number;
  }[];
  subtotal: number;
  discount: number;
  gst: number;
  grandTotal: number;
  paymentMethod: string;
}

