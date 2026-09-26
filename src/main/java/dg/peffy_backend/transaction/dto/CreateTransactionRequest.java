package dg.peffy_backend.transaction.dto;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateTransactionRequest {

    @NotNull (message = "AccountId is required")
    private Integer accountId;

    @NotNull (message = "categoryId is required")
    private Integer categoryId;

    @NotNull (message = "transactionDate is required")
    private Date transactionDate;

    @NotNull (message = "amount is required")
    private BigDecimal amount;

    @NotEmpty (message = "transDescription is required")
    private String transDescription;

    private String notes;


    public CreateTransactionRequest(){}

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

    
}
