import { Customer } from "./customer";
import { SaleMedicine } from "./sale-medicine";
import { UserDetail } from "./user-detail";

export interface SaleHistoryDetail {
    id: number;
    customer: Customer;
    soldBy: UserDetail;
    subtotal: number;
    discount: number;
    gst: number;
    grandTotal: number;
    paymentMethod: string;
    saleDate: string; // ISO date string
    saleMedicines: SaleMedicine[];
}
