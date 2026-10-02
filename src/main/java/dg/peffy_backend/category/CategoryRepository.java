package dg.peffy_backend.category;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dg.peffy_backend.category.dto.CategoryTotals;

public interface CategoryRepository extends JpaRepository<Category, Integer> {


    @Query ("""
        SELECT new dg.peffy_backend.category.dto.CategoryTotals(t.categoryId, c.categoryName, SUM(t.amount))
        FROM Transaction t  JOIN Category c ON t.categoryId = c.id
        WHERE t.accountId  IN :accountsId 
        AND t.transactionDate >= :startDate 
        AND t.transactionDate <= :endDate 
        GROUP BY t.categoryId, c.categoryName
        """) 
    List<CategoryTotals> getCategoryTotals(@Param("accountsId") List<Integer> accountsId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    
}
