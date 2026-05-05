package com.banking.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service // Îi spune lui Spring că această clasă este un "muncitor" care face calcule sau aduce date
public class CurrencyService {

    // RestTemplate este unealta Spring care se comportă ca un browser: merge la un URL și ia datele
    private final RestTemplate restTemplate;

    public CurrencyService() {
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> getExchangeRates() {
        String url = "https://api.exchangerate-api.com/v4/latest/RON";

        // getForObject trimite cererea la URL și transformă automat JSON-ul primit într-un Map (dicționar) de Java
        return restTemplate.getForObject(url, Map.class);
    }
}
