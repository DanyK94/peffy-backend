package dg.peffy_backend.transaction;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import dg.peffy_backend.transaction.dto.CreateTransactionRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;





@Controller 
@RequestMapping("api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@RequestAttribute  Integer id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/")
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @PostMapping("/")
    public Transaction createTransaction( @Valid @RequestBody CreateTransactionRequest request) {
        return transactionService.createTransaction(request);
    }
    
    
    
}
