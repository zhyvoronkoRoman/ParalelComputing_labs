package com.edu.walletServiceApplication.mapper;

import com.edu.walletServiceApplication.dto.PortfolioResponse;
import com.edu.walletServiceApplication.model.Portfolio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {
    PortfolioResponse toDTO(Portfolio entity);
}
