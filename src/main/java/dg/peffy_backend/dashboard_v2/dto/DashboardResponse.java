package dg.peffy_backend.dashboard_v2.dto;

import java.math.BigDecimal;
import java.util.List;

import dg.peffy_backend.account.dto.AccountSummary;
import dg.peffy_backend.category.dto.CategoryTotals;
import dg.peffy_backend.transaction.dto.TransactionData;

public class DashboardResponse {

    private BigDecimal balance;
    private BigDecimal monthIncome;
    private BigDecimal monthExpenses;


    private List<AccountSummary> accountSummList;
    private List<CategoryTotals> categorySummList;
    private List<TransactionData> transactionSummList;
    
    
    public DashboardResponse(){};
    
     public BigDecimal getBalance() {
        return balance;
    }


    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    public BigDecimal getMonthIncome() {
        return monthIncome;
    }


    public void setMonthIncome(BigDecimal monthIncome) {
        this.monthIncome = monthIncome;
    }


    public BigDecimal getMonthExpenses() {
        return monthExpenses;
    }

    public void setMonthExpenses(BigDecimal monthExpenses) {
        this.monthExpenses = monthExpenses;
    }

    public List<AccountSummary> getAccountSummList() {
        return accountSummList;
    }

    public void setAccountSummList(List<AccountSummary> accountSummList) {
        this.accountSummList = accountSummList;
    }

    public List<CategoryTotals> getCategorySummList() {
        return categorySummList;
    }
    public void setCategorySummList(List<CategoryTotals> categorySummList) {
        this.categorySummList = categorySummList;
    }
    public List<TransactionData> getTransactionSummList() {
        return transactionSummList;
    }
    public void setTransactionSummList(List<TransactionData> transactionSummList) {
        this.transactionSummList = transactionSummList;
    }
    
}
