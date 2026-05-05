package com.banking.controller;

import com.banking.model.Transaction;
import com.banking.service.CurrencyService;
import com.banking.service.PdfGeneratorService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;
    private final PdfGeneratorService pdfService; // Adăugat pentru PDF

    public CurrencyController(CurrencyService currencyService, PdfGeneratorService pdfService) {
        this.currencyService = currencyService;
        this.pdfService = pdfService;
    }

    @GetMapping("/rates")
    public Map<String, Object> getRates() {
        Map<String, Object> fullResponse = currencyService.getExchangeRates();
        Map<String, Object> ratesOnly = (Map<String, Object>) fullResponse.get("rates");

        Map<String, String> details = getCurrencyDetails();
        Map<String, Object> finalResponse = new TreeMap<>();

        finalResponse.put("0_TITLU", "### Curs valutar RON (Monede Principale) ###");

        ratesOnly.forEach((code, value) -> {
            if (details.containsKey(code)) {
                finalResponse.put(code, value + " - " + details.get(code));
            }
        });

        return finalResponse;
    }

    // Această metodă lipsea din ultimul tău mesaj!
    @GetMapping("/export-pdf")
    public void generatePDF(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=extras_cont_Toth_David.pdf");

        List<Transaction> testTransactions = Arrays.asList(
                new Transaction("2026-05-01", "Transfer Salariu", 5500.00),
                new Transaction("2026-05-02", "Plata Utilitati", -450.20),
                new Transaction("2026-05-04", "Cumparaturi Supermarket", -120.50),
                new Transaction("2026-05-05", "Transfer catre Economii", -1000.00)
        );

        pdfService.export(response, testTransactions);
    }

    private Map<String, String> getCurrencyDetails() {
        Map<String, String> m = new HashMap<>();
        m.put("RON", "Leu Românesc (România)");
        m.put("EUR", "Euro (Uniunea Europeană)");
        m.put("USD", "Dolar American (Statele Unite)");
        m.put("GBP", "Lira Sterlină (Marea Britanie)");
        m.put("CHF", "Franc Elvețian (Elveția)");
        m.put("HUF", "Forint (Ungaria)");
        m.put("BGN", "Leva (Bulgaria)");
        m.put("MDL", "Leu Moldovenesc (Moldova)");
        m.put("UAH", "Hrivnă (Ucraina)");
        m.put("CAD", "Dolar Canadian (Canada)");
        m.put("AUD", "Dolar Australian (Australia)");
        m.put("JPY", "Yen (Japonia)");
        m.put("TRY", "Liră Turcească (Turcia)");
        m.put("DKK", "Coroană Daneză (Danemarca)");
        m.put("SEK", "Coroană Suedeză (Suedia)");
        m.put("NOK", "Coroană Norvegiană (Norvegia)");
        m.put("CZK", "Coroană Cehă (Cehia)");
        m.put("PLN", "Zlot (Polonia)");
        return m;
    }
}