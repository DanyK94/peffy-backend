package dg.peffy_backend.category.dto;

public class CategoryResponse {

    private Integer id;
    private Integer userId;
    private String categoryName;
    private String categoryType;
    private Integer parentId;

    public CategoryResponse() {
    }

    public CategoryResponse(Integer id, Integer userId, String categoryName, String categoryType, Integer parentId) {
        this.id = id;
        this.userId = userId;
        this.categoryName = categoryName;
        this.categoryType = categoryType;
        this.parentId = parentId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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