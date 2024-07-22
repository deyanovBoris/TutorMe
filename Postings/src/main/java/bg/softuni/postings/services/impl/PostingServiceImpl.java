package bg.softuni.postings.services.impl;

import bg.softuni.postings.entities.Posting;
import bg.softuni.postings.entities.dtos.AddPostingDTO;
import bg.softuni.postings.entities.dtos.PostDTO;
import bg.softuni.postings.repositories.PostingRepository;
import bg.softuni.postings.services.PostingService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostingServiceImpl implements PostingService {

    private final PostingRepository postingRepository;
    private final ModelMapper modelMapper;
    public PostingServiceImpl(PostingRepository postingRepository, ModelMapper modelMapper) {
        this.postingRepository = postingRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDTO createPost(AddPostingDTO addPostingDTO) {
        Posting posting = new Posting()
                .setUserId(addPostingDTO.getUserId())
                .setPostContent(addPostingDTO.getPostContent())
                .setPostingDate(LocalDateTime.now());

        this.postingRepository.save(posting);
        PostDTO map = modelMapper.map(posting, PostDTO.class);
        return map;
    }

    @Override
    public void deletePost(Long offerId) {

    }

    @Override
    public PostDTO getPostById(Long id) {
        return null;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return this.postingRepository.findAll()
                .stream()
                .map(p -> new PostDTO()
                        .setPostContent(p.getPostContent())
                        .setPostingDate(p.getPostingDate())
                        .setId(p.getId())
                        .setUserId(p.getUserId()))
                .collect(Collectors.toList());
    }
}
