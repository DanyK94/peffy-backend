package dg.peffy_backend.category.dto;

import java.math.BigDecimal;

public class CategorySummary {
    
    private Integer categoryId;
    private BigDecimal total;

    public CategorySummary(Integer categoryId, BigDecimal total) {
        this.categoryId = categoryId;
        this.total = total;
    }

    public CategorySummary(){}

    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
