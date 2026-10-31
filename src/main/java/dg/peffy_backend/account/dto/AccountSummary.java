package dg.peffy_backend.account.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import dg.peffy_backend.account.Account;

public class AccountSummary {

    //Account Informations
    private Integer accountId;
    private String accountName;
    private String accountType;
    private String currency;

    //Calculated values
    private BigDecimal balance;
    private BigDecimal income;
    private BigDecimal expenses;

    //Time range within summary has value
    private LocalDate startDateSum;
    private LocalDate endDateSum;

    public AccountSummary(){};

    public void setAccountData(Account account){
        this.accountId = account.getId();
        this.accountName = account.getAccountName();
        this.accountType = account.getAccountType();
        this.currency = account.getCurrency();
    }

    public Integer getAccountId() {
        return accountId;
    }
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    public String getAccountName() {
        return accountName;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public BigDecimal getIncome() {
        return income;
    }
    public void setIncome(BigDecimal income) {
        this.income = income;
    }
    public BigDecimal getExpenses() {
        return expenses;
    }
    public void setExpenses(BigDecimal expenses) {
        this.expenses = expenses;
    }

     public LocalDate getStartDateSum() {
        return startDateSum;
    }

    public void setStartDateSum(LocalDate startDateSum) {
        this.startDateSum = startDateSum;
    }

    public LocalDate getEndDateSum() {
        return endDateSum;
    }

    public void setEndDateSum(LocalDate endDateSum) {
        this.endDateSum = endDateSum;
    }
    
}
