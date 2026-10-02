package dg.peffy_backend.transaction.dto;

import java.math.BigDecimal;

public class TransactionTotals {

    private Integer categoryId; 
    private BigDecimal income;
    private BigDecimal expenses;

    public TransactionTotals(){ 
    }
    
    public TransactionTotals(BigDecimal income, BigDecimal expenses) {
        this.income = income;
        this.expenses = expenses;
    }
    public TransactionTotals(Integer categoryId, BigDecimal expenses) {
        this.categoryId = categoryId;
        this.expenses = expenses;
        
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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
