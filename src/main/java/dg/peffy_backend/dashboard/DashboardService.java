package dg.peffy_backend.dashboard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import dg.peffy_backend.account.AccountService;
import dg.peffy_backend.account.dto.AccountResponse;
import dg.peffy_backend.budget.BudgetService;
import dg.peffy_backend.budget.dto.BudgetResponse;
import dg.peffy_backend.budget.dto.BudgetSummaryResponse;
import dg.peffy_backend.category.CategoryService;
import dg.peffy_backend.category.dto.CategoryTotals;
import dg.peffy_backend.transaction.TransactionService;
import dg.peffy_backend.transaction.dto.TransactionAccountTotal;


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




        // 2- Calculate Balance
        TransactionAccountTotal totals = transactionService.getAccountTotals(accountIds);
        //
        BigDecimal initBalance = accounts.stream().map(AccountResponse::getInitialBalance).filter(Objects::nonNull).reduce(BigDecimal.ZERO,  BigDecimal::add);
        BigDecimal balance = initBalance.add(totals.getIncome()).subtract(totals.getExpenses());

        // 3- Calculate Month Dates and Income and Expenses Monthly
        List<LocalDate> dates = getStartEndMonth(LocalDate.now());
        TransactionAccountTotal monthTotals = transactionService.getAccountTotals(accountIds, dates.get(0), dates.get(1));
        BigDecimal monthlyIncome = monthTotals.getIncome();
        BigDecimal monthlyExpenses = monthTotals.getExpenses();

        // 4- Get Monthly Budgets
        List<BudgetResponse> budgets = budgetService.getUserBudgetsByMonth(userId, LocalDate.now());

        // 5-Expenses for category
        List<CategoryTotals> categoryTotals = categoryService.getCategoryTotals(accountIds, LocalDate.now());
    
        // 6- Budget Summary Response
        List<BudgetSummaryResponse> bSummaries = new ArrayList<>();

        for (BudgetResponse budget : budgets) {

            CategoryTotals category = categoryTotals.stream()
                .filter(s -> s.getCategoryId().equals(budget.getCategoryId()))
                .findFirst().orElse(null);
            BigDecimal spent = BigDecimal.ZERO;
            String categoryName = "Unkown";

            if(category != null) {
                spent = category.getTotal();
                categoryName = category.getCategoryName();
            }
            BudgetSummaryResponse bSummary = new BudgetSummaryResponse(budget.getCategoryId(), categoryName ,budget.getAmount(), spent);
            bSummaries.add(bSummary);
        }


        // 6-Build Dashboard
        DashboardResponse dashboard = new DashboardResponse();
        dashboard.setMonthlyExpenses(monthlyExpenses);
        dashboard.setMonthlyIncome(monthlyIncome);
        dashboard.setTotalBalance(balance);
        dashboard.setBudgets(bSummaries);

        
        return dashboard;
    }



    private List<LocalDate> getStartEndMonth(LocalDate reference){
        YearMonth ymonth = YearMonth.from(reference);

        List<LocalDate> dates = new ArrayList<>();
        dates.add(ymonth.atDay(1));
        dates.add(ymonth.atEndOfMonth());
        return dates;
    }

}

