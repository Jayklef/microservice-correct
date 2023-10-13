package com.jayklef.stockservice.controller;

import com.jayklef.stockservice.dto.StockResponse;
import com.jayklef.stockservice.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<StockResponse> isInStock(@RequestParam List<String> skuCode){
        return stockService.isInStock(skuCode);
    }
}
