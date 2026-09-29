package dg.peffy_backend.budget;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import dg.peffy_backend.budget.dto.BudgetResponse;
import dg.peffy_backend.budget.dto.CreateBudgetRequest;
import dg.peffy_backend.exception.ResourceNotFoundException;

@Service 
public class BudgetService {
    
    private final BudgetRepository budgetRepository;

    public BudgetService(dg.peffy_backend.budget.BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public BudgetResponse createBudget(CreateBudgetRequest request){
        Budget budget = new Budget();
        budget.setUserId(request.getUserId());
        budget.setCategoryId(request.getCategoryId());
        budget.setAmount(request.getAmount());
        budget.setBudgetMonth(request.getBudgetMonth());
        return parseBudget(budgetRepository.save(budget));

    }

    public BudgetResponse getBudgetById(Integer id){
        return parseBudget(budgetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Budget with ID: " + id + "not Found")));
    }

    public List<BudgetResponse> getAllBudgets(){
        List<Budget> listBudget  = budgetRepository.findAll();
        if (listBudget == null) { return Collections.emptyList();}
        return listBudget.stream().map(this:: parseBudget).toList();   
    }

    public List<BudgetResponse> getBudgetsByUserId(Integer userId){
        List<Budget> budgets = budgetRepository.findAllByUserId(userId);
        if (budgets == null) {return Collections.emptyList();}
        return budgets.stream().map(this::parseBudget).toList();
    }


    public BudgetResponse parseBudget(Budget budget){
        return new BudgetResponse(
            budget.getUserId(), 
            budget.getCategoryId(), 
            budget.getAmount(), 
            budget.getBudgetMonth());
    }




}
