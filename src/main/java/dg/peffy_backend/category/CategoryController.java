package dg.peffy_backend.category;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dg.peffy_backend.category.dto.CreateCategoryRequest;
import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController 
@RequestMapping ("api/categories")
public class CategoryController {

    
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping ("/")
    public Category createCategory(@Valid @RequestBody CreateCategoryRequest createCategoryRequest){
        return categoryService.createCategory(createCategoryRequest);
    }

    @GetMapping("/{id}")
    public Category getMethodName(@PathVariable Integer id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping("/")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    
    
    
    
}
