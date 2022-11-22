package com.likelion.currencycalculationservice.controller;

import com.likelion.currencycalculationservice.facade.CurrencyExchangeProxy;
import com.likelion.currencycalculationservice.model.CalculatedAmount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
//@RefreshScope
public class CurrencyCalculationController {
    private Logger logger = LoggerFactory.getLogger(CurrencyCalculationController.class);

    @Autowired
    private CurrencyExchangeProxy proxy;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment environment;

    @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
    public CalculatedAmount calculateAmount(@PathVariable String from, @PathVariable String to,
                                            @PathVariable BigDecimal quantity) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        ResponseEntity<CalculatedAmount> responseEntity = restTemplate.getForEntity("http://CURRENCY-EXCHANGE-SERVICE/currency-exchange/from/{from}/to/{to}"
                , CalculatedAmount.class, uriVariables);
        CalculatedAmount calculatedAmount =responseEntity.getBody();
        return new CalculatedAmount(calculatedAmount.getId(), calculatedAmount.getFrom(),
                calculatedAmount.getTo(), calculatedAmount.getConversionMultiple(),
                quantity, quantity.multiply(calculatedAmount.getConversionMultiple()),
                calculatedAmount.getPort());
    }

    @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
    public CalculatedAmount calculateAmountFeign(@PathVariable String from, @PathVariable String to,
                                                 @PathVariable BigDecimal quantity) {
        CalculatedAmount calculatedAmount= proxy.retrieveExchangeValue(from, to);
        logger.info("Port Exchange: " + calculatedAmount.getPort());
        logger.info("Port Calculation: " + environment.getProperty("local.server.port"));
        return new CalculatedAmount(calculatedAmount.getId(), calculatedAmount.getFrom(),
                calculatedAmount.getTo(), calculatedAmount.getConversionMultiple(),
                quantity, quantity.multiply(calculatedAmount.getConversionMultiple()),
                calculatedAmount.getPort());
    }
}
