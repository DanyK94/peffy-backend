package dg.peffy_backend.account;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import dg.peffy_backend.account.dto.AccountResponse;
import dg.peffy_backend.account.dto.AccountSummary;
import dg.peffy_backend.account.dto.CreateAccountRequest;
import dg.peffy_backend.account.dto.UpdateAccountRequest;
import dg.peffy_backend.exception.BusinessException;
import dg.peffy_backend.exception.ResourceNotFoundException;
import dg.peffy_backend.security.CurrentUserService;
import dg.peffy_backend.transaction.TransactionService;

@Service 
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    private final CurrentUserService currentUserService;

    
    public AccountService(AccountRepository accountRepository, TransactionService transactionService, CurrentUserService currentUserService){
        
        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
        this.currentUserService = currentUserService;
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

    public List<AccountSummary> getAllAccountSummary(){
        Integer userId = currentUserService.getCurrentUserId();
        List<AccountResponse> accoList = getAllAccountsByUserId(userId);
        List<AccountSummary> listAcSumm = new ArrayList<>();

        for (AccountResponse ar : accoList) {
            listAcSumm.add(getAccountSummary(ar.getId(), LocalDate.of(1, 1, 1), LocalDate.of(9999, 12, 31)));            
        }
        return  listAcSumm;
    }

    public AccountSummary getAccountSummary(Integer accountId, LocalDate sDate, LocalDate eDate){
        AccountSummary aSummary = new AccountSummary(); 
        Integer userId = currentUserService.getCurrentUserId();
        Account account = accountRepository.findByIdAndUserId(accountId, userId).orElseThrow(() -> new ResourceNotFoundException("Account with ID: " + accountId + " not found."));
        aSummary.setAccountData(account);
        aSummary.setBalance(account.getInitialBalance());

        aSummary.setStartDateSum(sDate);
        aSummary.setEndDateSum(eDate);
        aSummary = transactionService.populateAccountValues(aSummary);
        return aSummary;
    }

    /////////////////////

    public AccountResponse createAccount(CreateAccountRequest request){

        Integer userId = currentUserService.getCurrentUserId();

        Account account = new Account();
        account.setUserId(userId);
        account.setAccountName(request.getAccountName());
        account.setAccountType(request.getAccountType());
        account.setCurrency(request.getCurrency());
        account.setInitialBalance(request.getInitialBalance());

        Account savedAccount = accountRepository.save(account);

        return parseAccountResponse(savedAccount);
    }

    public List<AccountResponse> getAllAccounts() {
        Integer userId = currentUserService.getCurrentUserId();
        return  getAllAccountsByUserId(userId);
    }

    public AccountResponse getAccountById(Integer id){
        Integer userId = currentUserService.getCurrentUserId();
        Account account = accountRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Account with id: "+ id +" not found."));
        return parseAccountResponse(account);
    }

    public AccountResponse updateAccount(UpdateAccountRequest request){
        Integer userId = currentUserService.getCurrentUserId();
        Account account = accountRepository.findByIdAndUserId(request.accountId(), userId).orElseThrow(() -> new ResourceNotFoundException("Account with id: " + request.accountId() + " not found."));

        account.setAccountName(request.accountName());
        account.setAccountType(request.accountType());
        account.setCurrency(request.currency());
        account.setInitialBalance(request.initialBalance());
        
        return parseAccountResponse(accountRepository.save(account));
    }

    public void deleteAccount(Integer id) {
        Integer userId = currentUserService.getCurrentUserId();
        Account account = accountRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Account with id: " + id + " not found."));
        if (transactionService.getAllTransactionsByAccountId(id).size() > 0){
            throw new BusinessException("Transaction assigned to account with id: " + id + " - Must have zero transaction assigned");
        }
        accountRepository.delete(account);
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
