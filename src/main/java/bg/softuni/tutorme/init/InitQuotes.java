package bg.softuni.tutorme.init;

import bg.softuni.tutorme.service.QuoteService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class InitQuotes {

    private final QuoteService quoteService;

    public InitQuotes(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @Bean
    public void initializeQuotes(){
        if (!quoteService.hasInitializedQuotes()){
            this.quoteService.updateQuotes(quoteService.fetchQuotes());
        }
    }
}
