package dg.peffy_backend.dashboard;

import java.math.BigDecimal;
import java.util.List;

import dg.peffy_backend.budget.Budget;

public class DashboardResponse {

    private BigDecimal totalBalance;

    private BigDecimal monthlyIncome;

    private BigDecimal monthlyExpenses;

    private List<Budget> budgets;

    public DashboardResponse(){}

    public DashboardResponse(BigDecimal totalBalance, BigDecimal monthlyIncome, BigDecimal monthlyExpenses,
            List<Budget> budgets) {
        this.totalBalance = totalBalance;
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpenses = monthlyExpenses;
        this.budgets = budgets;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(BigDecimal monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public BigDecimal getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(BigDecimal monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public List<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(List<Budget> budgets) {
        this.budgets = budgets;
    }
    
}
