package com.web.pharma.utils;

import org.springframework.web.multipart.MultipartFile;

public class CommonUtils {

    public static String[] getMedicineNames() {
        // Predefined valid medicines
        String[] medicineNames = {
                "Paracetamol", "Amoxicillin", "Ibuprofen", "Metformin", "Aspirin",
                "Cetirizine", "Omeprazole", "Ciprofloxacin", "Atorvastatin", "Lisinopril",
                "Levothyroxine", "Azithromycin", "Clindamycin", "Simvastatin", "Losartan",
                "Prednisone", "Furosemide", "Hydrochlorothiazide", "Gabapentin", "Sertraline",
                "Amlodipine", "Montelukast", "Fluoxetine", "Warfarin", "Alprazolam",
                "Tramadol", "Pantoprazole", "Ranitidine", "Tamsulosin", "Doxycycline",
                "Levocetirizine", "Salbutamol", "Diazepam", "Metoprolol", "Clopidogrel",
                "Insulin", "Allopurinol"
        };
        return medicineNames;
    }
}
