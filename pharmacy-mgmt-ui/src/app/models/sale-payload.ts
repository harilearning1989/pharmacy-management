export interface SalePayload {
  customerId: number;
  items: {
    medicineId: number;
    batchNumber: string;
    quantity: number;
    price: number;
    total: number;
  }[];
  subTotal: number;
  discount: number;
  gst: number;
  grandTotal: number;
  paymentMethod: string;
}

