package dg.peffy_backend.account;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import dg.peffy_backend.account.dto.AccountResponse;
import dg.peffy_backend.account.dto.AccountSummary;
import dg.peffy_backend.account.dto.CreateAccountRequest;
import dg.peffy_backend.exception.ResourceNotFoundException;
import dg.peffy_backend.transaction.TransactionService;
import dg.peffy_backend.user.UserService;

@Service 
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final UserService userService;

    
    public AccountService(AccountRepository accountRepository,
        UserService userService, TransactionService transactionService){
        
        this.accountRepository = accountRepository;
        this.userService = userService;
        this.transactionService = transactionService;
    }

    // ____V2___ BY ACCOUNT ID AND TIMERANGE BUILD AN ACCOUNT SUMMARY DATA
    public List<AccountSummary> getAllAccountSummary(Integer userId, LocalDate sDate, LocalDate eDate){
        List<AccountResponse> accoList = getAllAccountsByUserId(userId);
        List<AccountSummary> listAcSumm = new ArrayList<>();

        for (AccountResponse ar : accoList) {
            listAcSumm.add(getAccountSummary(ar.getId(), sDate, eDate));            
        }

        return  listAcSumm;
    }

    public AccountSummary getAccountSummary(Integer accountId, LocalDate sDate, LocalDate eDate){
        AccountSummary aSummary = new AccountSummary(); 
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new ResourceNotFoundException("Account with ID: " + accountId + " not found."));
        aSummary.setAccountData(account);
        aSummary.setBalance(account.getInitialBalance());

        aSummary.setStartDateSum(sDate);
        aSummary.setEndDateSum(eDate);
        aSummary = transactionService.populateAccountValues(aSummary);
        return aSummary;
    }

    /////////////////////

    public AccountResponse createAccount(CreateAccountRequest request){

        userService.getUserById(request.getUserId());

        Account account = new Account();
        account.setUserId(request.getUserId());
        account.setAccountName(request.getAccountName());
        account.setAccountType(request.getAccountType());
        account.setCurrency(request.getCurrency());
        account.setInitialBalance(request.getInitialBalance());

        Account savedAccount = accountRepository.save(account);

        return parseAccountResponse(savedAccount);
    }

    public List<AccountResponse> getAllAccounts() {
        List<Account> listAccounts = accountRepository.findAll();
        List<AccountResponse> listResponses = new ArrayList<>();
        for (Account account : listAccounts) {
            listResponses.add(parseAccountResponse(account));
        }
        return listResponses;
    }

    public AccountResponse getAccountById(Integer id){
        Account account = accountRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Account with id: "+ id +" not found."));
        return parseAccountResponse(account);
    }

    public Account updateAccount(Integer id, Account account){
        Account exsAccount = accountRepository.findById(id).orElseThrow();

        exsAccount.setUserId(account.getId());
        exsAccount.setAccountName(account.getAccountName());
        exsAccount.setAccountType(account.getAccountType());
        exsAccount.setCurrency(account.getCurrency());
        exsAccount.setInitialBalance(account.getInitialBalance());
        
        return accountRepository.save(exsAccount);
    }

    public void deleteAccount(Integer id) {
        accountRepository.deleteById(id);
    }

    public List<AccountResponse> getAllAccountsByUserId(Integer userId){
        List<Account> listAccounts = accountRepository.findByUserId(userId);
        if (listAccounts == null) { return Collections.emptyList();}
        return listAccounts.stream().map(this::parseAccountResponse).toList();
    }

    private AccountResponse parseAccountResponse(Account account){
        return new AccountResponse(
            account.getId(),
            account.getUserId(),
            account.getAccountName(),
            account.getAccountType(),
            account.getCurrency(),
            account.getInitialBalance()
        );
    }
    
    
}
