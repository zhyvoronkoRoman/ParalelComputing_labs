package com.edu.cryptoTradingApp.utils.mapper;

import com.edu.cryptoTradingApp.dto.response.PortfolioResponse;
import com.edu.cryptoTradingApp.model.Portfolio;
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
public class PortfolioMapperImpl implements PortfolioMapper {

    @Override
    public Portfolio toEntity(PortfolioResponse dto) {
        if ( dto == null ) {
            return null;
        }

        Portfolio portfolio = new Portfolio();

        portfolio.setAmount( BigDecimal.valueOf( dto.amount() ) );
        portfolio.setSymbol( dto.symbol() );

        return portfolio;
    }

    @Override
    public PortfolioResponse toDTO(Portfolio entity) {
        if ( entity == null ) {
            return null;
        }

        Symbol symbol = null;
        double amount = 0.0d;

        symbol = entity.getSymbol();
        if ( entity.getAmount() != null ) {
            amount = entity.getAmount().doubleValue();
        }

        PortfolioResponse portfolioResponse = new PortfolioResponse( symbol, amount );

        return portfolioResponse;
    }
}
