package dg.peffy_backend.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;
import dg.peffy_backend.account.AccountService;
import dg.peffy_backend.account.dto.AccountResponse;
import dg.peffy_backend.budget.BudgetService;
import dg.peffy_backend.budget.dto.BudgetResponse;
import dg.peffy_backend.budget.dto.BudgetSummaryResponse;
import dg.peffy_backend.category.CategoryService;
import dg.peffy_backend.category.dto.CategoryTotals;
import dg.peffy_backend.transaction.TransactionService;
import dg.peffy_backend.transaction.dto.TransactionTotals;


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

    public DashboardResponse getUserDashboard(Integer userId){
        // 1- Get Accounts List and IDs List
        List<AccountResponse> accounts = accountService.getAllAccountsByUserId(userId);
        List<Integer> accountIds = accounts.stream().map(AccountResponse::getId).toList();

        // 2- Calculate Month Dates
        List<LocalDate> dates = getStartEndMonth(LocalDate.now());

        // 3- Calculate Income and Expenses
        TransactionTotals totals = transactionService.getAccountTotals(accountIds, dates.get(0), dates.get(1));
        BigDecimal income = totals != null ? Optional.ofNullable(totals.getIncome()).orElse(BigDecimal.ZERO) : BigDecimal.ZERO;
        BigDecimal expenses = totals != null ? Optional.ofNullable(totals.getExpenses()).orElse(BigDecimal.ZERO) : BigDecimal.ZERO;
        BigDecimal accountSum = accounts.stream().map(AccountResponse::getInitialBalance).filter(Objects::nonNull).reduce(BigDecimal.ZERO,  BigDecimal::add);

        // 4- Get Monthly Budgets
        List<BudgetResponse> budgets = budgetService.getUserBudgetsByMonth(userId, LocalDate.now());


        // 5-Expenses for category
        List<CategoryTotals> categoryTotals = categoryService.getCategoryTotals(accountIds, LocalDate.now());
    
        // 6- Budget Summary Response
        List<BudgetSummaryResponse> bSummaries = new ArrayList<>();

        for (BudgetResponse budget : budgets) {

            CategoryTotals category = categoryTotals.stream().filter(s -> s.getCategoryId().equals(budget.getCategoryId())).findFirst().orElse(null);
            BudgetSummaryResponse bSummary = new BudgetSummaryResponse(budget.getCategoryId(), category.getCategoryName() ,budget.getAmount(), category.getTotal());
            bSummaries.add(bSummary);
        }


        // 6-Build Dashboard
        DashboardResponse dashboard = new DashboardResponse();
        dashboard.setMonthlyExpenses(totals.getExpenses());
        dashboard.setMonthlyIncome(totals.getIncome());
        dashboard.setTotalBalance(accountSum.add(income).subtract(expenses));
        dashboard.setBudgets(bSummaries);

        
        return dashboard;
    }


    public DashboardResponse defineUserDashboard(Integer userId){

        List<AccountResponse> accountsList = accountService.getAllAccountsByUserId(userId);
        List<Integer> accountsId = accountsList.stream().map(AccountResponse::getId).toList();
        
        List<LocalDate> dates = getStartEndMonth(LocalDate.now());

        List<BigDecimal> totals = calcTotalsByDates(accountsId, dates.get(0), dates.get(1));

        BigDecimal balance = BigDecimal.ZERO;

        for (Integer accountId : accountsId) {
            balance = balance.add(accountService.getBalance(accountId));
        }

        List<BudgetSummaryResponse> budgets = transactionService.getTotalForBudgets(userId,dates.get(0), dates.get(1));

        return new DashboardResponse(balance,totals.get(0), totals.get(1), budgets);

    }

    private List<BigDecimal> calcTotalsByDates(List<Integer> accountsId , LocalDate start, LocalDate end){
        BigDecimal positive = BigDecimal.ZERO;
        BigDecimal negative = BigDecimal.ZERO;

        List<BigDecimal> tAmounts = transactionService.getTAmountsForDates(accountsId, start, end);

        for (BigDecimal value : tAmounts) {
            if (value.compareTo(BigDecimal.ZERO) >= 0){
                positive = positive.add(value);
            } else {
                negative = negative.add(value);
            }
            
        }
        List<BigDecimal> totals = new ArrayList<>();
        totals.add(positive);
        totals.add(negative);
        return totals;
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