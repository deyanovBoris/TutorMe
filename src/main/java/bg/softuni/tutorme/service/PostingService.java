package bg.softuni.tutorme.service;

import bg.softuni.tutorme.entities.dtos.posts.AddPostingDTO;
import bg.softuni.tutorme.entities.dtos.posts.PostDetailDTO;

import java.security.Principal;
import java.util.List;

public interface PostingService {

    void createPost(AddPostingDTO addPostingDTO, Principal principal);

    List<PostDetailDTO> getAllPosts();
}
