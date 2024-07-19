package bg.softuni.postings.services;

import bg.softuni.postings.entities.dtos.AddPostingDTO;
import bg.softuni.postings.entities.dtos.PostDTO;

public interface PostingService {

    PostDTO createPost(AddPostingDTO addPostingDTO);
}
