package bg.softuni.tutorme.service;

import bg.softuni.tutorme.entities.dtos.AddPostingDTO;
import bg.softuni.tutorme.entities.dtos.PostDetailDTO;
import bg.softuni.tutorme.service.exceptions.UserNotFoundException;

import java.security.Principal;
import java.util.List;

public interface PostingService {

    void createPost(AddPostingDTO addPostingDTO, Principal principal);

    List<PostDetailDTO> getAllPosts();
}
