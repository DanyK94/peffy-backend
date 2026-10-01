package dg.peffy_backend.transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import dg.peffy_backend.account.AccountService;
import dg.peffy_backend.budget.dto.BudgetSummaryResponse;
import dg.peffy_backend.category.CategoryService;
import dg.peffy_backend.exception.ResourceNotFoundException;
import dg.peffy_backend.transaction.dto.CreateTransactionRequest;
import dg.peffy_backend.transaction.dto.TransactionResponse;

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


    public TransactionResponse getTransactionById(Integer id){
        return parseTransaction(transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction with ID: " + id + " not found.")));
    }

    public List<TransactionResponse> getAllTransactions(){
        List<Transaction> listTransactions = transactionRepository.findAll();
        
        if (listTransactions == null) {return Collections.emptyList();}

        return listTransactions.stream().map(this::parseTransaction).toList();
    }

    public List<TransactionResponse> getAllTransactionsByAccountId(Integer accountId){
        List<Transaction> listTransactions = transactionRepository.findByAccountId(accountId);
        
        if (listTransactions == null) {return Collections.emptyList();}

        return listTransactions.stream().map(this::parseTransaction).toList();
    }

    public TransactionResponse createTransaction(CreateTransactionRequest request){
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

        return parseTransaction(transactionRepository.save(transaction));

    }

    public List<BudgetSummaryResponse> getTotalForBudgets(Integer userId, LocalDate startDate, LocalDate endDate){
        return transactionRepository.queryBudgetSummary(userId, startDate, endDate);
    }

    public List<BigDecimal> getTAmountsForDates(List<Integer> accountsId, LocalDate startDate, LocalDate endDate){
        return transactionRepository.queryTAmountsForDates(accountsId, startDate, endDate);
    }

    public BigDecimal getSumTransactions(Integer accountId){
        return transactionRepository.findSumTransactions(accountId);  
    }

    public TransactionResponse parseTransaction(Transaction transaction){
        return new TransactionResponse(
        transaction.getId(),
        transaction.getAccountId(),
        transaction.getCategoryId(),
        transaction.getTransactionDate(),
        transaction.getAmount(),
        transaction.getTransDescription(),
        transaction.getNotes(),
        transaction.getCreatedAt()
        ); 
    }


    
}
