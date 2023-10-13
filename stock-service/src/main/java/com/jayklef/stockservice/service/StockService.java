package com.jayklef.stockservice.service;

import com.jayklef.stockservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public boolean isInStock(String skuCode){
        return stockRepository.findBySkuCode(skuCode).isPresent();
    }
}
