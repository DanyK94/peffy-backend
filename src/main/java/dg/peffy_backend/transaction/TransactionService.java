package dg.peffy_backend.transaction;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import dg.peffy_backend.account.AccountService;
import dg.peffy_backend.category.CategoryService;
import dg.peffy_backend.exception.ResourceNotFoundException;
import dg.peffy_backend.transaction.dto.CreateTransactionRequest;

@Service 
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final CategoryService categoryService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService,
            CategoryService categoryService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.categoryService = categoryService;
    }


    public Transaction getTransactionById(Integer id){
        return transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction with ID: " + id + " not found."));
    }

    public List<Transaction> getAllTransactions(){
        return transactionRepository.findAll();
    }

    public Transaction createTransaction(CreateTransactionRequest request){
        Transaction transaction = new Transaction();
        //Check AccountID
        accountService.getAccountById(request.getAccountId());
        transaction.setAccountId(request.getAccountId());
        
        //Check CategoryID
        categoryService.getCategoryById(request.getCategoryId());
        transaction.setCategoryId(request.getCategoryId());

        //Populate
        transaction.setAmount(request.getAmount());
        transaction.setTransactionDate(request.getTransactionDate());
        transaction.setTransDescription(request.getTransDescription());
        transaction.setNotes(request.getNotes());

        transaction.setCreatedAt(Instant.now());

        return transactionRepository.save(transaction);

    }


    
}
