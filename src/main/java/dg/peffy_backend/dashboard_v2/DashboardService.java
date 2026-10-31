package dg.peffy_backend.dashboard_v2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import dg.peffy_backend.account.AccountService;
import dg.peffy_backend.account.dto.AccountSummary;
import dg.peffy_backend.category.CategoryService;
import dg.peffy_backend.dashboard_v2.dto.DashboardResponse;
import dg.peffy_backend.transaction.TransactionService;



@Service 
public class DashboardService {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final CategoryService categoryService;

    


    public DashboardService(AccountService accountService, TransactionService transactionService, CategoryService categoryService){
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.categoryService = categoryService;
    }


    public DashboardResponse getDashboardResponse(Integer userId, LocalDate sDate, LocalDate eDate){
        DashboardResponse response = new DashboardResponse();

        // 1# accountSummList
        response.setAccountSummList(accountService.getAllAccountSummary(userId, sDate, eDate));
        // 2# total balance, total income total expenses
        response = calcTotals(response);
        // Aux# List accounts id
        List<Integer> accountsId = response.getAccountSummList().stream().map(AccountSummary::getAccountId).toList(); 
        // 3# List<CategoryTotal> categorySummList
        response.setCategorySummList(categoryService.getCategoryTotals(accountsId, sDate, eDate));
        // 4# List<TransactionData> transactionSummList
        response.setTransactionSummList(transactionService.getAllTransactionsDataByDate(accountsId, sDate, eDate));
        return  response;
    }

    private DashboardResponse calcTotals(DashboardResponse response){

        BigDecimal balance = response.getAccountSummList().stream().map(AccountSummary::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal income = response.getAccountSummList().stream().map(AccountSummary::getIncome).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expenses = response.getAccountSummList().stream().map(AccountSummary::getExpenses).reduce(BigDecimal.ZERO, BigDecimal::add);
        
        response.setBalance(balance);
        response.setMonthIncome(income);
        response.setMonthExpenses(expenses);
        return  response;
    }

    
}

