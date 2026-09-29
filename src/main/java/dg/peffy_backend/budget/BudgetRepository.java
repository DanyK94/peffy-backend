package dg.peffy_backend.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface BudgetRepository extends JpaRepository<Budget, Integer> {

    public List<Budget> findAllByUserId(Integer userId);
    
}
