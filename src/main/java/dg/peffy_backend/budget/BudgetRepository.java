package dg.peffy_backend.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import dg.peffy_backend.budget.dto.BudgetResponse;

import java.time.LocalDate;
import java.util.List;


public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    public List<Budget> findAllByUserId(Integer userId);

    @Query ("""
            SELECT new dg.peffy_backend.budget.dto.BudgetResponse(
                b.userId, b.categoryId, b.amount, b.budgetMonth
            )
            FROM Budget b
            WHERE b.userId = :userId
            AND b.budgetMonth >= :startDate
            AND b.budgetMonth < :endDate
            """)
    public List<BudgetResponse> getUserBudgetsByMonth(Integer userId, LocalDate startDate, LocalDate endDate);
    
}
