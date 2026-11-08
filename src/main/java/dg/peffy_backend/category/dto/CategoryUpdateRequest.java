package dg.peffy_backend.category.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateRequest(
    @NotBlank String categoryName,
    @NotBlank String categoryType,
    Integer parentId
) {}
