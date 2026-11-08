package dg.peffy_backend.transaction.dto.Requests;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record TransactionUpdateRequest(
    @NotNull Integer categoryId,
    @NotNull LocalDate transactionDate,
    @NotNull BigDecimal amount,
    String transDescription,
    String notes
) {}
