package com.edu.cryptoTradingApp.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(@NotBlank(message = "email must not be empty")String email,
                          @NotBlank(message = "password must not be empty") String password) {
}
