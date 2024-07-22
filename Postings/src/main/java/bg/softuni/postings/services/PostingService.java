package bg.softuni.postings.services;

import bg.softuni.postings.entities.dtos.AddPostingDTO;
import bg.softuni.postings.entities.dtos.PostDTO;

import java.util.List;

public interface PostingService {

    PostDTO createPost(AddPostingDTO addPostingDTO);

    void deletePost(Long offerId);

    PostDTO getPostById(Long id);

    List<PostDTO> getAllPosts();
}
