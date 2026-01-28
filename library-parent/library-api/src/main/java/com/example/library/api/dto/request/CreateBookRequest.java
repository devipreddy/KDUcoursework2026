package com.example.library.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Request payload for creating a new book.
 */
public class CreateBookRequest {

    /**
     * Human-readable title of the book.
     * Must be non-blank and at least two characters long.
     */
    @NotBlank(message = "title must not be blank")
    @Size(min = 2, message = "title must have at least 2 characters")
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
