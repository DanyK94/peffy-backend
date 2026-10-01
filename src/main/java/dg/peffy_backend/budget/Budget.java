package dg.peffy_backend.budget;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "budgets")
public class Budget {
    
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column (name = "user_id", nullable = false)
    private Integer userId;

    @Column (name = "category_id", nullable = false)
    private Integer categoryId;

    @Column (name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    @Column (name = "b_month", nullable = false)
    private LocalDate budgetMonth;

    /*
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    category_id INT NOT NULL,
    amount NUMERIC(19, 4) NOT NULL,
    b_month DATE NOT NULL,
    */

    public Budget(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public LocalDate  getBudgetMonth() {
        return budgetMonth;
    }

    public void setBudgetMonth(LocalDate  budgetMonth) {
        this.budgetMonth = budgetMonth;
    } 


}


