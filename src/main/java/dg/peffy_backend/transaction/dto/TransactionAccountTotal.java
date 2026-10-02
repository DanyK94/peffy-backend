package dg.peffy_backend.transaction.dto;

import java.math.BigDecimal;

public class TransactionAccountTotal {
    
    private Integer accountId;
    private BigDecimal income;
    private BigDecimal expenses;

    public TransactionAccountTotal(){ 
    }

    public TransactionAccountTotal(BigDecimal income, BigDecimal expenses) {
        this.accountId = null;
        this.income = income != null ? income : BigDecimal.ZERO;
        this.expenses = expenses != null ? income : BigDecimal.ZERO;
    }
    
    public TransactionAccountTotal(Integer accountId, BigDecimal income, BigDecimal expenses) {
        this.accountId = accountId;
        this.income = income != null ? income : BigDecimal.ZERO;
        this.expenses = expenses != null ? income : BigDecimal.ZERO;
    }


    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
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
    
}
