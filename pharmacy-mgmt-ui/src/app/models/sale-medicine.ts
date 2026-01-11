import { Medicine } from "./medicine";

export interface SaleMedicine {
    quantity: number;
    price: number;
    total: number;
    medicineResponseDto: Medicine;
}
