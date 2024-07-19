package bg.softuni.tutorme.service;

import bg.softuni.tutorme.entities.dtos.AddPostingDTO;

public interface PostingService {

    void createPost(AddPostingDTO addPostingDTO);
}
