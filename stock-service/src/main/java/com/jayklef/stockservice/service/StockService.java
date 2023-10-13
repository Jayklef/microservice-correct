package com.jayklef.stockservice.service;

import com.jayklef.stockservice.dto.StockResponse;
import com.jayklef.stockservice.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    public List<StockResponse> isInStock(List<String> skuCode){
        return stockRepository.findBySkuCodeIn(skuCode).stream()
                .map(stock ->
                    StockResponse.builder()
                            .skuCode(stock.getSkuCode())
                            .isInStock(stock.getQuantity() > 0 )
                            .build()
                ).toList();
    }
}
