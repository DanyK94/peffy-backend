package dg.peffy_backend.account;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service 
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account){
        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Integer id){
        return  accountRepository.findById(id);
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
    
    
}
