package dg.peffy_backend.account;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import dg.peffy_backend.account.dto.AccountResponse;
import dg.peffy_backend.account.dto.CreateAccountRequest;
import dg.peffy_backend.exception.ResourceNotFoundException;
import dg.peffy_backend.user.UserService;

@Service 
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserService userService;


    public AccountService(AccountRepository accountRepository,
        UserService userService){
        
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

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
