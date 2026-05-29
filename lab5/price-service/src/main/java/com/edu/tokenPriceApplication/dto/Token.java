package com.edu.tokenPriceApplication.dto;

import java.math.BigDecimal;

public record Token (
        String name,
        BigDecimal price){}
