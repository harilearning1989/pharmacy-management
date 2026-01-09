package com.web.pharma.repos;

import com.web.pharma.models.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    List<Medicine> findByNameContainingIgnoreCase(String name);

    @Query("""
        SELECT m FROM Medicine m
        WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
           OR LOWER(m.batchNumber) LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Medicine> searchByNameOrBatch(@Param("keyword") String keyword);
}

