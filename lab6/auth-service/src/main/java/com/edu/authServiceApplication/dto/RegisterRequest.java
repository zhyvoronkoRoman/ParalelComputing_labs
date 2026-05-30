package com.edu.authServiceApplication.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(@NotBlank(message = "name must not be empty")String name,
                              @NotBlank(message = "email must not be empty")String email,
                              @NotBlank(message = "password must not be empty") String password) {

}
