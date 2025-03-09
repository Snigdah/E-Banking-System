package com.salary.management.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Generic response wrapper with pagination details.
 *
 * @param <T> the type of content in the response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse<T> {
    private List<T> content;
    private PaginationResponse pagination;
}
