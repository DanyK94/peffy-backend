package dg.peffy_backend.account;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dg.peffy_backend.account.dto.AccountResponse;
import dg.peffy_backend.account.dto.AccountSummary;
import dg.peffy_backend.account.dto.CreateAccountRequest;
import dg.peffy_backend.account.dto.UpdateAccountRequest;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController 
@RequestMapping("api/accounts")
public class AccountController {
    
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @GetMapping("/")
    public List<AccountResponse> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public AccountResponse getAccountById(@PathVariable Integer id){
        return accountService.getAccountById(id);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return accountService.createAccount(request);
    }

    @PutMapping("/")
    public AccountResponse updateAccount(@Valid @RequestBody UpdateAccountRequest request) {
        return accountService.updateAccount(request);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable("id")  Integer accountId) {
        accountService.deleteAccount(accountId);
    }

    @GetMapping("/summary/")
    public ResponseEntity<List<AccountSummary>> getSummary() {
        List<AccountSummary> listSummaries = accountService.getAllAccountSummary();
        return ResponseEntity.ok(listSummaries);
    }
    
    

}
