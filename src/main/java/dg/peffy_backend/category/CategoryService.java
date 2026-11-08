package dg.peffy_backend.category;

import dg.peffy_backend.transaction.TransactionRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import dg.peffy_backend.category.dto.CategoryResponse;
import dg.peffy_backend.category.dto.CategoryTotals;
import dg.peffy_backend.category.dto.CategoryUpdateRequest;
import dg.peffy_backend.category.dto.CreateCategoryRequest;
import dg.peffy_backend.exception.BusinessException;
import dg.peffy_backend.exception.ResourceNotFoundException;
import dg.peffy_backend.security.CurrentUserService;

@Service 
public class CategoryService {

    private final TransactionRepository transactionRepository;
    private final CategoryRepository categoryRepository;
    private final CurrentUserService currentUserService;

    public CategoryService(CategoryRepository categoryRepository, CurrentUserService currentUserService, TransactionRepository transactionRepository){
        this.categoryRepository = categoryRepository;
        this.currentUserService = currentUserService;
        this.transactionRepository = transactionRepository;

    }

    public CategoryResponse createCategory(CreateCategoryRequest request){

        Integer userId = currentUserService.getCurrentUserId();

        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category.setCategoryType(request.getCategoryType());
        category.setUserId(userId);

        if (request.getParentId() != null){
            categoryRepository.findByIdAndUserId(request.getParentId(), userId).orElseThrow(() -> new ResourceNotFoundException("Parent Category with ID: " + request.getParentId() + " not found."));
            category.setParentId(request.getParentId());
        }

        Category savedCat = categoryRepository.save(category);
        return parseResponse(savedCat);
    }

    public CategoryResponse getCategoryById(Integer id){
        Integer userId = currentUserService.getCurrentUserId();
        Category category = categoryRepository.findByIdAndUserId(id,userId).orElseThrow(() -> new ResourceNotFoundException("Category with ID: " + id + " not found."));
        return  parseResponse(category);
    }

    public List<CategoryResponse> getCategoryByUserId(){
        Integer userId = currentUserService.getCurrentUserId();
        return categoryRepository.findByUserId(userId).stream().map(this::parseResponse).toList();
    }

    public CategoryResponse updateCategory(Integer id, CategoryUpdateRequest request){
        Integer userId = currentUserService.getCurrentUserId();
        Category category = categoryRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Category with ID:" + id + "not found."));

        Integer newParentId = request.parentId();
        if (newParentId != null){
            if(newParentId.equals(id)) {
                throw new BusinessException("A category cannot be its own parent.");
            }
            categoryRepository.findByIdAndUserId(newParentId, userId).orElseThrow(() -> new ResourceNotFoundException("Parent category with ID: " + newParentId + " not found."));
        }
        
        category.setCategoryName(request.categoryName());
        category.setCategoryType(request.categoryType());
        category.setParentId(request.parentId());
        return parseResponse(categoryRepository.save(category));
    }

    public void deleteCategoryById(Integer id){
        Integer userId = currentUserService.getCurrentUserId();
        Category category = categoryRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new ResourceNotFoundException("Category with ID: " + id + " not found."));
        if (transactionRepository.existsByCategoryId(id)){
            throw new BusinessException( id + " category has transaction associated. Unbound first.");}
        if (categoryRepository.existsByParentId(id)){
            throw new BusinessException( id + " category is parent of at least one category. Unbound first.");}
        categoryRepository.delete(category);
    }

    //TODO ----- Delete getAllCategories
    public List<CategoryResponse> getAllCategories(){
        List<Category> listCat = categoryRepository.findAll();
        if (listCat == null) { return Collections.emptyList(); }
        return listCat.stream().map(this::parseResponse).toList();
        
    }

    //------------------------
    //GET CategoryTotals
    //Montly Totals
    public List<CategoryTotals> getCategoryTotals(List<Integer> accountsId, LocalDate dateMonth){
        if (accountsId.isEmpty()) {return List.of();}
        LocalDate startDate = dateMonth.withDayOfMonth(1);
        LocalDate endDate = dateMonth.plusMonths(1).withDayOfMonth(1);
        return categoryRepository.getCategoryTotals(accountsId, startDate, endDate);
    }

    public List<CategoryTotals> getCategoryTotals(List<Integer> accountsId, LocalDate sDate, LocalDate eDate){
        LocalDate startDate = sDate;
        LocalDate endDate = eDate;
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
