package dg.peffy_backend.budget.dto;

import java.math.BigDecimal;

public class BudgetSummaryResponse {

    private Integer categoryId;
    private String categoryName;
    private BigDecimal budget;
    private BigDecimal spent;
    private BigDecimal remaining;

    public BudgetSummaryResponse(){


    }

    public BudgetSummaryResponse(Integer categoryId, String categoryName, BigDecimal budget, BigDecimal spent) 
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.budget = budget;
        this.spent = spent;
        this.remaining = this.budget.subtract(this.spent);
    }

    public BudgetSummaryResponse(Integer categoryId, String categoryName, BigDecimal budget, BigDecimal spent,
            BigDecimal remaining) 
    {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.budget = budget;
        this.spent = spent;
        this.remaining = remaining;
    }
    
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public BigDecimal getBudget() {
        return budget;
    }
    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }
    public BigDecimal getSpent() {
        return spent;
    }
    public void setSpent(BigDecimal spent) {
        this.spent = spent;
    }
    public BigDecimal getRemaining() {
        return remaining;
    }
    public void setRemaining(BigDecimal remaining) {
        this.remaining = remaining;
    }

    
}
