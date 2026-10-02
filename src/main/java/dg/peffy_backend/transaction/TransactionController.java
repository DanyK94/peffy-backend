package dg.peffy_backend.transaction;


import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import dg.peffy_backend.transaction.dto.CreateTransactionRequest;
import dg.peffy_backend.transaction.dto.TransactionResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;







@RestController 
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public TransactionResponse getTransactionById(@PathVariable Integer id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping
    public List<TransactionResponse> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @PostMapping
    public TransactionResponse createTransaction( @Valid @RequestBody CreateTransactionRequest request) {
        return transactionService.createTransaction(request);
    }

    
    
    
    
}
