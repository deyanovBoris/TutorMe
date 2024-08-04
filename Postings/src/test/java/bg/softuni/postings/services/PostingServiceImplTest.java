package bg.softuni.postings.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import bg.softuni.postings.entities.Posting;
import bg.softuni.postings.entities.dtos.AddPostingDTO;
import bg.softuni.postings.entities.dtos.PostDTO;
import bg.softuni.postings.repositories.PostingRepository;
import bg.softuni.postings.services.impl.PostingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class PostingServiceImplTest {

    @Mock
    private PostingRepository mockPostingRepository;
    @Mock
    private ModelMapper mockModelMapper;

    @InjectMocks
    private PostingServiceImpl toTest; // This is your service class containing the createPost method

    @BeforeEach
    public void setUp() {
        toTest = new PostingServiceImpl(mockPostingRepository, mockModelMapper);
    }

    @Test
    public void testCreatePost_Success() {
        // Arrange
        AddPostingDTO addPostingDTO = new AddPostingDTO();
        addPostingDTO.setUserId(1L);
        addPostingDTO.setPostContent("Test Content");

        Posting posting = new Posting();
        posting.setUserId(1L);
        posting.setPostContent("Test Content");
        posting.setPostingDate(LocalDateTime.now());

        PostDTO postDTO = new PostDTO();
        postDTO.setUserId(1L);
        postDTO.setPostContent("Test Content");

        when(mockModelMapper.map(any(Posting.class), any(Class.class))).thenReturn(postDTO);
        when(mockPostingRepository.save(any(Posting.class))).thenReturn(posting);

        // Act
        PostDTO result = toTest.createPost(addPostingDTO);

        // Assert
        assertNotNull(result);
        assertEquals(postDTO, result);
        verify(mockPostingRepository).save(any(Posting.class));
        verify(mockModelMapper).map(any(Posting.class), any(Class.class));
    }
}
