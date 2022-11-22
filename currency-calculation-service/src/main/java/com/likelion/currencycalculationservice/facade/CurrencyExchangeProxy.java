package com.likelion.currencycalculationservice.facade;

import com.likelion.currencycalculationservice.model.CalculatedAmount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//@FeignClient(name ="currency-exchange-service", url ="http://localhost:8000")
@FeignClient(name ="CURRENCY-EXCHANGE-SERVICE") // Định nghĩa tên của module Currency Exchange Service đăng ký tại Eureka Server
public interface CurrencyExchangeProxy {
//    @GetMapping("/currency-exchange-service/currency-exchange/from/{from}/to/{to}")
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    CalculatedAmount retrieveExchangeValue(@PathVariable("from") String from,
                                                  @PathVariable("to") String to);
}