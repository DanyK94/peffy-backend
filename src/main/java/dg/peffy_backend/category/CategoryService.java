package dg.peffy_backend.category;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public Category createCategory(CreateCategoryRequest request){

        userService.getUserById(request.getUserId());

        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category.setCategoryType(request.getCategoryType());
        category.setUserId(request.getUserId());

        if (request.getParentId() != null){
            categoryRepository.findById(request.getParentId());
            category.setParentId(request.getParentId());
        }

        return categoryRepository.save(category);
    }

    public Category getCategoryById(Integer id){
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category with ID: " + id + " not found."));
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }


    
}
