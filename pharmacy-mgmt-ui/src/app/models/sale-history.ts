export interface SaleHistory {
    saleId: number;    
    subtotal: number;          // BigDecimal -> number        // Long -> number
    discount: number;          // BigDecimal -> number
    gst: number;               // BigDecimal -> number
    grandTotal: number;        // BigDecimal -> number
    paymentMethod: string;
    saleDate: string;          // LocalDate -> ISO date string (YYYY-MM-DD)
    customerName: string;
    customerPhone: string;
    username: string;
    userPhone: string;
}
