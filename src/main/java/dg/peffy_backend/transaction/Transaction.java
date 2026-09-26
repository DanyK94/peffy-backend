package dg.peffy_backend.transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "account_id")
    private Integer accountId;

    @Column (name = "category_id")
    private Integer categoryId;

    @Column (name = "transaction_date")
    private Date transactionDate;

    @Column (name = "amount")
    private BigDecimal amount;

    @Column (name = "t_description")
    private String transDescription;

    @Column (name = "notes")
    private String notes;

    @Column (name = "created_ad")
    private Instant createdAt;


    public Transaction(){
        
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransDescription() {
        return transDescription;
    }

    public void setTransDescription(String transDescription) {
        this.transDescription = transDescription;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    
}



/*
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    category_id INT,
    transaction_date DATE NOT NULL DEFAULT CURRENT_DATE,
    amount NUMERIC(19, 4) NOT NULL,
    t_description VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_transaction_account FOREIGN KEY (account_id) REFERENCES accounts(id),
    CONSTRAINT fk_transaction_category FOREIGN KEY (category_id) REFERENCES categories(id)
);
*/