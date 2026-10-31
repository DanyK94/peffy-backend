package dg.peffy_backend.transaction;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dg.peffy_backend.transaction.dto.TransactionAccountTotal;
import dg.peffy_backend.transaction.dto.TransactionCategoryTotals;
import dg.peffy_backend.transaction.dto.TransactionData;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByAccountId(Integer accountId);
   

    //GET A CALCULATION OF ALL INCOME AND EXPENSES OF ACCOUNTS INDICATED (ALL TIME LONG)
    @Query ("""
        SELECT new dg.peffy_backend.transaction.dto.TransactionAccountTotal (
        SUM(CASE WHEN t.amount > 0 THEN t.amount ELSE 0 END),
        SUM(CASE WHEN t.amount < 0 THEN ABS(t.amount) ELSE 0 END)
        )
        FROM Transaction t 
        WHERE t.accountId IN :accountsId
        """)
    public TransactionAccountTotal getAccountTotals(@Param("accountsId") List<Integer> accountsId);

    //GET A CALCULATION OF ALL INCOME AND EXPENSES OF ACCOUNTS INDICATED (BETWEEN DATES)
    @Query ("""
        SELECT new dg.peffy_backend.transaction.dto.TransactionAccountTotal (
        SUM(CASE WHEN t.amount > 0 THEN t.amount ELSE 0 END),
        SUM(CASE WHEN t.amount < 0 THEN ABS(t.amount) ELSE 0 END)
        )
        FROM Transaction t 
        WHERE t.accountId IN :accountsId
        AND t.transactionDate >= :startDate
        AND t.transactionDate < :endDate
        """)
    public TransactionAccountTotal getAccountTotals(@Param("accountsId") List<Integer> accountsId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    //GET LIST OF A CALCULATION FOR CATEGORY'S TRANSACTION FOR ACCOUNTS 
    @Query ("""
        SELECT new dg.peffy_backend.transaction.dto.TransactionCategoryTotals (
        t.categoryId,
        SUM(CASE WHEN t.amount < 0 THEN ABS(t.amount) ELSE 0 END)
        )
        FROM Transaction t 
        WHERE t.accountId IN :accountsId
        AND t.transactionDate >= :startDate
        AND t.transactionDate < :endDate
        GROUP BY t.categoryId
        """)
    public List<TransactionCategoryTotals> getCategoryTotals(@Param("accountsId") List<Integer> accountsId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    //GET ALL TRANSACTION COMPLETE DATA
    
    @Query ("""
        SELECT new dg.peffy_backend.transaction.dto.TransactionData (
        t.id, t.amount, t.categoryId, c.categoryName, t.accountId, t.transDescription, t.transactionDate, t.notes
        )
        FROM Transaction t 
        JOIN Category c ON t.categoryId = c.id
        WHERE t.accountId IN :accountsId
        AND t.transactionDate >= :startDate
        AND t.transactionDate < :endDate
        """)
    public List<TransactionData> getTransactionsData(@Param("accountsId") List<Integer> accountsId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    


}
