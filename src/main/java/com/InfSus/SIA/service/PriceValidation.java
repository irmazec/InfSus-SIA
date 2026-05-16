package com.InfSus.SIA.service;

import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

public class PriceValidation {
    public static Boolean validatePrice(BigDecimal price){
        return (price.doubleValue() > 0.0);
    }
}
