package com.sebastianwebapp.Ejercicio2.domain;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductDTO(
        @NotBlank(message = "Name cannot be empty")
        @Size(min = 3, message = "Name must have at least 3 characters")
        String name,

        @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
        @Digits(integer = 10, fraction = 2, message = "Price can have up to 2 decimals")
        double price,

        @NotBlank(message = "Category cannot be empty")
        @Size(min = 3, message = "Category must have at least 3 characters")
        String category
) {}
