package dg.peffy_backend.transaction.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public class TransactionResponse {

    private Integer id;
    private Integer accountId;
    private Integer categoryId;
    private LocalDate transactionDate;
    private BigDecimal amount;
    private String transDescription;
    private String notes;
    private Instant createdAt;

    public TransactionResponse(Integer id, Integer accountId, Integer categoryId, LocalDate transactionDate,
            BigDecimal amount, String transDescription, String notes, Instant createdAt) {
        this.id = id;
        this.accountId = accountId;
        this.categoryId = categoryId;
        this.transactionDate = transactionDate;
        this.amount = amount;
        this.transDescription = transDescription;
        this.notes = notes;
        this.createdAt = createdAt;
    }

    public TransactionResponse() {
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

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
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
