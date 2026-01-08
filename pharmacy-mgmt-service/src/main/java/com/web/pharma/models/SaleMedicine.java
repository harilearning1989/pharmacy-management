package com.web.pharma.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "sale_medicines",
        indexes = {
                @Index(name = "idx_sale_medicines_sale", columnList = "sale_id"),
                @Index(name = "idx_sale_medicines_medicine", columnList = "medicine_id"),
                @Index(name = "idx_sale_medicines_batch", columnList = "batch_number")
        }
)
public class SaleMedicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Column(name = "batch_number", nullable = false, length = 50)
    private String batchNumber;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    // 🔹 Auto-calculate total before save/update
    @PrePersist
    @PreUpdate
    private void calculateTotal() {
        if (price != null && quantity != null) {
            this.total = price.multiply(BigDecimal.valueOf(quantity));
        }
    }

}


