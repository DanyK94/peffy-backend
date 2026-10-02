package dg.peffy_backend.category;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import dg.peffy_backend.category.dto.CategoryResponse;
import dg.peffy_backend.category.dto.CategorySummary;
import dg.peffy_backend.category.dto.CategoryTotals;
import dg.peffy_backend.category.dto.CreateCategoryRequest;
import dg.peffy_backend.exception.ResourceNotFoundException;
import dg.peffy_backend.user.UserService;

@Service 
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public CategoryService(CategoryRepository categoryRepository, UserService userService){
        this.categoryRepository = categoryRepository;
        this.userService = userService;

    }

    public CategoryResponse createCategory(CreateCategoryRequest request){

        userService.getUserById(request.getUserId());

        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category.setCategoryType(request.getCategoryType());
        category.setUserId(request.getUserId());

        if (request.getParentId() != null){
            categoryRepository.findById(request.getParentId()).orElseThrow(() -> new ResourceNotFoundException("Parent Category with ID: " + request.getParentId() + " not found."));
            category.setParentId(request.getParentId());
        }

        Category savedCat = categoryRepository.save(category);
        return parseResponse(savedCat);
    }

    public CategoryResponse getCategoryById(Integer id){
        Category category = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with ID: " + id + " not found."));
        return  parseResponse(category);
    }

    public List<CategoryResponse> getAllCategories(){
        List<Category> listCat = categoryRepository.findAll();
        if (listCat == null) { return Collections.emptyList(); }
        return listCat.stream().map(this::parseResponse).toList();
        
    }

    public List<CategorySummary> getCategorySummaryByUserId(Integer userId, LocalDate start, LocalDate end){
        List<CategorySummary> cs = categoryRepository.findSumAmmountByCategoryForUser(userId, start, end);
        return cs;
    }

    //------------------------
    //GET CategoryTotals
    //Montly Totals
    public List<CategoryTotals> getCategoryTotals(List<Integer> accountsId, LocalDate dateMonth){
        LocalDate startDate = dateMonth.withDayOfMonth(1);
        LocalDate endDate = dateMonth.plusMonths(1).withDayOfMonth(1);
        return categoryRepository.getCategoryTotals(accountsId, startDate, endDate);
    }

    //---------------------
    
    private CategoryResponse parseResponse(Category category){
        return new CategoryResponse(
        category.getId(),
        category.getUserId(),
        category.getCategoryName(),
        category.getCategoryType(),
        category.getParentId());
    }


    
}
