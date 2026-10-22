package dg.peffy_backend.transaction.dto.Requests;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.NotNull;

public class TransactionListRequest {

    @NotNull (message = "AccountId is required")
    private List<Integer> accountsId;

    private LocalDate startDate;
    private LocalDate endDate;

    public  TransactionListRequest(){

    }



    public List<Integer> getAccountId() {
        return accountsId;
    }
    public void setAccountId(List<Integer> accountsId) {
        this.accountsId = accountsId;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
}
