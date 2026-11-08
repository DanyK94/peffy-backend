package dg.peffy_backend.account.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateAccountRequest(
    @NotNull 
    Integer accountId,
    @NotBlank 
    String accountName,
    @NotBlank 
    String accountType,
    @NotBlank  
    String currency,
    @NotNull  
    BigDecimal initialBalance
) {}
