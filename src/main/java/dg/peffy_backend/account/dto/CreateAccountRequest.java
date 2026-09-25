package dg.peffy_backend.account.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateAccountRequest {

    @NotNull(message = "User ID is required")
    private Integer userId;

    @NotBlank(message = "Account Name is Required")
    @Size(max = 100, message = "Account name must not exceed 100 characters")
    private String accountName;

    @NotBlank(message = "Account Type is Required")
    @Size(max = 50, message = "Account type must not exceed 50 characters")
    private String accountType;

    @NotBlank (message = "Currency is Required")
    @Size(min = 3, max = 3, message = "3 char required")
    private String currency;

    @NotNull(message = "Balance is Required")
    @DecimalMin(value = "0.0", message = "Initial balance cannot be negative")
    private BigDecimal initialBalance;

    public CreateAccountRequest() {
    }

    public CreateAccountRequest(Integer userId, String accountName, String accountType, String currency, BigDecimal initialBalance) {
        this.userId = userId;
        this.accountName = accountName;
        this.accountType = accountType;
        this.currency = currency;
        this.initialBalance = initialBalance;
    }

    public Integer getUserId(){
        return this.userId;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(BigDecimal initialBalance) {
        this.initialBalance = initialBalance;
    }
}
