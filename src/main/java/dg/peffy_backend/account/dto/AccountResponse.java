package dg.peffy_backend.account.dto;

import java.math.BigDecimal;

public class AccountResponse {

    private Integer id;
    private Integer userId;
    private String accountName;
    private String accountType;
    private String currency;
    private BigDecimal initialBalance;

    public AccountResponse() {
    }

    public AccountResponse(
            Integer id,
            Integer userId,
            String accountName,
            String accountType,
            String currency,
            BigDecimal initialBalance) {

        this.id = id;
        this.userId = userId;
        this.accountName = accountName;
        this.accountType = accountType;
        this.currency = currency;
        this.initialBalance = initialBalance;
    }

    // getter e setter

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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
