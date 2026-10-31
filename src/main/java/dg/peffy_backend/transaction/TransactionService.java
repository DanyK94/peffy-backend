package dg.peffy_backend.transaction;

import dg.peffy_backend.account.AccountRepository;
import dg.peffy_backend.account.dto.AccountSummary;

import java.math.BigDecimal;
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
import dg.peffy_backend.transaction.dto.Requests.TransactionListRequest;
import dg.peffy_backend.transaction.dto.TransactionCategoryTotals;
import dg.peffy_backend.transaction.dto.TransactionData;

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

    //___V2___ GET A PARTIALLY POPULATED ACCOUNT SUMMARY DATA AND POPULATE WITH TRANSACTION RELATED DATAS
    public AccountSummary populateAccountValues (AccountSummary aSummary){
        TransactionAccountTotal tTotal = getAccountTotals( List.of(aSummary.getAccountId()), aSummary.getStartDateSum(), aSummary.getEndDateSum());
        aSummary.setIncome(tTotal.getIncome());
        aSummary.setExpenses(tTotal.getExpenses());

        BigDecimal balance = aSummary.getBalance();
        TransactionAccountTotal total = getAccountTotals(List.of(aSummary.getAccountId()));
        balance = balance.add(total.getIncome());
        balance =balance.subtract(total.getExpenses());
        aSummary.setBalance(balance);
        return  aSummary;
    }
    //


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

    public List<TransactionData> getAllTransactionsDataByDate(TransactionListRequest request){

        LocalDate startDate = LocalDate.ofYearDay(1600, 1);
        LocalDate endDate = LocalDate.ofYearDay(3600, 1);

        if (request.getStartDate() != null){
            startDate = request.getStartDate();
        }
        if (request.getEndDate() != null){
            endDate = request.getEndDate();
        }
        return transactionRepository.getTransactionsData(request.getAccountId(), startDate, endDate);
    }

    public List<TransactionData> getAllTransactionsDataByDate(List<Integer> accountIds, LocalDate sDate, LocalDate eDate){
        return transactionRepository.getTransactionsData(accountIds, sDate, eDate);
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
