package dg.peffy_backend.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dg.peffy_backend.budget.dto.BudgetSummaryResponse;
import dg.peffy_backend.transaction.dto.TransactionTotals;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByAccountId(Integer accountId);


    @Query("""
        SELECT new dg.peffy_backend.budget.dto.BudgetSummaryResponse(
        c.id, c.categoryName, b.amount, SUM(t.amount))
        FROM Budget b 
        JOIN Category c ON b.categoryId = c.id           
        JOIN Transaction t ON t.categoryId = c.id
        WHERE b.userId = :userId
        AND t.transactionDate >= :startDate
        AND t.transactionDate <= :endDate
        GROUP BY c.id, c.categoryName, b.amount
        """)
    List<BudgetSummaryResponse> queryBudgetSummary(@Param("userId") Integer userId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate enDate );

    @Query("""
            SELECT t.amount FROM Transaction t 
            WHERE t.accountId IN :accountsId
            AND t.transactionDate >= :startDate
            AND t.transactionDate <= :endDate
            """)
    public List<BigDecimal> queryTAmountsForDates(@Param("accountsId") List<Integer> accountsId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query("""
        SELECT SUM(t.amount) FROM Transaction t WHERE t.accountId = :accountId 
        """)
    public BigDecimal findSumTransactions(@Param("accountId") Integer accountId);

    
    @Query ("""
        SELECT new dg.peffy_backend.transaction.dto.TransactionTotals (
        SUM(CASE WHEN t.amount > 0 THEN t.amount ELSE 0 END),
        SUM(CASE WHEN t.amount < 0 THEN ABS(t.amount) ELSE 0 END)
        )
        FROM Transaction t 
        WHERE t.accountId = :accountId
        """)
    public TransactionTotals getAccountTotals(@Param("accountId") Integer accountId);

    @Query ("""
        SELECT new dg.peffy_backend.transaction.dto.TransactionTotals (
        SUM(CASE WHEN t.amount > 0 THEN t.amount ELSE 0 END),
        SUM(CASE WHEN t.amount < 0 THEN ABS(t.amount) ELSE 0 END)
        )
        FROM Transaction t 
        WHERE t.accountId IN :accountsId
        """)
    public TransactionTotals getAccountTotals(@Param("accountsId") List<Integer> accountsId);

        @Query ("""
        SELECT new dg.peffy_backend.transaction.dto.TransactionTotals (
        SUM(CASE WHEN t.amount > 0 THEN t.amount ELSE 0 END),
        SUM(CASE WHEN t.amount < 0 THEN ABS(t.amount) ELSE 0 END)
        )
        FROM Transaction t 
        WHERE t.accountId = :accountId
        AND t.transactionDate >= :startDate
        AND t.transactionDate <= :endDate
        """)
    public TransactionTotals getAccountTotals(@Param("accountId") Integer accountId, @Param("starDate") LocalDate starDate, @Param("endDate") LocalDate endDate);

    @Query ("""
        SELECT new dg.peffy_backend.transaction.dto.TransactionTotals (
        SUM(CASE WHEN t.amount > 0 THEN t.amount ELSE 0 END),
        SUM(CASE WHEN t.amount < 0 THEN ABS(t.amount) ELSE 0 END)
        )
        FROM Transaction t 
        WHERE t.accountId IN :accountsId
        AND t.transactionDate >= :startDate
        AND t.transactionDate <= :endDate
        """)
    public TransactionTotals getAccountTotals(@Param("accountsId") List<Integer> accountsId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query ("""
        SELECT new dg.peffy_backend.transaction.dto.TransactionTotals (
        t.categoryId,
        SUM(CASE WHEN t.amount < 0 THEN ABS(t.amount) ELSE 0 END)
        )
        FROM Transaction t 
        WHERE t.accountId IN :accountsId
        AND t.transactionDate >= :startDate
        AND t.transactionDate <= :endDatecle
        GROUP BY t.categoryId
        """)
    public List<TransactionTotals> getCategoryTotals(@Param("accountsId") List<Integer> accountsId, @Param("starDate") LocalDate starDate, @Param("endDate") LocalDate endDate);


    


}
