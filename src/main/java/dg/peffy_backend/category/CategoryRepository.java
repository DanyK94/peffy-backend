package dg.peffy_backend.category;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dg.peffy_backend.category.dto.CategorySummary;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query ("""
            SELECT new dg.peffy_backend.category.dto.CategorySummary(t.categoryId, SUM(t.amount))
            FROM Transaction t JOIN Account a ON a.id = t.accountId 
            WHERE a.userId = :userId 
            AND t.transactionDate >= :startDate 
            AND t.transactionDate <= :endDate 
            GROUP BY t.categoryId
            """)
    List<CategorySummary> findSumAmmountByCategoryForUser( @Param("userId") Integer userId, @Param("startDate") LocalDate starDate, @Param("endDate") LocalDate endDate );

    
}
