package dg.peffy_backend.category.dto;

import java.math.BigDecimal;

public class CategoryTotals {

    private Integer categoryId;
    private String categoryName;
    private BigDecimal total;

    public CategoryTotals(Integer categoryId, String categoryName, BigDecimal total) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.total = total;
    }

    public CategoryTotals(){
        
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
    
}
