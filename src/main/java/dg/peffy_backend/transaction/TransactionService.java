package dg.peffy_backend.transaction;

import dg.peffy_backend.account.AccountRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import dg.peffy_backend.category.CategoryService;
import dg.peffy_backend.exception.ResourceNotFoundException;
import dg.peffy_backend.transaction.dto.CreateTransactionRequest;
import dg.peffy_backend.transaction.dto.TransactionAccountTotal;
import dg.peffy_backend.transaction.dto.TransactionResponse;
import dg.peffy_backend.transaction.dto.TransactionCategoryTotals;

@Service 
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final CategoryService categoryService;

    public TransactionService(TransactionRepository transactionRepository,
            CategoryService categoryService, AccountRepository accountRepository) {

        this.transactionRepository = transactionRepository;
        this.categoryService = categoryService;
        this.accountRepository = accountRepository;
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
        if(!accountRepository.existsById(request.getAccountId())) {
            throw new ResourceNotFoundException("Account with ID: " + request.getAccountId() + " not found");
        }
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


    // **********
    // GET ACCOUNT TRANSACTION TOTALS (BETWEEN DATES)
    public TransactionAccountTotal getAccountTotals(List<Integer> accountsId, LocalDate startDate, LocalDate endDate){
        return transactionRepository.getAccountTotals(accountsId, startDate, endDate);
    }
    
    public TransactionAccountTotal getAccountTotals(List<Integer> accountsId){
        return transactionRepository.getAccountTotals(accountsId);
    }

    // *******
    // GET LIST OF A CALCULATION FOR CATEGORY'S TRANSACTION FOR ACCOUNTS'S USER
    public List<TransactionCategoryTotals> getCategoryTotals(List<Integer> accountsId, LocalDate startDate, LocalDate endDate ){
        return transactionRepository.getCategoryTotals(accountsId, startDate, endDate);
    }

    public List<TransactionCategoryTotals> getCategoryTotals(List<Integer> accountsId, LocalDate dateMonth){
        LocalDate startDate = dateMonth.withDayOfMonth(1);
        LocalDate endDate = dateMonth.plusMonths(1).withDayOfMonth(1);
        return transactionRepository.getCategoryTotals(accountsId, startDate, endDate);
    }

    // *****

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
