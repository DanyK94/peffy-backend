package dg.peffy_backend.budget.dto;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.validation.constraints.NotNull;

public class CreateBudgetRequest {
    
    @NotNull (message = "UserId Cannot be empty")
    private Integer userId;

    @NotNull (message = "CategoryId Cannot be empty")
    private Integer categoryId;

    @NotNull (message = "Amount Cannot be empty")
    private BigDecimal amount;

    @NotNull (message = "BudgetMonth Cannot be empty")
    private Instant budgetMonth;

    public CreateBudgetRequest(){

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

    public Instant getBudgetMonth() {
        return budgetMonth;
    }

    public void setBudgetMonth(Instant budgetMonth) {
        this.budgetMonth = budgetMonth;
    }
    
}
