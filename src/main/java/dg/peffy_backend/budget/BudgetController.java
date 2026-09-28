package dg.peffy_backend.budget;

import org.springframework.web.bind.annotation.RestController;

import dg.peffy_backend.budget.dto.BudgetResponse;
import dg.peffy_backend.budget.dto.CreateBudgetRequest;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController 
@RequestMapping("/api/budget")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService){
        this.budgetService = budgetService;
    }


    @PostMapping 
    public BudgetResponse createBudget(@Valid @RequestBody CreateBudgetRequest request){
        return budgetService.createBudget(request);
    }

    @GetMapping("/{id}")
    public BudgetResponse getBudgetById(@PathVariable Integer id ){
        return budgetService.getBudgetById(id);
    }

    @GetMapping
    public List<BudgetResponse> getAllBudget(){
        return budgetService.getAllBudgets();
    }



    
}
