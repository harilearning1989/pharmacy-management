package com.web.pharma.repos;

import com.web.pharma.dtos.SaleHistoryDto;
import com.web.pharma.models.Sale;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {


    // Date range
    @Query("""
                SELECT s FROM Sale s
                WHERE s.saleDate BETWEEN :start AND :end
            """)
    List<Sale> findSalesBetween(
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );


    // Customer based
    List<Sale> findByCustomerId(Long customerId);

   /* @Query("""
                SELECT COALESCE(SUM(s.totalAmount),0)
                FROM Sale s
                WHERE s.saleDate BETWEEN :start AND :end
            """)
    BigDecimal getTotalSales(LocalDateTime start, LocalDateTime end);*/

    @Query("SELECT COALESCE(SUM(s.grandTotal), 0) " +
            "FROM Sale s " +
            "WHERE s.saleDate BETWEEN :start AND :end")
    BigDecimal calculateTotalSales(@Param("start") LocalDateTime start,
                                   @Param("end") LocalDateTime end);

    @Query("SELECT new com.web.pharma.dtos.SaleHistoryDto(" +
            "s.id," +
            "s.subtotal," +
            "s.discount," +
            "s.gst," +
            "s.grandTotal," +
            "s.paymentMethod," +
            "s.saleDate," +
            "c.name," +
            "c.phone," +
            "u.username," +
            "u.phoneNumber) " +
            "FROM Sale s " +
            "JOIN s.customer c " +
            "JOIN s.soldBy u " +
            "ORDER BY s.saleDate DESC")
    List<SaleHistoryDto> findAllSalesWithCustomerAndUser();

    @EntityGraph(attributePaths = {
            "customer",
            "soldBy",
            "saleMedicines",
            "saleMedicines.medicine"
    })
    Optional<Sale> findWithDetailsById(Long id);

    List<Sale> findBySaleDateBetween(
            LocalDateTime start,
            LocalDateTime end
    );

}
