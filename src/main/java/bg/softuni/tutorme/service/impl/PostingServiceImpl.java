package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.dtos.AddPostingDTO;
import bg.softuni.tutorme.service.PostingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PostingServiceImpl implements PostingService {

    private final RestClient postingRestClient;

    public PostingServiceImpl(@Qualifier("postingRestClient") RestClient restClient) {
        this.postingRestClient = restClient;
    }

    @Override
    public void createPost(AddPostingDTO addPostingDTO) {
        postingRestClient
                .post()
                .uri("http://localhost:8081/post/add-post")
                .body(addPostingDTO)
                .retrieve();
    }
}
