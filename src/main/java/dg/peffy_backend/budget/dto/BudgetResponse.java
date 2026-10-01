package dg.peffy_backend.budget.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BudgetResponse { 

    private Integer userId;
    private Integer categoryId;
    private BigDecimal amount;
    private LocalDate budgetMonth;

    public BudgetResponse(Integer userId, Integer categoryId, BigDecimal amount, LocalDate budgetMonth) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.amount = amount;
        this.budgetMonth = budgetMonth;
    }

    public BudgetResponse(){

    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getBudgetMonth() {
        return budgetMonth;
    }

    public void setBudgetMonth(LocalDate budgetMonth) {
        this.budgetMonth = budgetMonth;
    }
    
}
    
