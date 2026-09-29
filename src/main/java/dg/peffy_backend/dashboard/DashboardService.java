package dg.peffy_backend.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import dg.peffy_backend.account.AccountService;
import dg.peffy_backend.account.dto.AccountResponse;
import dg.peffy_backend.budget.BudgetService;
import dg.peffy_backend.budget.dto.BudgetResponse;
import dg.peffy_backend.budget.dto.BudgetSummaryResponse;
import dg.peffy_backend.category.CategoryService;
import dg.peffy_backend.transaction.TransactionService;
import dg.peffy_backend.transaction.dto.TransactionResponse;

@Service 
public class DashboardService {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final BudgetService budgetService;
    private final CategoryService categoryService;

    


    public DashboardService(AccountService accountService, TransactionService transactionService, BudgetService budgetService, CategoryService categoryService){
        this.accountService = accountService;
        this.transactionService = transactionService;
        this.budgetService = budgetService;
        this.categoryService = categoryService;
    }


    public List<DashboardResponse> defineUserDashboard(Integer userId){

        List<AccountResponse> accountsList = accountService.getAllAccountsByUserId(userId);
        List<LocalDate> dates = getStartEndMonth(LocalDate.now());

        BigDecimal balance = BigDecimal.ZERO;



        for (AccountResponse account : accountsList) {
            DashboardResponse dashboard = new DashboardResponse();
            balance.add(calcBalance(account)); //Add Account Balance

            List<BigDecimal> totals = calcTotalsByDates(account, dates.get(0), dates.get(1));
            
            dashboard.setMonthlyIncome(totals.get(0));
            dashboard.setMonthlyExpenses(totals.get(1));

            //categoryService.getCategorySummaryByUserId(userId, start, end);
        }


        return Collections.emptyList();

    }

    private BigDecimal calcBalance(AccountResponse account){
        List<TransactionResponse> transactions = transactionService.getAllTransactionsByAccountId(account.getId());
        BigDecimal balance = account.getInitialBalance();
        for (TransactionResponse transaction : transactions) {
            balance = balance.add(transaction.getAmount());
        }
        return balance;
    }

    private List<BigDecimal> calcTotalsByDates(AccountResponse account, LocalDate start, LocalDate end){
        BigDecimal positive = BigDecimal.ZERO;
        BigDecimal negative = BigDecimal.ZERO;

        List<TransactionResponse> transactions = transactionService.getAllTransactionsByDate(start, end);
        for (TransactionResponse transaction : transactions) {
            if (transaction.getAmount().compareTo(BigDecimal.ZERO) >= 0){
                positive = positive.add(transaction.getAmount());
            } else {
                negative = negative.add(transaction.getAmount());
            }
        }

        List<BigDecimal> totals = new ArrayList<>();
        totals.add(positive);
        totals.add(negative);
        return totals;
    }

    private List<BudgetSummaryResponse> calcBudgetSummary(Integer userId){
        List<BudgetResponse> budgets = budgetService.getBudgetsByUserId(userId);

        for (BudgetResponse budget : budgets) {
            BudgetSummaryResponse bSumm = new BudgetSummaryResponse();
            bSumm.setCategoryId(budget.getCategoryId());
            bSumm.setCategoryName(categoryService.getCategoryById(budget.getCategoryId()).getCategoryName());
            bSumm.setBudget(budget.getAmount());
        }



    }

    private List<LocalDate> getStartEndMonth(LocalDate reference){
        YearMonth ymonth = YearMonth.from(reference);

        List<LocalDate> dates = new ArrayList<>();
        dates.add(ymonth.atDay(1));
        dates.add(ymonth.atEndOfMonth());
        return dates;
    }

    /* 1- Total Balance = Initial Balance + Somma Transazioni
    Initia -> Account
    AccountId -> Transazioni
    
    */
    /* 2- Monthly Income = Sum Positive Transaction Actual Month */
    /* 3- Monthly Expenses Transaction Actual Month */
    /* 4- Budgets List:
      "categoryId": 1,
      "categoryName": "Food",
      "budget": 500.00,
      "spent": 320.50,  //Tutte le transazioni con Category ID e Account ID
      "remaining": 179.50
      ---------
        user_id INT NOT NULL,
        category_id INT NOT NULL,
        amount NUMERIC(19, 4) NOT NULL,
        b_month 
      
      */
    
}


/*
{
  "totalBalance": 2450.50,
  "monthlyIncome": 2500.00,
  "monthlyExpenses": 1230.40,
  "budgets": [
    {
      "categoryId": 1,
      "categoryName": "Food",
      "budget": 500.00,
      "spent": 320.50,
      "remaining": 179.50
    }
  ]
}
*/