package com.edu.orderServiceApplication.model;

import lombok.Getter;

@Getter
public enum Symbol {BTC("bitcoin"),
    ETH("etherium"),
    USDT("tether");

    private final String value;

    Symbol(String value){this.value = value;}

}