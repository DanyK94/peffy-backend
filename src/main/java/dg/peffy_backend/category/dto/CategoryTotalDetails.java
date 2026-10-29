package dg.peffy_backend.category.dto;

import java.math.BigDecimal;

public class CategoryTotalDetails {

    private Integer categoryId;
    private String categoryName;
    private BigDecimal total;
    private Integer accountId;



    public CategoryTotalDetails(){
        
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
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    
}
