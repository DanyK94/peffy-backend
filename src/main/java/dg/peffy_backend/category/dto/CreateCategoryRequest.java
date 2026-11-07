package dg.peffy_backend.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateCategoryRequest {

    @NotBlank (message = "CategoryName Cannot be empty")
    @Size (max = 100)
    private String categoryName;

    @NotBlank (message = "CategoryType Cannot be empty")
    @Size (max = 50)
    private String categoryType;

    private Integer parentId;
    
    public CreateCategoryRequest(String categoryName, String categoryType, Integer parentId){
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.parentId = parentId;
    }

    public String getCategoryName() {
        return categoryName;
    }



    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }



    public String getCategoryType() {
        return categoryType;
    }



    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }



    public Integer getParentId() {
        return parentId;
    }



    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }




}