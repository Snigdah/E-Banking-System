package com.salary.management.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the pagination details for paginated responses.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginationResponse {
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;
}