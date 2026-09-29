package dg.peffy_backend.transaction;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByAccountId(Integer accountId);

    List<Transaction> findByDateBetween(LocalDate start, LocalDate end);

}
