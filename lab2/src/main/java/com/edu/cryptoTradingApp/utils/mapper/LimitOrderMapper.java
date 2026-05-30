package com.edu.cryptoTradingApp.utils.mapper;

import com.edu.cryptoTradingApp.dto.request.LimitOrderRequest;
import com.edu.cryptoTradingApp.model.LimitOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LimitOrderMapper {
    LimitOrder toEntity(LimitOrderRequest dto);
    LimitOrderRequest toDTO(LimitOrder entity);
}
