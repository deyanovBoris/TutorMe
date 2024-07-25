package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.QuoteEntity;
import bg.softuni.tutorme.entities.dtos.quotes.QuoteDTO;
import bg.softuni.tutorme.entities.dtos.quotes.QuoteOutputDTO;
import bg.softuni.tutorme.repositories.QuoteRepository;
import bg.softuni.tutorme.service.QuoteService;
import bg.softuni.tutorme.config.QuoteApiConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final RestClient restClient;
    private final QuoteApiConfig quoteApiConfig;
    private final ModelMapper modelMapper;

    public QuoteServiceImpl(QuoteRepository quoteRepository,
                            @Qualifier("genericRestClient") RestClient restClient, QuoteApiConfig quoteApiConfig, ModelMapper modelMapper) {
        this.quoteRepository = quoteRepository;
        this.restClient = restClient;
        this.quoteApiConfig = quoteApiConfig;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean hasInitializedQuotes() {
        return this.quoteRepository.count() > 0;
    }

    @Override
    public List<QuoteDTO> fetchQuotes() {
        return restClient
                .get()
                .uri(quoteApiConfig.getUrl())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    @Override
    public void updateQuotes(List<QuoteDTO> quotes) {
        List<QuoteEntity> collect = quotes
                .stream()
                .map(q -> new QuoteEntity()
                        .setAuthor(q.getAuthor())
                        .setQuote(q.getContent()))
                .collect(Collectors.toList());

        this.quoteRepository.saveAll(collect);
    }

    @Override
    public QuoteOutputDTO getQuoteById(long id) {
        QuoteEntity quoteEntity = this.quoteRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return this.modelMapper.map(quoteEntity, QuoteOutputDTO.class);
    }

    @Override
    public QuoteOutputDTO getRandomQuote() {
        return restClient
                .get()
                .uri("http://localhost:8080/api/quotes/random")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(QuoteOutputDTO.class);
    }


}
