export interface SupplierDto {
    id: number;
    supplierCode: string;
    supplierName: string;
    contactPerson?: string;
    phone: string;
    email: string;
    gstNumber?: string;
    drugLicenseNumber?: string;
    bankName?: string;
    bankAccountNumber?: string;
    ifscCode?: string;
    status: string;
    createdAt: string; // ISO 8601 from backend
    updatedAt: string;
}
