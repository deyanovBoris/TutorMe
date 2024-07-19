package bg.softuni.postings.web;

import bg.softuni.postings.entities.dtos.AddPostingDTO;
import bg.softuni.postings.entities.dtos.PostDTO;
import bg.softuni.postings.services.PostingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class PostingController {

    private final PostingService postingService;

    public PostingController(PostingService postingService) {
        this.postingService = postingService;
    }

    @PostMapping("/post/add-post")
    public ResponseEntity<PostDTO> createOffer(
            @RequestBody AddPostingDTO addPostingDTO
    ) {
//        LOGGER.info("Going to create an offer {}", addOfferDTO);

        PostDTO postDTO = postingService.createPost(addPostingDTO);
        return ResponseEntity.
                created(
                        ServletUriComponentsBuilder
                                .fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(postDTO.getId())
                                .toUri()
                ).body(postDTO);
    }
}
