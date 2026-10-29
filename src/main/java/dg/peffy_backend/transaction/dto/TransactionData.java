package dg.peffy_backend.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionData {

    private Integer id;
    private BigDecimal amount;
    private Integer categoryId;
    private String categoryName;
    private Integer accountId;
    private String transDescription;
    private LocalDate transactionDate;
    private String notes;

    public TransactionData(Integer id, BigDecimal amount, Integer categoryId, String categoryName, Integer accountId,
            String transDescription, LocalDate transactionDate, String notes) {
        this.id = id;
        this.amount = amount;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.accountId = accountId;
        this.transDescription = transDescription;
        this.transactionDate = transactionDate;
        this.notes = notes;
    }

    public TransactionData(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
        public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getTransDescription() {
        return transDescription;
    }

    public void setTransDescription(String transDescription) {
        this.transDescription = transDescription;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }


}
