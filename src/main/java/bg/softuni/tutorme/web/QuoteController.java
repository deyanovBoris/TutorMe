package bg.softuni.tutorme.web;

import bg.softuni.tutorme.entities.dtos.quotes.QuoteOutputDTO;
import bg.softuni.tutorme.service.QuoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
public class QuoteController {

    private final QuoteService quoteService;
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("/api/quotes/random")
    public ResponseEntity<QuoteOutputDTO> getRandomQuote(){
        QuoteOutputDTO quoteById = this.quoteService
                .getQuoteById(ThreadLocalRandom.current().nextLong(1, 51));

        return ResponseEntity.ok(quoteById);
    }
}
