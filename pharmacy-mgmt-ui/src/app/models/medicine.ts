export const medicines = [
  {
    id: 1,
    name: 'Paracetamol',
    brand: 'Cipla',
    mg: '500mg',
    batchNumber: 'BATCH001',
    expiryDate: '2026-03-31',
    price: 2,
    stock: 12,
    qty: 1,
    prescriptionRequired: false,
    createdAt: '2024-01-10T10:00:00Z',
    updatedAt: '2024-06-01T12:00:00Z'
  },
  {
    id: 2,
    name: 'Amoxicillin',
    brand: 'Sun Pharma',
    mg: '250mg',
    batchNumber: 'BATCH002',
    expiryDate: '2025-11-15',
    price: 5,
    stock: 60,
    qty: 1,
    prescriptionRequired: true,
    createdAt: '2024-02-05T09:30:00Z',
    updatedAt: '2024-06-10T14:20:00Z'
  },
  {
    id: 3,
    name: 'Cetirizine',
    brand: 'Dr Reddy’s',
    mg: '10mg',
    batchNumber: 'BATCH003',
    expiryDate: '2026-01-10',
    price: 4,
    stock: 200,
    qty: 2,
    prescriptionRequired: false,
    createdAt: '2024-01-20T11:15:00Z',
    updatedAt: '2024-05-18T16:45:00Z'
  },
  {
    id: 4,
    name: 'Metformin',
    brand: 'Lupin',
    mg: '500mg',
    batchNumber: 'BATCH004',
    expiryDate: '2027-05-10',
    price: 10,
    stock: 85,
    qty: 1,
    prescriptionRequired: true,
    createdAt: '2024-03-01T08:45:00Z',
    updatedAt: '2024-06-12T10:10:00Z'
  },
  {
    id: 5,
    name: 'Ibuprofen',
    brand: 'Abbott',
    mg: '400mg',
    batchNumber: 'BATCH005',
    expiryDate: '2026-09-30',
    price: 11,
    stock: 150,
    qty: 1,
    prescriptionRequired: false,
    createdAt: '2024-02-18T13:00:00Z',
    updatedAt: '2024-06-08T09:00:00Z'
  },
  {
    id: 6,
    name: 'Azithromycin',
    brand: 'Pfizer',
    mg: '500mg',
    batchNumber: 'BATCH006',
    expiryDate: '2025-12-05',
    price: 5,
    stock: 40,
    qty: 1,
    prescriptionRequired: true,
    createdAt: '2024-01-28T10:20:00Z',
    updatedAt: '2024-06-15T15:30:00Z'
  },
  {
    id: 7,
    name: 'Pantoprazole',
    brand: 'Alkem',
    mg: '40mg',
    batchNumber: 'BATCH007',
    expiryDate: '2027-02-14',
    price: 7,
    stock: 95,
    qty: 1,
    prescriptionRequired: false,
    createdAt: '2024-03-10T09:00:00Z',
    updatedAt: '2024-06-11T11:25:00Z'
  },
  {
    id: 8,
    name: 'Atorvastatin',
    brand: 'Torrent',
    mg: '20mg',
    batchNumber: 'BATCH008',
    expiryDate: '2026-07-01',
    price: 13,
    stock: 55,
    qty: 1,
    prescriptionRequired: true,
    createdAt: '2024-02-25T14:10:00Z',
    updatedAt: '2024-06-14T17:40:00Z'
  },
  {
    id: 9,
    name: 'Vitamin C',
    brand: 'Himalaya',
    mg: '500mg',
    batchNumber: 'BATCH009',
    expiryDate: '2026-10-18',
    price: 5,
    stock: 300,
    qty: 1,
    prescriptionRequired: false,
    createdAt: '2024-01-05T08:00:00Z',
    updatedAt: '2024-05-30T10:00:00Z'
  },
  {
    id: 10,
    name: 'Insulin',
    brand: 'Novo Nordisk',
    mg: '100 IU/ml',
    batchNumber: 'BATCH010',
    expiryDate: '2025-08-25',
    price: 3,
    stock: 25,
    qty: 1,
    prescriptionRequired: true,
    createdAt: '2024-03-18T12:40:00Z',
    updatedAt: '2024-06-16T18:00:00Z'
  }
];

export interface Medicine {
  id: number;
  name: string;
  mg?: string;
  brand: string;
  batchNumber: string;
  expiryDate: string; // ISO date string
  price: number;
  stock: number;
  qty: number;   // 👈 optional
  totalPrice?: number;
  prescriptionRequired: boolean;
  createdAt: string;
  updatedAt: string;
}

