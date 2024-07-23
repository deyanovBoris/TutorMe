package bg.softuni.tutorme.service.impl;

import bg.softuni.tutorme.entities.UserEntity;
import bg.softuni.tutorme.entities.dtos.AddPostingDTO;
import bg.softuni.tutorme.entities.dtos.PostDetailDTO;
import bg.softuni.tutorme.repositories.UserRepository;
import bg.softuni.tutorme.service.PostingService;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostingServiceImpl implements PostingService {

    private final RestClient postingRestClient;
    private final UserRepository userRepository;

    public PostingServiceImpl(@Qualifier("postingRestClient") RestClient restClient, UserRepository userRepository) {
        this.postingRestClient = restClient;
        this.userRepository = userRepository;
    }

    @Override
    public void createPost(AddPostingDTO addPostingDTO, Principal principal) {

        addPostingDTO.setUserId(this.userRepository.findByUsername(principal.getName()).get().getId());
        postingRestClient
                .post()
                .uri("http://localhost:8081/post/add-post")
                .body(addPostingDTO)
                .retrieve();
    }

    @Override
    public List<PostDetailDTO> getAllPosts(){

        return getAllPostsRest()
                .stream()
                .map(p -> {
                    UserEntity user = this.userRepository.findById(p.getUserId()).get();
                    p.getUser()
                        .setId(p.getUserId())
                        .setUsername(user.getUsername())
                        .setProfilePhotoUrl(user.getProfilePhotoUrl());
                    return p;
                })
                .sorted(Comparator.comparing(PostDetailDTO::getPostingDate).reversed())
                .collect(Collectors.toList());
    }

    private List<PostDetailDTO> getAllPostsRest(){
        return postingRestClient
                .get()
                .uri("http://localhost:8081/posts/all")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }
}
