package com.edu.cryptoTradingApp.utils.mapper;

import com.edu.cryptoTradingApp.dto.request.LimitOrderRequest;
import com.edu.cryptoTradingApp.model.LimitOrder;
import com.edu.cryptoTradingApp.model.OrderType;
import com.edu.cryptoTradingApp.model.Symbol;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-05-30T17:06:06+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.46.0.v20260407-0427, environment: Java 21.0.10 (Eclipse Adoptium)"
)
@Component
public class LimitOrderMapperImpl implements LimitOrderMapper {

    @Override
    public LimitOrder toEntity(LimitOrderRequest dto) {
        if ( dto == null ) {
            return null;
        }

        LimitOrder limitOrder = new LimitOrder();

        limitOrder.setAmount( dto.amount() );
        limitOrder.setSymbol( dto.symbol() );
        limitOrder.setTargetPrice( dto.targetPrice() );
        limitOrder.setType( dto.type() );

        return limitOrder;
    }

    @Override
    public LimitOrderRequest toDTO(LimitOrder entity) {
        if ( entity == null ) {
            return null;
        }

        BigDecimal targetPrice = null;
        OrderType type = null;
        Symbol symbol = null;
        BigDecimal amount = null;

        targetPrice = entity.getTargetPrice();
        type = entity.getType();
        symbol = entity.getSymbol();
        amount = entity.getAmount();

        LimitOrderRequest limitOrderRequest = new LimitOrderRequest( targetPrice, type, symbol, amount );

        return limitOrderRequest;
    }
}
