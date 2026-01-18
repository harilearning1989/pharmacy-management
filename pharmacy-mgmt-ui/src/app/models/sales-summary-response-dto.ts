import { SaleHistory } from "./sale-history";

export interface SalesSummaryResponseDto {
    totalSales: number;
    totalAmount: number;
    totalDiscount: number;
    totalGst: number;
    sales: SaleHistory[];
}
