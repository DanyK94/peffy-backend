package dg.peffy_backend.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dg.peffy_backend.budget.dto.BudgetSummaryResponse;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByAccountId(Integer accountId);

    List<Transaction> findByDateBetween(LocalDate start, LocalDate end);


    @Query("""
            SELECT new dg.peffy_backend.budget.dto.BudgetSummaryResponse(
            c.categoryId, c.categoryName, b.amount, SUM(t.amount))
            FROM Transaction t 
            JOIN Category c ON t.categoryId = c.id
            JOIN Budget b ON b.categoryId = c.id
            WHERE b.userId = :userId
            AND t.transactionDate >= :startDate
            AND t.transactionDate <= :enbDate
            GROUP BY c.categoryId
            """)
    List<BudgetSummaryResponse> queryBudgetSummary(@Param("userId") Integer userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate enDate );

    @Query("""
            SELECT t.amount FROM Transaction t 
            WHERE t.accountId IN :accountsId
            AND t.transactionDate >= :startDate
            AND t.transactionDate <= :endDate
            """)
    public List<BigDecimal> queryTAmountsForDates(@Param("userId") List<Integer> accountsId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query("""
        SELECT SUM(t.amount) FROM Transaction t WHERE t.accountId = :accountId 
        """)
    public BigDecimal findSumTransactions(@Param("accountId") Integer accountId);


    


}
