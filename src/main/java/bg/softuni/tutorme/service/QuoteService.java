package bg.softuni.tutorme.service;

import bg.softuni.tutorme.entities.dtos.quotes.QuoteDTO;
import bg.softuni.tutorme.entities.dtos.quotes.QuoteOutputDTO;

import java.util.List;

public interface QuoteService {

    boolean hasInitializedQuotes();

    List<QuoteDTO> fetchQuotes();

    void updateQuotes(List<QuoteDTO> quotes);

    QuoteOutputDTO getQuoteById(long id);

    QuoteOutputDTO getRandomQuote();
}
