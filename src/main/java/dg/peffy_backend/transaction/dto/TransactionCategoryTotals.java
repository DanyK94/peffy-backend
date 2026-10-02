package dg.peffy_backend.transaction.dto;

import java.math.BigDecimal;

public class TransactionCategoryTotals {

    private Integer categoryId; 
    private BigDecimal amount;

    public TransactionCategoryTotals(){ 
    }
    
    public TransactionCategoryTotals(Integer categoryId, BigDecimal amount) {
        this.categoryId = categoryId;
        this.amount = amount != null ? amount : BigDecimal.ZERO;
        
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
    public void setAmount(BigDecimal income) {
        this.amount = income;
    }

}
