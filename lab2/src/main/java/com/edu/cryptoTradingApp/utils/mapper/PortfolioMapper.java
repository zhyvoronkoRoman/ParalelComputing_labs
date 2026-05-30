package com.edu.cryptoTradingApp.utils.mapper;

import com.edu.cryptoTradingApp.dto.response.PortfolioResponse;
import com.edu.cryptoTradingApp.model.Portfolio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {
    Portfolio toEntity(PortfolioResponse dto);
    PortfolioResponse toDTO(Portfolio entity);
}
