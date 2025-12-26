export interface Medicine {
  id: number;
  name: string;
  brand: string;
  batchNumber: string;
  expiryDate: string; // ISO date string
  price: number;
  stock: number;
  prescriptionRequired: boolean;
  createdAt: string;
  updatedAt: string;
}

